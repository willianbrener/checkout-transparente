package br.com.sga.core.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.ExampleMaskChildren;
import br.com.sga.core.model.ExampleMask;

public interface ExampleMaskChildrenRepository extends JpaRepository<ExampleMaskChildren, Long> {
	
	public Page<ExampleMaskChildren> findAll(Pageable pageable);
	
	public List<ExampleMaskChildren> findByExampleMask(ExampleMask eMask);
	
}
