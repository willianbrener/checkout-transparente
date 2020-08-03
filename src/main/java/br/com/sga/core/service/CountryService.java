package br.com.sga.core.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sga.core.exception.NotFoundException;
import br.com.sga.core.model.Country;
import br.com.sga.core.repository.jpa.CountryRepository;
import br.com.sga.core.repository.jpa.specification.CountrySpecification;
import br.com.sga.core.util.SpecificationUtil;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepository;
	
	public Page<Country> findAll(Pageable pageable){
		return countryRepository.findAll(pageable);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Country> findAll(Country countryFilter, Pageable pageable){

		Specification<Country> specification = null;
		
		if(StringUtils.isNotEmpty(countryFilter.getName())) {
			specification = SpecificationUtil.addClausulaAnd(specification, CountrySpecification.whereByName(countryFilter.getName()));
		}

		if(countryFilter.getRegion() != null && StringUtils.isNotEmpty(countryFilter.getRegion().getName())) {
			specification = SpecificationUtil.addClausulaAnd(specification, CountrySpecification.whereByRegionName(countryFilter.getRegion().getName()));
		}
		
		return countryRepository.findAll(specification, pageable);
	}
	
	public Country findById(Long id) {
		return countryRepository.findById(id).orElseThrow(() -> new NotFoundException("Country não encontrado"));
	}
	
	public Country save(Country region) {
		return countryRepository.save(region);
	}
	
	public void delete(Country region) {
		countryRepository.delete(region);
	}
}
