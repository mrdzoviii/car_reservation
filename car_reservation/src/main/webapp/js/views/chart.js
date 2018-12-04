
var vehicles = []
var firstVehicle;
var startDate;
var endDate;

var chartView = {
    panel: {
        id: "chartPanel",
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
                        template: "<span class='fa fa-bar-chart'></span> Reports"
                    },
                    {},
                    {
                        id: "selectFilter",
                        view: "richselect",
                        label: "Report:",
                        labelWidth: 70,
                        width: 200,
                        value: 1,
                        editable: false,
                        options: [
                            {
                                id: 1,
                                value: "Weekly"
                            },
                            {
                                id: 2,
                                value: "Monthly"
                            },
                            {
                                id: 3,
                                value: "Yearly"
                            }
                        ],
                        on: {
                            onChange: function (id) {
                                var picker = $$("datePickerFilter");
                                picker.setValue(null);
                                $$("chart").clearAll();
                                $$("chart").refresh();
                                picker.refresh();
                            }
                        }
                    },
                    {
                        id: "datePickerFilter",
                        view: "daterangepicker",
                        name: "datePickerFilter",
                        label: "From - to:",
                        width: 300,
                        labelWidth: 75,
                        suggest: {
                            view: "daterangesuggest",
                            body: {
                                calendarCount: 1,
                                icons: true,
                                css: "custom_date_picker_report",
                            }
                        },
                        on: {
                            onChange: function (dates) {
                                if (dates != null) {
                                    if (dates.start != null && dates.end != null) {
                                        var myFormat = webix.Date.dateToStr("%Y-%m-%d");
                                        var startDate = myFormat(new Date(dates.start));
                                        var endDate = myFormat(new Date(dates.end));

                                        $$("chart").clearAll();
                                        var chartInfo;
                                        var selectFilter = $$("selectFilter").getValue();
                                        if (selectFilter == reportType.weekly) {
                                            chartInfo = {
                                                dateFrom: startDate,
                                                dateTo: endDate,
                                                companyId: userData.companyId,
                                                chartType: reportType.weekly
                                            };
                                            $$('chart').config.xAxis.title = "Time Unit: Week/Year";
                                        } else if (selectFilter == reportType.monthly) {
                                            chartInfo = {
                                                dateFrom: startDate,
                                                dateTo: endDate,
                                                companyId: userData.companyId,
                                                chartType: reportType.monthly
                                            };
                                            $$('chart').config.xAxis.title = "Time Unit: Month/Year";
                                        } else {
                                            chartInfo = {
                                                dateFrom: startDate,
                                                dateTo: endDate,
                                                companyId: userData.companyId,
                                                chartType: reportType.yearly
                                            };
                                            $$('chart').config.xAxis.title = "Time Unit: Year";
                                        }
                                        $$("chart").load(function () {
                                            return webix.ajax().header({"Content-type": "application/json"})
                                                .post("api/expense/chart/", chartInfo).then(function (data) {
                                                    return data.json();
                                                }).fail(function (error) {
                                                    util.messages.showErrorMessage(error.responseText);
                                                });
                                        });

                                        $$('chart').refresh();
                                        $$("datePickerFilter").getPopup().hide();
                                    }
                                }
                            }
                        }
                    }
                ]
            },
            {
                view: "scrollview",
                id: "scrollview",
                scroll: "x",
                body: {
                    view: "chart",
                    id: "chart",
                    type: "bar",
                    scale: "logarithmic",
                    gradient: "rising",
                    width: 1000,
                    xAxis: {
                        template: "#timeUnit#",
                        lines: false
                    },
                    yAxis: {
                        title: "Amount [BAM]",
                        template: function (value) {
                            return value;
                        }
                    },
                    padding: {
                        left: 70,
                        right: 10,
                        top: 50
                    },
                    series: [
                        {
                            value: "#serviceCost#",
                            label:function (object) {
                                if (object.serviceCost != 0) {
                                    return parseFloat(object.serviceCost).toFixed(2)+" BAM";
                                }
                                return "";
                            },
                            tooltip: {
                                template: function (object) {
                                    if (object.serviceCost != 0) {
                                        return parseFloat(object.serviceCost).toFixed(2)+" BAM";
                                    }
                                    return "";
                                },
                            },
                            color: "#58dccd",
                        },
                        {
                            value: "#fuelCost#",
                            label: function (object) {
                                if (object.fuelCost != 0) {
                                    return parseFloat(object.fuelCost).toFixed(2)+" BAM";
                                }
                                return "";
                            },
                            tooltip: {
                                template: function (object) {
                                    if (object.fuelCost != 0) {
                                        return parseFloat(object.fuelCost).toFixed(2)+" BAM";
                                    }
                                    return "";
                                },
                            },

                            color: "#a7ee70",
                        },
                        {
                            value: "#otherCost#",
                            label:function (object) {
                                if (object.otherCost != 0) {
                                    return parseFloat(object.otherCost).toFixed(2)+" BAM";
                                }
                                return "";
                            },
                            tooltip: {
                                template: function (object) {
                                    if (object.otherCost != 0) {
                                        return parseFloat(object.otherCost).toFixed(2)+" BAM";
                                    }
                                    return "";
                                },
                            },
                            color: "#36abee",
                        }
                    ],
                    legend: {
                        values: [
                            {text: "Service & Maintenance", color: "#58dccd"},
                            {text: "Fuel", color: "#a7ee70"},
                            {text: "Other", color: "#36abee"}
                        ],
                        valign: "middle",
                        align: "right",
                        width: 200,
                        layout: "y"
                    },
                    data: []
                }
            },
            {
                view: "toolbar",
                padding: 8,
                css: "panelToolbar",
                height: 50,
                cols: [
                    {
                        view: "label",
                        width: 500,
                        template: "<span class='fa fa-download'></span> Download vehicle's monthly reports"
                    },
                    {},
                    {
                        id: "downloadMonthReportBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Download",
                        icon: "download",
                       // click: 'chartView.showDownloadDialog',
                        autowidth: true
                    }
                ]
            }
        ]
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "chartPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
    },
    /*
    downloadDialog: {
        view: "popup",
        id: "downloadDialog",
        modal: true,
        position: "center",
        body: {
            id: "downloadInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-download'></span> Preuzimanje mjesečnih izvještaja za troškove vozila",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('downloadDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "downloadForm",
                    width: 700,
                    elementsConfig: {
                        labelWidth: 325,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "monthReportType",
                            name: "monthReportType",
                            view: "radio",
                            label: "Vrsta mjesečnog izvještaja za troškove vozila:",
                            required: true,
                            value: 1,
                            invalidMessage: "Molimo Vas da odaberete jednu opciju.",
                            options: [
                                {
                                    id: 1,
                                    value: "Za sva vozila",
                                    newline: true
                                },
                                {
                                    id: 2,
                                    value: "Za jedno vozilo",
                                    newline: true
                                }
                            ],
                            on: {
                                onChange: function (newValue, oldvalue) {
                                    if (newValue == 1) {
                                        $$("vehicleId").disable();
                                    }
                                    else {
                                        $$("vehicleId").enable();
                                    }
                                }
                            }
                        },
                        {
                            id: "datePickerFilterDownloadFile",
                            view: "daterangepicker",
                            name: "datePickerFilterDownloadFile",
                            label: "Od - do:",
                            required: true,
                            invalidMessage: "Molimo Vas da odaberete datume.",
                            suggest: {
                                view: "daterangesuggest",
                                body: {
                                    calendarCount: 1,
                                    icons: true,
                                    css: "custom_date_picker_report",
                                }
                            },
                            on: {
                                onChange: function (dates) {
                                    if (dates.start != null && dates.end != null) {
                                        var myFormat = webix.Date.dateToStr("%d.%m.%Y");
                                        startDate = myFormat(new Date(dates.start));
                                        endDate = myFormat(new Date(dates.end));

                                        $$("datePickerFilterDownloadFile").getPopup().hide();
                                    }
                                }
                            }
                        },
                        {
                            id: "fileFormat",
                            name: "fileFormat",
                            view: "select",
                            value: 1,
                            invalidMessage: "Molimo Vas da odaberete format.",
                            required: true,
                            label: "Format fajla:",
                            options: [
                                {
                                    id: 1,
                                    value: "PDF",
                                },
                                {
                                    id: 2,
                                    value: "XLS",
                                },
                                {
                                    id: 3,
                                    value: "CSV",
                                }
                            ]
                        },
                        {
                            id: "vehicleId",
                            name: "vehicleId",
                            view: "select",
                            value: firstVehicle,
                            invalidMessage: "Molimo Vas da odaberete vozilo.",
                            required: true,
                            disabled: true,
                            label: "Vozilo:",
                            options: []
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "DownloadFile",
                                    view: "button",
                                    value: "Preuzmite mjesečni izvještaj",
                                    type: "form",
                                    click: "chartView.download",
                                    hotkey: "enter",
                                    width: 300
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    },

    showDownloadDialog: function () {
        if (util.popupIsntAlreadyOpened("downloadDialog")) {
            webix.ui(webix.copy(chartView.downloadDialog)).show();
            chartView.loadVehicle();
        }
    },

    download: function () {
        if ($$("downloadForm").validate()) {
            var monthReportType = $$("monthReportType").getValue();
            var fileFormatId = $$("fileFormat").getValue();
            var fileFormat;
            if (fileFormatId == 1) {
                fileFormat = "PDF";
            }
            else if (fileFormatId == 2) {
                fileFormat = "XLS";
            }
            else {
                fileFormat = "CSV";
            }
            if (monthReportType == 1) {
                webix.ajax().header({"Content-type": "application/x-www-form-urlencoded"})
                    .post("hub/vehicleMaintenance/report/all/month", "startDate=" + startDate + "&endDate=" + endDate + "&format=" + fileFormat).then(function (data) {
                    var file = data.json();
                    var blob = util.b64toBlob(file.content);
                    saveFileAs(blob, file.name);
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
            }
            else {
                var vehicleId = $$("vehicleId").getValue();
                webix.ajax().header({"Content-type": "application/x-www-form-urlencoded"})
                    .post("hub/vehicleMaintenance/report/vehicle/month", "startDate=" + startDate + "&endDate=" + endDate + "&format=" + fileFormat + "&vehicleId=" + vehicleId).then(function (data) {
                    var file = data.json();
                    var blob = util.b64toBlob(file.content);
                    saveFileAs(blob, file.name);
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
            }
        }
    },

    loadVehicle: function () {
        webix.ajax().get("hub/vehicle").then(function (data) {
            vehicles.length = 0;
            var vehiclesTemp = data.json();
            firstVehicle = vehiclesTemp[0].id;
            vehiclesTemp.forEach(function (obj) {
                vehicles.push({
                    id: obj.id,
                    value: obj.licensePlate + " - " + obj.manufacturerName + " " + obj.modelName,
                    item: obj
                });
            });

            $$("vehicleId").define({
                options: vehicles,
                value: firstVehicle
            });
            $$("vehicleId").refresh();
        }).fail(function (error) {
            util.messages.showErrorMessage("Neuspješno dobavljanje podataka o vozilima.");
        });
    }
    */
};
