package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Country;
import com.example.demo.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	public Country createCountry(Country country) {
		return countryRepository.save(country);
	}

	public Country updateCountry(UUID id, Country countryDetails) {
		Optional<Country> optionalCountry = countryRepository.findById(id);
		if (optionalCountry.isPresent()) {
			Country existingCountry = optionalCountry.get();
			existingCountry.setCode(countryDetails.getCode());
			existingCountry.setName(countryDetails.getName());
			return countryRepository.save(existingCountry);
		}
		return null;
	}

	public void deleteCountry(UUID id) {
		countryRepository.deleteById(id);
	}

	public Page<Country> getCountriesWithPagination(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		return countryRepository.findAll(pageable);
	}

}
