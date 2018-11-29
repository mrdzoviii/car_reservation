var loggerView = {
    panel: {
        id: "loggerPanel",
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
                        template: "<span class='fa fa-history'/> Actions logger"
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
                        on:{
                            onChange: function (dates) {
                                if (dates == undefined) {
                                    $$("loggerDT").filterByAll();
                                    return true;
                                }
                                if (dates.start != null && dates.end != null){
                                    $$("loggerDT").filterByAll();
                                    var startingDate = new Date(dates.start);
                                    var endingDate = new Date(dates.end.getFullYear(),dates.end.getMonth(),dates.end.getDate()+1);
                                    $$("loggerDT").filter(function (obj) {
                                        var dateOfObj= new Date(obj.created);
                                        if (dateOfObj >= startingDate && dateOfObj <= endingDate)
                                            return true;
                                        return false;
                                    });
                                    $$("datePickerFilter").getPopup().hide();
                                }
                            }
                        }
                    },
                    {
                        id: "richSelectFilter",
                        view: "richselect",
                        label: "Show:",
                        value: 1,
                        editable: false,
                        options: [
                            {
                                id: 1,
                                value: "All"
                            },
                            {
                                id: 2,
                                value: "Last 24 hours"
                            },
                            {
                                id: 3,
                                value: "Last week"
                            }
                        ],
                        on: {
                            onChange: function (id) {
                                var customFilterForDate = function (days) {
                                    var today = new Date();
                                    var startDate;
                                    var tempDay = today.getDate() - days;
                                    if (tempDay > 0 && today.getMonth() > 1) {
                                        startDate = new Date(today.getFullYear(), today.getMonth(), tempDay);
                                    } else if (tempDay < 0 && today.getMonth() > 1) {
                                        startDate = new Date(today.getFullYear(), today.getMonth() - 1, tempDay);
                                    } else if (tempDay < 0 && today.getMonth() == 1) {
                                        startDate = new Date(today.getFullYear() - 1, 12, tempDay);
                                    }

                                    $$("loggerDT").filter(function (obj) {
                                        var dateOfObj = new Date(obj.created);
                                        if (dateOfObj >= startDate) {
                                            return true;
                                        }

                                        return false;
                                    })
                                }
                                switch (id) {
                                    case 1:
                                        $$("loggerDT").filterByAll();
                                        break;
                                    case 2:
                                        customFilterForDate(1);
                                        break;
                                    case 3:
                                        customFilterForDate(7);
                                        break;
                                }
                            }
                        }
                    }
                ]

            },
            {
                id: "loggerDT",
                view: "datatable",
                css: "webixDatatable",
                multiselect: "false",
                select: false,
                navigation: true,
                editable: false,
                resizeColumn: true,
                resizeRow: true,
                url: "api/logger",
                tooltip: true,
                columns: [
                    {
                        id: "actionType",
                        header: [
                            "Action type",
                            {
                                content: "richSelectFilter",
                                sort: "string"
                            }
                        ],
                        tooltip: false,
                        fillspace: true,

                    },
                    {
                        id: "actionDetails",
                        header: [
                            "Action details",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ],

                        fillspace: true,

                    },
                    {
                        id: "tableName",
                        header: [
                            "Table",
                            {
                                content: "richSelectFilter",
                                sort: "string"
                            }
                        ],
                        tooltip: false,
                        fillspace: true,

                    },
                    {
                        id: "created",
                        template: function format(value) {
                            var date = new Date(value.created);
                            var format = webix.Date.dateToStr("%d.%m.%Y %H:%i");
                            return format(date);
                        },
                        sort: "date",
                        header: [
                            "Date",
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
                        id: "username",
                        header: [
                            "Username",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ],
                        tooltip: false,
                        fillspace: true,

                    },
                    {
                        id: "userRole",
                        template: function (obj) {
                            return dependencyMap['role'][obj.userRole];
                        },
                        header: [
                            "Role",
                            {
                                content: "richSelectFilter",

                                suggest: {
                                    body: {
                                        template: function (obj) {
                                            if (obj.$empty)
                                                return "";
                                            return dependencyMap['role'][obj.value];
                                        }
                                    }

                                },
                                fillspace: true,
                                sort: "string"
                            }
                        ],
                        tooltip: false,
                        fillspace: true,

                    },
                    {
                        id: "companyName",
                        header: [
                            "Company",
                            {
                                content: "richSelectFilter",
                                fillspace: true,
                                sort: "string",
                                suggest: {
                                    body: {
                                        template: function (obj) {
                                            if (obj.$empty)
                                                return "";
                                            return obj.value;
                                        }
                                    }
                                }
                            }
                        ],
                        tooltip: false,
                        fillspace: true,

                    }
                ]
            }
        ]
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "loggerPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        if (userData.companyId) {
            $$("loggerDT").hideColumn("companyName");
        }
    }
};
