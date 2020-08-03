var App = App || {}

App.DateRangePicker = (function() {

	function DateRangePicker() {
		this.pattern = 'DD/MM/YYYY';
		this.rangepiker = $('.js-daterangepiker');
		this.inputFrom = $(".js-daterangepiker-input-from");
		this.inputTo = $(".js-daterangepiker-input-to");
	}

	DateRangePicker.prototype.enable = function() {
		if (typeof ($.fn.daterangepicker) === 'undefined') {
			console
					.log('HumanResource.DateRangePicker dependency not loaded! $.fn.daterangepicker not found.');
			return;
		}

		this.rangepiker.bind("change", onChange.bind(this));

		this.rangepiker.daterangepicker({
			autoUpdateInput : false,
			autoApply : true,
			locale : {
				format : this.pattern
			}
		}, onApply.bind(this));
	}

	function onApply(start, end, label) {

		this.inputFrom.prop("value", start.format(this.pattern));
		this.inputTo.prop("value", end.format(this.pattern));

		this.rangepiker.val(start.format(this.pattern) + ' - '
				+ end.format(this.pattern));
	}

	function onChange() {

		if (this.rangepiker.val() == '') {
			this.inputFrom.prop('value', '');
			this.inputTo.prop('value', '');
		}
	}

	return DateRangePicker;

}());

App.DatePicker = (function() {

	function DatePicker(elem) {
		this.pattern = 'DD/MM/YYYY';

		if(elem){
			this.datepicker = elem.find(".js-datepicker:not([readonly])");
			this.datepickerLimiteDataAtual = elem.find('.js-datepicker-limite-data-atual:not([readonly])');
		} else {
			this.datepicker = $(".js-datepicker:not([readonly])");
			this.datepickerLimiteDataAtual = $('.js-datepicker-limite-data-atual:not([readonly])');
		}
		
	}

	DatePicker.prototype.enable = function() {

		if (typeof ($.fn.daterangepicker) === 'undefined') {
			console
					.log('HumanResource.DatePicker dependency not loaded! $.fn.daterangepicker not found.');
			return;
		}

		this.datepicker.daterangepicker({
			singleDatePicker : true,
			autoUpdateInput : false,
			locale : {
				format : this.pattern,
				cancelLabel : 'Clear'
			}
		});

		this.datepicker.on('hide.daterangepicker', onHide.bind(this));

		this.datepickerLimiteDataAtual.daterangepicker({
			singleDatePicker : true,
			autoUpdateInput : false,
			"maxDate" : new Date(),
			locale : {
				format : this.pattern,
				cancelLabel : 'Clear'
			}
		});

		this.datepickerLimiteDataAtual.on('hide.daterangepicker', onHide
				.bind(this));
	}

	function onHide(event, picker) {
		var input = $(event.currentTarget);
		input.val(picker.startDate.format(picker.locale.format));
		//chamada ao fechar o datepicker;
	}

	return DatePicker;

}());

App.ModalPesquisa = (function() {

	// Dados que estão em atributos do tipo data-CHAVE=VALOR
	const DATA_MODAL_PESQUISA_RESULT = 'result';
	const DATA_MODAL_PESQUISA_URL = 'url';
	const DATA_MODAL_PESQUISA_URL_SORT = 'urlSort';
	const DATA_MODAL_PESQUISA_URL_QUERY_PARAMS = 'urlQueryParams';

	function Modal(elementoModal, funcaoCriarItemSelecionado, callbackPosPesquisa) {
		this.modalPesquisa = $(elementoModal);
		this.funcaoCriarItemSelecionado = funcaoCriarItemSelecionado;
		this.callbackPosPesquisa = callbackPosPesquisa;

		this.itemSelecionado = {};
		this.pagina = 1;

		this.criarItemSelecionado();
	}

	Modal.prototype.criarItemSelecionado = function(){
		if(this.funcaoCriarItemSelecionado){
			this.itemSelecionado = this.funcaoCriarItemSelecionado();

			if(this.itemSelecionado.destinos && this.itemSelecionado.destinos.length > 0){
				for (var i=0; i < this.itemSelecionado.destinos.length; i++) {
			    var valorDestino = $(this.itemSelecionado.destinos[i].destino).val();
			    if(valorDestino){
			      $(this.itemSelecionado.destinos[i].valor).val(valorDestino).change();
			    }
			  }
			}
		}
	}

	Modal.prototype.show = function(options){
		if(typeof ($.fn.modal) === 'undefined'){ console.log('Modal dependency not loaded'); return; }

		this.limpar();

		this.itemSelecionado = {};

		this.setDestinoComSufixo(options);

		this.modalPesquisa.modal('show');

		this.pesquisar();
	}

	Modal.prototype.submit = function(){

		this.pagina = 1;

		this.pesquisar();
	}

	Modal.prototype.pesquisar = function(){
		var url = this.modalPesquisa.data(DATA_MODAL_PESQUISA_URL);

		url += '?page='+this.pagina;

		var sort = this.modalPesquisa.data(DATA_MODAL_PESQUISA_URL_SORT);
		if(sort){
			url += '&sort='+sort;
		}

		// var queryParams = this.modalPesquisa.data(DATA_MODAL_PESQUISA_URL_QUERY_PARAMS);

		// if(queryParams && queryParams.length > 0){
		// 	for (queryParam in queryParams) {
		// 		var chavesValores = queryParams[queryParam].split("=");
		// 		var chave = (chavesValores && chavesValores.length == 2) ? chavesValores[0] : '';
		// 		var valor = (chavesValores && chavesValores.length == 2) ? this.modalPesquisa.find(chavesValores[1]).val() : '';

		// 		if(chave && valor){
		// 			url += '&'+chave+'='+valor;
		// 		}
		// 	}
		// }

		url += '&'+this.modalPesquisa.find('form').serialize();

		(function(elemResult, recarregaEventoSelecao, itemSelecionado, callbackPosPesquisa) {
			// elemResult.LoadingOverlay("show", {fade  : 250});
			new App.Loading(elemResult).show();
			$.ajax({
				url: url
			})
			.done(function(data) {
				try{
					elemResult.html(data);

					new App.ComponentesBasicos().tooltips();
					// new App.ComponentesBasicos().iCheck();

					if(callbackPosPesquisa && Array.isArray(callbackPosPesquisa))
					for(var i=0; i<callbackPosPesquisa.length; i++)
					callbackPosPesquisa[i]();

					if(callbackPosPesquisa && !Array.isArray(callbackPosPesquisa))
					callbackPosPesquisa();
				}catch(e){
					console.error(e);
					return;
				}
			})
			.fail(function(erro) {
				try{
					var msg = new App.ExtrairErroAjax(erro).extrair();
					App.AlertaErro('Não foi possível continuar', msg);
				}catch(e){
					console.error(e);
					return;
				}
			})
			.always(function() {
				// elemResult.LoadingOverlay("hide", {fade  : 250});;
				new App.Loading(elemResult).hide();
			});
		}(this.modalPesquisa.find(this.modalPesquisa.data(DATA_MODAL_PESQUISA_RESULT)), this.recarregaEventoSelecao, this.itemSelecionado, this.callbackPosPesquisa));

	}

	Modal.prototype.paginar = function(pagina){
		this.pagina = pagina;

		this.pesquisar();
	}

	Modal.prototype.finalizar = function(){
		if(this.itemSelecionado && this.itemSelecionado.destinos && this.itemSelecionado.destinos.length > 0){

			for (var i=0; i < this.itemSelecionado.destinos.length; i++) {
				var destino = this.itemSelecionado.destinos[i].destino;
				if(this.destinosComSufixo && this.destinosComSufixo.destinos && this.destinosComSufixo.destinos.length > 0){
					destino = this.destinosComSufixo.destinos[i].destino;
				}
				//Necessário setar duas vezes, pois alguns campos não pegam o evento change
				$(destino).val(this.itemSelecionado.destinos[i].valor).change();
				$(destino).attr('value', this.itemSelecionado.destinos[i].valor);
				$(destino).change();
				$(destino).attr('value', this.itemSelecionado.destinos[i].valor);
			}

			this.fechar();
		}else{
			App.AlertaErro('Por favor, selecione um item para continuar.', '');
		}

	}

	Modal.prototype.limparDestinos = function(options){

		this.setDestinoComSufixo(options);

		if(this.destinosComSufixo && this.destinosComSufixo.destinos.length > 0){
			for (var i=0; i < this.itemSelecionado.destinos.length; i++) {
				$(this.destinosComSufixo.destinos[i].destino).val('').change();
			}
		}else{
			if(this.itemSelecionado && this.itemSelecionado.destinos && this.itemSelecionado.destinos.length > 0){
				for (var i=0; i < this.itemSelecionado.destinos.length; i++) {
					$(this.itemSelecionado.destinos[i].destino).val('').change();
				}
			}
		}
	}

	Modal.prototype.fechar = function(){
		new App.ComponentesBasicos().tooltips();
		this.limpar();
		this.modalPesquisa.modal('hide');
	}

	Modal.prototype.limpar = function(){
		this.sobrescrita = null;

		this.pagina = 1;

		this.modalPesquisa.find(this.modalPesquisa.data(DATA_MODAL_PESQUISA_RESULT)).html('');

		var queryParams = this.modalPesquisa.data(DATA_MODAL_PESQUISA_URL_QUERY_PARAMS);

		if(queryParams && queryParams.length > 0){
			for (queryParam in queryParams) {
				var chavesValores = queryParams[queryParam].split("=");
				if(!this.modalPesquisa.find(chavesValores[1]).is( ".bootstrap-toggle" )){
					this.modalPesquisa.find(chavesValores[1]).val('');
				}else{
					var checked = this.modalPesquisa.find(chavesValores[1]).data('checked');
					this.modalPesquisa.find(chavesValores[1]).prop('checked', checked).change();
				}
			}
		}
	}

	Modal.prototype.setDestinoComSufixo = function(options){
		if(options && options.sufixoDestino){
			var destinosComSufixo = this.funcaoCriarItemSelecionado();
			for (var i = 0; i < destinosComSufixo.destinos.length; i++) {
				destinosComSufixo.destinos[i].destino = destinosComSufixo.destinos[i].destino + '-' + options.sufixoDestino;
			}
			this.destinosComSufixo = destinosComSufixo;
		} else {
			this.destinosComSufixo = null;
		}
	}

	return Modal;

}());

