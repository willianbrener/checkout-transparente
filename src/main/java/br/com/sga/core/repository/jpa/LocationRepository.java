package br.com.sga.core.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	public List<Location> findAll(Specification<Location> where);
	
	public Page<Location> findAll(Specification<Location> where, Pageable pageable);
	
}
