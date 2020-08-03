package br.com.sga.core.model.dominio;

public enum OpcaoDominio  implements IAppEnum {

	OPCAO_1("1", "Opção de Domínio 1"),
	OPCAO_2("2", "Opção de Domínio 2"),
	OPCAO_3("3", "Opção de Domínio 3"),
	OPCAO_4("4", "Opção de Domínio 4"),
	OPCAO_5("5", "Opção de Domínio 5");
	
	public String value;
	public String descricao;
	
	private OpcaoDominio(String value, String descricao) {
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

	public static OpcaoDominio get(String value) {
		for (OpcaoDominio itemEnum : OpcaoDominio.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}