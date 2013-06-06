<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application Topology Manager</title>
<c:set var="serverHostPort" value="${pageContext.request.serverName}:${pageContext.request.serverPort}"/> 
<link href="//${serverHostPort}/css/bootstrap.css" rel="stylesheet" media="screen">
<link href="//${serverHostPort}/css/custom.css" rel="stylesheet" media="screen">
<link href="//${serverHostPort}/css/jquery.dataTables.css" rel="stylesheet" media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="//${serverHostPort}/js/bootstrap.js"></script>
<script src="//${serverHostPort}/js/jquery.dataTables.js"></script>

<script src="//${serverHostPort}/scripts/DataTablesRus.js"></script>
<script src="//${serverHostPort}/scripts/Actions.js"></script>
<script src="//${serverHostPort}/scripts/deployerView.js"></script>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="#">Application Topology Manager</a>
		</div>
	</div>
	<div class="container">
		<ul class="nav nav-tabs">
			<li id="appsList" class="active"><a>Приложения</a></li>
			<li id="historyList"><a>История сервера</a></li>
			<li id="appHistoryList"><a>История приложения</a></li>
			<li id="advancedSearchList"><a>Расширенный поиск</a></li>
		</ul>
		<div>
			<div id="serverNavigation">
				<span>Выбрать сервер </span>
				<input type="text" autocomplete="off" id="search">
				<button class="btn btn-primary" id="refreshButton" type="button">Обновить</button>
			</div>
			<div id="advancedSearch">
				<input class="span2" id="product" type="text" placeholder="Компонент...">
				<input class="span2" id="version" type="text" placeholder="Версия...">
				<input class="span2" id="revision" type="text" placeholder="Ревизия...">
				<button class="btn btn-primary" id="advancedSearchButton" type="button">Найти</button>
			</div>
		</div>
	</div>
</body>
</html>