App.ModalEndereco = (function() {

	function ModalEndereco() {
		this.modalEndereco = $('#modalCadastroEndereco');
	}

	ModalEndereco.prototype.enable = function() {
		this.modalEndereco.on('show.bs.modal', function(event) {
			var slcPais = $('#pais').val();
			if (slcPais == '') {
				alert("Selecionar primeiro o País.");
				$("#modalCadastroEndereco").modal('hide');
				return;
			}
			var button = $(event.relatedTarget);
			var actionUrl = button.data('action-url');
			var recordId = button.data('record-id');
			var recordName = button.data('record-name');
			var xmodal = $(this);

			// modal.find('.js-btn-modal').attr("onclick",
			// "location.href='" + actionUrl + "/" + recordId + "'");
			// modal.find('.modal-body').text(
			// "Are you sure to delete '" + recordName + "'?")
		});
	}

	return ModalEndereco;

}());

App.ModalDelete = (function() {

	function ModalDelete() {
		this.modalDelete = $('#modalDeleteConfirmation');
	}

	ModalDelete.prototype.enable = function() {

		this.modalDelete.on('show.bs.modal', function(event) {

			var button = $(event.relatedTarget);
			var actionUrl = button.data('action-url');
			var recordId = button.data('record-id');
			var recordName = button.data('record-name');
			var modal = $(this);
			modal.find('.js-btn-modal').attr("onclick",
					"location.href='" + actionUrl + "/" + recordId + "'");
			modal.find('.modal-body').text(
					"Você tem certeza que quer deletar  " + recordName + " ?")
		});
	}

	return ModalDelete;

}());

App.ModalConfirm = (function() {

	function ModalConfirm() {
		this.modalConfirm = $('#modalConfirm');
	}

	ModalConfirm.prototype.enable = function() {

		this.modalConfirm.on('show.bs.modal', function(event) {

			var button = $(event.relatedTarget);
			var actionUrl = button.data('action-url');
			var recordId = button.data('record-id');
			var recordName = button.data('record-name');
			var modal = $(this);
			if(actionUrl != undefined && recordId != undefined){
				modal.find('.js-btn-modal').attr("onclick",
					"location.href='" + actionUrl + "/" + recordId + "'");
			}else if(actionUrl != undefined  && recordId === undefined){
				modal.find('.js-btn-modal').attr("onclick",
						"location.href='" + actionUrl + "'");
			}else{ //se nao existir url e id o tipo da chamada é submit (lembrando que o include da modal precisa estar dentro do form p/ funcionar)
				modal.find('.js-btn-modal').attr("type",
				"submit");
			}
			modal.find('.modal-body').text(recordName)
		});
	}

	return ModalConfirm;

}());

App.AplicarICheck = (function() {
	
	function AplicarICheck() {
		this.inputICheck();
	}
	
	AplicarICheck.prototype.inputICheck = function(){
		
		$("input[type='checkbox']").iCheck({
		     checkboxClass: 'icheckbox_flat-orange',
		     labelHover: false,
		     cursor: true
		});
		$("input[type='radio']").iCheck({
		     radioClass: 'iradio_flat-orange',
		     labelHover: true,
		     cursor: true
		});

	}
	return AplicarICheck;
}());

