var CheckoutPaymentForm = CheckoutPaymentForm || {}

var paymentModule = 'pagseguro_app';


CheckoutPaymentForm.SubmitForm = function() {
	var formSerialized = $("#formPayment").serialize();
	new App.Loading($('body')).show();
	
	if($('#creditRadio').is(':checked')){
		var ccExpiration = $('#cartaoExpiracao').val().split("/");
		
		var cartaoSemEspaco = $('#cartaoNume').val() ? $('#cartaoNume').val().replace(/\s/g,'') : "";
		
		PagSeguroDirectPayment.createCardToken({
			brand: $('#cardBand').val(),
			cardNumber: cartaoSemEspaco,
			cvv: $('#cartaoCv').val(),
			expirationMonth: ccExpiration[0],
			expirationYear: ccExpiration[1],
			success: function(response) {
				$('#cartaoToken').val(response.card.token);

				$('#formPayment').submit();
			},
			error: function(response) {
				new App.Loading($('body')).hide();
				console.info(response);
			}
		});
		
	}else if($('#debitoRadio').is(':checked')){
		$('#formPayment').submit();
	} else if($('#boletoRadio').is(':checked')){
		$('#formPayment').submit();
	}
}

CheckoutPaymentForm.PagSeguroValidateCard = function(element, bypassLengthTest) {
	$('#cartaoToken').val('');
	var cardNum = $(element).val().replace(/[^\d.]/g, '');
	console.info(cardNum);
	var card_invalid = 'Validamos os primeiros 6 números do seu cartão de crédito e está inválido. Por favor esvazie o campo e tente digitar de novo.';

	if (cardNum.length == 6 || (bypassLengthTest && cardNum.length >= 6)) {
		new App.Loading($('body')).show();
		
		PagSeguroDirectPayment.getBrand({
			cardBin: cardNum.substr(0, 6),
			success: function(response) {
				
				if (typeof response.brand.name != 'undefined') {
					$('#cardBand').val(response.brand.name);
					
					var valorTotalProdutos = $('#valorTotalProdutos').val();

					PagSeguroDirectPayment.getInstallments({
						amount: valorTotalProdutos,
						brand: response.brand.name,
						maxInstallmentNoInterest: 12,
						success: function(response1) {
							console.info(response1);
							
							$('#installments')
							.find('option')
							.remove()
							.end()
							.append('<option value="">Selecione a quantidade de parcelas</option>')
							.val('');
							
							$.each(response1.installments[response.brand.name], function(key, value){
								$('#installments').append('<option value="'+value.quantity+'x'+value.installmentAmount.toFixed(2)+'">'+value.quantity+' vezes '+value.installmentAmount.toFixed(2).replace('.', ',')+' (Total: '+value.totalAmount.toFixed(2).replace('.', ',')+') - ' + response.brand.name.toUpperCase() + '</option>');
							});
							
							new App.Loading($('body')).hide();
						},
						error: function(e){
							
							console.info(e);
							new App.Loading($('body')).hide();
						}
					});

				}else{
					new App.Loading($('body')).hide();
					console.info(card_invalid);
				}
			},
			error: function(response) {
				console.info(response);
				new App.Loading($('body')).hide();
			}
		});
	}
}

$(document).ready(function() {

	new App.Loading($('body')).show();
	
	//checkoutCallbacks.add(new CheckoutPaymentForm.PagSeguroCheckout());
	
	$.ajax({
		url: '/checkout/custom-checkout/pagseguro/session',
		type: 'GET'
	}).done(function(data) {
		if(data){
			PagSeguroDirectPayment.setSessionId(data);
			$("#sessionPaymentId").val(data);
			
			PagSeguroDirectPayment.onSenderHashReady(function(response){
				if(response.status == 'error') {
					console.log(response.message);
					return false;
				}
				
				var hash = response.senderHash;
				console.info(hash);
				$("#senderHash").val(hash);
			});
		}
	}).fail(function(erro) {
		console.info(erro);
		try{
			var msg = new App.ExtrairErroAjax(erro).extrair();
			App.AlertaErro('Não foi possível continuar', msg);
		}catch(e){
			console.error(e);
			return;
		}
	}).always(function() {
		new App.Loading($('body')).hide();
	});

	$('#cartaoNume').keyup(function(){
		new CheckoutPaymentForm.PagSeguroValidateCard(this, false);
	});

	$('#cartaoNume').focusout(function(){
		new CheckoutPaymentForm.PagSeguroValidateCard(this, true);
	});

	$('input.pagseguro_radio').change(function(){
		$('.pagseguro-form').hide();
		$('.pagseguro-' + $('input.pagseguro_radio:checked').val() ).show();
	});
	
	$('#continuar-pagamento').click(function(){
		new CheckoutPaymentForm.SubmitForm();
	});
	
	$('#zip').mask('00.000-000');
	$('#cpf').mask('000.000.000-00');
	$('#birthDate').mask('00/00/0000');
	
	$('#areaCode').mask('00');
	$('#phoneNumber').mask('00000-0000');
	
	$('#cartaoNume').mask('0000 0000 0000 0000');
	$('#cartaoCv').mask('000');
	$('#cartaoExpiracao').mask('00/0000');
	
	//$('.input-numero-cartao').attr("data-inputmask", "'mask': '[9999 9999 9999 9999]'");
	
});
