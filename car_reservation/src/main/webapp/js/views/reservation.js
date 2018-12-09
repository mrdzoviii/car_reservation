var freeVehicles = [];
var firstFreeVehicle;
var reservationView = {
    panel: {
        id: "reservationPanel",
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
                        template: "<span class='fa fa-bookmark'></span> Reservation"
                    },
                    {},
                    {
                        id: "datePickerFilter",
                        view:"daterangepicker",
                        name:"dateRangePicker",
                        label:"Period:",
                        suggest:{
                            view:"daterangesuggest",
                            body:{
                                calendarCount:1,
                                icons:true,
                                css:"custom_date_picker_report",

                            }
                        },
                        on: {
                            onChange: function (dates) {
                                if (dates == undefined) {
                                    $$("reservationDT").filterByAll();
                                    return true;
                                }
                                if (dates.start != null && dates.end != null) {
                                    $$("reservationDT").filterByAll();
                                    var startingDate = new Date(dates.start);
                                    var endingDate = new Date(dates.end.getFullYear(), dates.end.getMonth(), dates.end.getDate() + 1);
                                    $$("reservationDT").filter(function (obj) {
                                        var startTime = new Date(obj.startTime);
                                        var endTime = new Date(obj.endTime);
                                        if (startTime >= startingDate && endTime <= endingDate && endTime >= startingDate && endTime <= endingDate)
                                            return true;
                                        return false;
                                    });
                                    $$("datePickerFilter").getPopup().hide();
                                }

                            }
                        }
                    },
                    {
                        id: "backBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Back",
                        click: "reservationView.back",
                        icon: "arrow-left",
                        autowidth: true,
                        hidden:true
                    },
                    {
                        id: "addReservationBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Make new reservation",
                        click: "reservationView.showAddDialog",
                        icon: "plus-circle",
                        autowidth: true
                    }
                ]
            },
            {
                id: "reservationDT",
                view: "datatable",
                css: "webixDatatable",
                select: true,
                navigation: true,
                fillspace: true,
                resizeColumn: true,

                on: {
                    onItemDblClick: function (id, e, node) {
                        reservationView.showDetailReservationDialog($$("reservationDT").getSelectedItem());
                    },
                    onBeforeContextMenu: function (id, e, node) {
                        var selectedItem = $$("reservationDT").getSelectedItem();
                        var contextMenuData = [];
                        if (userData.id == selectedItem.userId) {
                            if (selectedItem.stateId === reservationState.reserved) {
                                if (new Date(selectedItem.startTime) > Date.now()) {
                                    contextMenuData.push(
                                        {
                                            id: "1",
                                            value: "Edit",
                                            icon: "pencil-square-o"
                                        },
                                        {
                                            id: "2",
                                            value: "Cancel",
                                            icon: "close"
                                        },
                                        {
                                            $template: "Separator"
                                        },
                                        {
                                            id: "3",
                                            value: "More details",
                                            icon: "info-circle"
                                        }
                                    )
                                } else {
                                    contextMenuData.push(
                                        {
                                            id: "4",
                                            value: "Start",
                                            icon: "arrow-up"
                                        },
                                        {
                                            $template: "Separator"
                                        },
                                        {
                                            id: "3",
                                            value: "More details",
                                            icon: "info-circle"
                                        }
                                    )
                                }
                            }

                            if (selectedItem.stateId === reservationState.running) {
                                if (new Date(selectedItem.startTime) < Date.now()) {
                                    contextMenuData.push({
                                            id: "5",
                                            value: "Finish",
                                            icon: "arrow-up"
                                        },
                                        {
                                            $template: "Separator"
                                        },
                                        {
                                            id: "3",
                                            value: "More details",
                                            icon: "info-circle"
                                        })
                                } else {
                                    contextMenuData.push(
                                        {
                                            id: "3",
                                            value: "More details",
                                            icon: "info-circle"
                                        })
                                }
                            }
                            if (selectedItem.stateId === reservationState.finished) {
                                contextMenuData.push(
                                    {
                                        id:"7",
                                        value:"Complete reservation",
                                        icon:"check"
                                    },
                                    {
                                        $template: "Separator"
                                    },
                                    {
                                        id: "6",
                                        value: "Expenses",
                                        icon: "fas fa-wrench"
                                    },
                                    {
                                        id: "3",
                                        value: "More details",
                                        icon: "info-circle"
                                    })
                            }
                            if(selectedItem.stateId===reservationState.completed || selectedItem.stateId===reservationState.canceled){
                                contextMenuData.push(

                                    {
                                        id: "6",
                                        value: "Expenses",
                                        icon: "fas fa-wrench"
                                    },
                                    {
                                        id: "3",
                                        value: "More details",
                                        icon: "info-circle"
                                    })
                            }

                            $$("reservationContextMenu").clearAll();
                            $$("reservationContextMenu").define("data", contextMenuData);
                            $$("reservationContextMenu").refresh();

                        } else if (userData.roleId == role.companyAdministrator) {
                            contextMenuData.push(
                                {
                                    id: "3",
                                    value: "More details",
                                    icon: "info-circle"
                                },
                                {
                                    $template: "Separator"
                                },
                                {
                                    id: "8",
                                    value: "Delete",
                                    icon: "trash"
                                })
                            $$("reservationContextMenu").clearAll();
                            $$("reservationContextMenu").define("data", contextMenuData);
                            $$("reservationContextMenu").refresh();
                        } else {
                            contextMenuData.push(
                                {
                                    id: "6",
                                    value: "Expenses",
                                    icon: "fas fa-wrench"
                                },
                                {
                                    $template: "Separator"
                                },
                                {
                                    id: "3",
                                    value: "More details",
                                    icon: "info-circle"
                                })
                            $$("reservationContextMenu").clearAll();
                            $$("reservationContextMenu").define("data", contextMenuData);
                            $$("reservationContextMenu").refresh();
                        }
                    }
                },
                columns: [
                    {
                        id: "id",
                        hidden: true
                    },
                    {
                        id: "direction",
                        fillspace:true,
                        header: [
                            "Direction",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "fullName",
                        fillspace:true,
                        header: [
                            "Full name",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "stateId",
                        template: function (obj) {
                            return dependencyMap['state'][obj.stateId];
                        },
                        header: [
                            "Status",
                            {
                                content: "richSelectFilter",
                                suggest: {
                                    body: {
                                        template: function (obj) {	// template for options list
                                            if (obj.$empty)
                                                return "";
                                            return dependencyMap['state'][obj.value];
                                        }
                                    }

                                },
                                fillspace: true,
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "startTime",
                        template: function format(value) {
                            var date = new Date(value.startTime);
                            var format = webix.Date.dateToStr("%d.%m.%Y %H:%i");
                            return format(date);
                        },
                        sort: "date",
                        header: [
                            "Time from",
                            {
                                content: "datepickerFilter",
                                compare: function customCompare(value, filter) {
                                    var format = webix.Date.dateToStr("%d.%m.%Y");
                                    var tempFilter = format(filter);
                                    var tempValue = format(new Date(value));
                                    return tempFilter == tempValue;
                                }
                            }
                        ],
                        width: 225,
                        tooltip: false

                    },
                    {
                        id: "endTime",
                        template: function format(value) {
                            var date = new Date(value.endTime);
                            var format = webix.Date.dateToStr("%d.%m.%Y %H:%i");
                            return format(date);
                        },
                        sort: "date",
                        header: [
                            "Time to",
                            {
                                content: "datepickerFilter",
                                compare: function customCompare(value, filter) {
                                    var format = webix.Date.dateToStr("%d.%m.%Y");
                                    var tempFilter = format(filter);
                                    var tempValue = format(new Date(value));
                                    return tempFilter == tempValue;
                                }
                            }
                        ],
                        width: 225,
                        tooltip: false

                    },
                    {
                        id:"carName",
                        fillspace:true,
                        header: [
                            "Car",
                            {
                                content: "richSelectFilter",
                                fillspace: true,
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id:"plateNumber",
                        fillspace:true,
                        header: [
                            "Plate number",
                            {
                                content: "richSelectFilter",
                                fillspace: true,
                                sort: "string"
                            }
                        ]
                    }
                ]
            }
        ]
    },
    back:function(){
      vehicleView.selectPanel();
    },
    selectPanel: function (id) {
        $$("main").removeView(rightPanel);
        rightPanel = "reservationPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        if(userData.roleId==role.companyAdministrator){
            $$("reservationDT").define("url","/api/reservation/car/"+id);
            $$("reservationDT").hideColumn("carName");
            $$("reservationDT").hideColumn("plateNumber");
            $$("backBtn").show();
            $$("addReservationBtn").hide();
        }else{
            $$("reservationDT").define("url","api/reservation",)
        }
        $$("reservationDT").refresh();

        webix.ui({
            view: "contextmenu",
            id: "reservationContextMenu",
            width: 230,
            master: $$("reservationDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    switch (id) {
                        case "1":
                            reservationView.showEditDialog($$("reservationDT").getSelectedItem());
                            break;
                        case "2":
                            var cancelBox = (webix.copy(commonViews.cancelConfirm("reservation","reservation")));
                            cancelBox.callback = function (result) {
                                if (result) {
                                    var item = $$("reservationDT").getSelectedItem();
                                    webix.ajax().header({"Content-type": "application/json"})
                                        .put("api/reservation/cancel/" + item.id, item).then(function (data) {
                                        if (data) {
                                            var result=data.json();
                                            $$("reservationDT").updateItem(result.id,result);
                                            util.messages.showMessage("Reservation canceled.");
                                        } else {
                                            util.messages.showErrorMessage("Reservation not canceled.");
                                        }
                                    }).fail(function (error) {
                                        util.messages.showErrorMessage(error.responseText);
                                    });
                                }
                            };
                            webix.confirm(cancelBox);
                            break;
                        case "3":
                            reservationView.showDetailReservationDialog($$("reservationDT").getSelectedItem());
                            break;
                        case "4":
                            reservationView.showStartDialog();
                            break;
                        case "5":
                            reservationView.showFinishDialog();
                            break;
                        case "6":
                            expenseView.selectPanel($$("reservationDT").getSelectedItem());
                            break;
                        case "7":
                            reservationView.complete();
                            break;
                        case "8":
                            var delBox = (webix.copy(commonViews.deleteConfirm("reservation","reservation")));
                            delBox.callback = function (result) {
                                if (result) {
                                    var item = $$("reservationDT").getItem(context.id.row);
                                    connection.sendAjax("DELETE", "api/reservation/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("reservationDT").remove(context.id.row);
                                            util.messages.showMessage("Reservation deleted");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage("Reservation not deleted");
                                    }, item);
                                }
                            };
                            webix.confirm(delBox);
                            break;

                    }
                },

            }
        });
    },
    detailReservationDialog: {
        view: "popup",
        id: "detailReservationDialog",
        modal: true,
        position: "center",
        body: {
            id: "detailReservationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-bookmark'></span> Reservation details",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('detailReservationDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            width:300,
                            rows: [
                                {
                                    view: "template",
                                    borderless: true,
                                    id: "image",
                                    name: "image",
                                    width: 300,
                                    height: 300,
                                    template: "<img src='data:image/jpg;base64, #src#' width='300' height='300' alt='No image' class='photo-alignment'/>"
                                },
                                { template:"Vehicle details", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "manufacturer",
                                    name: "manufacturer",
                                    label: "Manufacturer",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "model",
                                    name: "model",
                                    label: "Model",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "plateNumber",
                                    name: "plateNumber",
                                    label: "Plate number",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "year",
                                    name: "year",
                                    label: "Year",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "engine",
                                    name: "engine",
                                    label: "Engine",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "transmission",
                                    name: "transmission",
                                    label: "Transmission",
                                    align: "left"
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "fuelName",
                                    name: "fuelName",
                                    label: "Fuel type",
                                    align: "left"
                                },
                            ]
                        },
                        {
                            width: 20
                        },
                        {
                            width:450,
                            rows: [
                                { template:"Basic info", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "direction",
                                    name: "direction",
                                    align: "left",
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "created",
                                    name: "created",
                                    align: "left",
                                },
                                { template:"Period", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "timeFrom",
                                    name: "timeFrom",
                                    align: "left",
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "timeTo",
                                    name: "timeTo",
                                    align: "left",
                                },
                                { template:"State", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "state",
                                    name: "state",
                                    align: "center",
                                },
                                { template:"User data", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "fullName",
                                    name: "fullName",
                                    align: "left",
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "username",
                                    name: "username",
                                    align: "left",
                                },
                                { template:"Mileage", type:"section"},
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "startMileage",
                                    name: "startMileage",
                                    align: "left",
                                },
                                {
                                    view: "label",
                                    borderless: true,
                                    id: "finishMileage",
                                    name: "finishMileage",
                                    align: "left",
                                }
                            ]
                        }

                    ]
                }
            ]
        }
    },
    showDetailReservationDialog: function(item){
        if (util.popupIsntAlreadyOpened("detailReservationDialog")) {
            var dialog=webix.ui(webix.copy(reservationView.detailReservationDialog));
            $$("image").setValues({
                src: item.image
            });
            $$("manufacturer").define("label", "Manufacturer: <b>" + item.manufacturerName+"</b>");
            $$("model").define("label","Model: <b>" + item.model+"</b>");
            $$("plateNumber").define("label","Plate number: <b>" + item.plateNumber+"</b>");
            $$("year").define("label","Year: <b>" + item.year+"</b>");
            $$("engine").define("label","Engine: <b>" + item.engine+"</b>");
            $$("transmission").define("label","Transmission: <b>" + item.transmission+"</b>");
            $$("fuelName").define("label","Fuel: <b>" + item.fuelName+"</b>");
            $$("direction").define("label","Direction: <b>" + item.direction+"</b>");
            var format = webix.Date.dateToStr("%d.%m.%Y %H:%i:%s");
            $$("created").define("label","Time created: <b>" + format(new Date(item.createdTime))+"</b>");
            $$("timeFrom").define("label","From: <b>" + format(new Date(item.startTime))+"</b>");
            $$("timeTo").define("label","To: <b>" + format(new Date(item.endTime))+"</b>");
            if(item.stateId==reservationState.reserved) {
                $$("state").define("label", "<b style='color:blue;'>" + item.state.toString().toUpperCase() + "</b>");
            }else if(item.stateId==reservationState.running){
                $$("state").define("label", "<b style='color:chocolate;'>" + item.state.toString().toUpperCase() + "</b>");
            }else if(item.stateId==reservationState.finished){
                $$("state").define("label", "<b style='color:yellow;'>" + item.state.toString().toUpperCase() + "</b>");
            }else if(item.stateId==reservationState.canceled){
                $$("state").define("label", "<b style='color:red;'>" + item.state.toString().toUpperCase() + "</b>");
            }
            else{
                $$("state").define("label", "<b style='color:green;'>" + item.state.toString().toUpperCase() + "</b>");
            }
            $$("fullName").define("label","Full name: <b>" + item.fullName+"</b>");
            $$("username").define("label","Username: <b>" + item.username+"</b>");
            $$("startMileage").define("label","Start: <b> " +(item.startMileage!=null? item.startMileage+' km':'/')+"</b>");
            $$("finishMileage").define("label","Finish: <b>" +(item.finishMileage!=null? item.finishMileage+' km':'/')+"</b>");
            dialog.show();
        }
    },
    loadFreeVehicle: function (startTime, endTime) {
        webix.ajax().get("api/car/reservation/" + startTime + "/" + endTime).then(function (data) {
            freeVehicles.length = 0;
            var freeVehiclesTemp = data.json();
            firstFreeVehicle = freeVehiclesTemp[0].id;
            freeVehiclesTemp.forEach(function (obj) {
                freeVehicles.push({
                    id: obj.id,
                    value: obj.plateNumber + " - " + obj.manufacturerName + " " + obj.model,
                    item: obj
                });
            });
            var item=freeVehiclesTemp[0];
            $$("carId").define("options",freeVehicles);
            $$("carId").define("value",firstFreeVehicle);
            $$("carId").refresh();
            $$("image").setValues({
                src: item.image
            });
            $$("manufacturer").define("label", "Manufacturer: <b>" + item.manufacturerName+"</b>");
            $$("model").define("label","Model: <b>" + item.model+"</b>");
            $$("plateNumber").define("label","Plate number: <b>" + item.plateNumber+"</b>");
            $$("year").define("label","Year: <b>" + item.year+"</b>");
            $$("engine").define("label","Engine: <b>" + item.engine+"</b>");
            $$("transmission").define("label","Transmission: <b>" + item.transmission+"</b>");
            $$("fuelName").define("label","Fuel: <b>" + item.fuelName+"</b>");
            $$("carDetails").show();
            $$("image").refresh();
            $$("carPhoto").show();
            $$("manufacturer").refresh();
            $$("model").refresh();
            $$("plateNumber").refresh();
            $$("year").refresh();
            $$("engine").refresh();
            $$("fuelName").refresh();
            $$("transmission").refresh();
        }).fail(function (error) {
            util.messages.showErrorMessage("Load free vehicles failed.");
        });
    },
    addReservationDialog: {
        view: "popup",
        id: "addReservationDialog",
        modal: true,
        position: "center",
        body: {
            id: "addReservationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-bookmark'></span>Make new reservation",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addReservationDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            rows: [
                                {
                                    height: 22
                                },
                                {
                                    hidden: true,
                                    id: "carPhoto",
                                    width: 300,
                                    rows: [
                                        {template: "Vehicle photo", type: "section"},
                                        {

                                            view: "template",
                                            borderless: true,
                                            id: "image",
                                            name: "image",
                                            width: 300,
                                            height: 300,
                                            template: "<img src='data:image/jpg;base64, #src#' width='300' height='300' alt='No image' class='photo-alignment'/>"
                                        },
                                    ]
                                }
                            ]
                        },
                        {
                            width: 20
                        },
                        {
                            rows: [
                                {
                                    height: 22,
                                },
                                {
                                    hidden: true,
                                    id: "carDetails",
                                    width: 300,
                                    rows: [
                                        {template: "Vehicle details", type: "section"},
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "manufacturer",
                                            name: "manufacturer",
                                            label: "Manufacturer",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "model",
                                            name: "model",
                                            label: "Model",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "plateNumber",
                                            name: "plateNumber",
                                            label: "Plate number",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "year",
                                            name: "year",
                                            label: "Year",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "engine",
                                            name: "engine",
                                            label: "Engine",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "transmission",
                                            name: "transmission",
                                            label: "Transmission",
                                            align: "left"
                                        },
                                        {
                                            view: "label",
                                            borderless: true,
                                            id: "fuelName",
                                            name: "fuelName",
                                            label: "Fuel type",
                                            align: "left"
                                        },
                                    ]
                                }
                            ]
                        },
                        {
                            width: 20
                        },
                        {
                            rows: [
                                {
                                    height: 5,
                                },
                                {
                                    view: "form",
                                    id: "addReservationForm",
                                    borderless: true,
                                    width: 600,
                                    elementsConfig: {
                                        labelWidth: 200,
                                        bottomPadding: 18
                                    },
                                    elements: [
                                        {template: "Reservation data", type: "section"},
                                        {
                                            id: "startTime",
                                            name: "startTime",
                                            view: "datepicker",
                                            stringResult: true,
                                            label: "From:",
                                            timepicker: true,
                                            type: "date",
                                            required: true,
                                            invalidMessage: "Enter reservation start time.",
                                            format: "%d-%m-%Y %H:%i:%s",
                                            suggest: {
                                                type: "calendar",
                                                body: {
                                                    type: "date",
                                                    timepicker: true,
                                                    calendarTime: "%d-%m-%Y %H:%i:%s",
                                                    minDate: new Date()
                                                }
                                            },
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    $$("endTime").define("value", newValue);
                                                    $$("endTime").define("disabled", false);
                                                    $$("endTime").getPopup().getBody().define("minDate", newValue);
                                                    $$("endTime").refresh();
                                                }
                                            }
                                        },
                                        {
                                            id: "endTime",
                                            name: "endTime",
                                            view: "datepicker",
                                            stringResult: true,
                                            label: "To:",
                                            disabled: true,
                                            timepicker: true,
                                            type: "date",
                                            required: true,
                                            invalidMessage: "Enter reservation finish time.",
                                            format: "%d-%m-%Y %H:%i:%s",
                                            suggest: {
                                                type: "calendar",
                                                body: {
                                                    type: "date",
                                                    timepicker: true,
                                                    calendarTime: "%d-%m-%Y %H:%i:%s"
                                                }
                                            },
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    $$("carId").define("disabled", false);
                                                    var startTime = webix.i18n.parseFormatStr($$("startTime").getValue()) + ":00";
                                                    var endTime = webix.i18n.parseFormatStr(newValue) + ":00";
                                                    reservationView.loadFreeVehicle(startTime, endTime);

                                                }
                                            }
                                        },
                                        {
                                            view: "text",
                                            id: "direction",
                                            name: "direction",
                                            label: "Direction:",
                                            invalidMessage: "Please enter direction.",
                                            required: true,
                                        },
                                        {
                                            id: "carId",
                                            name: "carId",
                                            view: "select",
                                            invalidMessage: "Please choose vehicle.",
                                            required: true,
                                            disabled: true,
                                            label: "Vehicle:",
                                            options: [],
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    console.log(firstFreeVehicle);
                                                    console.log(oldValue);
                                                    console.log(newValue);
                                                    var options = $$("carId").config.options;
                                                    for (var i = 0; i < options.length; i++) {
                                                        if (options[i].id == newValue) {
                                                            var item = options[i].item;

                                                            $$("image").setValues({
                                                                src: item.image
                                                            });
                                                            $$("manufacturer").define("label", "Manufacturer: <b>" + item.manufacturerName + "</b>");
                                                            $$("model").define("label", "Model: <b>" + item.model + "</b>");
                                                            $$("plateNumber").define("label", "Plate number: <b>" + item.plateNumber + "</b>");
                                                            $$("year").define("label", "Year: <b>" + item.year + "</b>");
                                                            $$("engine").define("label", "Engine: <b>" + item.engine + "</b>");
                                                            $$("transmission").define("label", "Transmission: <b>" + item.transmission + "</b>");
                                                            $$("fuelName").define("label", "Fuel: <b>" + item.fuelName + "</b>");
                                                            $$("image").refresh();
                                                            $$("manufacturer").refresh();
                                                            $$("model").refresh();
                                                            $$("plateNumber").refresh();
                                                            $$("year").refresh();
                                                            $$("engine").refresh();
                                                            $$("fuelName").refresh();
                                                            $$("transmission").refresh();

                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            margin: 5,
                                            cols: [
                                                {},
                                                {
                                                    id: "saveReservation",
                                                    view: "button",
                                                    value: "Save",
                                                    type: "form",
                                                    click: "reservationView.save",
                                                    hotkey: "enter",
                                                    width: 170
                                                }
                                            ]
                                        }
                                    ],
                                    rules: {
                                        "direction": function (value) {
                                            if (value.length > 250) {
                                                $$('addReservationForm').elements.direction.config.invalidMessage = 'Maximum length is 250.';
                                                return false;
                                            }

                                            return true;
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    },

    showAddDialog: function () {
        if (util.popupIsntAlreadyOpened("addReservationDialog")) {
            webix.ui(webix.copy(reservationView.addReservationDialog)).show();
            webix.UIManager.setFocus("startTime");
        }
    },

    save: function () {
        if ($$("addReservationForm").validate()) {
            var newItem = {
                startTime: $$("addReservationForm").getValues().startTime + ":00",
                endTime: $$("addReservationForm").getValues().endTime + ":00",
                direction: $$("addReservationForm").getValues().direction,
                stateId: 1,
                deleted: 0,
                userId: userData.id,
                carId: $$("addReservationForm").getValues().carId,
                companyId: userData.companyId
            };
            webix.ajax().header({"Content-type": "application/json"})
                .post("api/reservation/custom", newItem).then(function (data) {
                $$("reservationDT").add(data.json());
                util.messages.showMessage("Reservation successfully made.");
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
            console.log(newItem);
            util.dismissDialog('addReservationDialog');
        }
    },
    editReservationDialog: {
        view: "popup",
        id: "editReservationDialog",
        modal: true,
        position: "center",
        body: {
            id: "editReservationInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-bookmark'></span>Edit reservation",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('editReservationDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            rows: [
                                {
                                    height: 22
                                },
                                {
                                    id: "carPhoto",
                                    width: 300,
                                    rows: [
                                        {template: "Vehicle photo", type: "section"},
                                        {

                                            view: "template",
                                            borderless: true,
                                            id: "image",
                                            name: "image",
                                            width: 300,
                                            height: 300,
                                            template: "<img src='data:image/jpg;base64, #src#' width='300' height='300' alt='No image' class='photo-alignment'/>"
                                        },
                                    ]
                                }
                            ]
                        },
                        {
                            width: 20
                        },
                        {
                            rows: [
                                {
                                    height: 22,
                                },
                                {
                                    id: "carDetails",
                                    width: 300,
                                    rows: [
                                        {template: "Vehicle details", type: "section"},
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "manufacturer",
                                            name: "manufacturer",
                                            label: "Manufacturer",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "model",
                                            name: "model",
                                            label: "Model",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "plateNumber",
                                            name: "plateNumber",
                                            label: "Plate number",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "year",
                                            name: "year",
                                            label: "Year",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "engine",
                                            name: "engine",
                                            label: "Engine",
                                            align: "left"
                                        },
                                        {

                                            view: "label",
                                            borderless: true,
                                            id: "transmission",
                                            name: "transmission",
                                            label: "Transmission",
                                            align: "left"
                                        },
                                        {
                                            view: "label",
                                            borderless: true,
                                            id: "fuelName",
                                            name: "fuelName",
                                            label: "Fuel type",
                                            align: "left"
                                        },
                                    ]
                                }
                            ]
                        },
                        {
                            width: 20
                        },
                        {
                            rows: [
                                {
                                    height: 5,
                                },
                                {
                                    view: "form",
                                    id: "editReservationForm",
                                    borderless: true,
                                    width: 600,
                                    elementsConfig: {
                                        labelWidth: 200,
                                        bottomPadding: 18
                                    },
                                    elements: [
                                        {template: "Reservation data", type: "section"},
                                        {
                                            id: "startTime",
                                            name: "startTime",
                                            view: "datepicker",
                                            stringResult: true,
                                            label: "From:",
                                            timepicker: true,
                                            type: "date",
                                            required: true,
                                            invalidMessage: "Enter reservation start time.",
                                            format: "%d-%m-%Y %H:%i:%s",
                                            suggest: {
                                                type: "calendar",
                                                body: {
                                                    type: "date",
                                                    timepicker: true,
                                                    calendarTime: "%d-%m-%Y %H:%i:%s",
                                                    minDate: new Date()
                                                }
                                            },
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    $$("endTime").define("value", newValue);
                                                    $$("endTime").define("disabled", false);
                                                    $$("endTime").getPopup().getBody().define("minDate", newValue);
                                                    $$("endTime").refresh();
                                                }
                                            }
                                        },
                                        {
                                            id: "endTime",
                                            name: "endTime",
                                            view: "datepicker",
                                            stringResult: true,
                                            label: "To:",
                                            timepicker: true,
                                            type: "date",
                                            required: true,
                                            invalidMessage: "Enter reservation finish time.",
                                            format: "%d-%m-%Y %H:%i:%s",
                                            suggest: {
                                                type: "calendar",
                                                body: {
                                                    type: "date",
                                                    timepicker: true,
                                                    calendarTime: "%d-%m-%Y %H:%i:%s"
                                                }
                                            },
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    $$("carId").define("disabled", false);
                                                    var startTime = webix.i18n.parseFormatStr($$("startTime").getValue()) + ":00";
                                                    var endTime = webix.i18n.parseFormatStr(newValue) + ":00";
                                                    reservationView.loadFreeVehicle(startTime, endTime);

                                                }
                                            }
                                        },
                                        {
                                            view: "text",
                                            id: "direction",
                                            name: "direction",
                                            label: "Direction:",
                                            invalidMessage: "Please enter direction.",
                                            required: true,
                                        },
                                        {
                                            view: "text",
                                            id: "id",
                                            name: "id",
                                           hidden:true,
                                        },
                                        {
                                            view: "text",
                                            id: "deleted",
                                            name: "deleted",
                                            hidden:true,
                                        },
                                        {
                                            view: "text",
                                            id: "stateId",
                                            name: "stateId",
                                            hidden:true,
                                        },
                                        {
                                            view: "text",
                                            id: "userId",
                                            name: "userId",
                                            hidden:true,
                                        },
                                        {
                                            view: "text",
                                            id: "companyId",
                                            name: "companyId",
                                            hidden:true,
                                        },
                                        {
                                            view: "text",
                                            id: "createdTime",
                                            name: "createdTime",
                                            hidden:true,
                                        },
                                        {
                                            id: "carId",
                                            name: "carId",
                                            view: "select",
                                            invalidMessage: "Please choose vehicle.",
                                            required: true,
                                            label: "Vehicle:",
                                            options: [],
                                            on: {
                                                onChange: function (newValue, oldValue) {
                                                    var options = $$("carId").config.options;
                                                    for (var i = 0; i < options.length; i++) {
                                                        if (options[i].id == newValue) {
                                                            var item = options[i].item;

                                                            $$("image").setValues({
                                                                src: item.image
                                                            });
                                                            $$("manufacturer").define("label", "Manufacturer: <b>" + item.manufacturerName + "</b>");
                                                            $$("model").define("label", "Model: <b>" + item.model + "</b>");
                                                            $$("plateNumber").define("label", "Plate number: <b>" + item.plateNumber + "</b>");
                                                            $$("year").define("label", "Year: <b>" + item.year + "</b>");
                                                            $$("engine").define("label", "Engine: <b>" + item.engine + "</b>");
                                                            $$("transmission").define("label", "Transmission: <b>" + item.transmission + "</b>");
                                                            $$("fuelName").define("label", "Fuel: <b>" + item.fuelName + "</b>");
                                                            $$("image").refresh();
                                                            $$("manufacturer").refresh();
                                                            $$("model").refresh();
                                                            $$("plateNumber").refresh();
                                                            $$("year").refresh();
                                                            $$("engine").refresh();
                                                            $$("fuelName").refresh();
                                                            $$("transmission").refresh();

                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            margin: 5,
                                            cols: [
                                                {},
                                                {
                                                    id: "saveReservation",
                                                    view: "button",
                                                    value: "Save",
                                                    type: "form",
                                                    click: "reservationView.saveChanges",
                                                    hotkey: "enter",
                                                    width: 170
                                                }
                                            ]
                                        }
                                    ],
                                    rules: {
                                        "direction": function (value) {
                                            if (value.length > 250) {
                                                $$('addReservationForm').elements.direction.config.invalidMessage = 'Maximum length is 250.';
                                                return false;
                                            }

                                            return true;
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    },

    showEditDialog: function (item) {
        if (util.popupIsntAlreadyOpened("editReservationDialog")) {
            var dialog=  webix.ui(webix.copy(reservationView.editReservationDialog));
            webix.UIManager.setFocus("startTime");
            $$("image").setValues({
                src: item.image
            });
            $$("manufacturer").define("label", "Manufacturer: <b>" + item.manufacturerName + "</b>");
            $$("model").define("label", "Model: <b>" + item.model + "</b>");
            $$("plateNumber").define("label", "Plate number: <b>" + item.plateNumber + "</b>");
            $$("year").define("label", "Year: <b>" + item.year + "</b>");
            $$("engine").define("label", "Engine: <b>" + item.engine + "</b>");
            $$("transmission").define("label", "Transmission: <b>" + item.transmission + "</b>");
            $$("fuelName").define("label", "Fuel: <b>" + item.fuelName + "</b>");
            var form=$$("editReservationForm");
            form.elements.id.setValue(item.id);
            form.elements.deleted.setValue(item.deleted);
            form.elements.stateId.setValue(item.stateId);
            form.elements.userId.setValue(item.userId);
            form.elements.companyId.setValue(item.companyId);
            form.elements.createdTime.setValue(item.createdTime);
            form.elements.startTime.setValue(item.startTime);
            form.elements.endTime.setValue(item.endTime)
            form.elements.direction.setValue(item.direction);
            dialog.show();

        }
    },

    saveChanges: function () {
        if ($$("editReservationForm").validate()) {
            var updatedItem=$$("editReservationForm").getValues();
            updatedItem.startTime=$$("editReservationForm").getValues().startTime+":00";
            updatedItem.endTime=$$("editReservationForm").getValues().endTime+":00";
            webix.ajax().header({"Content-type": "application/json"})
                .put("api/reservation/custom/" + updatedItem.id, updatedItem).then(function (data) {
                if (data) {
                    var result=data.json();
                    $$("reservationDT").updateItem(result.id,result);
                    util.messages.showMessage("Reservation updated.");
                } else {
                    util.messages.showErrorMessage("Reservation not updated.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
            util.dismissDialog('editReservationDialog');
        }
    },

    startDialog: {
        view: "popup",
        id: "startDialog",
        modal: true,
        position: "center",
        body: {
            id: "startInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-bookmark'></span> Start trip",
                            width: 300
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('startDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            view: "form",
                            id: "startForm",
                            borderless: true,
                            width: 470,
                            elementsConfig: {
                                labelWidth: 170,
                                bottomPadding: 18
                            },
                            elements: [
                                {
                                    view: "text",
                                    id: "startMileage",
                                    name: "startMileage",
                                    label: "Start mileage:",
                                    invalidMessage: "Please enter start mileage.",
                                    required: true,
                                },
                                {
                                    margin: 5,
                                    cols: [
                                        {},
                                        {
                                            id: "saveStartTrip",
                                            view: "button",
                                            value: "Start",
                                            type: "form",
                                            click: "reservationView.start",
                                            hotkey: "enter",
                                            width: 250
                                        }
                                    ]
                                }
                            ],
                            rules: {
                                "startMileage": function (value) {
                                    if (value.length > 6) {
                                        $$('startForm').elements.startMileage.config.invalidMessage = 'Maximum length is 6.';
                                        return false;
                                    }

                                    if (!webix.rules.isNumber(value)) {
                                        $$('startForm').elements.startMileage.config.invalidMessage = 'Invalid format. Start mileage must be number';
                                        return false;
                                    }

                                    return true;
                                }
                            }
                        }

                    ]
                }
            ]
        }
    },

    showStartDialog: function () {
        if (util.popupIsntAlreadyOpened("startDialog")) {
            webix.ui(webix.copy(reservationView.startDialog)).show();
            webix.UIManager.setFocus("startDialog");
        }
    },

    start: function () {
        if ($$("startForm").validate()) {
            var item=$$("reservationDT").getSelectedItem();
            item.startMileage = $$("startForm").getValues().startMileage;
            webix.ajax().header({"Content-type": "application/json"})
                .put("api/reservation/start/" + item.id+"/"+item.startMileage).then(function (data) {
                if (data) {
                    var updated=data.json();
                    $$("reservationDT").updateItem(updated.id,updated);
                    util.messages.showMessage("Reservation running.");
                } else {
                    util.messages.showErrorMessage("Reservation running failed.");
                }

            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });

            util.dismissDialog('startDialog');
        }
    },

    finishDialog: {
        view: "popup",
        id: "finishDialog",
        modal: true,
        position: "center",
        body: {
            id: "finishInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa fa-bookmark'></span> Finish trip",
                            width: 300
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('finishDialog');"
                        }
                    ]
                },
                {
                    cols: [
                        {
                            view: "form",
                            id: "finishForm",
                            borderless: true,
                            width: 540,
                            elementsConfig: {
                                labelWidth: 170,
                                bottomPadding: 18
                            },
                            elements: [
                                {
                                    view: "text",
                                    id: "finishMileage",
                                    name: "finishMileage",
                                    label: "Finish mileage:",
                                    invalidMessage: "Enter finish mileage.",
                                    required: true,
                                },
                                {
                                    margin: 5,
                                    cols: [
                                        {},
                                        {
                                            id: "saveFinishTrip",
                                            view: "button",
                                            value: "Finish",
                                            type: "form",
                                            click: "reservationView.finish",
                                            hotkey: "enter",
                                            align:"right",
                                            width: 275
                                        }


                                    ]
                                }
                            ],
                            rules: {
                                "finishMileage": function (value) {
                                    if (value.length > 6) {
                                        $$('finishForm').elements.finishMileage.config.invalidMessage = 'Maximum length is 6.';
                                        return false;
                                    }

                                    if (!webix.rules.isNumber(value)) {
                                        $$('finishForm').elements.finishMileage.config.invalidMessage = 'Invalid format. Finish mileage must be number.';
                                        return false;
                                    }

                                    if(value <= $$("reservationDT").getSelectedItem().startMileage){
                                        $$('finishForm').elements.finishMileage.config.invalidMessage = 'Finish mileage must be greater than start mileage.';
                                        return false;
                                    }

                                    return true;
                                }
                            }
                        }

                    ]
                }
            ]
        }
    },

    showFinishDialog: function () {
        if (util.popupIsntAlreadyOpened("finishDialog")) {
            webix.ui(webix.copy(reservationView.finishDialog)).show();
            webix.UIManager.setFocus("finishMileage");
        }
    },

    finish: function () {
        if ($$("finishForm").validate()) {
            var item=$$("reservationDT").getSelectedItem();
            var finishMileage = $$("finishForm").getValues().finishMileage;
            webix.ajax().header({"Content-type": "application/json"})
                .put("api/reservation/finish/" + item.id+"/"+finishMileage,).then(function (data) {
                if (data) {
                    var updated=data.json();
                    $$("reservationDT").updateItem(updated.id, updated);
                    util.messages.showMessage("Reservation finished.")
                } else {
                    util.messages.showErrorMessage("Reservation not finished.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });

            util.dismissDialog('finishDialog');
        }
    },
    complete: function(){
        var item=$$("reservationDT").getSelectedItem();
        var completedBox = (webix.copy(commonViews.completeConfirm("reservation","reservation")));
        completedBox.callback = function (result) {
            if (result) {
                webix.ajax().header({"Content-type": "application/json"})
                    .put("api/reservation/complete/" + item.id).then(function (data) {
                    if (data) {
                        var updated = data.json();
                        $$("reservationDT").updateItem(updated.id, updated);
                        util.messages.showMessage("Reservation completed.");
                    } else {
                        util.messages.showErrorMessage("Reservation not completed.");
                    }

                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
            }
        }
        webix.confirm(completedBox);
    }
};
