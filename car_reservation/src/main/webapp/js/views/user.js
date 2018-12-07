var userView={
    panel:{
        id:"userPanel",
        adjust: true,
        rows:[
            {
                view: "toolbar",
                css: "panelToolbar",
                fillspace:true,
                cols: [
                    {
                        view: "label",
                        width: 400,
                        template: "<span class='fa fa-users'/> Users"
                    },
                    {},
                    {
                        id: "addUserBtn",
                        view: "button",
                        type: "iconButton",
                        label: "Add new user",
                        icon: "plus-circle",
                        click: 'userView.showAddUserDialog',
                        autowidth: true
                    }
                ]

            },
            {
                id: "userDT",
                view: "datatable",
                css:"webixDatatable",
                select: true,
                navigation: true,
                fillspace: true,
                url:"api/user",
                on: {
                    onBeforeContextMenu: function (item) {
                        var user=$$("userDT").getSelectedItem();
                        if (user.id === userData.id || user.roleId===role.companyAdministrator)
                            return false;
                        this.select(item.row);
                    }
                },
                columns: [
                    {
                        id: "id",
                        hidden: true
                    },
                    {
                        id: "email",
                        fillspace:true,
                        width:350,
                        header: [
                            "E-mail",
                            {
                                content: "textFilter",
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "username",
                        fillspace:true,
                        header: [
                            "Username",
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
                        id: "statusId",
                        template: function (obj) {
                            return dependencyMap['status'][obj.statusId];
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
                                            return dependencyMap['status'][obj.value];
                                        }
                                    }

                                },
                                fillspace: true,
                                sort: "string"
                            }
                        ]
                    },
                    {
                        id: "roleId",
                        width:150,
                        template: function (obj) {
                            return dependencyMap['role'][obj.roleId];
                        },
                        header: [
                            "Role",
                            {

                                content: "richSelectFilter",
                                suggest: {
                                    body: {

                                        template: function (obj) {	// template for options list
                                            if (obj.$empty)
                                                return "";
                                            return dependencyMap['role'][obj.value];
                                        }
                                    }

                                },
                                sort: "string"
                            }
                        ]

                    },
                    {
                        id:"locationName",
                        fillspace:true,
                        header: [
                            "Location",
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
    
    selectPanel:function () {
        $$("main").removeView(rightPanel);
        rightPanel = "userPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("userDT",'api/user');
        webix.ui({
            view: "contextmenu",
            id: "userContextMenu",
            width: "200",
            data: [
                {
                    id: "delete",
                    value: "Delete",
                    icon: "trash"
                }
            ],
            master: $$("userDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    const user=$$("userDT").getSelectedItem();
                    var delBox = (webix.copy(commonViews.deleteConfirm(user.username,user.username)));
                    delBox.callback = function (result) {
                        if (result) {
                            $$("userDT").remove(context.id.row);
                        }
                    };
                    webix.confirm(delBox);
                }
            }
        });
    },

    addUserDialog: {
        view: "popup",
        id: "addUserDialog",
        modal: true,
        position: "center",
        body: {
            id: "addUserInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> New user",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('addUserDialog');"
                }]
            }, {
                view: "form",
                id: "addUserForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [
                    {
                        view: "text",
                        id: "email",
                        name: "email",
                        label: "E-mail:",
                        required: true,
                        invalidMessage: "Enter valid email"
                    },{
                        view: "text",
                        id: "roleId",
                        name: "roleId",
                        value:role.user,
                        hidden:true
                    },
                    {
                        view: "text",
                        id: "deleted",
                        name: "deleted",
                        hidden:true,
                        value:0
                    },
                    {
                        view: "text",
                        id: "statusId",
                        name: "statusId",
                        value:userStatus.inactive,
                        hidden:true
                    },
                    {
                        view:"richselect",
                        id:"locationId",
                        name:"locationId",
                        label:"Location",
                        required:true,
                        invalidMessage:"Select location"
                    },
                    {
                        margin: 5,
                        cols: [{}, {
                            id: "saveUser",
                            view: "button",
                            value: "Save",
                            type: "form",
                            click: "userView.addUser",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "email": function (value) {
                        if (!value) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'Enter e-mail!';
                            return false;
                        }
                        if (value.length > 100) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'Maximum length 100';
                            return false;
                        }
                        if (!webix.rules.isEmail(value)) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'E-mail not valid.';
                            return false;
                        }

                        return true;
                    }
                }
            }]
        }
    },
    showAddUserDialog: function () {
        if (util.popupIsntAlreadyOpened("addUserDialog")) {
            webix.ui(webix.copy(userView.addUserDialog)).show();
            webix.UIManager.setFocus("email");
            var locations=[];
            webix.ajax().get("api/location").then(function (data) {
                var array=data.json();
                array.forEach(function (obj) {
                    locations.push({
                        id:obj.id,
                        value:obj.name+" @"+obj.address
                    });

                });
                $$("locationId").define("options",locations);
                $$("locationId").refresh();
            });

        }
    },
    
    addUser:function () {
        var form=$$("addUserForm");
        if (form.validate()){
            var user=form.getValues();
            user.companyId=userData.companyId;
            webix.ajax().header({"Content-type": "application/json"})
                .post("/api/user/",user).then(function (data) {
                if (data) {
                    util.messages.showMessage("Request sent on mail")
                } else {
                    util.messages.showErrorMessage("Request not sent.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
            util.dismissDialog('addUserDialog');
        }
    }
};