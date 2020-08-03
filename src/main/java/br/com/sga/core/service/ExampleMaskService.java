package br.com.sga.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sga.core.exception.NotFoundException;
import br.com.sga.core.model.ExampleMask;
import br.com.sga.core.repository.jpa.ExampleMaskRepository;
import br.com.sga.core.util.StringUtil;

@Service
public class ExampleMaskService {
	
	@Autowired
	private ExampleMaskRepository exampleMaskRepository;
	
	public Page<ExampleMask> findAll(Pageable pageable){
		
		return exampleMaskRepository.findAll(pageable);
	}
	
	public ExampleMask findById(Long id) {
		return exampleMaskRepository.findById(id).orElseThrow(() -> new NotFoundException("Example Mask não encontrada"));
	}
	
	public ExampleMask save(ExampleMask region) {
		region.setCpf(StringUtil.apenasNumeros(region.getCpf()));
		region.setCnpj(StringUtil.apenasNumeros(region.getCnpj()));
		return exampleMaskRepository.save(region);
	}
	
	public void delete(ExampleMask region) {
		exampleMaskRepository.delete(region);
	}
}
