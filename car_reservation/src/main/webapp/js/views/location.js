var lat;
var lng;

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
                        editable: true,
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
                editable: true,
                tooltip: true,
                editaction: "click",
                url: "api/location/",
                on: {
                    onAfterContextMenu: function (item) {
                        this.select(item.row);
                    }
                }
            }
        ]
    },
/*
    changeLocationDialog: {
        view: "popup",
        id: "changeLocationDialog",
        modal: true,
        position: "center",
        body: {
            id: "changeLocationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-map'></span> Izmjena lokacija",
                            width: 400
                        },
                        {},
                        {
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('changeLocationDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "changeLocationForm",
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
                            view: "text",
                            id: "name",
                            name: "name",
                            label: "Naziv:",
                            invalidMessage: "Molimo Vas da unesete naziv lokacije.",
                            required: true
                        },
                        {
                            view: "text",
                            id: "description",
                            name: "description",
                            label: "Opis:",
                            required: false
                        },
                        {
                            view: "fieldset",
                            label: "Podaci o adresi lokacije",
                            body: {
                                rows: [
                                    {
                                        view: "label",
                                        label: "Unesite lokaciju zgrade: ",
                                        inputWidth: 100,
                                    },
                                    {
                                        view: "select",
                                        options: countries,
                                        label: "Država:",
                                        id: "combo"
                                    },
                                    {
                                        view: "text",
                                        id: "city",
                                        name: "city",
                                        label: "Grad:",
                                        invalidMessage: "Molimo Vas da unesete naziv grada.",
                                        required: true
                                    },
                                    {
                                        view: "text",
                                        id: "address",
                                        name: "address",
                                        label: "Adresa:",
                                        invalidMessage: "Molimo Vas da unesete adresu.",
                                        required: true
                                    }
                                ]
                            }
                        },
                        {
                            margin: 5,
                            cols: [
                                {
                                    id: "showMap",
                                    view: "button",
                                    value: "Detaljna lokacija:",
                                    click: "locationView.showMap",
                                    width: 150
                                },
                                {},
                                {
                                    id: "saveLocation",
                                    view: "button",
                                    value: "Sačuvajte izmjene",
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
                            if (value.length > 128) {
                                $$('changeLocationForm').elements.name.config.invalidMessage = 'Maksimalan broj karaktera je 128.';
                                return false;
                            }

                            return true;
                        },
                        "description": function (value) {
                            if (value.length > 500) {
                                $$('changeLocationForm').elements.description.config.invalidMessage = 'Maksimalan broj karaktera je 500.';
                                return false;
                            }

                            return true;
                        }
                    }
                }
            ]
        }
    },
*/
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
                   /* var context = this.getContext();
                    switch (id) {
                        case "1":
                            locationView.showChangeLocationDialog($$("locationTable").getItem(context.id.row));
                            break;
                        case "2":
                            var delBox = (webix.copy(commonViews.brisanjePotvrda("lokacije", "lokaciju")));
                            var newItem = $$("locationTable").getItem(context.id.row);

                            delBox.callback = function (result) {
                                if (result == 1) {
                                    webix.ajax().del("hub/location/" + newItem.id).then(function (data) {
                                        if(data.text() === "Success"){
                                            util.messages.showMessage("Uspješno brisanje lokacije.");
                                            $$("locationTable").remove(context.id.row);
                                        }
                                        else{
                                            util.messages.showErrorMessage("Neuspješno brisanje lokacije.");
                                        }
                                    }).fail(function (error) {
                                        util.messages.showErrorMessage(error.responseText);
                                    });
                                }
                            };
                            webix.confirm(delBox);
                            break;
                        case "3":
                            locationView.showMapDetailsDialog($$("locationTable").getItem(context.id.row));
                            break;
                    }*/
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

    /*
    showMapDetailsDialog: function (location) {
        if (util.popupIsntAlreadyOpened("showMapDialog")) {
            webix.ui(webix.copy(locationView.showMapDialog));
            $$("mapLabel").data.label = "<span class='webix_icon fa fa-map-marker'></span> Lokacija";
            $$("saveMap").data.hidden = true;

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
                            window.alert("Lokacija " + location.name + " ne može biti locirana.");
                        }
                    } else {
                        window.alert("Lokacija " + location.name + " ne može biti locirana.");
                    }
                });
            });

            $$("showMapDialog").show();
        }
    },

    showChangeLocationDialog: function (location) {
        if (util.popupIsntAlreadyOpened("changeLocationDialog")) {
            var url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.latitude + "," + location.longitude + "&language=hr";
            fetch(url).then(function (result) {
                if (result.ok) {
                    return result.json();
                }
                throw new Error('Neuspješno dobavljanje tačne lokacije.');
            }).then(function (json) {
                var place = json['results'][0];

                var filtered_array = place.address_components.filter(function (address_component) {
                    return address_component.types.includes("country");
                });

                var country_long = filtered_array.length ? filtered_array[0].long_name : "";
                var country_short = filtered_array.length ? filtered_array[0].short_name : "";
                var filtered_array2 = place.address_components.filter(function (address_component) {
                    return address_component.types.includes("locality");
                });

                var city = filtered_array2.length ? filtered_array2[0].long_name : "";
                if (city == null) {
                    city = "" , console.log("grad prazan")
                }
                form.elements.city.setValue(city);
                if (country_short == "") {
                    country_short = "", console.log("grad prazan2")
                }
                ;
                if (country_long == "") {
                    country_long = "", console.log("grad prazan3")
                }
                ;
                console.log(country_long + " : " + country_short);
                state = country_long.trim() + " : " + country_short.trim();
                $$("combo").setValue(state);
            }).catch(function (error) {
                util.messages.showErrorMessage("Neuspješno dobavljanje grada.")
            });

            webix.ui(webix.copy(locationView.changeLocationDialog));
            var form = $$("changeLocationForm");

            form.elements.id.setValue(location.id);
            form.elements.name.setValue(location.name);
            form.elements.description.setValue(location.description);
            form.elements.address.setValue(location.address);

            $$("changeLocationDialog").show();
            webix.UIManager.setFocus("name");
        }
    },
    */

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
                                                    console.log("Location not found.");
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
                            key: "",
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
/*
    saveChanges: function () {
        var form = $$("changeLocationForm");
        if (form.validate()) {
            var address = $$("address").getValue();
            var res = address.replace(/ /g, "+");
            var country = $$("combo").getValue().split(" : ")[0];
            country = country.replace(/ /g, "+");
            var city = $$("city").getValue();
            city = city.replace(/ /g, "+");
            var query = res + "+" + city + "+" + country;
            var url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + query + "&language=hr";
            fetch(url).then(function (result) {
                if (result.ok) {
                    return result.json();
                }
                throw new Error('Neuspješno dobavljanje tačne lokacije.');
            }).then(function (json) {
                var validate = json['results'][0]['geometry']['location_type'];
                if (validate == 'APPROXIMATE') {
                    util.messages.showErrorMessage("Neispravna adresa!")
                } else {
                    lat = json['results'][0]['geometry']['location']['lat'];
                    lng = json['results'][0]['geometry']['location']['lng'];
                    var newItem = {
                        id: $$("changeLocationForm").getValues().id,
                        name: $$("changeLocationForm").getValues().name,
                        description: $$("changeLocationForm").getValues().description,
                        address: $$("changeLocationForm").getValues().address,
                        latitude: lat,
                        longitude: lng,
                        companyId: companyData.id,
                        deleted: 0
                    };

                    webix.ajax().header({"Content-type": "application/json"})
                        .put("hub/location/" + newItem.id, newItem).then(function (data) {
                        if (data.text() === "Success") {
                            util.messages.showMessage("Lokacija uspješno izmijenjena.");
                            $$("locationTable").updateItem(newItem.id, newItem);
                        }
                        else {
                            util.messages.showMessage("Lokacija neuspješno izmijenjena.");
                        }
                    }).fail(function (error) {
                        util.messages.showErrorMessage(error.responseText);
                    });

                    util.dismissDialog('changeLocationDialog');
                }
            });
        }
    },


*/
};
