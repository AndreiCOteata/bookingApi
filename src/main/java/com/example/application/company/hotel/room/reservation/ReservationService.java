package com.example.application.company.hotel.room.reservation;

import com.example.application.account.Account;
import com.example.application.account.dao.AccountRepository;
import com.example.application.account.exception.AccountDisabledException;
import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.common.date.CurrentTimeProvider;
import com.example.application.common.dtos.BookingEmailDto;
import com.example.application.common.email.NotificationService;
import com.example.application.company.dao.CompanyRepository;
import com.example.application.company.hotel.Hotel;
import com.example.application.company.hotel.dao.HotelRepository;
import com.example.application.company.hotel.room.Room;
import com.example.application.company.hotel.room.dao.dto.RoomRequestDto;
import com.example.application.company.hotel.room.reservation.dto.ReservationRequestDto;
import com.example.application.company.hotel.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final AccountRepository accountRepository;
    private final HotelRepository hotelRepository;
    private final RoomService roomService;
    private final NotificationService notificationService;

    private final CurrentTimeProvider timeProvider = new CurrentTimeProvider();

    @Autowired
    public ReservationService(ReservationRepository repository,
                              AccountRepository accountRepository,
                              HotelRepository hotelRepository,
                              RoomService roomService,
                              NotificationService notificationService) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.hotelRepository = hotelRepository;
        this.roomService = roomService;
        this.notificationService = notificationService;
    }

    public List<Reservation> getAllReservationByRoomId(Room room){
        return repository.getAllByRoomId(room);
    }

    public void reserveRooms(ReservationRequestDto requestDto) throws AccountDisabledException, AccountNotFoundException, MessagingException {
        System.out.println(requestDto.toString());
        Account account = accountRepository.getByEmail(requestDto.getEmail());
        Date checkInDate = timeProvider.getDate(requestDto.getCheckIn());
        Date checkOutDate = timeProvider.getDate(requestDto.getCheckOut());
        Hotel hotel = hotelRepository.getByName(requestDto.getHotelName());
        Predicate<Room> filterRoomfromList = each -> {
            String roomType = each.getRoomDescription().getRoom_type().getType().toString();
            for (RoomRequestDto eachRoom: requestDto.getRoomDtoList()) {
                if(eachRoom.getType().equals(roomType))
                    return true;
            }
            return false;
        };
        List<Room> rooms = roomService.gellAllRoomsByHotel(hotel)
                .stream()
                .filter(filterRoomfromList)
                .collect(Collectors.toList());
        long numberOfRooms = 0L;
        for (Room room: rooms) {
            if(requestDto.getDetails() == null){
                requestDto.setDetails("");
            }
            Reservation reservation = new Reservation();
            reservation.setReservationStatus(openReservationStatus(requestDto.getDetails()));
            reservation.setStartDate(checkInDate);
            reservation.setEndDate(checkOutDate);
            reservation.setAccount(account);
            reservation.setRoom(room);
            for (RoomRequestDto each: requestDto.getRoomDtoList()) {
                if(room.getRoomDescription().getRoom_type().getType().toString().equals(each.getType())){
                    numberOfRooms = numberOfRooms + each.getNumberOfRooms();
                    reservation.setNumberOfRooms(each.getNumberOfRooms());
                    reservation.setTotalPrice(each.getNumberOfRooms()*room.getRoomDescription().getPrice());
                }
            }
            repository.save(reservation);
        }
        BookingEmailDto bookingEmailDto = new BookingEmailDto();
        bookingEmailDto.setHotelName(hotel.getName());
        bookingEmailDto.setNumbersOfRooms(numberOfRooms);
        bookingEmailDto.setCheckin(checkInDate);
        bookingEmailDto.setCheckout(checkOutDate);
        bookingEmailDto.setUserEmail(requestDto.getEmail());
        bookingEmailDto.setGuests(requestDto.getGuests());
        bookingEmailDto.setAddress(hotel.getCompany().getCity().getName() + ", " + hotel.getCompany().getCity().getCountry().getName());
        bookingEmailDto.setHotelEmail(hotel.getCompany().getEmail());
        notificationService.sendBookingNotificationHotel(bookingEmailDto);
        notificationService.sendBookingNotificationUser(bookingEmailDto);
    }

    public ReservationStatusEvents openReservationStatus(String details){
        ReservationStatusEvents event = new ReservationStatusEvents();
        event.setDetails(details);
        event.setStatus(ReservationStatus.OPEN);
        return event;
    }
}
