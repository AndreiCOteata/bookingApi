package com.example.application.company.hotel.dao;

import com.example.application.company.hotel.Hotel;
import com.example.application.location.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findHotelByCompanyId(Long companyId);
    List<Hotel> findAllByCity(City city);
    Optional<Hotel> findByNameIgnoreCase(String name);

    default Hotel getByName(String name){
        Optional<Hotel> optionalHotel = findByNameIgnoreCase(name);
        if (optionalHotel.isEmpty()) {
            return null;
        }
        return optionalHotel.get();
    }
}
