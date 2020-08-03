package br.com.sga.core.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
	
	public Page<Country> findAll(Specification<Country> where, Pageable pageable);
	
}
