package br.com.sga.core.model.dominio;

public enum SegundaViaEnum  implements IAppEnum {

	SEGUNDA_VIA	("10", 	"2 Via"),
	NAO_TEM		("0", 	"Não Tem"),
	TICKET		("5", 	"Ticket");
	
	public String value;
	public String descricao;
	
	private SegundaViaEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SegundaViaEnum get(String value) {
		for (SegundaViaEnum itemEnum : SegundaViaEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}