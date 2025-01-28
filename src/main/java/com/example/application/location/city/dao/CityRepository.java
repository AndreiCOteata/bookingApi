package com.example.application.location.city.dao;

import com.example.application.location.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Override
    List<City> findAll();

    Optional<City> findCityByNameIgnoreCase(String name);

    default City getByName(String name){
        Optional<City> optionalCity = findCityByNameIgnoreCase(name);
        if (optionalCity.isEmpty()) {
           return null;
        }
        return optionalCity.get();
    }
}
