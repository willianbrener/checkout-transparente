var CheckoutPaymentForm = CheckoutPaymentForm || {}

var paymentModule = 'pagseguro_app';


CheckoutPaymentForm.SubmitForm = function() {
	var formSerialized = $("#formPayment").serialize();
	new App.Loading($('body')).show();
	
	if($('#creditRadio').is(':checked')){
		var ccExpiration = $('#cc-expiration').val().split("/");
		
		PagSeguroDirectPayment.createCardToken({
			brand: $('#cardBand').val(),
			cardNumber: $('#cc-number').val(),
			cvv: $('#cc-cvv').val(),
			expirationMonth: ccExpiration[0],
			expirationYear: ccExpiration[1],
			success: function(response) {
				$('#creditCardToken').val(response.card.token);

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

CheckoutPaymentForm.PagSeguroCheckout = function() {
	var senderHash = PagSeguroDirectPayment.getSenderHash();
	
	$('input[name=pagseguro\\[senderHash\\]]').val(senderHash);

	if ($('input[name=pagseguro\\[option\\]]:checked').val() == 'creditCard') {

		if ($('input[name=pagseguro\\[creditCardToken\\]]').val().length == 0) {
			checkoutNoSubmit = true;

			var param = {
					brand: $('input[name=pagseguro\\[brand\\]]').val(),
					cardNumber: $('.pagseguro_cc_card_num').val(),
					cvv: $('.pagseguro_cc_card_code').val(),
					expirationMonth: $('.pagseguro_cc_exp_date_mm').val(),
					expirationYear: $('.pagseguro_cc_exp_date_yy').val(),
					success: function(response) {
						$('input[name=pagseguro\\[creditCardToken\\]]').val(response.card.token);
						checkoutNoSubmit = false;
						$('#onepage_checkoutform').trigger('submit');
					},
					error: function(response) {
						alert('Cartão de crédito inválido. Não conseguimos processar seu pedido.');
						$checkoutButton = $('#onepage_checkoutform').find('button[type=submit]');
						$checkoutButton.removeClass('disabled');
						$checkoutButton.html('Finalizar compra');
					}
			}
			
			PagSeguroDirectPayment.createCardToken(param);
		}
	}
	
}

CheckoutPaymentForm.PagSeguroValidateCard = function(element, bypassLengthTest) {
	$('#creditCardToken').val('');
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
							
							$('#mySelect')
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
						error: function(){
							console.info(card_invalid);
							new App.Loading($('body')).hide();
						}
					});

				}else{
					new App.Loading($('body')).hide();
					console.info(card_invalid);
				}
			},
			error: function(response) {
				console.info(card_invalid);
				new App.Loading($('body')).hide();
			}
		});
	}
}

$(document).ready(function() {

	new App.Loading($('body')).show();
	
	//checkoutCallbacks.add(new CheckoutPaymentForm.PagSeguroCheckout());
	
	$.ajax({
		url: '/checkout/session',
		type: 'GET'
	}).done(function(data) {
		PagSeguroDirectPayment.setSessionId(data);
		$("#sessionPaymentId").val(data);
	}).fail(function(erro) {
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

	$('#cc-number').keyup(function(){
		new CheckoutPaymentForm.PagSeguroValidateCard(this, false);
	});

	$('#cc-number').focusout(function(){
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
	
	$('#areaCode').mask('00');
	$('#phoneNumber').mask('00000-0000');
	
	$('#cc-number').mask('0000 0000 0000 0000');
	$('#cc-cvv').mask('000');
	$('#cc-expiration').mask('00/0000');
	
	//$('.input-numero-cartao').attr("data-inputmask", "'mask': '[9999 9999 9999 9999]'");
	
});
