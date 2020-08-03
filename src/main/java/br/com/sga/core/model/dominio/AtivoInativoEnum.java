package br.com.sga.core.model.dominio;

public enum AtivoInativoEnum  implements IAppEnum {

	ATIVO("A", "Ativo"),
	INATIVO("I", "Inativo");
	
	public String value;
	public String descricao;
	
	private AtivoInativoEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static AtivoInativoEnum get(String value) {
		for (AtivoInativoEnum itemEnum : AtivoInativoEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}