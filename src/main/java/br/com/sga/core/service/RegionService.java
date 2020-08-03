package br.com.sga.core.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sga.core.exception.NotFoundException;
import br.com.sga.core.model.Region;
import br.com.sga.core.repository.jpa.RegionRepository;
import br.com.sga.core.repository.jpa.specification.RegionSpecification;
import br.com.sga.core.util.SpecificationUtil;

@Service
public class RegionService {
	
	@Autowired
	private RegionRepository regionRepository;
	
	public Page<Region> findAll(Pageable pageable){
		return regionRepository.findAll(pageable);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Region> findAll(Region regionFilter, Pageable pageable){

		Specification<Region> specification = null;
		
		if(StringUtils.isNotEmpty(regionFilter.getName())) {
			specification = SpecificationUtil.addClausulaAnd(specification, RegionSpecification.whereByName(regionFilter.getName()));
		}
		
		return regionRepository.findAll(specification, pageable);
	}
	
	public Region findById(Long id) {
		return regionRepository.findById(id).orElseThrow(() -> new NotFoundException("Region não encontrada"));
	}
	
	public Region save(Region region) {
		return regionRepository.save(region);
	}
	
	public void delete(Region region) {
		regionRepository.delete(region);
	}
}
