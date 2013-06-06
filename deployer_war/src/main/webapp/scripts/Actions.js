var Actions = (function() {
    
    return {
        showAppsHistory: function(id, appName) {
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
                    "oLanguage" : DataTablesRus.ruLang(),
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
        },
        showHistory: function (id) {
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
                    "oLanguage" : DataTablesRus.ruLang(),
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
        },
        showApplications: function (id, map, val) {
            var _this = this;
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
                    "oLanguage" : DataTablesRus.ruLang(),
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
                    var hostId = map[val];
                    var appName = $("td:eq(1)", this).text();
                    $("#historyList").removeAttr("class");
                    $("#appsList").removeAttr("class");
                    $("#appHistoryList").attr("class", "active");
                    $("#advancedSearchList").removeAttr("class");
                    $("#advancedSearch").css("display","none");
                    $("#serverNavigation").css("display","block");
                    
                    $("#lists").remove();
                    
                    _this.showAppsHistory(hostId, appName);
                });
            }
        },
        advancedSearch: function (product, version, revision) {
            $(".container").append("<div id='lists'></div>");
            $("#lists")
                    .append(
                            "<table cellpadding='0' cellspacing='0' border='0' class='display' id='resTabList'></table>");
            $('#resTabList').dataTable({
                "sPaginationType" : "full_numbers",
                "bProcessing" : true,
                "sAjaxSource" : "/rest/host/list",
                "sAjaxDataProp" : "host",
                "oLanguage" : DataTablesRus.ruLang(),
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData = '{"productName":"'+product+'",' 
                            +'"version":"'+version+'",' 
                            +'"revision":"'+revision+'"}';
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
        }
    };
})();