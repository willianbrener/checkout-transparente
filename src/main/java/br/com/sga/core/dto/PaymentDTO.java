package br.com.sga.core.dto;

/**
 * 
 * @author Willian
 *
 */
public class PaymentDTO {

	private String sessionPaymentId;
	private String creditCardToken;
	private String cardBand;
	private String paymentType;
	private String bankSelected;

	private SenderDTO senderDTO = new SenderDTO();
	private CheckoutDTO checkoutDTO;

	private HolderDTO holderDTO = new HolderDTO();

	private String installmentSelected;

	private Double valorTotalProdutos;
	private Integer quantidadeProdutos;


	public PaymentDTO() {
		super();
		this.checkoutDTO = new CheckoutDTO();
		this.paymentType = "creditRadio";
	}

	public PaymentDTO(CheckoutDTO checkoutDTO) {
		super();
		this.checkoutDTO = checkoutDTO;
	}

	public PaymentDTO(CheckoutDTO checkoutDTO, Double valorTotalProdutos, Integer quantidadeProdutos) {
		super();
		this.checkoutDTO = checkoutDTO; 
		this.valorTotalProdutos = valorTotalProdutos; 
		this.quantidadeProdutos = quantidadeProdutos; 
	}

	public String getSessionPaymentId() {
		return sessionPaymentId;
	}

	public void setSessionPaymentId(String sessionPaymentId) {
		this.sessionPaymentId = sessionPaymentId;
	}

	public String getCreditCardToken() {
		return creditCardToken;
	}

	public String getCardBand() {
		return cardBand;
	}

	public void setCardBand(String cardBand) {
		this.cardBand = cardBand;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBankSelected() {
		return bankSelected;
	}

	public void setBankSelected(String bankSelected) {
		this.bankSelected = bankSelected;
	}

	public void setCreditCardToken(String creditCardToken) {
		this.creditCardToken = creditCardToken;
	}

	public SenderDTO getSenderDTO() {
		return senderDTO;
	}

	public void setSenderDTO(SenderDTO senderDTO) {
		this.senderDTO = senderDTO;
	}

	public CheckoutDTO getCheckoutDTO() {
		return checkoutDTO;
	}

	public void setCheckoutDTO(CheckoutDTO checkoutDTO) {
		this.checkoutDTO = checkoutDTO;
	}

	public HolderDTO getHolderDTO() {
		return holderDTO;
	}

	public void setHolderDTO(HolderDTO holderDTO) {
		this.holderDTO = holderDTO;
	}

	public String getInstallmentSelected() {
		return installmentSelected;
	}

	public void setInstallmentSelected(String installmentSelected) {
		this.installmentSelected = installmentSelected;
	}

	public Double getValorTotalProdutos() {
		return valorTotalProdutos;
	}

	public void setValorTotalProdutos(Double valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos;
	}

	public Integer getQuantidadeProdutos() {
		return quantidadeProdutos;
	}

	public void setQuantidadeProdutos(Integer quantidadeProdutos) {
		this.quantidadeProdutos = quantidadeProdutos;
	}

}
