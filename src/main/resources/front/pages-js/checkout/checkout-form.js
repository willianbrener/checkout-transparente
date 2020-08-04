var CheckoutForm = CheckoutForm || {}


CheckoutForm.addProductValue = function(elem) {
    if(elem){
		new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
	}
}

CheckoutForm.removeProductValue = function(elem) {
	if(elem){
		new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
	}
}

CheckoutForm.ChangeQuantity = function(elem) {
	if(elem){
		var id = $(elem).attr('id');
		var newVal = $(elem).val();
		var url = $(elem).data('url');
		$(elem).data('url', url+'/'+newVal);
		
		new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
	}
}

$(document).ready(function() {
	
});
