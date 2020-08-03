var EMaskForm = EMaskForm || {}

EMaskForm.AddEListMask = function(elem) {
	new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
}

$(document).ready(function() {
});