App.AplicarMascaras = (function() {

	function AplicarMascaras() {

		this.inputMasks();

		this.autoNumeric();

		this.monetarios();
	}

	AplicarMascaras.prototype.inputMasks = function() {
		// mascaras de preenchimento completo
		$('.input-number').mask('#').bind('paste', function() {
			$(this).val('');
		});
		
		$('.input-codigo').mask('9999').bind('paste', function() {
			$(this).val('');
		});

		$('.input-date').attr("data-inputmask", "'mask': '[99/99/9999]'");
		$('.apenas-numerico').mask('0#', {
			maxlength : false
		});

		$('.input-date-time').attr("data-inputmask", "'mask': '[99/99/9999 99:99:99]'");
		$('.input-cnpj').attr("data-inputmask", "'mask': '[99.999.999/9999-99]'");
		$('.input-cpf').attr("data-inputmask", "'mask': '[999.999.999-99]'");
		$('.input-cep').attr("data-inputmask", "'mask': '[99999-999]'");
		$(".input-cep").inputmask({
			autoUnmask : true
		});
		

		$('.input-hora').attr("data-inputmask", "'mask': '[99:99]'");
		$('.input-date-mes-ano').attr("data-inputmask", "'mask': '[99/9999']");
		$('.input-date-range').attr("data-inputmask",
				"'mask': '[99/99/9999 - 99/99/9999]'");

		// mascaras de preenchimento parcial
		$('.input-cnpj-base').mask('99.999.999').bind('paste', function() {
			$(this).val('');
		});
		$('.input-numero-auto').mask('9999999999999').bind('paste', function() {
			$(this).val('');
		});
		$('.input-year').mask('9999').bind('paste', function() {
			$(this).val('');
		});
		
		$('.input-area').maskMoney({decimal:",",thousands:".",numeralMaxLength: true});
		
		$('.input-placa-veiculo').attr("data-inputmask", "'mask': 'AAA-9*99'");

		// força renderização
		$(":input").inputmask();

        $('.input-hora').clockpicker({
            autoclose: true
        });

		$(".input-integer").TouchSpin({
			buttondown_class: "btn btn-default",
			buttonup_class: "btn btn-default",
			verticalbuttons: true,
			verticalup: '<i class="fas fa-plus fa-xs"></i>',
			verticaldown: '<i class="fas fa-minus fa-xs"></i>',
		});

		$('.input-date').datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			format: 'dd/mm/yyyy',
			todayHighlight: true,
			autoclose: true
		});

		$('.input-date-max-today').datepicker('remove');

		$('.input-date-max-today').datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			format: 'dd/mm/yyyy',
			todayHighlight: true,
			endDate : '0d',
			autoclose: true
		});

		$('.input-date-mes-ano').datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			format: 'mm/yyyy',
			todayHighlight: true,
			autoclose: true,
			minViewMode: 1
		});

		$('.input-date-time').daterangepicker({
            "timePicker": true,
            "timePicker24Hour": true,
            "timePickerSeconds": true,
            "singleDatePicker": true,
            "autoUpdateInput": false,
            "showDropdowns": true,
            "alwaysShowCalendars": true,
            "applyClass": "btn-orange",
            "locale": {
                "format": "DD/MM/YYYY HH:mm:ss",
                "applyLabel": "Aplicar",
                "cancelLabel": "Cancelar",
                "daysOfWeek": ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
                "monthNames": ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
                ],
                "firstDay": 1
            }
        });

        $('.input-date-time').on('apply.daterangepicker', function (ev, picker) {
            $(this).val(picker.startDate.format('DD/MM/YYYY HH:mm:ss')).change();
        });

        $('.input-date-time').on('cancel.daterangepicker', function (ev, picker) {
            $(this).val('').change();
        });

		$('.input-date-range').daterangepicker({
			"ranges": {
				'Hoje': [moment(), moment()],
				'Ontem': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
				'Últimos 7 Dias': [moment().subtract(6, 'days'), moment()],
				'Últimos 30 Dias': [moment().subtract(29, 'days'), moment()],
				'Esse Mês': [moment().startOf('month'), moment().endOf('month')],
				'Último Mês': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			},
			"locale": {
				"format": "DD/MM/YYYY",
				"separator": " - ",
				"applyLabel": "Aplicar",
				"cancelLabel": "Cancelar",
				"fromLabel": "De",
				"toLabel": "Para",
				"customRangeLabel": "Personalizado",
				"weekLabel": "S",
				"daysOfWeek": ["Dom", "Seg", "Ter", "Qui", "Qua", "Sex", "Sab"],
				"monthNames": ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
				],
				"firstDay": 1
			},
			"alwaysShowCalendars": true,
			"autoUpdateInput": false,
			"applyClass": "btn-orange",
			"opens": "left"
		});

		$('.input-date-range').on('apply.daterangepicker', function (ev, picker) {
			$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY')).change();
		});

		$('.input-date-range').on('cancel.daterangepicker', function (ev, picker) {
			$(this).val('').change();
		});

		$('.percentual').mask('P', {
			translation : {
				'P' : {
					pattern : /[\d\.,]/,
					recursive : true
				}
			},
			onKeyPress : function(val, e, field, options) {
				var old_value = $(field).data('oldValue') || '';

				val = val.trim();
				val = val.replace(',', '.');
				val = val.length > 0 ? val : '0';

				// Transformando múltiplos pontos em um único ponto
				val = val.replace(/[\.]+/, '.');

				// Verificando se o valor contém mais de uma ocorrência de ponto
				var dot_occurrences = (val.match(/\./g) || []).length > 1;

				// Verificando se o valor está de acordo com a sintaxe do float
				var is_float = /[-+]?[\d]*\.?[\d]+/.test(val);

				if (dot_occurrences || !is_float) {
					val = old_value;
				}

				// Força o valor a ficar no intervalo de 0 à 100
				val = parseFloat(val) >= 100 ? '100' : val;
				val = parseFloat(val) < 0 ? '0' : val;

				$(field).val(val).data('oldValue', val);
			}
		});

		$('.percentual').on('keyup', function(event) {
			var target = event.target;
			
			var valorCampoAtual = document.getElementById(target.id).value ;
			if (valorCampoAtual.length > 1){
				if (valorCampoAtual.substring(0,1) === '0'){
					document.getElementById(target.id).value = valorCampoAtual.substring(1,11);
				}
			}
			
			if(valorCampoAtual.length > 10){
				document.getElementById(target.id).value = valorCampoAtual.substring(0,11);
			}	
		});

		//função para campos somente numeros. Apenas incluir o class 'somente-numero' no input desejado
		$(document).on('keypress', 'input.somente-numero', function (e) {
			var key = (window.event) ? event.keyCode : e.which;
			if ((key > 47 && key < 58)) {
				return true;

			} else {
				return (key == 8 || key == 0) ? true : false;

			}
		});

		$(".numero-protocolo-duplo").keyup(function(e){
			var code = (e.keyCode || e.which);

			if(code == 37 || code == 38 || code == 39 || code == 40) {
				return;
			}
			
			if($(e.target).val().replace(/\D/g,'').length != 8){
				$(e.target).inputmask({mask: "9999999999", keepStatic: true});
			} else {
				$(e.target).inputmask({mask: ["9999-9999", "9999999999",], keepStatic: true});
			}
		});

		$(".numero-protocolo-duplo").each(function () {
			if($(this).val().replace(/\D/g,'').length != 8){
				$(this).inputmask({mask: "9999999999", keepStatic: true});
			} else {
				$(this).inputmask({mask: ["9999-9999", "9999999999",], keepStatic: true});
			}
		});

	}

	AplicarMascaras.prototype.autoNumeric = function(){
		/*
		const
		autoNumericCurrency = {
			digitGroupSeparator : '.',
			decimalCharacter : ',',
			maximumValue : '999999999999'
		};
		*/
		
		const
		autoTotalPercentagem = {
			digitGroupSeparator : '.',
			decimalCharacter : ',',
			maximumValue : '100000',
			minimumValue : '0',
			decimalPlaces : 8,	
		};
		
		const
		autoNumeric = {
			digitGroupSeparator : AutoNumeric.options.digitGroupSeparator.noSeparator,
			decimalPlaces : 0
		};

		/*
		for (var i = 0; i < $('.js-currency').length; i++) {
			var element = $('.js-currency')[i];
			new AutoNumeric(element, autoNumericCurrency);
		}
		*/
		for (var i = 0; i < $('.js-numeric').length; i++) {
			var element = $('.js-numeric')[i];
			new AutoNumeric(element, autoNumeric);
		}
	
		for (var i = 0; i < $('.js-total-percentagem-partilha').length; i++) {
			var element = $('.js-total-percentagem-partilha')[i];
			new AutoNumeric(element, autoTotalPercentagem);
		}
	}

	AplicarMascaras.prototype.monetarios = function() {
		var countMascaraReal = $(".mascara-real").length;
		$('.mascara-real').each(function( i ) {
			$(this).attr('data-affixes-stay', 'true');
			$(this).attr('data-prefix', 'R$ ');
			$(this).attr('data-thousands', '.');
			$(this).attr('data-decimal', ',');

			if (i+1 === countMascaraReal) {
				$('.mascara-real').maskMoney();
			}
		});

		$('.campoMonetario').maskMoney({decimal:",",thousands:"."});
	}

	return AplicarMascaras;
}());

App.RequisicaoFormAjax = (function(){

	function RequisicaoFormAjax(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros, paramCallbacks){
		this.idForm = idForm;
		this.idButton = idButton;
		this.idContent = idContent;
		this.idContentSpinner = (idContentSpinner) ? idContentSpinner : idContent;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
		this.paramCallbacks = paramCallbacks;
	}

	RequisicaoFormAjax.PorElemento = function(elem, callbacks, callbacksErros, paramCallbacks){
		var idForm = '#'+$(elem).closest('form').attr('id');
		var idButton = '#'+$(elem).attr('id');
		var idContent = '#'+$(elem).data('id-content');
		var idContentSpinner = undefined;
		if($(elem).data('id-content-spinner')){
			idContentSpinner = '#'+$(elem).data('id-content-spinner');
		}
		return new App.RequisicaoFormAjax(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros, paramCallbacks);
	}

	RequisicaoFormAjax.prototype.requisitar = function(){

		if(!this.idForm || !this.idContent || !this.idButton){
			App.AlertaErro('Não é possível realizar a operação.', '');
		}

		var form = $(this.idForm);
		var button = $(this.idButton);
		var content = $(this.idContent);
		var contentSpinner = $(this.idContentSpinner);

		var url = "";
		if($(button).data("chosen")){
			url = $(button).chosen().find("option:selected").data('url');
		}
		if(!url){
			url = button.data('url');
		}

		(function(content, contentSpinner, callbacks, callbacksErros, paramCallbacks) {
			new App.Loading(contentSpinner).show();

			$.ajax({
				type: 'POST',
				url: url,
				data: form.serialize()
			})
			.done(function(data) {
				try{
					content.html(data);
					//pós-carregamento
					new App.AplicarMascaras();

					new App.ComponentesBasicos().chosen(content.attr('id'));

					//new App.ComponentesBasicos().iCheck(content.attr('id'));

					new App.ComponentesBasicos().filestyleElement(content.find(':file'));

					new App.ComponentesBasicos().tooltips();

					new App.ComponentesBasicos().popovers();

					new App.AlternaAbas().enable();

					new App.DatePicker().enable();
					
					new App.LoadingAjaxOnTag('a', true).enable();
					new App.LoadingAjaxOnTag('.button-loading-body', false).enable();

					if(callbacks && Array.isArray(callbacks))
						for(var i=0; i<callbacks.length; i++)
							if(typeof callbacks[i] === "function")
								callbacks[i](paramCallbacks);

					if(callbacks && !Array.isArray(callbacks))
						callbacks(paramCallbacks);
				}catch(e){
					console.error(e);
					return;
				}
			})
			.fail(function(erro) {
				try{
					var msg = new App.ExtrairErroAjax(erro).extrair();
					App.AlertaErro('Não foi possível continuar', msg);

					if(callbacksErros && Array.isArray(callbacksErros))
						for(var i=0; i<callbacksErros.length; i++)
							callbacksErros[i](paramCallbacks);

					if(callbacksErros && !Array.isArray(callbacksErros))
						callbacksErros(paramCallbacks);
				}catch(e){
					console.error(e);
					return;
				}
			})
			.always(function() {
				new App.Loading(contentSpinner).hide();
			});

		}(content, contentSpinner, this.callbacks, this.callbacksErros, this.paramCallbacks));

	}

	return RequisicaoFormAjax;
}());

