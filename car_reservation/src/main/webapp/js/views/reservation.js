
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
                        id: "addReservationBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Make new reservation",
                       // click: "reservationView.showAddDialog",
                        icon: "plus-circle",
                        autowidth: true
                    }
                ]
            },
            {
                id: "reservationDT",
                view: "datatable",
                css:"webixDatatable",
                select: true,
                navigation: true,
                fillspace: true,
                resizeColumn:true,
                url:"api/reservation",
                /*
                on: {
                    onBeforeContextMenu: function (item) {
                        if (item.row === userData.id)
                            return false;
                        this.select(item.row);
                    }
                },*/
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

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "reservationPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
    },
}