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
                    {}
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
                        tooltip: false,

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
