package br.com.sga.core.repository.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.sga.core.model.Location;

@SuppressWarnings("serial")
public class LocationSpecification {

	public static Specification<Location> whereByStreetName(String streetName) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("streetAddress")), "%" + streetName.trim().toLowerCase() + "%");
			}
		};
	}

	public static Specification<Location> whereByPostalCode(String postalCode) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("postalCode")), "%" + postalCode.trim().toLowerCase() + "%");
			}
		};
	}

	public static Specification<Location> whereByCity(String city) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("city")), "%" + city.trim().toLowerCase() + "%");
			}
		};
	}

	public static Specification<Location> whereByStateProvince(String stateProvince) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(cb.lower(root.<String> get("stateProvince")), "%" + stateProvince.trim().toLowerCase() + "%");
			}
		};
	}

	public static Specification<Location> whereByCountryId(Long idCountry) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Long> get("country").get("id"), idCountry);
			}
		};
	}

	public static Specification<Location> whereByRegionId(Long idRegion) {
		return new Specification<Location>() {

			@Override
			public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Long> get("country").get("region").get("id"), idRegion);
			}
		};
	}
	
}
