package br.com.sga.core.model.dominio;

public enum TipoExportacaoEnum  implements IAppEnum {

	NAO_EXPORTA	("0", 	"Não exporta"),
	DBF			("1", 	"DBF"),
	TEXTO		("2", 	"Texto");
	
	public String value;
	public String descricao;
	
	private TipoExportacaoEnum(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoExportacaoEnum get(String value) {
		for (TipoExportacaoEnum itemEnum : TipoExportacaoEnum.values()) {
			if (itemEnum.getValue().equals(value)) {
				return itemEnum;
			}
		}
		return null;
	}	
}