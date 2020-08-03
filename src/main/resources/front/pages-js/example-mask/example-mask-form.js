var ExampleMaskForm = ExampleMaskForm || {}

ExampleMaskForm.AddChildren = function(elem) {
	new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
}

ExampleMaskForm.CancelEditChildren = function(elem) {
	new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
}

ExampleMaskForm.EditarChildren = function(elem) {
	new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
}

ExampleMaskForm.RemoverChildren = function(elem) {
	new App.RequisicaoFormAjax.PorElemento(elem).requisitar();
}

$(document).ready(function() {
});