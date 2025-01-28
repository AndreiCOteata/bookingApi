package com.example.application.location.country.dao;

import com.example.application.location.country.Country;
import com.example.application.location.country.CountryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Override
    List<Country> findAll();

    Optional<Country> findByName(CountryEnum name);
}
