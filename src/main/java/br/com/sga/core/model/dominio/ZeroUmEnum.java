package br.com.sga.core.model.dominio;

public enum ZeroUmEnum  implements IAppEnum {

	ZERO("0", "Não", "Inativo", "Remove"),
	UM("1", "Sim", "Ativo", "Mantém");
	
	public String value;
	public String descricao;
	public String situacao;
	public String remocao;
	
	private ZeroUmEnum(String value, String descricao, String situacao, String remocao) {
		this.value = value;
		this.descricao = descricao;
		this.situacao = situacao;
		this.remocao = remocao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public String getRemocao() {
		return remocao;
	}

	public static ZeroUmEnum get(String value) {
		for (ZeroUmEnum itemEnum : ZeroUmEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}