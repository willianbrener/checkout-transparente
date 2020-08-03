
jQuery(document).ready(function() {
	
	/* Fullscreen Random Backgrounds */
	
	var contextPath = $('#layout-box-js').data('context-path');
	
	var imgBgAbstract = [
		 contextPath + "resources/core-template/layout-box/backgrounds/abstract/1.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/abstract/2.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/abstract/3.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/abstract/4.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/abstract/5.jpg"
	]
	
	var imgBgFuture = [
		 contextPath + "resources/core-template/layout-box/backgrounds/future/1.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/future/2.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/future/3.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/future/4.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/future/5.jpg"
	]
	
	var imgBgOffice = [
		 contextPath + "resources/core-template/layout-box/backgrounds/office/1.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/2.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/3.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/4.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/5.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/6.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/7.jpg"
		,contextPath + "resources/core-template/layout-box/backgrounds/office/8.jpg"
	]
	
	var imgBgOptions = [imgBgAbstract, imgBgFuture, imgBgOffice]
	
	var imgBgOptionsRandomIndex = Math.floor(Math.random() * imgBgOptions.length)
	
	$.backstretch(imgBgOptions[imgBgOptionsRandomIndex], {duration: 3000, fade: 750});
});