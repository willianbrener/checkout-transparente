package br.com.sga.core.dto;

/**
 * 
 * @author Willian
 *
 */
public class ProductDTO {

	private Integer id;
	private String nomeProduto;
	private String fotoProduto;
	private Double precoProduto;
	private Integer quantidadeProduto;
	private Double precoTotalProduto;

	public ProductDTO() {
		super();
	}

	public ProductDTO(Integer id, String nomeProduto, String fotoProduto, Double precoProduto, Integer quantidadeProduto, Double precoTotalProduto) {
		super();
		this.id = id;
		this.fotoProduto = fotoProduto;
		this.nomeProduto = nomeProduto;
		this.precoProduto = precoProduto;
		this.quantidadeProduto = quantidadeProduto;
		this.precoTotalProduto = precoTotalProduto;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getFotoProduto() {
		return fotoProduto;
	}

	public void setFotoProduto(String fotoProduto) {
		this.fotoProduto = fotoProduto;
	}

	public Double getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(Double precoProduto) {
		this.precoProduto = precoProduto;
	}

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Double getPrecoTotalProduto() {
		return precoTotalProduto;
	}

	public void setPrecoTotalProduto(Double precoTotalProduto) {
		this.precoTotalProduto = precoTotalProduto;
	}
	
}
