var LocationList = LocationList || {}

LocationList.ExportarExcel =  function(elem){
    if(elem){
        $(elem).data('nome-documento', 'locations-'+moment().format()+'.xls');
		new App.RequisicaoDownloadArquivo.PorElemento(elem).requisitar();
    }
}

LocationList.ExportarPdf =  function(elem){
    if(elem){
        $(elem).data('nome-documento', 'locations-'+moment().format()+'.pdf');
		new App.RequisicaoDownloadArquivo.PorElemento(elem).requisitar();
    }
}

$(document).ready(function() {
});
