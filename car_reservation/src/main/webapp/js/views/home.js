
var homeView = {
    panel: {
        id: "homePanel",
        adjust: true,
        rows: [
            {
                view: "toolbar",
                padding: 8,
                css: "panelToolbar",
                cols: [
                    {
                        view: "label",
                        width: 400,
                        template: "<span class='fa fa-home'></span>Home"
                    },
                    {}
                ]
            },
            {
                key: mapProperties.key,
                view: "google-map",
                id: "homeMap",
                zoom: mapProperties.zoom,
                center: [mapProperties.longitude,mapProperties.latitude]
            }
        ]
    },

    homeDialog: {
        view: "popup",
        id: "homeDialog",
        modal: true,
        position: "center",
        width: 1008,
        height: 586,
        body: {
            id: "homeInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            id: "homeLabel",
                            view: "label",
                            label: "<span class='webix_icon fa fa-car'></span> Location",
                            width: 900
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('homeDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            view: "dataview",
                            id: "homeDV",
                            container: "dataA",
                            select: true,
                            url: "",
                            type: {
                                height: 520,
                                width: 330
                            },
                            template: "<div><div style='height: 13px'></div><div align='center'><img src='data:image/png;base64, #image#' alt='No picture' width='300' height='300' align='center'/></div><br/>" +
                                "<div style='height: 1px' align='center'>Manufacturer: #manufacturerName#</div><br/>" +
                                "<div style='height: 1px' align='center'>Model: #modelName#</div><br/>" +
                                "<div style='height: 1px' align='center'>Plate number: #plateNumber#</div><br/>" +
                                "<div style='height: 1px' align='center'>Year: #year#</div><br/>" +
                                "<div style='height: 1px' align='center'>Engine: #engine#</div><br/>" +
                                "<div style='height: 1px' align='center'>Transmission: #transmission#</div><br/>" +
                                "<div style='height: 1px' align='center'>Fuel: #fuelName#</div><br/>" +
                                "</div>"
                        }
                    ]
                }
            ]
        }
    },

    showHomeDialog: function(selectedItem){
        if (util.popupIsntAlreadyOpened("homeDialog")) {
            webix.ui(webix.copy(homeView.homeDialog)).show();
            $$("homeLabel").define("label", "<span class='webix_icon fa fa-car'></span> " + selectedItem.name+" @"+selectedItem.address);
            $$("homeDV").clearAll();
            $$("homeDV").load("api/car/location/" + selectedItem.id).then(function () {
                $$("homeLabel").refresh();
                $$("homeDV").refresh();

                $$("homeDialog").show();
            });
        }
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "homePanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));

        $$("homeMap").getMap("waitMap").then(function (mapObj) {
            var geocoder = new google.maps.Geocoder();
            webix.ajax().get("api/location").then(function (data) {
                var locations = data.json();
                locations.forEach(function (obj) {
                    var latlng = {
                        lat: parseFloat(obj.latitude),
                        lng: parseFloat(obj.longitude)
                    };

                    geocoder.geocode({'location': latlng}, function(results, status) {
                        if (status === 'OK') {
                            if (results[0]) {
                                var marker = new google.maps.Marker({
                                    position: latlng,
                                    map: mapObj,
                                    item: obj
                                });
                                marker.addListener('click', function() {
                                    homeView.showHomeDialog(obj);
                                });
                            } else {
                                util.messages.showErrorMessage("Location " + obj.name + " not found.");
                            }
                        } else {
                            util.messages.showErrorMessage("Location " + obj.name + " not found.");
                        }
                    });
                })
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
        });

    }
};