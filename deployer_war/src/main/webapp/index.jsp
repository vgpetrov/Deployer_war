<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Deployer</title>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/jquery.dataTables.css" rel="stylesheet" media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script>
	$(document)
			.ready(
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
											});
							return res;
						}

						$("#search").typeahead({
							source : initHostList(),
							updater : function(item) {
								console.log(map[item]);
								return item;
							}
						});

						$("#historyList").click(function() {
							$("#appsList").removeAttr("class");
							$("#historyList").attr("class", "active");
						});

						$("#appsList").click(function() {
							$("#historyList").removeAttr("class");
							$("#appsList").attr("class", "active");
						});
						
					    $('#example').dataTable( {
					        "aaData": [
					            /* Reduced data set */
					            [ "Trident", "Internet Explorer 4.0", "Win 95+", 4, "X" ],
					            [ "Trident", "Internet Explorer 5.0", "Win 95+", 5, "C" ],
					            [ "Trident", "Internet Explorer 5.5", "Win 95+", 5.5, "A" ],
					            [ "Trident", "Internet Explorer 6.0", "Win 98+", 6, "A" ],
					            [ "Trident", "Internet Explorer 7.0", "Win XP SP2+", 7, "A" ],
					            [ "Gecko", "Firefox 1.5", "Win 98+ / OSX.2+", 1.8, "A" ],
					            [ "Gecko", "Firefox 2", "Win 98+ / OSX.2+", 1.8, "A" ],
					            [ "Gecko", "Firefox 3", "Win 2k+ / OSX.3+", 1.9, "A" ],
					            [ "Webkit", "Safari 1.2", "OSX.3", 125.5, "A" ],
					            [ "Webkit", "Safari 1.3", "OSX.3", 312.8, "A" ],
					            [ "Webkit", "Safari 2.0", "OSX.4+", 419.3, "A" ],
					            [ "Webkit", "Safari 3.0", "OSX.4+", 522.1, "A" ]
					        ],
					        "aoColumns": [
					            { "sTitle": "Engine" },
					            { "sTitle": "Browser" },
					            { "sTitle": "Platform" },
					            { "sTitle": "Version", "sClass": "center" },
					            { "sTitle": "Grade", "sClass": "center" }
					        ]
					    } );    

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
		Выбрать сервер <input type="text" id="search">
		<ul class="nav nav-tabs">
			<li id="appsList" class="active"><a>Приложения</a></li>
			<li id="historyList"><a>История</a></li>
		</ul>
		<div id="#demo">
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>
		</div>
	</div>
</body>
</html>