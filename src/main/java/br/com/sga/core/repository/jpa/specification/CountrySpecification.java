package br.com.sga.core.repository.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.sga.core.model.Country;

public class CountrySpecification {

	public static Specification<Country> whereByName(String name) {
		return new Specification<Country>() {

			@Override
			public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("name")), "%" + name.trim().toLowerCase() + "%");
			}
		};
	}

	public static Specification<Country> whereByRegionName(String regionName) {
		return new Specification<Country>() {

			@Override
			public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("region").get("name")), "%" + regionName.trim().toLowerCase() + "%");
			}
		};
	}
	
}
