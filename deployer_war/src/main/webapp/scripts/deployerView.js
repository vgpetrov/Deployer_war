$(document)
        .ready(
                function() {
                    
                    function initHostList() {
                        res = [];
                        adminPorts = [];
                        map = {};
                        $.ajax({
                                    type : "GET",
                                    dataType : "json",
                                    url : "/rest/host/",
                                })
                                .done(
                                        function(result) {
                                            if (result != undefined && result.host != undefined) {
                                                if (result.host.length == undefined) {
                                                    map[result.host.hostName + " " + result.host.profile] = result.host.id;
                                                    res.push(result.host.hostName + " " + result.host.profile);
                                                } else {
                                                    for ( var i = 0; i < result.host.length; i++) {
                                                        map[result.host[i].hostName + " " + result.host[i].profile] = result.host[i].id;
                                                        adminPorts[result.host[i].hostName + " " + result.host[i].profile] = result.host[i].adminPort;
                                                        res.push(result.host[i].hostName + " " + result.host[i].profile);
                                                    }
                                                }
                                            }
                                            return res;
                                        });
                        return res;
                    }

                    function ruLang() {
                        return {
                            "sProcessing" : "Подождите...",
                            "sLengthMenu" : "Показать _MENU_ записей",
                            "sZeroRecords" : "Записи отсутствуют.",
                            "sInfo" : "Записи с _START_ до _END_ из _TOTAL_ записей",
                            "sInfoEmpty" : "Записи с 0 до 0 из 0 записей",
                            "sInfoFiltered" : "(отфильтровано из _MAX_ записей)",
                            "sInfoPostFix" : "",
                            "sSearch" : "Поиск:",
                            "sUrl" : "",
                            "oPaginate" : {
                                "sFirst" : "Первая",
                                "sPrevious" : "Предыдущая",
                                "sNext" : "Следующая",
                                "sLast" : "Последняя"
                            },
                            "oAria" : {
                                "sSortAscending" : ": активировать для сортировки столбца по возрастанию",
                                "sSortDescending" : ": активировать для сортировки столбцов по убыванию"
                            }
                        };
                    }

                    $("#search").val("");
                    $("#search").typeahead({
                        source : initHostList(),
                        updater : function(item) {
                            close();
                            var pos = this.source.indexOf(item);
                            if ($("#appsList").attr("class") == "active") {
                                showApplications(map[item]);
                                $("#adminLink").attr("href","http://"+(item.split(" "))[0]+":"+adminPorts[item]+"/ibm/console")
                                               .css("display","block");
                            } else if ($("#historyList").attr("class") == "active") {
                                showHistory(map[item]);
                            }
                            return item;
                        }
                    });
                    
                    $("#refreshButton").click(function() {
                        $("#search").data("typeahead").source = initHostList();
                    });

                    function showApplications(id) {
                        if (id != undefined) {
                            $(".container").append("<div id='lists'></div>");
                            $("#lists")
                                    .append(
                                            "<table cellpadding='0' cellspacing='0' border='0' class='display' id='resTabList'></table>");
                            $('#resTabList').dataTable({
                                "sPaginationType" : "full_numbers",
                                "bProcessing" : true,
                                "sAjaxSource" : "/rest/eventService/" + id,
                                "sAjaxDataProp" : "event",
                                "oLanguage" : ruLang(),
                                "aoColumns" : [ {
                                    "mData" : "eventDate",
                                    "sTitle" : "Дата"
                                }, {
                                    "mData" : "productName",
                                    "sTitle" : "Название"
                                }, {
                                    "mData" : "revision",
                                    "sTitle" : "Ревизия"
                                }, {
                                    "mData" : "version",
                                    "sTitle" : "Версия"
                                } ]
                            });
                            $("#resTabList tbody").delegate("tr", "click", function() {
                                var hostId = map[$("#search").val()];
                                var appName = $("td:eq(1)", this).text();
                                $("#historyList").removeAttr("class");
                                $("#appsList").removeAttr("class");
                                $("#appHistoryList").attr("class", "active");
                                $("#advancedSearchList").removeAttr("class");
                                close();
                                showAppsHistory(hostId, appName);
                            });
                        }
                    }

                    function showAppsHistory(id, appName) {
                        if (id != undefined && appName != undefined) {
                            $(".container").append("<div id='lists'></div>");
                            $("#lists")
                                    .append(
                                            "<table cellpadding='0' cellspacing='0' border='0' class='display' id='resTabList'></table>");
                            $('#resTabList').dataTable({
                                "sPaginationType" : "full_numbers",
                                "bProcessing" : true,
                                "sAjaxSource" : "/rest/eventService/" + id + "/" + appName,
                                "sAjaxDataProp" : "event",
                                "oLanguage" : ruLang(),
                                "aoColumns" : [ {
                                    "mData" : "eventDate",
                                    "sTitle" : "Дата"
                                }, {
                                    "mData" : "eventName",
                                    "sTitle" : "Событие"
                                }, {
                                    "mData" : "productName",
                                    "sTitle" : "Название"
                                }, {
                                    "mData" : "revision",
                                    "sTitle" : "Ревизия"
                                }, {
                                    "mData" : "version",
                                    "sTitle" : "Версия"
                                } ]
                            });
                        }
                    }

                    function showHistory(id) {
                        if (id != undefined) {
                            $(".container").append("<div id='lists'></div>");
                            $("#lists")
                                    .append(
                                            "<table cellpadding='0' cellspacing='0' border='0' class='display' id='resTabList'></table>");
                            $('#resTabList').dataTable({
                                "sPaginationType" : "full_numbers",
                                "bProcessing" : true,
                                "sAjaxSource" : "/rest/eventService/" + id + "/list",
                                "sAjaxDataProp" : "event",
                                "oLanguage" : ruLang(),
                                "aoColumns" : [ {
                                    "mData" : "eventDate",
                                    "sTitle" : "Дата"
                                }, {
                                    "mData" : "eventName",
                                    "sTitle" : "Событие"
                                }, {
                                    "mData" : "productName",
                                    "sTitle" : "Название"
                                }, {
                                    "mData" : "revision",
                                    "sTitle" : "Ревизия"
                                }, {
                                    "mData" : "version",
                                    "sTitle" : "Версия"
                                } ]
                            });
                        }
                    }
                    
                    function showAdvancedSearch() {
                        $(".container").append("<div id='lists'></div>");
                        $("#lists").append("<div id='advSearchMenu' style='width: 300px;'></div>");
                        $("#advSearchMenu").append("<input class='input-block-level' style='margin-bottom:15px' type='text' placeholder='Компонент...'>");
                        $("#advSearchMenu").append("<input class='input-block-level' style='margin-bottom:15px' type='text' placeholder='Ревизия...'>");
                        $("#advSearchMenu").append("<input class='input-block-level' style='margin-bottom:15px' type='text' placeholder='Версия...'>");
                        $("#advSearchMenu").append("<button class='btn btn-primary' style='float:right' id='advancedSearchButton' type='button'>Найти</button>");
                    }

                    function close() {
                        $("#lists").remove();
                    }

                    $("#appsList").click(function() {
                        $("#historyList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#appsList").attr("class", "active");
                        $("#advancedSearchList").removeAttr("class");
                        close();
                        showApplications(map[$("#search").val()]);
                    });

                    $("#historyList").click(function() {
                        $("#appsList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#historyList").attr("class", "active");
                        $("#advancedSearchList").removeAttr("class");
                        close();
                        showHistory(map[$("#search").val()]);
                    });
                    
                    $("#advancedSearchList").click(function() {
                        $("#appsList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#historyList").removeAttr("class");
                        $("#advancedSearchList").attr("class", "active");
                        close();
                        showAdvancedSearch();
                    });

                });
