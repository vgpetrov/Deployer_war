<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application Topology Manager</title>
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link href="css/custom.css" rel="stylesheet" media="screen">
<link href="css/jquery.dataTables.css" rel="stylesheet" media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script src="scripts/deployerView.js"></script>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="#">Application Topology Manager</a>
		</div>
	</div>
	<div class="container">
		<div style="margin-bottom: 20px;">
			<span>Выбрать сервер </span>
			<input type="text" autocomplete="off" id="search">
			<button class="btn btn-primary" id="refreshButton" type="button">Обновить</button>
			<a id="adminLink" style="display:none" href="#">В консоль администратора</a>
		</div>
		<ul class="nav nav-tabs">
			<li id="appsList" class="active"><a>Приложения</a></li>
			<li id="historyList"><a>История сервера</a></li>
			<li id="appHistoryList"><a>История приложения</a></li>
			<li id="advancedSearchList"><a>Расширенный поиск</a></li>
		</ul>
	</div>
</body>
</html>