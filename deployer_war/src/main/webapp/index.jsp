<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Deployer</title>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/jquery.dataTables.css" rel="stylesheet" media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script>
	$(document).ready(
			function() {
						function initHostList() {
							res = [];
							map = {};
							$
									.ajax(
											{
												type : "GET",
												dataType : "json",
												url : "/deployer_war/rest/hostService/list",
											})
									.done(
											function(result) {
												if (result != undefined
														&& result.host != undefined) {
													for ( var i = 0; i < result.host.length; i++) {
														map[result.host[i].hostName
																+ " "
																+ result.host[i].profile] = result.host[i].id;
														res
																.push(result.host[i].hostName
																		+ " "
																		+ result.host[i].profile);
													}
												}
												return res;
											});
							return res;
						}

						$("#search").val("");
						$("#search").typeahead({
							source : initHostList(),
							updater : function(item) {
								console.log(map[item]);
								close();
								if($("#appsList").attr("class")=="active") {
									showApplications(map[item]);
								}else if($("#historyList").attr("class")=="active") {
									showHistory(map[item]);
								}
								return item;
							}
						});
						
						function showApplications(id) {
							$(".container").append("<div id='lists'></div>");
							$("#lists").append("<table cellpadding='0' cellspacing='0' border='0' class='display' id='example'></table>");
						    $('#example').dataTable( {
						    	"bProcessing": true,
						        "sAjaxSource": "/deployer_war/rest/eventService/"+id,
						        "sAjaxDataProp": "event",
						        "aoColumns": [
						                      { "mData": "eventDate", "sTitle":"Дата" },
						                      { "mData": "eventName", "sTitle":"Событие" },
						                      { "mData": "productName", "sTitle":"Название" },
						                      { "mData": "revision", "sTitle":"Ревизия" },
						                      { "mData": "version", "sTitle":"Версия" }]
						    } );
						}
						
						function showHistory() {
							$(".container").append("<div id='lists'></div>");
							$("#lists").append("<table cellpadding='0' cellspacing='0' border='0' class='display' id='example'></table>");
						    $('#example').dataTable( {
						    	"bProcessing": true,
						        "sAjaxSource": "/deployer_war/rest/eventService/list",
						        "sAjaxDataProp": "event",
						        "aoColumns": [
						                      { "mData": "eventDate", "sTitle":"Дата" },
						                      { "mData": "eventName", "sTitle":"Событие" },
						                      { "mData": "productName", "sTitle":"Название" },
						                      { "mData": "revision", "sTitle":"Ревизия" },
						                      { "mData": "version", "sTitle":"Версия" }]
						    } );
						}
						
						function close() {
							$("#lists").remove();
						}

						$("#historyList").click(function() {
							$("#appsList").removeAttr("class");
							$("#historyList").attr("class", "active");
							close();
							showHistory();
						});

						$("#appsList").click(function() {
							$("#historyList").removeAttr("class");
							$("#appsList").attr("class", "active");
							close();
							showApplications(map[$("#search").val()]);
						});

					});
</script>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="#">Deployer</a>
		</div>
	</div>
	<div class="container">
		<div><span>Выбрать сервер </span><input type="text" id="search"></div>
		<ul class="nav nav-tabs">
			<li id="appsList" class="active"><a>Приложения</a></li>
			<li id="historyList"><a>История</a></li>
		</ul>
	</div>
</body>
</html>