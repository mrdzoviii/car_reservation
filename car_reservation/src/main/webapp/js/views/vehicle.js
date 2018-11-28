var manufacturers = [];
var models = [];
var firstLocation;
var locations = [];
var locationFull = [];

var vehicleView = {
        panel: {
            id: "vehiclePanel",
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
                            template: "<span class='fa fa-car'></span> Vehicles"
                        },
                        {},
                        {
                            id: "locationFilter",
                            name: "locationFilter",
                            view: "select",
                            value: 0,
                            options: locations,
                            width: 400,
                            on: {
                                onChange: function (newv, oldv) {
                                    $$("vehicleDV").filter(function (obj) {
                                        if (newv === undefined)
                                            newv = oldv;
                                        if (newv != 0) {
                                            return obj.locationId == newv;
                                        }
                                        return obj.locationId != 0;
                                    });
                                }
                            }
                        },
                        {
                            id: "addVehicleBtn",
                            view: "button",
                            type: "iconButton",
                            label: "Add new vehicle",
                            click: "vehicleView.showAddDialog",
                            icon: "plus-circle",
                            autowidth: true
                        }
                    ]
                },
                {
                    view: "dataview",
                    id: "vehicleDV",
                    container: "dataA",
                    select: true,
                    onContext: {},
                    on: {
                        onAfterContextMenu: function (item) {
                            this.select(item);
                        }
                    },
                    url: "api/car",
                    type: {
                        height: 620,
                        width: 370
                    },
                    template: "<div><div style='height: 13px'></div><div align='center'><img src='data:image/png;base64, #image#' alt='No photo' width='300' height='300' align='center'/></div><br/>" +
                        "<div style='height: 1px' align='center'>Manufacturer: #manufacturerName#</div><br/>" +
                        "<div style='height: 1px' align='center'>Model: #model#</div><br/>" +
                        "<div style='height: 1px' align='center'>Transmission: #transmission#</div><br/>" +
                        "<div style='height: 1px' align='center'>Year: #year#</div><br/>" +
                        "<div style='height: 1px' align='center'>Engine: #engine#</div><br/>" +
                        "<div style='height: 1px' align='center'>Fuel: #fuelName#</div><br/>" +
                        "<div style='height: 1px' align='center'>Plate number: #plateNumber#</div><br/>" +
                        "</div>",
                    /* on: {
                         onItemDblClick: function (id) {
                             vehicleView.showVehicleDetailsDialog($$("vehicleDataView").getSelectedItem().id);
                         }
                     }*/
                }
            ]
        },

        selectPanel: function () {
            $$("main").removeView(rightPanel);
            rightPanel = "vehiclePanel";
            var panelCopy = webix.copy(this.panel);
            $$("main").addView(webix.copy(panelCopy));
            webix.ui({
                view: "contextmenu",
                id: "vehicleContextMenu",
                width: 230,
                data: [
                    {
                        id: "1",
                        value: "Edit",
                        icon: "pencil-square-o"
                    },
                    {
                        id: "2",
                        value: "Delete",
                        icon: "trash"
                    },
                    {
                        $template: "Separator"
                    },
                    {
                        id: "3",
                        value: "Show on map",
                        icon: "map-marker"
                    },
                ],
                master: $$("vehicleDV"),
                on: {
                    onItemClick: function (id) {
                        var context = this.getContext();
                        switch (id) {
                            case "1":
                                vehicleView.showEditDialog($$("vehicleDV").getSelectedItem());
                                break;
                        case "2":
                            var delBox = (webix.copy(commonViews.deleteConfirm("vehicle", "vehicle")));
                            var item = $$("vehicleDV").getSelectedItem();

                            delBox.callback = function (result) {
                                if (result == 1) {
                                    connection.sendAjax("DELETE", "api/car/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("vehicleDV").remove(item.id);
                                            util.messages.showMessage("Vehicle removed");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage(text);
                                    });
                                }
                            };
                            webix.confirm(delBox);
                            break;
                            case "3":
                                var item = $$("vehicleDV").getSelectedItem();
                                vehicleView.showMapDetailsDialog(item.latitude, item.longitude, item.locationName);
                                break;

                        }

                    }
                }
            });

            if (userData.roleId === role.user) {
                $$("addVehicleBtn").define("hidden", true);
                $$("addVehicleBtn").refresh();

                var userContextMenu = [
                    {
                        id: "3",
                        value: "Show on map",
                        icon: "map-marker"
                    },
                ];

                $$("vehicleContextMenu").clearAll();
                $$("vehicleContextMenu").define("data", userContextMenu);
                $$("vehicleContextMenu").refresh();
            }
        },
        showMapDetailsDialog: function (latitude, longitude, locationName) {
            if (util.popupIsntAlreadyOpened("showMapDialog")) {
                webix.ui(webix.copy(vehicleView.showMapDialog));
                $$("mapLabel").data.label = "<span class='webix_icon fa fa-map-marker'></span>" + locationName;
                $$("map").getMap("waitMap").then(function (mapObj) {
                    var geocoder = new google.maps.Geocoder();
                    var latlng = {
                        lat: parseFloat(latitude),
                        lng: parseFloat(longitude)
                    };

                    var center = new google.maps.LatLng(latitude, longitude);
                    mapObj.panTo(center);
                    geocoder.geocode({'location': latlng}, function (results, status) {
                        if (status === 'OK') {
                            if (results[0]) {
                                var marker = new google.maps.Marker({
                                    position: latlng,
                                    map: mapObj,
                                });
                            } else {
                                util.messages.showErrorMessage("Location " + locationName + " cannot be found.");
                            }
                        } else {
                            util.messages.showErrorMessage("Location " + locationName + " cannot be found.");
                        }
                    });
                });

                $$("showMapDialog").show();
            }
        },
        showMapDialog: {
            view: "popup",
            id: "showMapDialog",
            modal: true,
            position: "center",
            body: {
                id: "showMapDialogInside",
                rows: [
                    {
                        view: "toolbar",
                        cols: [
                            {
                                id: "mapLabel",
                                view: "label",
                                label: "<span class='webix_icon fa fa-map-marker '></span>",
                                width: 600,
                            },
                            {},
                            {
                                hotkey: 'esc',
                                view: "icon",
                                icon: "close",
                                align: "right",
                                click: "util.dismissDialog('showMapDialog');"
                            }
                        ]
                    },
                    {
                        key: "",
                        view: "google-map",
                        id: "map",
                        zoom: 15,
                        width: 600,
                        height: 500
                    }
                ]
            }
        },
        preloadDependencies: function () {
            webix.ajax().get("api/manufacturer").then(function (data) {
                manufacturers.length = 0;
                var manufacturersTemp = data.json();
                manufacturersTemp.forEach(function (obj) {
                    manufacturers.push({id: obj.id, value: obj.name});
                });
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });

            webix.ajax().get("api/location").then(function (data) {
                locations.length = 0;
                locations.push({id: 0, value: "All locations"});
                var locationsTemp = data.json();
                firstLocation = 0;
                locationsTemp.forEach(function (obj) {
                    locations.push({id: obj.id, value: obj.name + " @" + obj.address});
                });
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
        }
        ,

        loadModels: function (manufacturer) {
            if (manufacturer !== "undefined") {
                webix.ajax().get("api/model/manufacturer/" + manufacturer).then(function (data) {
                    models.length = 0;
                    var modelsTemp = data.json();
                    modelsTemp.forEach(function (obj) {
                        models.push({id: obj.id, value: obj.model, data: obj});
                    });
                    $$("model").define("suggest", {
                        data: models,
                        on: {
                            onValueSuggest: function (obj) {
                                console.log(obj);
                                $$("modelId").define("value", obj.id);
                                $$("engine").define("value", obj.data.engine);
                                $$("transmission").define("value", obj.data.transmission);
                                $$("year").define("value", obj.data.year);
                                $$("fuelId").define("value", obj.data.fuelId);
                                $$("image").setValues({
                                    src: "data:image/png;base64," + obj.data.image
                                });
                                $$("image").refresh();
                                $$("fuelId").refresh();
                                $$("year").refresh();
                                $$("transmission").refresh();
                                $$("engine").refresh();
                                $$("modelId").refresh();
                            }
                        }
                    });
                    $$("model").refresh();
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
            }
        },

        addVehicleDialog: {
            view: "popup",
            id: "addVehicleDialog",
            modal: true,
            position: "center",
            body: {
                id: "addVehicleInside",
                rows: [
                    {
                        margin: 5,
                        bottomPadding: 5,
                        view: "toolbar",
                        cols: [
                            {
                                view: "label",
                                label: "<span class='webix_icon fa fa-car'></span>Add new vehicle",
                                width: 400
                            },
                            {},
                            {
                                hotkey: 'esc',
                                view: "icon",
                                icon: "close",
                                align: "right",
                                click: "util.dismissDialog('addVehicleDialog');"
                            }
                        ]
                    },
                    {
                        cols: [
                            {
                                "view": "template",
                                borderless: true,
                                width: 30,
                                "template": "<p></p>"
                            },
                            {
                                view: "form",
                                id: "addVehicleForm",
                                borderless: true,
                                width: 600,
                                elementsConfig: {
                                    labelWidth: 200,
                                    bottomPadding: 18
                                },
                                elements: [
                                    {
                                        view: "text",
                                        id: "manufacturerId",
                                        name: "manufacturerId",
                                        hidden: true,
                                    },
                                    {
                                        view: "text",
                                        id: "manufacturerName",
                                        name: "manufacturerName",
                                        label: "Manufacturer:",
                                        invalidMessage: "Please enter manufacturer.",
                                        required: true,
                                        suggest: {
                                            data: manufacturers,
                                            on: {
                                                onValueSuggest: function (obj) {
                                                    $$("addVehicleForm").elements.manufacturerId.setValue(obj.id);
                                                    vehicleView.loadModels(obj.id);
                                                }
                                            }
                                        }

                                    },
                                    {
                                        view: "text",
                                        id: "modelId",
                                        name: "modelId",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "model",
                                        name: "model",
                                        label: "Model:",
                                        invalidMessage: "Enter vehicle model.",
                                        required: true,
                                    },
                                    {
                                        view: "text",
                                        id: "plateNumber",
                                        name: "plateNumber",
                                        label: "Plate number:",
                                        invalidMessage: "Enter plate number.",
                                        required: true
                                    },
                                    {
                                        view: "text",
                                        id: "transmission",
                                        name: "transmission",
                                        label: "Vehicle transmission:",
                                        invalidMessage: "Enter vehicle transmission.",
                                        required: true
                                    },
                                    {
                                        view: "text",
                                        id: "year",
                                        name: "year",
                                        label: "Year:",
                                        invalidMessage: "Enter year.",
                                        required: true,
                                    },
                                    {
                                        view: "text",
                                        id: "engine",
                                        name: "engine",
                                        label: "Engine:",
                                        invalidMessage: "Enter engine description.",
                                        required: true
                                    },
                                    {
                                        view: "richselect",
                                        id: "fuelId",
                                        name: "fuelId",
                                        label: "Fuel type:",
                                        invalidMessage: "Select fuel type.",
                                        required: true
                                    },
                                    {
                                        id: "locationId",
                                        name: "locationId",
                                        view: "richselect",
                                        label: "Location:",
                                        invalidMessage: "Select location",
                                        required: true,
                                        on: {
                                            onChange: function (newv, old) {
                                                var obj = locationFull.filter(function (item) {
                                                    return item.id === newv;
                                                })[0];
                                                $$("addVehicleForm").elements.locationName.setValue(obj.name);
                                                $$("addVehicleForm").elements.longitude.setValue(obj.latitude);
                                                $$("addVehicleForm").elements.latitude.setValue(obj.longitude);
                                            }
                                        }
                                    },
                                    {
                                        id: "deleted",
                                        name: "deleted",
                                        view: "text",
                                        value: 0,
                                        hidden: true
                                    },
                                    {
                                        id: "companyId",
                                        name: "companyId",
                                        view: "text",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "companyName",
                                        name: "companyName",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "locationName",
                                        name: "locationName",
                                        hidden: true
                                    },
                                    {
                                        id: "longitude",
                                        name: "longitude",
                                        hidden: true,
                                        view: "text"
                                    },
                                    {
                                        id: "latitude",
                                        name: "latitude",
                                        hidden: true,
                                        view: "text"
                                    }
                                ],
                                rules: {
                                    "plateNumber": function (value) {
                                        if (value.length > 10) {
                                            $$('addVehicleForm').elements.plateNumber.config.invalidMessage = 'Maximum length is 10.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "year": function (value) {
                                        var regex = /[0-9]{4}/;
                                        if (!regex.test(value)) {
                                            $$('addVehicleForm').elements.year.config.invalidMessage = 'Incorrect year.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "engine": function (value) {
                                        if (value.length > 100) {
                                            $$('addVehicleForm').elements.engine.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "transmission": function (value) {
                                        if (value.length > 100) {
                                            $$('addVehicleForm').elements.transmission.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "manufacturerName": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.manufacturerName.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "model": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.model.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    }
                                }
                            },
                            {
                                "view": "template",
                                borderless: true,
                                width: 30,
                                height: 30,
                                "template": "<p></p>"
                            },
                            {
                                rows: [
                                    {
                                        "view": "template",
                                        borderless: true,
                                        height: 50,
                                        "template": "<p>Photo</p>"
                                    },
                                    {
                                        view: "template",
                                        borderless: true,
                                        id: "image",
                                        name: "image",
                                        width: 300,
                                        height: 300,
                                        template: "<img src='#src#' width='300' height='300' class='photo-alignment'/>",
                                        onClick: {
                                            "photo-alignment": function (e, id, trg) {
                                                $$("vehicleUploadAPI").fileDialog();
                                                return false;
                                            }
                                        },
                                    },
                                    {
                                        height: 90,
                                    },
                                    {
                                        marginTop: 20,
                                        cols: [
                                            {},
                                            {
                                                id: "saveVehicle",
                                                view: "button",
                                                value: "Save",
                                                type: "form",
                                                click: "vehicleView.save",
                                                hotkey: "enter",
                                                width: 270,
                                                height: 70
                                            },
                                            {}
                                        ]
                                    },
                                    {},
                                    {}
                                ]
                            }
                        ]
                    }
                ]
            }
        },

        showAddDialog: function () {
            if (util.popupIsntAlreadyOpened("addVehicleDialog")) {
                webix.ui(webix.copy(vehicleView.addVehicleDialog)).show();
                webix.UIManager.setFocus("manufacturerId");
                $$("fuelId").define("options", dependency.fuel);
                $$("image").setValues({src: "../../img/car-default.jpg"});
                var locationData = [];
                webix.ajax().get("api/location").then(function (data) {
                    var locationsTemp = data.json();
                    locationFull = locationsTemp;
                    locationsTemp.forEach(function (obj) {
                        locationData.push({id: obj.id, value: obj.name + " @" + obj.address, data: obj});
                    });
                    $$("locationId").define("options", locationData);
                    $$("locationId").refresh();
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
                $$("addVehicleForm").elements.companyName.setValue(userData.companyName);
                $$("addVehicleForm").elements.companyId.setValue(userData.companyId);
                $$("companyId").refresh();
                $$("fuelId").refresh();
                $$("image").refresh();
            }
        },


        save: function () {
            if ($$("addVehicleForm").validate()) {
                var newCar = $$("addVehicleForm").getValues();
                newCar.image = $$("image").getValues()["src"].split("base64,")[1];
                console.log(newCar);
                webix.ajax().header({"Content-type": "application/json"})
                    .post("api/car/custom", newCar).then(function (data) {
                    $$("vehicleDV").add(data.json());
                    $$("vehicleDV").refresh();
                    $$("locationFilter").setValue(newCar.locationId);
                    $$("locationFilter").refresh();
                    util.messages.showMessage("Vehicle added successfully.");
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
                util.dismissDialog('addVehicleDialog');
            }
        },
        editVehicleDialog: {
            view: "popup",
            id: "editVehicleDialog",
            modal: true,
            position: "center",
            body: {
                id: "editVehicleInside",
                rows: [
                    {
                        margin: 5,
                        bottomPadding: 5,
                        view: "toolbar",
                        cols: [
                            {
                                view: "label",
                                label: "<span class='webix_icon fa fa-car'></span>Edit vehicle",
                                width: 400
                            },
                            {},
                            {
                                hotkey: 'esc',
                                view: "icon",
                                icon: "close",
                                align: "right",
                                click: "util.dismissDialog('editVehicleDialog');"
                            }
                        ]
                    },
                    {
                        cols: [
                            {
                                "view": "template",
                                borderless: true,
                                width: 30,
                                "template": "<p></p>"
                            },
                            {
                                view: "form",
                                id: "editVehicleForm",
                                borderless: true,
                                width: 600,
                                elementsConfig: {
                                    labelWidth: 200,
                                    bottomPadding: 18
                                },
                                elements: [
                                    {
                                        view: "text",
                                        id: "id",
                                        name: "id",
                                        hidden: true,
                                    },
                                    {
                                        view: "text",
                                        id: "manufacturerId",
                                        name: "manufacturerId",
                                        hidden: true,
                                    },
                                    {
                                        view: "text",
                                        id: "manufacturerName",
                                        name: "manufacturerName",
                                        label: "Manufacturer:",
                                        invalidMessage: "Please enter manufacturer.",
                                        required: true,
                                        suggest: {
                                            data: manufacturers,
                                            on: {
                                                onValueSuggest: function (obj) {
                                                    $$("editVehicleForm").elements.manufacturerId.setValue(obj.id);
                                                    vehicleView.loadModels(obj.id);
                                                }
                                            }
                                        }

                                    },
                                    {
                                        view: "text",
                                        id: "modelId",
                                        name: "modelId",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "model",
                                        name: "model",
                                        label: "Model:",
                                        invalidMessage: "Enter vehicle model.",
                                        required: true,
                                    },
                                    {
                                        view: "text",
                                        id: "plateNumber",
                                        name: "plateNumber",
                                        label: "Plate number:",
                                        invalidMessage: "Enter plate number.",
                                        required: true
                                    },
                                    {
                                        view: "text",
                                        id: "transmission",
                                        name: "transmission",
                                        label: "Vehicle transmission:",
                                        invalidMessage: "Enter vehicle transmission.",
                                        required: true
                                    },
                                    {
                                        view: "text",
                                        id: "year",
                                        name: "year",
                                        label: "Year:",
                                        invalidMessage: "Enter year.",
                                        required: true,
                                    },
                                    {
                                        view: "text",
                                        id: "engine",
                                        name: "engine",
                                        label: "Engine:",
                                        invalidMessage: "Enter engine description.",
                                        required: true
                                    },
                                    {
                                        view: "richselect",
                                        id: "fuelId",
                                        name: "fuelId",
                                        label: "Fuel type:",
                                        invalidMessage: "Select fuel type.",
                                        required: true
                                    },
                                    {
                                        id: "locationId",
                                        name: "locationId",
                                        view: "richselect",
                                        label: "Location:",
                                        invalidMessage: "Select location",
                                        required: true,
                                        on: {
                                            onChange: function (newv, old) {
                                                var obj = locationFull.filter(function (item) {
                                                    return item.id === newv;
                                                })[0];
                                                $$("editVehicleForm").elements.locationName.setValue(obj.name);
                                                $$("editVehicleForm").elements.longitude.setValue(obj.latitude);
                                                $$("editVehicleForm").elements.latitude.setValue(obj.longitude);
                                            }
                                        }
                                    },
                                    {
                                        id: "deleted",
                                        name: "deleted",
                                        view: "text",
                                        value: 0,
                                        hidden: true
                                    },
                                    {
                                        id: "companyId",
                                        name: "companyId",
                                        view: "text",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "companyName",
                                        name: "companyName",
                                        hidden: true
                                    },
                                    {
                                        view: "text",
                                        id: "locationName",
                                        name: "locationName",
                                        hidden: true
                                    },
                                    {
                                        id: "longitude",
                                        name: "longitude",
                                        hidden: true,
                                        view: "text"
                                    },
                                    {
                                        id: "latitude",
                                        name: "latitude",
                                        hidden: true,
                                        view: "text"
                                    }
                                ],
                                rules: {
                                    "plateNumber": function (value) {
                                        if (value.length > 10) {
                                            $$('editVehicleForm').elements.plateNumber.config.invalidMessage = 'Maximum length is 10.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "year": function (value) {
                                        var regex = /[0-9]{4}/;
                                        if (!regex.test(value)) {
                                            $$('editVehicleForm').elements.year.config.invalidMessage = 'Incorrect year.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "engine": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.engine.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "transmission": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.transmission.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "manufacturerName": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.manufacturerName.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    },
                                    "model": function (value) {
                                        if (value.length > 100) {
                                            $$('editVehicleForm').elements.model.config.invalidMessage = 'Maximum length is 1000.';
                                            return false;
                                        }

                                        return true;
                                    }
                                }
                            },
                            {
                                "view": "template",
                                borderless: true,
                                width: 30,
                                height: 30,
                                "template": "<p></p>"
                            },
                            {
                                rows: [
                                    {
                                        "view": "template",
                                        borderless: true,
                                        height: 50,
                                        "template": "<p>Photo</p>"
                                    },
                                    {
                                        view: "template",
                                        borderless: true,
                                        id: "image",
                                        name: "image",
                                        width: 300,
                                        height: 300,
                                        template: "<img src='#src#' width='300' height='300' class='photo-alignment'/>",
                                        onClick: {
                                            "photo-alignment": function (e, id, trg) {
                                                $$("vehicleUploadAPI").fileDialog();
                                                return false;
                                            }
                                        },
                                    },
                                    {
                                        height: 90,
                                    },
                                    {
                                        marginTop: 20,
                                        cols: [
                                            {},
                                            {
                                                id: "saveVehicle",
                                                view: "button",
                                                value: "Save",
                                                type: "form",
                                                click: "vehicleView.saveChanges",
                                                hotkey: "enter",
                                                width: 270,
                                                height: 70
                                            },
                                            {}
                                        ]
                                    },
                                    {},
                                    {}
                                ]
                            }
                        ]
                    }
                ]
            }
        },

        showEditDialog: function (item) {
            if (util.popupIsntAlreadyOpened("editVehicleDialog")) {
                var dialog = webix.ui(webix.copy(vehicleView.editVehicleDialog))
                webix.UIManager.setFocus("manufacturerId");
                $$("fuelId").define("options", dependency.fuel);
                $$("fuelId").define("value", item.fuelId);
                $$("image").setValues({src: "data:image/png;base64," + item.image});
                var locationData = [];
                webix.ajax().get("api/location").then(function (data) {
                    var locationsTemp = data.json();
                    locationFull = locationsTemp;
                    locationsTemp.forEach(function (obj) {
                        locationData.push({id: obj.id, value: obj.name + " @" + obj.address, data: obj});
                    });
                    $$("locationId").define("options", locationData);
                    $$("locationId").define("value", item.locationId);
                    $$("locationId").refresh();
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
                var form = $$("editVehicleForm");
                form.elements.companyName.setValue(item.companyName);
                form.elements.companyId.setValue(item.companyId);
                form.elements.model.setValue(item.model);
                form.elements.modelId.setValue(item.modelId);
                form.elements.deleted.setValue(item.deleted);
                form.elements.locationName.setValue(item.locationName);
                form.elements.longitude.setValue(item.longitude);
                form.elements.latitude.setValue(item.latitude);
                form.elements.id.setValue(item.id);
                form.elements.plateNumber.setValue(item.plateNumber);
                form.elements.year.setValue(item.year);
                form.elements.transmission.setValue(item.transmission);
                form.elements.engine.setValue(item.engine);
                form.elements.manufacturerName.setValue(item.manufacturerName);
                form.elements.manufacturerId.setValue(item.manufacturerId);

                $$("companyId").refresh();
                $$("fuelId").refresh();
                $$("image").refresh();
                dialog.show();
            }
        },


        saveChanges: function () {
            if ($$("editVehicleForm").validate()) {
                var newCar = $$("editVehicleForm").getValues();
                newCar.image = $$("image").getValues()["src"].split("base64,")[1];
                webix.ajax().header({"Content-type": "application/json"})
                    .put("api/car/custom/"+newCar.id, newCar).then(function (data) {
                        newCar=data.json();
                    $$("vehicleDV").updateItem(newCar.id,newCar);
                    $$("vehicleDV").refresh();
                    $$("locationFilter").setValue(newCar.locationId);
                    $$("locationFilter").refresh();
                    util.messages.showMessage("Vehicle changes made.");
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
                util.dismissDialog('editVehicleDialog');
            }
        },

    }
;

webix.ui(
    {
        id: "vehicleUploadAPI",
        view: "uploader",
        accept: "image/jpeg, image/png",
        autosend: false,
        width: 200,
        apiOnly: true,
        align: "center",
        multiple: false,
        on: {
            onBeforeFileAdd: function (upload) {
                var type = upload.type.toLowerCase();
                if (type != "jpg" && type != "png") {
                    util.messages.showErrorMessage("Allowed extensions .jpg and.png!")
                    return false;
                }
                var file = upload.file;
                var reader = new FileReader();
                reader.onload = function (event) {
                    var img = new Image();
                    img.onload = function (ev) {
                        if (img.width === 300 && img.height === 300) {
                            $$("image").setValues({
                                src: event.target.result
                            });
                        } else {
                            util.messages.showErrorMessage("Image dimension must be 300x300 px!")
                        }
                    };
                    img.src = event.target.result;
                };
                reader.readAsDataURL(file);

                return false;
            }
        }
    }
);