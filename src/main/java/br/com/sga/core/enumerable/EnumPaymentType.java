package br.com.sga.core.enumerable;

/**
 * 
 * @author Willian
 *
 */
public enum EnumPaymentType {

	CARTAO_CREDITO(1, "Cartão de crédito", "creditCard"), 
	DEBITO_ONLINE(2, "Débito online", "debito"), 
	BOLETO(3, "Boleto", "boleto");

	private Integer id;
	private String descricao;
	private String valueField;

	EnumPaymentType(Integer id, String descricao, String valueField) {
		this.id = id;
		this.descricao = descricao;
		this.valueField = valueField;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public static EnumPaymentType getById(Integer id) {
		if(id != null) {
			for (EnumPaymentType item : EnumPaymentType.values()) {
				if(item.getId().equals(id)) {
					return item;
				}
			}
		}

		return null;
	}

	public static EnumPaymentType getByValueField(String valueField) {
		if(valueField != null) {
			for (EnumPaymentType item : EnumPaymentType.values()) {
				if(item.getValueField().equals(valueField)) {
					return item;
				}
			}
		}

		return null;
	}
}
