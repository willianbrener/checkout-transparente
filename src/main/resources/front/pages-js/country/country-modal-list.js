var CountryModalList = CountryModalList || {}

CountryModalList.EventoItemSelecionado = function (item) {
	var id = $(item).data('id');
	var name = $(item).data('name');

	ModalPesquisaCountry.itemSelecionado = CountryModalList.CriaItemSelecionado(id, name);

	ModalPesquisaCountry.finalizar();
}

CountryModalList.CriaItemSelecionado = function(id, name){
	return {
		destinos: [
			{
				valor: id,
				destino: '#country-id'
			},
			{
				valor: name,
				destino: '#country-name'
			}
		]
	};
}

$(function() {
	ModalPesquisaCountry = new App.ModalPesquisa(
			'#country-modal-list',
			CountryModalList.CriaItemSelecionado
	);
});
