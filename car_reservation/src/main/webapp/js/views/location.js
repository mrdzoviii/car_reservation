var lat;
var lng;
var markerTmp;
var locationView = {
    panel: {
        id: "locationPanel",
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
                        template: "<span class='fa fa-map'></span> Company locations"
                    },
                    {},
                    {
                        id: "addLocationBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Add location",
                        click: "locationView.showAddLocationDialog",
                        icon: "plus-circle",
                        autowidth: true
                    }
                ]
            },
            {
                view: "datatable",
                css: "webixDatatable",
                multiselect: false,
                id: "locationDT",
                resizeColumn: true,
                resizeRow: true,
                onContext: {},
                columns: [
                    {
                        id: "id",
                        hidden: true,
                        fillspace: true,
                    },
                    {
                        id: "name",
                        fillspace: true,
                        editor: "text",
                        sort: "string",
                        editable:false,
                        tooltip: false,
                        header: [
                            "Name",
                            {
                                content: "textFilter"
                            }
                        ]
                    },
                    {
                        tooltip: false,
                        id: "address",
                        fillspace: true,
                        editable: false,
                        sort: "text",
                        header: [
                            "Address",
                            {
                                content: "textFilter"
                            }
                        ]
                    },
                    {
                        id: "latitude",
                        hidden: true,
                    },
                    {
                        id: "longitude",
                        hidden: true,
                    }
                ],
                select: "row",
                navigation: true,
                editable: false,
                tooltip: true,
                editaction: "click",
                url: "api/location/",
                on: {
                    onAfterContextMenu: function (item) {
                        this.select(item.row);
                    },
                    onItemDblClick:function(id,e,node){
                        locationView.showMapDialog(this.getSelectedItem());
                    }
                }
            }
        ]
    },


    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "locationPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("locationDT", "api/location", false);
        webix.ui({
            view: "contextmenu",
            id: "locationContextMenu",
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
            master: $$("locationDT"),
            on: {
                onItemClick: function (id) {
                   var context = this.getContext();
                    switch (id) {
                        case "1":
                            locationView.showChangeLocationDialog($$("locationDT").getSelectedItem());
                            break;
                        case "2":
                            var delBox = (webix.copy(commonViews.deleteConfirm("location", "location")));
                            delBox.callback = function (result) {
                                if (result) {
                                    var item = $$("locationDT").getItem(context.id.row);
                                    $$("locationDT").detachEvent("onBeforeDelete");
                                    connection.sendAjax("DELETE", "api/location/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("locationDT").remove(context.id.row);
                                            util.messages.showMessage("Location deleted");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage(text);
                                    }, item);
                                }
                            };
                            webix.confirm(delBox);
                            break;

                        case "3":
                            locationView.showMapDialog($$("locationDT").getItem(context.id.row));
                            break;
                    }
                }
            }
        });

        if(userData.roleId === role.user){
            $$("addLocationBtn").define("hidden", true);
            $$("addLocationBtn").refresh();
            $$("locationDT").define("editable", false);
            $$("locationDT").refresh();

            var userContextMenu = [
                {
                    id: "3",
                    value: "Show on map",
                    icon: "map-marker"
                },
            ];

            $$("locationContextMenu").clearAll();
            $$("locationContextMenu").define("data", userContextMenu);
            $$("locationContextMenu").refresh();
        }
    },


    mapDialog: {
        view: "popup",
        id: "mapDialog",
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
                            label: "<span class='webix_icon fa fa-map-marker '></span> Location",
                            width: 600,
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('mapDialog');"
                        }
                    ]
                },
                {
                    key: "AIzaSyBpbWc3t35Tahjs45QIi7TnpvV6b_UQSNs",
                    view: "google-map",
                    id: "map",
                    zoom: 15,
                    width: 600,
                    height: 500,
                    //center: tableCenter,
                    //data: tableData
                }
            ]
        }
    },

    showMapDialog: function (location) {
        if (util.popupIsntAlreadyOpened("mapDialog")) {
            webix.ui(webix.copy(locationView.mapDialog));
            $$("mapLabel").data.label = "<span class='webix_icon fa fa-map-marker'></span>"+location.name;
            $$("map").getMap("waitMap").then(function(mapObj) {
                var geocoder = new google.maps.Geocoder();

                var latlng = {
                    lat: parseFloat(location.latitude),
                    lng: parseFloat(location.longitude)
                };

                var center = new google.maps.LatLng(location.latitude, location.longitude);
                mapObj.panTo(center);

                console.log(latlng);

                geocoder.geocode({'location': latlng}, function(results, status) {
                    if (status === 'OK') {
                        if (results[0]) {
                            var marker = new google.maps.Marker({
                                position: latlng,
                                map: mapObj,
                            });
                        } else {
                            util.messages.showErrorMessage("Location " + location.name + " cannot be found.");
                        }
                    } else {
                        util.messages.showErrorMessage("Location " + location.name + " cannot be found.");
                    }
                });
            });

            $$("mapDialog").show();
        }
    },




    addLocationDialog: {
        view: "popup",
        id: "addLocationDialog",
        modal: true,
        position: "center",
        body: {
            id: "addLocationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-map'></span> New location",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addLocationDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "addLocationForm",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            view: "text",
                            id: "name",
                            name: "name",
                            label: "Name:",
                            invalidMessage: "Location name required.",
                            required: true
                        },
                        {
                            view: "text",
                            id: "address",
                            name: "address",
                            label: "Address:",
                            required: true,
                            invalidMessage: "Address required.",
                            on:{
                                onBlur:function (prev_view) {
                                        console.log(prev_view);
                                        $$("map").getMap("waitMap").then(function (mapObj) {
                                            var geocoder = new google.maps.Geocoder();
                                            geocoder.geocode({'address': prev_view.data.value}, function (results, status) {
                                                if (status == 'OK') {
                                                    mapObj.setCenter(results[0].geometry.location);
                                                    var marker = new google.maps.Marker({
                                                        draggable:false,
                                                        map: mapObj,
                                                        position: results[0].geometry.location
                                                    });
                                                    lat = results[0]['geometry']['location']['lat'];
                                                    lng = results[0]['geometry']['location']['lng'];
                                                    $$("latitude").define("value",lat());
                                                    $$("latitude").refresh();
                                                    $$("longitude").define("value",lng());
                                                    $$("longitude").refresh();
                                                } else {
                                                    console.log("Location not found");
                                                    $$("address").define("value",null);
                                                    $$("address").refresh();
                                            }
                                            });

                                        });

                                }
                            }
                        },
                        {
                          id:"deleted",
                            name:"deleted",
                            hidden:true,
                            value:0,
                            view:"text"
                        },
                        {
                            id:"companyId",
                            name:"companyId",
                            hidden:true,
                            view:"text"
                        },
                        {
                            id:"latitude",
                            name:"latitude",
                            hidden:true,
                            view:"text"
                        },
                        {
                            id:"longitude",
                            name:"longitude",
                            hidden:true,
                            view:"text"
                        },

                        {
                            view:"label",
                            label:"Map:"
                        },
                        {
                            key: "AIzaSyBpbWc3t35Tahjs45QIi7TnpvV6b_UQSNs",
                            view: "google-map",
                            id: "map",
                            label:"Map:",
                            zoom: 15,
                            width: 400,
                            height: 400
                            //center: tableCenter,
                            //data: tableData
                        },

                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "saveLocation",
                                    view: "button",
                                    value: "Save location",
                                    type: "form",
                                    click: "locationView.save",
                                    hotkey: "enter",
                                    width: 300
                                }
                            ]
                        }
                    ],
                    rules: {
                        "name": function (value) {
                            if (value.length > 50) {
                                $$('addLocationForm').elements.name.config.invalidMessage = 'Maximum length 50.';
                                return false;
                            }

                            return true;
                        },
                        "address":webix.rules.isNotEmpty
                        }

                }
            ]
        }
    },

    showAddLocationDialog: function () {
        if (util.popupIsntAlreadyOpened("addLocationDialog")) {
            webix.ui(webix.copy(locationView.addLocationDialog)).show();
            webix.UIManager.setFocus("name");
            $$("companyId").define("value",userData.companyId);
            $$("companyId").refresh();
        }
    },
    save: function () {
        var form = $$("addLocationForm");
        if (form.validate()) {
            var location = form.getValues();
            console.log(location);
            if(location.longitude && location.latitude) {
                $$("locationDT").add(location);
                util.messages.showMessage("Location added successfully.");
                util.dismissDialog('addLocationDialog');
            }else{
                util.messages.showErrorMessage("Location not found.Please enter valid address")
            }
        }
    },
    editLocationDialog: {
        view: "popup",
        id: "editLocationDialog",
        modal: true,
        position: "center",
        body: {
            id: "editLocationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-map'></span> Edit location",
                            width: 400
                        },
                        {},
                        {
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('editLocationDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "editLocationForm",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            name: "id",
                            view: "text",
                            hidden: true
                        },
                        {
                            name: "deleted",
                            view: "text",
                            hidden: true
                        },
                        {
                            id:"latitude",
                            name: "latitude",
                            view: "text",
                            hidden: true
                        },
                        {
                            id:"longitude",
                            name: "longitude",
                            view: "text",
                            hidden: true
                        },
                        {
                            name: "companyId",
                            view: "text",
                            hidden: true
                        },
                        {
                            view: "text",
                            id: "name",
                            name: "name",
                            label: "Name:",
                            invalidMessage: "Location name required.",
                            required: true
                        },
                        {
                            view: "text",
                            id: "address",
                            name: "address",
                            label: "Address:",
                            invalidMessage: "Location address required.",
                            required: true,
                            on: {
                                onBlur: function (prev_view) {
                                    console.log(prev_view);
                                    $$("editMap").getMap("waitMap").then(function (mapObj) {
                                        var geocoder = new google.maps.Geocoder();
                                        geocoder.geocode({'address': prev_view.data.value}, function (results, status) {
                                            if (status == 'OK') {
                                                mapObj.setCenter(results[0].geometry.location);
                                                var marker = new google.maps.Marker({
                                                    draggable: false,
                                                    map: mapObj,
                                                    position: results[0].geometry.location
                                                });
                                                markerTmp.setMap(null);
                                                markerTmp=marker;
                                                lat = results[0]['geometry']['location']['lat'];
                                                lng = results[0]['geometry']['location']['lng'];
                                                $$("latitude").define("value", lat());
                                                $$("latitude").refresh();
                                                $$("longitude").define("value", lng());
                                                $$("longitude").refresh();
                                            } else {
                                                console.log("Location not found");
                                                $$("address").define("value", null);
                                                $$("address").refresh();
                                            }
                                        });

                                    });

                                }
                            }
                        },
                        {
                            view:"label",
                            label:"Map:"
                        },
                        {
                            key: "AIzaSyBpbWc3t35Tahjs45QIi7TnpvV6b_UQSNs",
                            view: "google-map",
                            id: "editMap",
                            zoom: 15,
                            width: 400,
                            height: 400
                            //center: tableCenter,
                            //data: tableData
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "saveLocation",
                                    view: "button",
                                    value: "Save",
                                    type: "form",
                                    click: "locationView.saveChanges",
                                    hotkey: "enter",
                                    width: 150
                                }
                            ]
                        }
                    ],
                    rules: {
                        "name": function (value) {
                            if (value.length > 50) {
                                $$('editLocationForm').elements.name.config.invalidMessage = 'Maximum length is 50.';
                                return false;
                            }

                            return true;
                        }
                    }
                }
            ]
        }
    },
    showChangeLocationDialog: function (location) {
        if (util.popupIsntAlreadyOpened("editLocationDialog")) {
            webix.ui(webix.copy(locationView.editLocationDialog));
            $$("editLocationDialog").show();
            webix.UIManager.setFocus("name");
            $$("editMap").getMap("waitMap").then(function(mapObj) {
                var geocoder = new google.maps.Geocoder();
                var latlng = {
                    lat: parseFloat(location.latitude),
                    lng: parseFloat(location.longitude)
                };

                var center = new google.maps.LatLng(location.latitude, location.longitude);
                mapObj.panTo(center);
                geocoder.geocode({'location': latlng}, function(results, status) {
                    if (status === 'OK') {
                        if (results[0]) {
                            var marker = new google.maps.Marker({
                                position: latlng,
                                map: mapObj,
                            });
                            markerTmp=marker;
                        } else {
                            util.messages.showErrorMessage("Location " + location.name + " cannot be found.");
                        }
                    } else {
                        util.messages.showErrorMessage("Location " + location.name + " cannot be found.");
                    }
                });
            });

            console.log(location);
            var form = $$("editLocationForm");
            form.elements.id.setValue(location.id);
            form.elements.name.setValue(location.name);
            form.elements.address.setValue(location.address);
            form.elements.longitude.setValue(location.longitude);
            form.elements.latitude.setValue(location.latitude);
            form.elements.deleted.setValue(location.deleted);
            form.elements.companyId.setValue(location.companyId);

        }
    },
    saveChanges: function () {
        var form = $$("editLocationForm");
        if (form.validate()) {
            var newItem = form.getValues();
            connection.sendAjax("PUT", "api/location/" + newItem.id,
                function (text, data, xhr) {
                    if (text) {
                        util.messages.showMessage("Location updated successfully.");
                        $$("locationDT").updateItem(newItem.id,newItem);
                    } else
                        util.messages.showErrorMessage("Location edit failed.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, newItem);

            util.dismissDialog('editLocationDialog');
        }

    },
};
