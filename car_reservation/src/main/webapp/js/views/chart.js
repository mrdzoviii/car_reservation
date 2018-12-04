
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
                        width: 120,
                        template: "<span class='fa fa-bar-chart'></span> Reports"
                    },
                    {
                        id: "downloadMonthReportBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Get monthly report",
                        icon: "download",
                        click: 'chartView.showDownloadDialog',
                        autowidth: true
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
            }
        ]
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "chartPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
    },
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
                            label: "<span class='webix_icon fa-download'></span> Get monthly report",
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
                    width: 500,
                    elementsConfig: {
                        labelWidth: 150,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "type",
                            name: "type",
                            view: "radio",
                            label: "Report type:",
                            required: true,
                            value: 1,
                            invalidMessage: "Choose your type.",
                            options: [
                                {
                                    id: 1,
                                    value: "All vehicle",
                                    newline: true
                                },
                                {
                                    id: 2,
                                    value: "Single vehicle",
                                    newline: true
                                }
                            ],
                            on: {
                                onChange: function (newValue, oldvalue) {
                                    if (newValue == 1) {
                                        $$("carId").disable();
                                    }
                                    else {
                                        $$("carId").enable();
                                    }
                                }
                            }
                        },
                        {
                            id: "datePickerFilterDownloadFile",
                            view: "daterangepicker",
                            label: "From - To:",
                            required: true,
                            invalidMessage: "Please select report range.",
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
                                        var myFormat = webix.Date.dateToStr("%Y-%m-%d");
                                        startDate = myFormat(new Date(dates.start));
                                        endDate = myFormat(new Date(dates.end));
                                        $$("datePickerFilterDownloadFile").getPopup().hide();
                                    }
                                }
                            }
                        },
                        {
                            id: "format",
                            name: "format",
                            view: "richselect",
                            value: "PDF",
                            invalidMessage: "Choose file format.",
                            required: true,
                            label: "File format:",
                            options: [
                                {
                                    id: "PDF",
                                    value: "PDF",
                                },
                                {
                                    id: "XLS",
                                    value: "XLS",
                                },
                                {
                                    id: "CSV",
                                    value: "CSV",
                                }
                            ]
                        },
                        {
                            id: "carId",
                            name: "carId",
                            view: "richselect",
                            invalidMessage: "Please choose vehicle.",
                            required: true,
                            disabled: true,
                            label: "Vehicle:",
                            options: []
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "DownloadFile",
                                    view: "button",
                                    value: "Download",
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

            var report = $$("downloadForm").getValues();
            console.log(report);
            report.dateFrom = startDate;
            report.dateTo = endDate;
            report.companyId = userData.companyId;

            webix.ajax().header({"Content-type": "application/json"})
                .post("api/expense/report/", report).then(function (data) {
                var file = data.json();
                var blob = util.b64toBlob(file.content);
                saveFileAs(blob, file.name);
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
        }
    },

    loadVehicle: function () {
        webix.ajax().get("api/car").then(function (data) {
            vehicles.length = 0;
            var vehiclesTemp = data.json();
            firstVehicle = vehiclesTemp[0].id;
            vehiclesTemp.forEach(function (obj) {
                vehicles.push({
                    id: obj.id,
                    value: obj.plateNumber + "  " + obj.manufacturerName + " " + obj.model
                });
            });

            $$("carId").define({
                options: vehicles,
                value: firstVehicle
            });
            $$("carId").refresh();
        }).fail(function (error) {
            util.messages.showErrorMessage("Car load fail.");
        });
    }
};