App.RequisicaoUploadArquivo = (function(){

	function RequisicaoUploadArquivo(idForm, idArquivo, idContent, idContentSpinner, callbacks, callbacksErros, paramCallbacks){
		this.idForm = idForm;
		this.idArquivo = idArquivo;
		this.idContent = idContent;
		this.idContentSpinner = idContentSpinner;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
		this.paramCallbacks = paramCallbacks;
	}

	RequisicaoUploadArquivo.PorElemento = function(elem, callbacks, callbacksErros, paramCallbacks){
		var idForm = '#'+$(elem).closest('form').attr('id');
		var idArquivo = '#'+$(elem).data('id-arquivo');
		var idContent = '#'+$(elem).data('id-content');
		var idContentSpinner = '#'+$(elem).data('id-content-spinner');
		return new App.RequisicaoUploadArquivo(idForm, idArquivo, idContent, idContentSpinner, callbacks, callbacksErros, paramCallbacks);
	}

	RequisicaoUploadArquivo.prototype.requisitar = function(){

		if(!this.idForm || !this.idArquivo || !this.idContent){
			App.AlertaErro('Não é possível realizar a operação.', 'Faltam dados para fazer requisição.');
			return;
		}

		var form = $(this.idForm);
		var arquivo = $(this.idArquivo);
		var content = $(this.idContent);
		var contentSpinner = this.idContentSpinner ? $(this.idContentSpinner) : $(this.idContent);

		var file = arquivo[0].files[0];
		if(!file){
			App.AlertaErro('Escolha um arquivo para continuar', '');
			return;
		}

		var url = arquivo.data('url');

		(function(contentSpinner, content, callbacks, callbacksErros, paramCallbacks) {
			new App.Loading($(contentSpinner)).show();
			$('.bloquear-no-upload').attr('disabled', 'disabled');

			var fileUpload = new FormData(),
			csrf = $('[name="_csrf"]').val(),
			formSerialized = form/*.find('input[class!="base64"]')*/.serialize();

			fileUpload.append("fileUpload", arquivo[0].files[0]);

			$.ajax({
				type: "POST",
				url: url+"?_csrf="+csrf+"&"+formSerialized,
				data: fileUpload,
				contentType: false,
				processData: false,
				cache: false
			}).done(function(data) {
				content.html(data);
				//pós-carregamento
				new App.AplicarMascaras();

				new App.ComponentesBasicos().tooltips();
				
				new App.ComponentesBasicos().chosen(content.attr('id'));

				new App.ComponentesBasicos().filestyleElement(content.find(':file'));

				new App.ComponentesBasicos().eventosXPanel(content.attr('id'));

				if(callbacks && Array.isArray(callbacks))
					for(var i=0; i<callbacks.length; i++)
						callbacks[i](paramCallbacks);

				if(callbacks && !Array.isArray(callbacks))
					callbacks(paramCallbacks);
			})
			.fail(function(erro) {
				var msg = new App.ExtrairErroAjax(erro).extrair();
				App.AlertaErro('Não foi possível continuar', msg);

				if(callbacksErros && Array.isArray(callbacksErros))
					for(var i=0; i<callbacksErros.length; i++)
						callbacksErros[i](paramCallbacks);

				if(callbacksErros && !Array.isArray(callbacksErros))
					callbacksErros(paramCallbacks);
			})
			.always(function() {
				new App.Loading($(contentSpinner)).hide();
				$('.bloquear-no-upload').removeAttr('disabled');
			});

		}(contentSpinner, content, this.callbacks, this.callbacksErros, this.paramCallbacks));

	}

	return RequisicaoUploadArquivo;
}());

App.RequisicaoDownloadArquivo = (function(){

	function RequisicaoDownloadArquivo(idForm, url, nomeDocumento, idContentSpinner, callbacks, callbacksErros, paramCallbacks, fileType){
		this.idForm = idForm;
		this.url = url;
		this.nomeDocumento = nomeDocumento;
		this.idContentSpinner = idContentSpinner;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
		this.paramCallbacks = paramCallbacks;
		if(!fileType){
			fileType = 'application/pdf'
		}
		this.fileType = fileType;
	}

	RequisicaoDownloadArquivo.PorElemento = function(elem, callbacks, callbacksErros, paramCallbacks){
		var idForm = '#'+$(elem).closest('form').attr('id');
		var url = $(elem).data('url');
		var nomeDocumento = $(elem).data('nome-documento');
		var idContentSpinner = '#'+$(elem).data('id-content-spinner');

		var fileType = 'application/pdf';
		if($(elem).data('fileType')){
			fileType = $(elem).data('fileType')
		}

		return new App.RequisicaoDownloadArquivo(idForm, url, nomeDocumento, idContentSpinner, callbacks, callbacksErros, paramCallbacks, fileType);
	}

	RequisicaoDownloadArquivo.prototype.requisitar = function(){

		if(!this.idForm || !this.url || !this.nomeDocumento){
			App.AlertaErro('Não é possível realizar a operação.', 'Faltam dados para fazer requisição.');
			return;
		}

		(function(idForm, url, nomeDocumento, idContentSpinner, callbacks, callbacksErros, paramCallbacks, fileType) {
			if(idContentSpinner){
				new App.Loading($(idContentSpinner)).show();
			}
	
			$.ajax({
				type: "POST",
				xhrFields: {
					responseType: 'blob'
				},
				url: url,
				data: $(idForm).serialize()
			}).done(function(data) {

				new App.ComponentesBasicos().tooltips();
				
				try{
					var file = new Blob([data], {type: fileType});
    				saveAs(file, nomeDocumento);   

					if(callbacks && Array.isArray(callbacks))
						for(var i=0; i<callbacks.length; i++)
							callbacks[i](paramCallbacks);

					if(callbacks && !Array.isArray(callbacks))
						callbacks(paramCallbacks);
				}catch(e){
					console.error(e);
					return;
				}
	
			}).fail(function(erro) {
				try{
					if(callbacksErros && Array.isArray(callbacksErros))
						for(var i=0; i<callbacksErros.length; i++)
							callbacksErros[i](paramCallbacks);

					if(callbacksErros && !Array.isArray(callbacksErros))
						callbacksErros(paramCallbacks);
						
					var msg = new App.ExtrairErroAjax(erro).extrair();
					App.AlertaErro('Não foi possível continuar', msg);
				}catch(e){
					console.error(e);
					return;
				}
			}).always(function() {
				if(idContentSpinner){
					new App.Loading($(idContentSpinner)).hide();
				}
			});
		}(this.idForm, this.url, this.nomeDocumento, this.idContentSpinner, this.callbacks, this.callbacksErros, this.paramCallbacks, this.fileType));

	}

	return RequisicaoDownloadArquivo;
}());

