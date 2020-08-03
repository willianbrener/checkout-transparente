package br.com.sga.core.enumerable;

public enum EnumBancoDebito {

	BRADESCO("bradesco", "Bradesco"),
	ITAU("itau", "Itaú"),
	BANCO_DO_BRASIL("bancodobrasil", "Banco do Brasil"),
	BANRISUL("banrisul", "Banrisul"),
	HSBC("hsbc", "HSBC");

	private final String id;
	private final String descricao;


	private EnumBancoDebito(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public String getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EnumBancoDebito getByDesc(String descricao) {
		if(descricao != null) {
			for (EnumBancoDebito item : EnumBancoDebito.values()) {
				if(item.getDescricao().equals(descricao)) {
					return item;
				}
			}
		}

		return null;
	}

}
