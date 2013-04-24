$(document)
        .ready(
                function() {
                    
                    init = function() {
                        $("#search").val("");
                        $("#product").val("");
                        $("#version").val("");
                        $("#revision").val("");
                    }();
                    
                    function initHostList() {
                        res = [];
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

                    $("#search").typeahead({
                        source : initHostList(),
                        updater : function(item) {
                            close();
                            var pos = this.source.indexOf(item);
                            if ($("#appsList").attr("class") == "active") {
                                showApplications(map[item]);
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
                                $("#advancedSearch").css("display","none");
                                $("#serverNavigation").css("display","block");
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
                    
                    $("#advancedSearchButton").click(function() {
                        close();
                        $(".container").append("<div id='lists'></div>");
                        $("#lists")
                                .append(
                                        "<table cellpadding='0' cellspacing='0' border='0' class='display' id='resTabList'></table>");
                        $('#resTabList').dataTable({
                            "sPaginationType" : "full_numbers",
                            "bProcessing" : true,
                            "sAjaxSource" : "/rest/host/list",
                            "sAjaxDataProp" : "host",
                            "oLanguage" : ruLang(),
                            "fnServerData": function ( sSource, aoData, fnCallback ) {
                                    aoData = '{"productName":"'+$("#product").val()+'",' 
                                        +'"version":"'+$("#version").val()+'",' 
                                        +'"revision":"'+$("#revision").val()+'"}';
                                    $.ajaxSetup({
                                        contentType: "application/json; charset=utf-8"
                                    });
                                    $.post(sSource, aoData, function (json) {
                                                                fnCallback(json); 
                                                             },
                                            'json');
                            },
                            "aoColumns" : [ {
                                "mData" : "hostName",
                                "sTitle" : "Хост"
                            }, {
                                "mData" : "profile",
                                "sTitle" : "Профиль"
                            }, {
                                "mData" : "adminPort",
                                "sTitle" : "Admin порт"
                            }, {
                                "mData" : "webPort",
                                "sTitle" : "Http порт"
                            }  ]
                        });
                    });

                    function close() {
                        $("#lists").remove();
                    }

                    $("#appsList").click(function() {
                        $("#historyList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#appsList").attr("class", "active");
                        $("#advancedSearchList").removeAttr("class");
                        $("#advancedSearch").css("display","none");
                        $("#serverNavigation").css("display","block");
                        close();
                        showApplications(map[$("#search").val()]);
                    });

                    $("#historyList").click(function() {
                        $("#appsList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#historyList").attr("class", "active");
                        $("#advancedSearchList").removeAttr("class");
                        $("#advancedSearch").css("display","none");
                        $("#serverNavigation").css("display","block");
                        close();
                        showHistory(map[$("#search").val()]);
                    });
                    
                    $("#advancedSearchList").click(function() {
                        $("#appsList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#historyList").removeAttr("class");
                        $("#advancedSearchList").attr("class", "active");
                        $("#advancedSearch").css("display","block");
                        $("#serverNavigation").css("display","none");
                        close();
                    });

                });