App.ModalDeleteAjaxFormEstatico = (function () {

	const elementoModal = '#modal-delete-ajax';
	const botaoExcluir = '#botao-excluir-modal-delete-ajax';
	const spanSufixo = '#sufixo';

	function ModalDeleteAjaxFormEstatico(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros) {
		this.elementoModal = elementoModal;
		this.botaoExcluir = botaoExcluir;
		this.spanSufixo = spanSufixo;
		this.idForm = idForm;
		this.idButton = idButton;
		this.idContent = idContent;
		this.idContentSpinner = (idContentSpinner) ? idContentSpinner : idContent;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
	}

	ModalDeleteAjaxFormEstatico.PorElemento = function (elem, callbacks, callbacksErros) {
		var idForm = '#' + $(elem).closest('form').attr('id');
		var idButton = '#' + $(elem).attr('id');
		var idContent = '#' + $(elem).data('id-content');
		var idContentSpinner = undefined;
		if($(elem).data('id-content-spinner')){
			idContentSpinner = '#' + $(elem).data('id-content-spinner');
		}
		return new App.ModalDeleteAjaxFormEstatico(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros);
	}

	ModalDeleteAjaxFormEstatico.prototype.show = function () {
		if (typeof ($.fn.modal) === 'undefined') { console.log('Modal dependency not loaded'); return; }

		$(this.elementoModal).unbind();

		var sufixo = $(this.idButton).data('sufixo');
		if (sufixo) {
			$(this.elementoModal).find(this.spanSufixo).text(sufixo);
		} else {
			$(this.elementoModal).find(this.spanSufixo).text('');
		}

		$(this.elementoModal).find(this.botaoExcluir).attr("onclick", "new App.ModalDeleteAjaxFormEstatico('" + this.idForm + "', '" + this.idButton + "', '" + this.idContent + "', '" + this.idContentSpinner + "', "+ this.callbacks +", "+ this.callbacksErros +").excluir()");

		$(this.elementoModal).modal({ backdrop: 'static', keyboard: false });

		$(this.elementoModal).on("hidden.bs.modal", { elementoModal: this.elementoModal, spanSufixo: this.spanSufixo, botaoExcluir: this.botaoExcluir }, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoExcluir).attr("onclick", "");
		});
	}

	ModalDeleteAjaxFormEstatico.prototype.excluir = function () {
		$(this.elementoModal).on("hidden.bs.modal", {
			idForm: this.idForm,
			idButton: this.idButton,
			idContent: this.idContent,
			idContentSpinner: this.idContentSpinner,
			callbacks: this.callbacks,
			callbacksErros: this.callbacksErros,
			elementoModal: this.elementoModal,
			botaoExcluir: this.botaoExcluir
		}, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoExcluir).attr("onclick", "");

			new App.RequisicaoFormAjax(e.data.idForm, e.data.idButton, e.data.idContent, e.data.idContentSpinner, e.data.callbacks, e.data.callbacksErros).requisitar();
		});

		$(this.elementoModal).modal('hide');
	}

	return ModalDeleteAjaxFormEstatico;

}());

App.ModalRequisicaoAjaxFormEstatico = (function () {

	const elementoModal = '#modal-requisicao-ajax';
	const botaoAction = '#botao-action-modal-requisicao-ajax';
	const spanSufixo = '#sufixo';

	function ModalRequisicaoAjaxFormEstatico(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros) {
		this.elementoModal = elementoModal;
		this.botaoAction = botaoAction;
		this.spanSufixo = spanSufixo;
		this.idForm = idForm;
		this.idButton = idButton;
		this.idContent = idContent;
		this.idContentSpinner = (idContentSpinner) ? idContentSpinner : idContent;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
	}

	ModalRequisicaoAjaxFormEstatico.PorElemento = function (elem, callbacks, callbacksErros) {
		var idForm = '#' + $(elem).closest('form').attr('id');
		var idButton = '#' + $(elem).attr('id');
		var idContent = '#' + $(elem).data('id-content');
		var idContentSpinner = undefined;
		if($(elem).data('id-content-spinner')){
			idContentSpinner = '#' + $(elem).data('id-content-spinner');
		}
		return new App.ModalRequisicaoAjaxFormEstatico(idForm, idButton, idContent, idContentSpinner, callbacks, callbacksErros);
	}

	ModalRequisicaoAjaxFormEstatico.prototype.show = function () {
		if (typeof ($.fn.modal) === 'undefined') { console.log('Modal dependency not loaded'); return; }

		$(this.elementoModal).unbind();

		var sufixo = $(this.idButton).data('sufixo');
		if (sufixo) {
			$(this.elementoModal).find(this.spanSufixo).text(sufixo);
		} else {
			$(this.elementoModal).find(this.spanSufixo).text('');
		}

		$(this.elementoModal).find(this.botaoAction).attr("onclick", "new App.ModalRequisicaoAjaxFormEstatico('" + this.idForm + "', '" + this.idButton + "', '" + this.idContent + "', '" + this.idContentSpinner + "', "+ this.callbacks +", "+ this.callbacksErros +").excluir()");

		$(this.elementoModal).modal({ backdrop: 'static', keyboard: false });

		$(this.elementoModal).on("hidden.bs.modal", { elementoModal: this.elementoModal, spanSufixo: this.spanSufixo, botaoAction: this.botaoAction }, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoAction).attr("onclick", "");
		});
	}

	ModalRequisicaoAjaxFormEstatico.prototype.excluir = function () {
		$(this.elementoModal).on("hidden.bs.modal", {
			idForm: this.idForm,
			idButton: this.idButton,
			idContent: this.idContent,
			idContentSpinner: this.idContentSpinner,
			callbacks: this.callbacks,
			callbacksErros: this.callbacksErros,
			elementoModal: this.elementoModal,
			botaoAction: this.botaoAction
		}, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoAction).attr("onclick", "");

			new App.RequisicaoFormAjax(e.data.idForm, e.data.idButton, e.data.idContent, e.data.idContentSpinner, e.data.callbacks, e.data.callbacksErros).requisitar();
		});

		$(this.elementoModal).modal('hide');
	}

	return ModalRequisicaoAjaxFormEstatico;

}());

App.ModalDeleteJustificativaAjaxFormEstatico = (function () {

	const elementoModal = '#modal-delete-justificativa-ajax';
	const elementoInput = '#input-justificativa-modal';
	const botaoExcluir = '#botao-excluir-modal-delete-justificativa-ajax';
	const spanSufixo = '#sufixo-justificativa';

	function ModalDeleteJustificativaAjaxFormEstatico(idForm, nameInput, idButton, idContent, idContentSpinner, callbacks, callbacksErros) {
		this.elementoModal = elementoModal;
		this.nameInput = nameInput;
		this.botaoExcluir = botaoExcluir;
		this.spanSufixo = spanSufixo;
		this.idForm = idForm;
		this.idButton = idButton;
		this.idContent = idContent;
		this.idContentSpinner = (idContentSpinner) ? idContentSpinner : idContent;
		this.callbacks = callbacks;
		this.callbacksErros = callbacksErros;
	}

	ModalDeleteJustificativaAjaxFormEstatico.PorElemento = function (elem, callbacks, callbacksErros) {
		var idForm = '#' + $(elem).closest('form').attr('id');
		var nameInput = $(elem).data('name-input-justificativa');
		var idButton = '#' + $(elem).attr('id');
		var idContent = '#' + $(elem).data('id-content');
		var idContentSpinner = undefined;
		if($(elem).data('id-content-spinner')){
			idContentSpinner = '#' + $(elem).data('id-content-spinner');
		}
		return new App.ModalDeleteJustificativaAjaxFormEstatico(idForm, nameInput, idButton, idContent, idContentSpinner, callbacks, callbacksErros);
	}

	ModalDeleteJustificativaAjaxFormEstatico.prototype.show = function () {
		if (typeof ($.fn.modal) === 'undefined') { console.log('Modal dependency not loaded'); return; }

		$(this.elementoModal).unbind();

		$(this.elementoModal).find(elementoInput).attr("name", this.nameInput);
		$(this.elementoModal).find(elementoInput).val("");

		var sufixo = $(this.idButton).data('sufixo-justificativa');
		if (sufixo) {
			$(this.elementoModal).find(this.spanSufixo).text(sufixo);
		} else {
			$(this.elementoModal).find(this.spanSufixo).text('');
		}

		$(this.elementoModal).find(this.botaoExcluir).attr("onclick", "new App.ModalDeleteJustificativaAjaxFormEstatico('" + this.idForm + "', '" + this.nameInput + "', '" + this.idButton + "', '" + this.idContent + "', '" + this.idContentSpinner + "', "+ this.callbacks +", "+ this.callbacksErros +").excluir()");

		$(this.elementoModal).modal({ backdrop: 'static', keyboard: false });

		$(this.elementoModal).on("hidden.bs.modal", { elementoModal: this.elementoModal, spanSufixo: this.spanSufixo, botaoExcluir: this.botaoExcluir }, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoExcluir).attr("onclick", "");
		});
	}

	ModalDeleteJustificativaAjaxFormEstatico.prototype.excluir = function () {
		$(this.elementoModal).on("hidden.bs.modal", {
			idForm: this.idForm,
			idButton: this.idButton,
			idContent: this.idContent,
			idContentSpinner: this.idContentSpinner,
			callbacks: this.callbacks,
			callbacksErros: this.callbacksErros,
			elementoModal: this.elementoModal,
			botaoExcluir: this.botaoExcluir
		}, function (e) {
			$(this).unbind();
			$(e.data.elementoModal).find(e.data.spanSufixo).text('');
			$(e.data.elementoModal).find(e.data.botaoExcluir).attr("onclick", "");

			new App.RequisicaoFormAjax(e.data.idForm, e.data.idButton, e.data.idContent, e.data.idContentSpinner, e.data.callbacks, e.data.callbacksErros).requisitar();
		});

		$(this.elementoModal).modal('hide');
	}

	return ModalDeleteJustificativaAjaxFormEstatico;

}());

