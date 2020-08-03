package br.com.sga.core.model.dominio;

public enum TipoDivisaoMenuEnum  implements IAppEnum {

	FORCA_CADA	("2", 	"Força Cada"),
	FORCA_TODAS	("1", 	"Força Todas"),
	MENU		("0", 	"Menu");
	
	public String value;
	public String descricao;
	
	private TipoDivisaoMenuEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoDivisaoMenuEnum get(String value) {
		for (TipoDivisaoMenuEnum itemEnum : TipoDivisaoMenuEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}