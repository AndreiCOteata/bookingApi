package com.example.application.company.hotel.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.company.Company;
import com.example.application.company.dao.CompanyRepository;
import com.example.application.company.hotel.Hotel;
import com.example.application.company.hotel.api.HotelsRequestDto;
import com.example.application.company.hotel.api.RoomsResponseDto;
import com.example.application.company.hotel.dao.HotelRepository;
import com.example.application.company.hotel.dao.dto.HotelConverterImpl;
import com.example.application.company.hotel.dao.dto.HotelDto;
import com.example.application.company.hotel.exception.HotelNotFoundException;
import com.example.application.company.hotel.room.Room;
import com.example.application.company.hotel.room.RoomDescription;
import com.example.application.company.hotel.room.reservation.Reservation;
import com.example.application.company.hotel.room.reservation.ReservationService;
import com.example.application.company.hotel.room.service.RoomService;
import com.example.application.location.city.City;
import com.example.application.location.city.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelConverterImpl hotelConverter;
    private final CompanyRepository companyRepository;
    private final CityService cityService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    @Transactional
    public void createHotel(HotelDto hotelDto) {
        Hotel hotel = hotelConverter.createFrom(hotelDto);
        hotelRepository.save(hotel);
    }

    public void updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + id + " not found"));
        hotelConverter.updateEntity(hotel, hotelDto);
        hotelRepository.save(hotel);
    }

    public HotelDto getHotel(String companyName, String name) {
        Company company = companyRepository.findCompanyByName(companyName).orElseThrow(() ->
                new ResourceNotFoundException("Company not found"));

        return hotelRepository.findHotelByCompanyId(company.getId())
                .stream()
                .filter(e -> e.getName().equals(name))
                .map(hotelConverter::createFrom).findAny().orElseThrow(() ->
                        new ResourceNotFoundException("Hotel not found"));


    }

    List<Hotel> getAllByCity(HotelsRequestDto requestDto) throws HotelNotFoundException {
        City city = cityService.getByName(requestDto.getDestination());
        if (city == null) {
            Hotel hotel = hotelRepository.getByName(requestDto.getDestination());
            if(hotel == null) {
                throw new HotelNotFoundException("Hotel not found");
            }
            List<Hotel> hotels = new ArrayList<>();
            hotels.add(hotel);
            return hotels;
        }
        return hotelRepository.findAllByCity(city);
    }

    public List<HotelDto> filterHotelList(HotelsRequestDto requestDto) throws HotelNotFoundException {
        if(requestDto.getChildren() == null){
            requestDto.setChildren(0);
        }
        Predicate<Hotel> isActive = hotel -> hotel.getIsActive() != null && Boolean.TRUE.equals(hotel.getIsActive());
        Predicate<Hotel> hasNumberOfRooms = hotel -> {
            List<Room> rooms = roomService.gellAllRoomsByHotel(hotel);
            if(rooms.isEmpty()){
                return false;
            }
            return !rooms.stream().filter(each -> verifyReservations(
                    each, requestDto.getCheckIn(), requestDto.getCheckOut(),
                    requestDto.getAdults() + requestDto.getChildren())).
                    collect(Collectors.toList()).isEmpty();
        };
        List<Hotel> hotels = getAllByCity(requestDto).stream()
                .filter(isActive)
                .filter(hasNumberOfRooms)
                .collect(Collectors.toList());

        List<HotelDto> hotelDto = new ArrayList<>();
        for (Hotel each: hotels) {
            HotelDto dto = hotelConverter.createFrom(each);
            List<RoomsResponseDto> roomsDto = new ArrayList<>();
            for (Room room: roomService.gellAllRoomsByHotel(each)) {
                RoomsResponseDto responseDto = createFrom(room, generateNumberOfRooms(requestDto.getAdults(),
                        room.getRoomDescription().getRoom_type().getType().getNumberOfPersons()));
                roomsDto.add(responseDto);
            }
            dto.setRooms(roomsDto);
            hotelDto.add(dto);
        }
        return hotelDto;
    }

    private boolean verifyReservations(Room room, Long checkIn, Long checkOut, Integer numberOfGuests){
        RoomDescription roomDescription = room.getRoomDescription();
        Long numberOfRooms = roomDescription.getNumberOfRooms();
        long roomsReserved = 0;
        List<Reservation> reservationList = reservationService.getAllReservationByRoomId(room);
        if(!reservationList.isEmpty()){
            Predicate<Reservation> filterReservationStatus = each -> {
                if(each.getReservationStatus() != null){
                    return each.getReservationStatus().getStatus().toString().equals("OPEN");
                }
                return false;
            };
            Predicate<Reservation> filterCheckInCheckOutDates = each -> each.getEndDate().getTime() < checkIn
                    || each.getStartDate().getTime() > checkOut;

            reservationList = reservationList.stream()
                    .filter(filterReservationStatus)
                    .filter(filterCheckInCheckOutDates)
                    .collect(Collectors.toList());
            for (Reservation each : reservationList) {
                roomsReserved = roomsReserved + each.getNumberOfRooms();
            }
        }
        int numberOfPersonsPerRoomDescription = roomDescription.getRoom_type().getType().getNumberOfPersons();
        return numberOfRooms * numberOfPersonsPerRoomDescription - roomsReserved >= numberOfGuests;
    }

    public RoomsResponseDto createFrom(Room room, Integer numberOfRooms) {
        RoomsResponseDto roomsDto = new RoomsResponseDto();
        roomsDto.setNumberOfRooms(numberOfRooms);
        roomsDto.setType(room.getRoomDescription().getRoom_type().getType().toString());
        return roomsDto;
    }

    public Integer generateNumberOfRooms(Integer numberOfGuests, Integer numberPerRoom){
        if(numberOfGuests%numberPerRoom==0){
            return numberOfGuests/numberPerRoom;
        }else{
            return numberOfGuests+1/numberPerRoom;
        }
    }
}
