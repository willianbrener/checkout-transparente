package br.com.sga.core.repository.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.sga.core.model.Region;

public class RegionSpecification {

	public static Specification<Region> whereByName(String name) {
		return new Specification<Region>() {

			@Override
			public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("name")), "%" + name.trim().toLowerCase() + "%");
			}
		};
	}
	
}
