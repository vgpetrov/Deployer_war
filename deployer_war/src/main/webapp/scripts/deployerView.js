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
                                            var expr = new RegExp('http://[A-Za-z0-9\\:]*/server/([A-Za-z0-9]*)/profile/([A-Za-z0-9]*)');
                                            var key = location.href.replace(expr, "$1 $2");
                                            var profileId = map[key];
                                            if (profileId!=undefined) {
                                                $("#search").val(key);
                                                Actions.showApplications(profileId, map, key);
                                            }
                                            return res;
                                        });
                        return res;
                    }

                    $("#search").typeahead({
                        source : initHostList(),
                        updater : function(item) {
                            close();
                            var pos = this.source.indexOf(item);
                            if ($("#appsList").attr("class") == "active") {
                                var profile = item.split(' ');
                                window.history.pushState("object or string", "Title", 
                                        "/server/"+profile[0]+"/profile/"+profile[1]);
                                Actions.showApplications(map[item], map, item);
                            } else if ($("#historyList").attr("class") == "active") {
                                Actions.showHistory(map[item]);
                            }
                            return item;
                        }
                    });
                    
                    $("#refreshButton").click(function() {
                        $("#search").data("typeahead").source = initHostList();
                    });
                    
                    $("#advancedSearchButton").click(function() {
                        close();
                        Actions.advancedSearch($("#product").val(),$("#version").val(),$("#revision").val());
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
                        Actions.showApplications(map[$("#search").val()], map, $("#search").val());
                    });

                    $("#historyList").click(function() {
                        $("#appsList").removeAttr("class");
                        $("#appHistoryList").removeAttr("class");
                        $("#historyList").attr("class", "active");
                        $("#advancedSearchList").removeAttr("class");
                        $("#advancedSearch").css("display","none");
                        $("#serverNavigation").css("display","block");
                        close();
                        Actions.showHistory(map[$("#search").val()]);
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
