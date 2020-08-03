package br.com.sga.core.model.dominio;

public enum TipoDigitacaoEnum  implements IAppEnum {

	LOOK		("10", 	"LOOK"),
	SGA			("5", 	"SGA"),
	SGA_LOT		("15", 	"SGA_LOT");
	
	public String value;
	public String descricao;
	
	private TipoDigitacaoEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoDigitacaoEnum get(String value) {
		for (TipoDigitacaoEnum itemEnum : TipoDigitacaoEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}