App.ExtrairErroAjax = (function() {

	function ExtrairErroAjax(resultado) {
		this.resultado = resultado;
	}

	ExtrairErroAjax.prototype.extrair = function() {
		if (this.resultado) {
			var msg = this.resultado.responseText;

			try {
				msg = $(this.resultado.responseText).find('#detalhe-erro')
						.text();

				var myRegexp = /(Detalhes do erro: )([^¨]*)$/;
				var match = myRegexp.exec(msg);
				if (match[2]) {
					return match[2];
				} else {
					return msg;
				}
			} catch (erro) {
				return msg;
			}

		}
		return '';
	}

	return ExtrairErroAjax;

}());

App.AlertaErro = (function() {

	function AlertaErro(titulo, texto) {
		var alerta = new PNotify({
			title : titulo,
			text : (texto) ? texto : '',
			type : 'error',
			styling : 'bootstrap3',
			icon : false,
			animate : {
				animate : true,
				in_class : 'slideInDown',
				out_class : 'slideOutUp'
			}
		});
		alerta.get().click(function() {
			alerta.remove();
		});
	}

	return AlertaErro;

}());

App.AlertaSucesso = (function() {

	function AlertaSucesso(titulo, texto) {
		var alerta = new PNotify({
			title : titulo,
			text : (texto) ? texto : '',
			type : 'success',
			styling : 'bootstrap3',
			icon : false,
			animate : {
				animate : true,
				in_class : 'slideInDown',
				out_class : 'slideOutUp'
			}
		});
		alerta.get().click(function() {
			alerta.remove();
		});
	}

	return AlertaSucesso;

}());

App.Loading = (function () {

	var defaults = {
		text: '',
	};

	function Loading(resultado, button, options) {
		this.resultado = resultado;
		this.button = button;
		this.settings = $.extend( {}, defaults, options );
	}

	Loading.prototype.show = function () {
		var isEmpty = !$.trim($(this.resultado).html());
		if(!isEmpty){

			var text = "";
			if(this.button && this.button.data('spinner-text')){
				text = this.button.data('spinner-text');
			}
			if(this.settings.text){
				text = this.settings.text;
			}

			this.resultado.LoadingOverlay('show', {
				image: '<svg version="1.1" id="L7" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"viewBox="0 0 100 100" enable-background="new 0 0 100 100" xml:space="preserve"><circle fill="none" stroke="#bf360c" stroke-width="6" cx="50" cy="50" r="45" style="opacity:0.4;"/><path fill="#bf360c" d="M31.6,3.5C5.9,13.6-6.6,42.7,3.5,68.4c10.1,25.7,39.2,38.3,64.9,28.1l-3.1-7.9c-21.3,8.4-45.4-2-53.8-23.3 c-8.4-21.3,2-45.4,23.3-53.8L31.6,3.5z"><animateTransform attributeName="transform" attributeType="XML" type="rotate" dur="1s" from="0 50 50" to="360 50 50" repeatCount="indefinite" /></path></circle></svg>',
				fade: 250,
				text: text,
				textColor: "#bf360c",
				textResizeFactor: 0.3
			});
		}
	}

	Loading.prototype.hide = function () {
		this.resultado.LoadingOverlay("hide", {
			fade: 250
		});
	}

	return Loading;

}());

App.ComponentesBasicos = (function(){

	function ComponentesBasicos(){}

	ComponentesBasicos.prototype.toggles = function(contentId){

		var selectorContent = (contentId) ? ('#' + contentId) : '';

		$(selectorContent + ' .bootstrap-toggle').each(function () {
			var on = $(this).data('on');
			var off = $(this).data('off');

			$(this).val($(this).prop('checked'));

			$(this).bootstrapToggle({
				on: on,
				off: off,
				width: '100%',
				size: 'normal'
			}).change(function () {
				if ($(this).prop('checked') != undefined) {
					$(this).val($(this).prop('checked'));
				}
			});
		});
	}

	ComponentesBasicos.prototype.chosen = function(contentId){
		var selectorContent = (contentId) ? ('#' + contentId) : '';

		$(selectorContent + ' .chosen-select').chosen({
			"no_results_text": 'Nenhum resultado encontrado',
			"disable_search": false,
			"allow_single_deselect": true
		});
		$(selectorContent + ' .chosen-select-deselect').chosen({ allow_single_deselect: true });
	}

	ComponentesBasicos.prototype.reset = function () {
		//Evento para limpeza de todo o form ao limpar
		$('form :reset').on('click', function (e) {
			e.preventDefault();
			e.stopImmediatePropagation();
			$("form input:hidden,form :text,form textarea").val('').change();
			$(".chosen-select").val('').trigger("chosen:updated");
		});
	}

	ComponentesBasicos.prototype.iCheck = function(contentId){
		var selectorContent = (contentId) ? ('#' + contentId) : '';
		//força renderização dos radio e checkbox.
		$(selectorContent+' input.flat').iCheck({
			checkboxClass: 'icheckbox_flat-orange',
			radioClass: 'iradio_flat-orange'
		});
	}

	ComponentesBasicos.prototype.tooltips = function(){
		$('.tooltip.in').remove();
		// $('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="tooltip"]').tooltip({
			container: 'body',
			trigger: "hover"
		});
	}

	ComponentesBasicos.prototype.popovers = function(){
		$('[data-toggle="popover"]').popover({
			html : true,
			trigger: "hover"
		});
	}

	ComponentesBasicos.prototype.filestyle = function(){
		$(":file").filestyle({
			buttonText : 'Escolher arquivo',
			placeholder: "Nenhum arquivo selecionado",
			buttonBefore: true,
			icon: false
		});
	}

	ComponentesBasicos.prototype.filestyleElement = function($elem){
		if($elem && $elem.length){
			for(var i=0; i < $elem.length; i++){
				$($elem[i]).filestyle({
					buttonText : 'Escolher arquivo',
					placeholder: "Nenhum arquivo selecionado",
					buttonBefore: true,
					icon: false
				});
			}
		}
	}	
	
	ComponentesBasicos.prototype.eventosXPanel = function (idElementoPai) {
		$('#' + idElementoPai + ' .collapse-link').on('click', function () {
			var $BOX_PANEL = $(this).closest('[class^="x_panel"]'),
				$ICON = $(this).find('i'),
				$BOX_CONTENT = $BOX_PANEL.find('.x_content');

			// fix for some div with hardcoded fix class
			if ($BOX_PANEL.attr('style')) {
				$BOX_CONTENT.slideToggle(200, function () {
					$BOX_PANEL.removeAttr('style');
				});
			} else {
				$BOX_CONTENT.slideToggle(200);
				$BOX_PANEL.css('height', 'auto');
			}

			$ICON.toggleClass('fa-chevron-up fa-chevron-down');
		});

		$('#' + idElementoPai + ' .close-link').click(function () {
			var $BOX_PANEL = $(this).closest('.x_panel');

			$BOX_PANEL.remove();
		});
	}

	return ComponentesBasicos;
}());

