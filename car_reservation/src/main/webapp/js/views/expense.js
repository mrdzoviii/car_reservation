var selectedReservation;
var expenseView = {
    panel: {
        id: "expensePanel",
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
                        template: "<span class='fa fa-wrench'></span> Expenses"
                    },
                    {},
                    {
                        id: "backBtn",
                        name:"backBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Back",
                        click: "expenseView.back",
                        icon: "arrow-left",
                        autowidth: true,
                    },
                    {
                        id: "backAdminBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Back",
                        click: "expenseView.backAdmin",
                        icon: "arrow-left",
                        hidden:true,
                        autowidth: true,
                    },
                    {
                        id: "addBtn",
                        name:"addBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Add new expense",
                        click: "expenseView.showAddDialog",
                        icon: "plus-circle",
                        autowidth: true,
                    }
                ]
            },
            {
                id: "expenseDT",
                view: "datatable",
                css:"webixDatatable",
                select: true,
                navigation: true,
                fillspace: true,
                resizeColumn:true,
                on: {
                    onBeforeContextMenu:function (id,e,node) {
                        var selectedItem=$$("expenseDT").getSelectedItem();
                        var contextMenuData = [];
                        if (userData.id == selectedItem.userId && selectedReservation.stateId==reservationState.finished) {
                                    contextMenuData.push(
                                        {
                                            id: "1",
                                            value: "Edit",
                                            icon: "pencil-square-o"
                                        },
                                        {
                                            $template: "Separator"
                                        },
                                        {
                                            id: "2",
                                            value: "Delete",
                                            icon: "trash"
                                        }
                                    )
                            $$("expenseContextMenu").clearAll();
                            $$("expenseContextMenu").define("data", contextMenuData);
                            $$("expenseContextMenu").refresh();
                            return true;
                                }
                            return false;
                        }
                    },
                columns: [
                    {
                        id: "id",
                        hidden: true
                    },
                    {
                        fillspace:2,
                        id: "description",
                        tooltip:true,
                        header: [
                            "Description",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "fullName",
                        hidden:true,
                        fillspace:1,
                        header: [
                            "Full name",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "costId",
                        template: function (obj) {
                            return dependencyMap['cost'][obj.costId];
                        },
                        width:300,
                        header: [
                            "Cost",
                            {
                                content: "richSelectFilter",
                                suggest: {
                                    body: {
                                        template: function (obj) {	// template for options list
                                            if (obj.$empty)
                                                return "";
                                            return dependencyMap['cost'][obj.value];
                                        }
                                    }

                                },
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id:"carModel",
                        hidden:true,
                        fillspace:1,
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
                        id: "price",
                        editor:"text",
                          header: [
                            "Price",
                            {
                                content: "numberFilter",
                            }
                        ],
                        template:function (obj) {
                            var price=webix.Number.format(obj.price, {
                                decimalSize: 2, groupSize: 3,
                                decimalDelimiter: ".", groupDelimiter: ","
                            });
                            return price+" BAM";

                        }

                    },
                    {
                        id: "date",
                        editable: false,
                        sort: "date",
                        width:200,
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
                        ]
                    },

                ]
            }
        ]
    },

    selectPanel: function (reservation) {
        $$("main").removeView(rightPanel);

        rightPanel = "expensePanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        if(userData.roleId==role.companyAdministrator){
            $$("expenseDT").showColumn("fullName");
            $$("addBtn").hide();
            $$("backBtn").hide();
            $$("backAdminBtn").show();
            $$("expenseDT").define("url","/api/expense/car/"+reservation);
        }else{
            selectedReservation=reservation;
            if(userData.id!=selectedReservation.userId || selectedReservation.stateId==reservationState.completed) {
                $$("addBtn").hide();
            }
            $$("expenseDT").hideColumn("carModel");
            $$("expenseDT").hideColumn("fullName");
            $$("expenseDT").define("url","/api/expense/custom/reservation/"+selectedReservation.id);
        }
        $$("expenseDT").refresh();
        webix.ui({
            view: "contextmenu",
            id: "expenseContextMenu",
            width: 230,
            master: $$("expenseDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    switch (id) {
                        case "1":
                            expenseView.showEditDialog();
                            break;
                        case "2":
                            var delBox = (webix.copy(commonViews.deleteConfirm("expense","expense")));
                            delBox.callback = function (result) {
                                if (result) {
                                    var item = $$("expenseDT").getSelectedItem();
                                    connection.sendAjax("DELETE", "api/expense/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("expenseDT").remove(context.id.row);
                                            util.messages.showMessage("Expense removed");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage("Expense not removed");
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

    addExpenseDialog: {
        view: "popup",
        id: "addExpenseDialog",
        modal: true,
        position: "center",
        body: {
            id: "addExpenseInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-wrench'></span> Add expense",
                            width: 400
                        },
                        {},
                        {
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addExpenseDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "addExpenseForm",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            view: "richselect",
                            id: "costId",
                            name: "costId",
                            label: "Cost:",
                            required:true,
                            invalidMessage:"Select cost type"
                        },
                        {
                            view: "text",
                            id: "description",
                            name: "description",
                            label: "Description:",
                            required: false
                        },
                        {
                            view: "text",
                            id: "userId",
                            name: "userId",
                            hidden:true
                        },
                        {
                            view: "text",
                            id: "carId",
                            name: "carId",
                            hidden:true
                        },
                        {
                          view:"text",
                          id:"deleted",
                          name:"deleted",
                          value:0,
                          hidden:true
                        },
                        {
                            view: "text",
                            id: "reservationId",
                            name: "reservationId",
                            hidden:true
                        },
                        {
                            view:"text",
                            id:"carModel",
                            label:"Model:",
                            readonly:true
                        },
                        {
                            view: "text",
                            id: "price",
                            name: "price",
                            label: "Price in BAM(xxx.xx):",
                            format:"1,111.00",
                            numberFormat:"1,111.00",
                            invalidMessage: "Enter price.",
                            required: true
                        },
                        {
                            view: "datepicker",
                            id: "date",
                            name: "date",
                            label: "Date:",
                            stringResult: true,
                            type: "date",
                            format: "%d/%m/%Y",
                            invalidMessage: "Enter date",
                            required: true,
                            suggest:{
                            type: "calendar",
                                body: {
                                        type: "date",
                                        calendarDate: "%d/%m/%Y",
                        }}
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "finishMaintenanceBtn",
                                    view: "button",
                                    align:"right",
                                    type:"form",
                                    value: "Save expenses",
                                    click: "expenseView.addExpense",
                                    width: 200
                                },
                            ]
                        }
                    ],
                   rules: {
                        "price": function (value) {
                            var regex = /[0-9]+[.][0-9]{2}/;

                            if (!regex.test(value)) {
                                $$('addExpenseForm').elements.price.config.invalidMessage = 'Price format not valid.';
                                return false;
                            }

                            return true;
                        },
                        "date": function (value) {
                            var myFormat = webix.Date.strToDate("%d.%m.%Y %H:%i");
                            var startDate = myFormat(new Date(selectedReservation.startTime));
                            var endDate = myFormat(new Date(selectedReservation.endTime));
                            myFormat = webix.Date.strToDate("%Y.%m.%d %H:%i");
                            var date = myFormat(value);
                            if (!(startDate.getDate() <= date.getDate() && date.getDate() < endDate.getDate())) {
                                $$('addExpenseForm').elements.date.config.invalidMessage = 'Date not within reservation period.';
                                return false;
                            }

                            return true;
                        }
                    }
                }
            ]
        }
    },

    showAddDialog: function () {
        if (util.popupIsntAlreadyOpened("addExpenseDialog")) {
            var dialog=webix.ui(webix.copy(expenseView.addExpenseDialog));
            webix.UIManager.setFocus("costId");
            $$("carModel").define("value",selectedReservation.carName);
            $$("carModel").refresh();
            $$("costId").define("options",dependency.cost);
            $$("costId").refresh();
            $$("date").getPopup().getBody().config.maxDate = new Date(selectedReservation.endTime)
            $$("date").getPopup().getBody().config.minDate = new Date(selectedReservation.startTime);
            $$('date').refresh();
            var form=$$("addExpenseForm");
            form.elements.userId.setValue(selectedReservation.userId);
            form.elements.carId.setValue(selectedReservation.carId);
            form.elements.reservationId.setValue(selectedReservation.id);
            form.elements.date.setValue(new Date(selectedReservation.startTime));
            dialog.show();
        }
    },

    addExpense: function(){
        if ($$("addExpenseForm").validate()) {
            var item=$$("addExpenseForm").getValues();
            console.log(item)
            webix.ajax().header({"Content-type": "application/json"})
                .post("/api/expense/custom/",item).then(function (data) {
                if (data) {
                    var inserted=data.json();
                    $$("expenseDT").add(inserted);
                    util.messages.showMessage("Expense added")
                } else {
                    util.messages.showErrorMessage("Expense not added.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
            util.dismissDialog('addExpenseDialog');
        }
    },
    editExpenseDialog: {
        view: "popup",
        id: "editExpenseDialog",
        modal: true,
        position: "center",
        body: {
            id: "editExpenseInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-wrench'></span>Edit expense",
                            width: 400
                        },
                        {},
                        {
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('editExpenseDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "editExpenseForm",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            view: "richselect",
                            id: "costId",
                            name: "costId",
                            label: "Cost:",
                            required:true,
                            invalidMessage:"Select cost type"
                        },
                        {
                            view:"text",
                            id:"id",
                            name:"id",
                            hidden:true
                        },
                        {
                            view: "text",
                            id: "description",
                            name: "description",
                            label: "Description:",
                            required: false
                        },
                        {
                            view: "text",
                            id: "userId",
                            name: "userId",
                            hidden:true
                        },
                        {
                            view: "text",
                            id: "carId",
                            name: "carId",
                            hidden:true
                        },
                        {
                            view:"text",
                            id:"deleted",
                            name:"deleted",
                            value:0,
                            hidden:true
                        },
                        {
                            view: "text",
                            id: "reservationId",
                            name: "reservationId",
                            hidden:true
                        },
                        {
                            view:"text",
                            id:"carModel",
                            label:"Model:",
                            readonly:true
                        },
                        {
                            view: "text",
                            id: "price",
                            name: "price",
                            label: "Price in BAM(xxx.xx):",
                            format:"1,111.00",
                            numberFormat:"1,111.00",
                            invalidMessage: "Enter price.",
                            required: true
                        },
                        {
                            view: "datepicker",
                            id: "date",
                            name: "date",
                            label: "Date:",
                            stringResult: true,
                            type: "date",
                            format: "%d/%m/%Y",
                            invalidMessage: "Enter date",
                            required: true,
                            suggest:{
                                type: "calendar",
                                body: {
                                    type: "date",
                                    calendarDate: "%d/%m/%Y",
                                }}
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "finishMaintenanceBtn",
                                    view: "button",
                                    align:"right",
                                    type:"form",
                                    value: "Save changes",
                                    click: "expenseView.saveChanges",
                                    width: 200
                                },
                            ]
                        }
                    ],
                    rules: {
                        "price": function (value) {
                            var regex = /[0-9]+[.][0-9]{2}/;

                            if (!regex.test(value)) {
                                $$('editExpenseForm').elements.price.config.invalidMessage = 'Price format not valid.';
                                return false;
                            }

                            return true;
                        },
                        "date": function (value) {
                            var myFormat = webix.Date.strToDate("%d.%m.%Y %H:%i");
                            var startDate = myFormat(new Date(selectedReservation.startTime));
                            var endDate = myFormat(new Date(selectedReservation.endTime));
                            myFormat = webix.Date.strToDate("%Y.%m.%d %H:%i");
                            var date = myFormat(value);
                            if (!(startDate.getDate() <= date.getDate() && date.getDate() < endDate.getDate())) {
                                $$('editExpenseForm').elements.date.config.invalidMessage = 'Date not within reservation period.';
                                return false;
                            }

                            return true;
                        }
                    }
                }
            ]
        }
    },

    showEditDialog: function () {
        if (util.popupIsntAlreadyOpened("editExpenseDialog")) {
            var dialog=webix.ui(webix.copy(expenseView.editExpenseDialog));
            var selectedItem=$$("expenseDT").getSelectedItem();
            webix.UIManager.setFocus("costId");
            $$("carModel").define("value",selectedReservation.carName);
            $$("carModel").refresh();
            $$("costId").define("value",selectedItem.costId);
            $$("costId").define("options",dependency.cost);
            $$("costId").refresh();
            $$("date").getPopup().getBody().config.maxDate = new Date(selectedReservation.endTime)
            $$("date").getPopup().getBody().config.minDate = new Date(selectedReservation.startTime);
            $$('date').refresh();
            var form=$$("editExpenseForm");
            form.elements.userId.setValue(selectedReservation.userId);
            form.elements.carId.setValue(selectedReservation.carId);
            form.elements.id.setValue(selectedItem.id);
            form.elements.description.setValue((selectedItem.description==null?"":selectedItem.description));
            form.elements.date.setValue(new Date(selectedItem.date));
            var price=webix.Number.format(selectedItem.price, {
                    decimalSize: 2, groupSize: 3,
                    decimalDelimiter: ".", groupDelimiter: ","
                });
            form.elements.price.setValue(price);
            form.elements.deleted.setValue(selectedItem.deleted);
            form.elements.reservationId.setValue(selectedReservation.id);
            form.elements.date.setValue(new Date(selectedReservation.startTime));
            dialog.show();
        }
    },

    saveChanges: function(){
        if ($$("editExpenseForm").validate()) {
            var item=$$("editExpenseForm").getValues();
            console.log(item)
            webix.ajax().header({"Content-type": "application/json"})
                .put("/api/expense/custom/"+item.id,item).then(function (data) {
                if (data) {
                    item=data.json();
                    $$("expenseDT").updateItem(item.id,item);
                    util.messages.showMessage("Expense has been changed")
                } else {
                    util.messages.showErrorMessage("Expense not changed.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
            util.dismissDialog('editExpenseDialog');
        }
    },
    back:function () {
        reservationView.selectPanel();
    },
    backAdmin:function(){
        vehicleView.selectPanel();
    }

   };