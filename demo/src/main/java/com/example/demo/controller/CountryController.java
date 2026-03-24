package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@GetMapping
	public List<Country> getAllCountries() {
		return countryService.getAllCountries();
	}

	@PostMapping
	public Country createCountry(@RequestBody Country country) {
		return countryService.createCountry(country);
	}

	@PutMapping("/{id}")
	public Country updateCoutry(@PathVariable UUID id, @RequestBody Country country) {
		return countryService.updateCountry(id, country);
	}

	@DeleteMapping("/{id}")
	public void deleteCountry(@PathVariable UUID id) {
		countryService.deleteCountry(id);
	}

	@GetMapping("/page")
	public Page<Country> getCountriesPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return countryService.getCountriesWithPagination(page, size);
	}
}