App.ModalEstatico = (function () {

	var defaults = {
		idForm: null,
		idButton: null,
		idContent: null,
		idContentSpinner: null,
		callbackPosShow: null,
		callbackPosHide: null,
		callbacksSucessoAjax: null,
		callbacksErroAjax: null
	};

	function ModalEstatico(elementoModal, options) {
		this.elementoModal = elementoModal;
		this.settings = $.extend( {}, defaults, options );
	}

	ModalEstatico.PorElemento = function(elem, options){
		var settings = $.extend( {}, defaults, options );
		settings.idContent = '#'+$(elem).data('id-content');
		settings.idButton = '#'+$(elem).attr('id');
		if($(elem).data('id-content-spinner')){
			settings.idContentSpinner = '#'+$(elem).data('id-content-spinner');
		}else{
			settings.idContentSpinner = settings.idContent;
		}

		settings.idForm = '#'+$(settings.idContent).closest('form').attr('id');
		
		return new App.ModalEstatico(elem, settings);
	}

	ModalEstatico.prototype.show = function (param) {
		if (typeof ($.fn.modal) === 'undefined') { console.log('Modal dependency not loaded'); return; }

		$(this.elementoModal).unbind();

		$(this.elementoModal).modal({ backdrop: 'static', keyboard: false });

		$(this.elementoModal).on("shown.bs.modal", { callbackPosShow: this.settings.callbackPosShow, param: param }, function (e) {

			if (e.data.callbackPosShow) {
				e.data.callbackPosShow(e.data.param);
			}

		});

		$(this.elementoModal).on("hidden.bs.modal", { callbackPosHide: this.settings.callbackPosHide, param: param }, function (e) {
			$(this).unbind();

			if (e.data.callbackPosHide) {
				e.data.callbackPosHide(e.data.param);
			}

		});
	}

	ModalEstatico.prototype.fechar = function () {
		$(this.elementoModal).modal('hide');
	}

	ModalEstatico.prototype.requisicaoAjaxConfirmada = function () {
		$(this.elementoModal).on("hidden.bs.modal", {
			idForm: this.settings.idForm,
			idButton: this.settings.idButton,
			idContent: this.settings.idContent,
			idContentSpinner: this.settings.idContentSpinner,
			callbacksSucessoAjax: this.settings.callbacksSucessoAjax,
			callbacksErroAjax: this.settings.callbacksErroAjax
		}, function (e) {
			$(this).unbind();

			new App.RequisicaoFormAjax(e.data.idForm, e.data.idButton, e.data.idContent, e.data.idContentSpinner, e.data.callbacksSucessoAjax, e.data.callbacksErroAjax).requisitar();
		});

		$(this.elementoModal).modal('hide');
	}

	return ModalEstatico;

}());

App.FuncoesNativas = (function () {

	function FuncoesNativas() {
		//includes
		if (!String.prototype.includes) {
			String.prototype.includes = function () {
				'use strict';
				return String.prototype.indexOf.apply(this, arguments) !== -1;
			};
		}
	}

	return FuncoesNativas;

}());

App.AlternaAbas = (function() {

	AlternaAbas.prototype.htmlPassoAnterior =
		'<button type="button" onclick="javascript: new App.AlternaAbas().alternarAba(\'${idAba}\')" class="btn btn-default btn-sm min-width-100px" data-original-title="Clique para voltar ao passo anterior" data-toggle="tooltip">'+
		'		<i class="fa fa-chevron-left before-margin-right-5"></i>Passo anterior'+
		'</button>';

	AlternaAbas.prototype.htmlProximoPasso =
		'<button type="button" onclick="javascript: new App.AlternaAbas().alternarAba(\'${idAba}\')" class="btn btn-primary btn-sm min-width-100px" data-original-title="Clique para avançar ao próximo passo" data-toggle="tooltip">'+
		'		Próximo passo<i class="fa fa-chevron-right before-margin-left-5"></i>'+
		'</button>';

	AlternaAbas.prototype.htmlWrapper =
		'<div align="right" style="margin-bottom: 10px;">'+
		'		<span class="input-group-btn">${buttons}</span>'+
		'</div>';

	function AlternaAbas() {}

	AlternaAbas.prototype.enable = function(){
		this.elementos = $('.alternador-abas');

		for(var i=0; i < this.elementos.length; i++){
			var html = AlternaAbas.prototype.htmlWrapper;

			var $elem = $(this.elementos[i]);
			var prevHref = this.buscaPrev($elem);
			var nextHref = this.buscaNext($elem);

			var htmlButtons = '';
			if(prevHref.length){
				htmlButtons = htmlButtons+(this.htmlPassoAnterior.replace('${idAba}', prevHref.get(0).id));
			}
			if(nextHref.length){
				htmlButtons = htmlButtons+(this.htmlProximoPasso.replace('${idAba}', nextHref.get(0).id));
			}

			html = html.replace('${buttons}', htmlButtons);

			$elem.html(html);
		}
	}

	AlternaAbas.prototype.buscaNext = function($elem){
		li = this.buscaLi($elem);
		var nextHref = li.next().find('a');

		return nextHref;
	}

	AlternaAbas.prototype.buscaPrev = function($elem){
		li = this.buscaLi($elem);
		var prevHref = li.prev().find('a');

		return prevHref;
	}

	AlternaAbas.prototype.buscaLi = function($elem){
		var tabpanel1 = $elem.closest("[role='tabpanel']");
		var href = $('#'+tabpanel1.attr('aria-labelledby'));
		var li = href.closest('li');

		return li;
	}

	AlternaAbas.prototype.alternarAba = function(idHref){
		$('#'+idHref).trigger('click');
	}

	return AlternaAbas;

}());

App.QueryParamManager = (function() {

	function QueryParamManager() {
	}

	QueryParamManager.prototype.get = function(name) {
		var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
		return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
	}

	QueryParamManager.prototype.addParam = function(url, param, value){
		var hash       = {};
		var parser     = document.createElement('a');
	
		parser.href    = url;
	
		var parameters = parser.search.split(/\?|&/);
	
		for(var i=0; i < parameters.length; i++) {
			if(!parameters[i])
				continue;
	
			var ary      = parameters[i].split('=');
			hash[ary[0]] = ary[1];
		}
	
		hash[param] = value;
	
		var list = [];  
		Object.keys(hash).forEach(function (key) {
			list.push(key + '=' + hash[key]);
		});
	
		parser.search = '?' + list.join('&');
		return parser.href;
	}
	
	return QueryParamManager;
}()); 

App.AcaoTeclaEnter =  (function() {

	function AcaoTeclaEnter(elemOuvinte, elemAcao) {
		this.elemOuvinte = elemOuvinte;
		this.elemAcao = elemAcao;
	}

	AcaoTeclaEnter.prototype.enable = function(){
		if(this.elemOuvinte, this.elemAcao){
			$(document).on("keypress", this.elemOuvinte, {elemOuvinte:this.elemOuvinte, elemAcao:this.elemAcao}, function(event) {
				if(event.keyCode == 13){//se tecla difitada for enter
					if($(event.data.elemOuvinte).find(event.data.elemAcao).length){//se o elem de ação for filho do ouvinte
						$(event.data.elemOuvinte).find(event.data.elemAcao).click();
					}else{
						$(event.data.elemAcao).click();
					}
				}

				return event.keyCode != 13;
			});
		}
	}

	return AcaoTeclaEnter;

}());

App.PosChange = (function() {

	function PosChange(elemObservado, callback) {
		this.elemObservado = $(elemObservado);
		this.callback = callback;
	}

	PosChange.prototype.enable = function(){

		if(this.elemObservado.hasClass("flat")){//iChecks
			this.elemObservado.on('ifChecked ifUnchecked', function (event) {
				$(event.target).change();
			});
		}

		this.verificaEstado(this.elemObservado, true, this.callback);

		this.elemObservado.change({
			sePrimeiraVez:false,
			callback:this.callback,
			functionVerificaEstado: this.verificaEstado
		}, this.manipulaAlteracao);
	}

	PosChange.prototype.manipulaAlteracao = function(e){
		var elemObservado = $(this);
		var sePrimeiraVez = e.data.sePrimeiraVez;
		var callback = e.data.callback;
		var functionVerificaEstado = e.data.functionVerificaEstado;

		functionVerificaEstado(elemObservado, sePrimeiraVez, callback);
	}

	PosChange.prototype.verificaEstado = function(elemObservado, sePrimeiraVez, callback){
		if(!sePrimeiraVez){
			callback();
		}
	}

	return PosChange;

}());

