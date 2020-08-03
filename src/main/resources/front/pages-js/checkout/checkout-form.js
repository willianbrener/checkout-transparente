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

CheckoutForm.ChangeQuantity = function(id) {
	if(id){
		
		/*$('#totalProdutos').each(function() {
			var valorTotal = parseFloat($(this).text());
			var precoProduto = parseFloat($('#th-preco-'+ id).val());
			var subtotalProduto = parseFloat($('#th-subtotal-'+ id).val());
			
			
			if ($(this).data('old-value') < $(this).val()) {
		        alert('Alert up');
		    } else {
		        alert('Alert dowm');
		    }
			console.log(valorTotal);
			console.log($('#th-preco-'+ id).val());
			console.log(subtotalProduto);
		});*/
	}
}

$(document).ready(function() {
	
});
