package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.response.CountryResponse;
import com.example.demo.entity.Country;

public interface CountryService {
	Page<CountryResponse> searchByPage(int pageIndex, int pageSize);

	CountryResponse createCountry(Country country);

	CountryResponse updateCountry(UUID id, Country countryDetails);

	void deleteCountry(UUID id);
}