App.ClearForm = (function() {

	var defaults = {
		clearHiddensById: [],
		notClearById: [],
		callback: null,
	}

	function ClearForm(elem, options) {
		this.elem = elem;
		this.form = $(elem.closest('form'));
		this.settings = $.extend( {}, defaults, options );
	}

	ClearForm.prototype.clear = function(){
		this.clearInputs();
		this.clearChosens();

		if(this.settings.callback && typeof this.settings.callback === "function"){
			this.settings.callback(this.form);
		}
	}

	ClearForm.prototype.clearInputs = function(){
		var inputs = this.form.find('input');
		for(var i=0; i < inputs.length; i++){
			var input = $(inputs[i]);

			var isHidden = input.is(':hidden');

			if(isHidden){
				if(this.settings.clearHiddensById.includes(input.attr('id'))){
					input.val('');
				}
			} else {
				if(!this.settings.notClearById.includes(input.attr('id'))){
					input.val('');
				}
			}
		}
	}

	ClearForm.prototype.clearChosens = function(){
		var chosens = this.form.find('.chosen-select');
		for(var i=0; i < chosens.length; i++){
			var chosen = $(chosens[i]);
			
			if(!this.settings.notClearById.includes(chosen.attr('id'))){
				chosen.val('').trigger("chosen:updated");
			}
		}
	}

	return ClearForm;

}());

App.ManipulaBoostrapToggle = (function () {

	function ManipulaBoostrapToggle(elemento, valorAtivado, valorDesativado, sePadraoAtivado, functionPosAlteracao, params) {
		this.elemento = elemento;
		this.valorAtivado = valorAtivado;
		this.valorDesativado = valorDesativado;
		this.sePadraoAtivado = sePadraoAtivado;
		this.functionPosAlteracao = functionPosAlteracao;
		this.params = params;

		this.verificaEstadoInicial();
	}

	ManipulaBoostrapToggle.prototype.verificaEstadoInicial = function () {
		var $elemento = $(this.elemento);

		if (this.sePadraoAtivado && !$elemento.val()) {
			$elemento.prop('checked', true).change();
			var width = ($elemento.data('width')) ? $elemento.data('width') : '100%';
			var size = ($elemento.data('size')) ? $elemento.data('size') : 'normal';
			$elemento.bootstrapToggle({ width: width, size: size });
			this.habilita($elemento, this.valorAtivado, this.valorDesativado);
			return;
		}

		if ($elemento.val() == this.valorAtivado) {
			$elemento.prop('checked', true).change();
			var width = ($elemento.data('width')) ? $elemento.data('width') : '100%';
			var size = ($elemento.data('size')) ? $elemento.data('size') : 'normal';
			$elemento.bootstrapToggle({ width: width, size: size });
			this.habilita($elemento, this.valorAtivado, this.valorDesativado);
		} else {
			$elemento.prop('checked', false).change();
			var width = ($elemento.data('width')) ? $elemento.data('width') : '100%';
			var size = ($elemento.data('size')) ? $elemento.data('size') : 'normal';
			$elemento.bootstrapToggle({ width: width, size: size });
			this.desabilita($elemento, this.valorAtivado, this.valorDesativado);
		}
	}

	ManipulaBoostrapToggle.prototype.enable = function () {
		$(this.elemento).change({
			valorAtivado: this.valorAtivado,
			valorDesativado: this.valorDesativado,
			functionHabilita: this.habilita,
			functionDesabilita: this.desabilita,
			functionVerificaEstado: this.verificaEstado,
			functionPosAlteracao: this.functionPosAlteracao,
			params: this.params
		}, this.manipulaAlteracao);
	}

	ManipulaBoostrapToggle.prototype.manipulaAlteracao = function (e) {
		var $elemento = $(this);
		var valorAtivado = e.data.valorAtivado;
		var valorDesativado = e.data.valorDesativado;
		var functionHabilita = e.data.functionHabilita;
		var functionDesabilita = e.data.functionDesabilita;
		var functionVerificaEstado = e.data.functionVerificaEstado;
		var functionPosAlteracao = e.data.functionPosAlteracao;
		var params = e.data.params;

		functionVerificaEstado($elemento, valorAtivado, valorDesativado, functionHabilita, functionDesabilita);

		if (functionPosAlteracao) {
			functionPosAlteracao(params);
		}
	}

	ManipulaBoostrapToggle.prototype.verificaEstado = function ($elemento, valorAtivado, valorDesativado, functionHabilita, functionDesabilita) {

		if ($elemento.is(":checked")) {
			functionHabilita($elemento, valorAtivado, valorDesativado);
		} else {
			functionDesabilita($elemento, valorAtivado, valorDesativado);
		}
	}
	
	ManipulaBoostrapToggle.prototype.forcaHabilitacao = function () {
		var $elemento = $(this.elemento);
		$elemento.prop('checked', true).change();
		$elemento.bootstrapToggle({ width: '100%' });
		this.habilita($elemento, this.valorAtivado, this.valorDesativado);
	}

	ManipulaBoostrapToggle.prototype.habilita = function ($elemento, valorAtivado, valorDesativado) {

		var $elementoHelper = $("#" + $elemento.attr("data-helper"));
		var name = $elemento.attr("data-name-check");

		$elemento.val(valorAtivado);
		$elementoHelper.val(valorAtivado);
		
		if($elemento.is(':disabled')){ //habilitado mas desativado
			$elemento.removeAttr("name");
			$elementoHelper.attr("name", name);
		} else {
			$elemento.attr("name", name);
			$elementoHelper.removeAttr("name");
		}
	}
	
	ManipulaBoostrapToggle.prototype.forcaDesabilitacao = function () {
		var $elemento = $(this.elemento);
		$elemento.prop('checked', false).change();
		$elemento.bootstrapToggle({ width: '100%' });
		this.desabilita($elemento, this.valorAtivado, this.valorDesativado);
	}

	ManipulaBoostrapToggle.prototype.desabilita = function ($elemento, valorAtivado, valorDesativado) {
		
		var $elementoHelper = $("#" + $elemento.attr("data-helper"));
		var name = $elemento.attr("data-name-check");

		$elemento.val(valorDesativado);
		$elementoHelper.val(valorDesativado);
		$elemento.removeAttr("name");
		$elementoHelper.attr("name", name);
	}

	return ManipulaBoostrapToggle;

}());

App.LoadingAjaxOnTag = (function() {

	function LoadingOnTag(a, isHref) {
		this.tag = $(a);
		this.isHref = isHref ? isHref : false;
	}

	LoadingOnTag.prototype.enable = function() {
		var isHref = this.isHref;
		var tag = this.tag;
		
		$(tag).each(function() {
			
			$( this ).on('click', function(e){
				
				if(isHref){
					var href = $(this).attr("href");
					
					if(href && href.search("/") == 0){
						new App.Loading($('body'), $(this)).show();
					}
				}else{
					new App.Loading($('body'), $(this)).show();
				}
			});
			
		});
	}
	
	return LoadingOnTag;
}());

App.CountingTextArea = (function() {

	function CountingTextArea(elem) {
		this.elem = $(elem);
	}

	CountingTextArea.prototype.enable = function() {

		var html = 
		'<br>'+
		'<h6>'+
		'	(Máximo de caracteres: 300)'+
		'	</br>'+
		'	Você tem <msg id="count-justificativa"></msg> caracteres restantes.'+
		'</h6>';
	}
	
	return CountingTextArea;
}());

$(function() {

	new App.ModalDelete().enable();
	new App.ModalEndereco().enable();
	new App.DateRangePicker().enable();
	new App.DatePicker().enable();
	new App.AplicarMascaras();
	new App.ModalConfirm().enable();
	new App.FuncoesNativas();

	new App.ComponentesBasicos().chosen();
	new App.ComponentesBasicos().reset();
	new App.ComponentesBasicos().filestyle();

	new App.ComponentesBasicos().tooltips();
	
	new App.AlternaAbas().enable();

	new App.LoadingAjaxOnTag('a', true).enable();
	new App.LoadingAjaxOnTag('.button-loading-body', false).enable();
	new App.LoadingAjaxOnTag('#pagination-bar-js a[href!="#"]', false).enable();
	$('#select-page-size ').change(function () {new App.Loading($('body')).show();});

	console.log('init_app');
 
});



