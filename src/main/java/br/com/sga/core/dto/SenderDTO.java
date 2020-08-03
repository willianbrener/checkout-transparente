package br.com.sga.core.dto;

/**
 * 
 * @author Willian
 *
 */
public class SenderDTO {
	
	private String cpf;
	private String name;
	private String email;
	private String hash;

	private PhoneDTO phoneDTO = new PhoneDTO();

	private AddressDTO addressDTO = new AddressDTO();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public PhoneDTO getPhoneDTO() {
		return phoneDTO;
	}

	public void setPhoneDTO(PhoneDTO phoneDTO) {
		this.phoneDTO = phoneDTO;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

}
