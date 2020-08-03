package br.com.sga.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sga.core.exception.NotFoundException;
import br.com.sga.core.model.ExampleMaskChildren;
import br.com.sga.core.model.ExampleMask;
import br.com.sga.core.repository.jpa.ExampleMaskChildrenRepository;

@Service
public class ExampleMaskChildrenService {
	
	@Autowired
	private ExampleMaskChildrenRepository exampleMaskChildrenRepository;
	
	public ExampleMaskChildren findById(Long id) {
		return exampleMaskChildrenRepository.findById(id).orElseThrow(() -> new NotFoundException("Example Mask Children não encontrada"));
	}
	
	public List<ExampleMaskChildren> findByExampleMask(ExampleMask eMask){
		return exampleMaskChildrenRepository.findByExampleMask(eMask);
	}
	
	public ExampleMaskChildren save(ExampleMaskChildren reListMask) {
		return exampleMaskChildrenRepository.save(reListMask);
	}
	
	public void delete(ExampleMaskChildren reListMask) {
		exampleMaskChildrenRepository.delete(reListMask);
	}
}
