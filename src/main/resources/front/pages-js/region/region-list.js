var RegionList = RegionList || {}

RegionList.RemoveRegion = function(elem) {
    if(elem){
		new App.ModalDeleteAjaxFormEstatico.PorElemento(elem, RegionList.PosRemoveRegion).show();
	}
}

RegionList.PosRemoveRegion = function(){
	new App.LoadingAjax($('body')).show();
	window.location.reload(true);
}

$(document).ready(function() {
});
