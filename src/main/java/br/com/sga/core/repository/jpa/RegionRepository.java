package br.com.sga.core.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	
	public Page<Region> findAll(Specification<Region> where, Pageable pageable);
	
}
