package br.com.sga.core.dto;

public class PaymentReturnDTO {

	private String url;
	private Boolean paymentFinish;
	private String paymentType;

	public PaymentReturnDTO(String url, Boolean paymentFinish, String paymentType) {
		super();
		this.url = url;
		this.paymentFinish = paymentFinish;
		this.paymentType = paymentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getPaymentFinish() {
		return paymentFinish;
	}

	public void setPaymentFinish(Boolean paymentFinish) {
		this.paymentFinish = paymentFinish;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
