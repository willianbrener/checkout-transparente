var RegionModalList = RegionModalList || {}

RegionModalList.EventoItemSelecionado = function (item) {
	var id = $(item).data('id');
	var name = $(item).data('name');

	ModalPesquisaRegion.itemSelecionado = RegionModalList.CriaItemSelecionado(id, name);

	ModalPesquisaRegion.finalizar();
}

RegionModalList.CriaItemSelecionado = function(id, name){
	return {
		destinos: [
			{
				valor: id,
				destino: '#region-id'
			},
			{
				valor: name,
				destino: '#region-name'
			}
			],
			radio: {
				name: 'input-regions',
				value: id
			}
	};
}

$(function() {
	ModalPesquisaRegion = new App.ModalPesquisa(
			'#region-modal-list',
			RegionModalList.CriaItemSelecionado
	);
});
