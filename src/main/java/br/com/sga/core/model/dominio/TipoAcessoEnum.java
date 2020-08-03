package br.com.sga.core.model.dominio;

public enum TipoAcessoEnum  implements IAppEnum {

	TREINAMENTO		("5", 	"Treinamento"),
	PRODUCAO		("100", "Produção");
	
	public String value;
	public String descricao;
	
	private TipoAcessoEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoAcessoEnum get(String value) {
		for (TipoAcessoEnum itemEnum : TipoAcessoEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}