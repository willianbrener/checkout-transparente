package br.com.sga.core.dto;

public class HolderDTO {

	private String cpf;
	private String name;
	private String cardNumber;

	private PhoneDTO phoneDTO = new PhoneDTO();


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhoneDTO getPhoneDTO() {
		return phoneDTO;
	}

	public void setPhoneDTO(PhoneDTO phoneDTO) {
		this.phoneDTO = phoneDTO;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
