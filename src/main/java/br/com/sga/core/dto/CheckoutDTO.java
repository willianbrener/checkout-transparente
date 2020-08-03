package br.com.sga.core.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Willian
 *
 */
public class CheckoutDTO {

	private Double precoTotal;
	private List<ProductDTO> products = new ArrayList<>();

	public CheckoutDTO() {
		super();
	}

	public CheckoutDTO(Double precoTotal, List<ProductDTO> products) {
		super();
		this.precoTotal = precoTotal;
		this.products = products;
	}


	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
