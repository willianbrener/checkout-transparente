package br.com.sga.core.model.dominio;

public enum TipoCortadaEnum  implements IAppEnum {

	M		("1", 	"M"),
	C		("2", 	"C"),
	D		("3", 	"D"),
	U		("4", 	"U"),
	G		("5", 	"G"),
	SM		("6", 	"SM");
	
	public String value;
	public String descricao;
	
	private TipoCortadaEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCortadaEnum get(String value) {
		for (TipoCortadaEnum itemEnum : TipoCortadaEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}