package com.example.demo.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.CountryResponse;
import com.example.demo.entity.Country;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CountryRepository;
import com.example.demo.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Page<CountryResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return countryRepository.findAll(pageable).map(CountryResponse::new);
	}

	@Override
	public CountryResponse createCountry(Country country) {
		Country savedCountry = countryRepository.save(country);
		return new CountryResponse(savedCountry);
	}

	@Override
	public CountryResponse updateCountry(UUID id, Country countryDetails) {
		Country existingCountry = countryRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Quốc gia!", HttpStatus.NOT_FOUND));

		existingCountry.setCode(countryDetails.getCode());
		existingCountry.setName(countryDetails.getName());

		Country updatedCountry = countryRepository.save(existingCountry);
		return new CountryResponse(updatedCountry);
	}

	@Override
	public void deleteCountry(UUID id) {
		countryRepository.deleteById(id);
	}

}