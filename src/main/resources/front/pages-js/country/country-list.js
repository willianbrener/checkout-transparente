var CountryList = CountryList || {}

CountryList.RemoveCountry = function(elem) {
    if(elem){
		new App.ModalDeleteAjaxFormEstatico.PorElemento(elem, CountryList.PosRemoveCountry).show();
	}
}

CountryList.PosRemoveCountry = function(){
	new App.LoadingAjax($('body')).show();
	window.location.reload(true);
}

$(document).ready(function() {
});
