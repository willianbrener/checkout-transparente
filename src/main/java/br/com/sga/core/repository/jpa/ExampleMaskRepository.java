package br.com.sga.core.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.ExampleMask;

public interface ExampleMaskRepository extends JpaRepository<ExampleMask, Long> {
	
	public Page<ExampleMask> findAll(Pageable pageable);
	
}
