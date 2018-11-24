
var onCompanyClick=function (id) {
    $$("companyDT").select(id);
    var companyId = id === -1 ? 0 : id;
    var datatable = $$("userDT");
    connection.dettachAjaxEvents("userDT");
    datatable.clearAll();
    connection.attachAjaxEvents("userDT","api/user");
    return datatable.load("api/user/company/" + companyId);

};
var companyView = {

    panel: {
        id: "companyPanel",
        adjust: true,
        cols: [
            {
                width:450,
                rows: [
                    {
                        view: "toolbar",
                        css: "panelToolbar",
                        cols: [
                            {
                                view: "label",
                                width: 150,
                                template: "<span class='fa fa-briefcase'/> Companies"
                            },
                            {},
                            {
                                id: "addCompanyBtn",
                                view: "button",
                                type: "iconButton",
                                label: "New company",
                                icon: "plus-circle",
                                click: 'companyView.showAddDialog',
                                autowidth: true
                            }
                        ]
                    },
                    {
                        id: "companyDT",
                        view: "datatable",
                        css:"webixDatatable",
                        fillspace:true,
                        borderless:true,
                        header: true,
                        adjust:true,
                        select: true,
                        navigation: true,
                        editable: true,
                        editaction: "custom",
                        on: {
                            onItemClick:function(id){
                                onCompanyClick(id.row);
                            } ,
                            onBeforeContextMenu: function (item) {
                                if (item.row === -1)
                                    return false;
                                this.select(item.row);
                            }
                        },

                        url: "api/company",
                        data: [
                            {
                                name: "System admins",
                                id: -1,
                                logo: null
                            }
                        ],
                        columns: [
                            {
                                id: "id",
                                hidden: true
                            },
                            {
                                id: "deleted",
                                hidden: true
                            },
                            {
                                minWidth:180,
                                width:180,
                                id: "logo",
                                editable:"false",
                                template: function (obj) {
                                    if(obj.logo){
                                        return '<img src="data:image/jpeg;charset=utf-8;base64,'+obj.logo+' " width="176px" height="40px">';
                                    }
                                    return '<img src="../../img/system-admin.jpg" width="176px" height="40px">';
                                },
                                fillspace:true,
                                header:{text:"Logo", css:"wrap-line"}

                            },

                            {
                                id: "name",
                                editable: false,
                                fillspace: true,
                                header: [ "Name",{
                                    content:"textFilter"
                                }]
                            }
                        ]
                    }
                ]
            },

            //users part

            {
                rows: [
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
                                id: "addSystemAdminBtn",
                                view: "button",
                                type: "iconButton",
                                label: "New system admin",
                                icon: "plus-circle",
                                click: 'companyView.showAddNewSystemAdminDialog',
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
                        resizeColumn:true,
                        url:"api/user",
                        on: {
                            onBeforeContextMenu: function (item) {
                                if (item.row === userData.id)
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
            }
        ]

    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "companyPanel";
        var panelCopy = webix.copy(this.panel);
        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("companyDT", "api/company");
        webix.ui({
            view: "contextmenu",
            id: "companyContextMenu",
            width: "200",
            data: [
                {
                    id: "update",
                    value: "Update",
                    icon: "pencil"
                },
                {
                    id: "addUser",
                    value: "Add new user",
                    icon: "user-plus"
                },
                {
                    $template: "Separator"
                },
                {
                    id: "delete",
                    value: "Delete",
                    icon: "trash"
                }

            ],
            master: $$("companyDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    switch(id){
                        case "delete":
                            var delBox = (webix.copy(commonViews.deleteConfirm("company")));
                            delBox.callback = function (result) {
                                if (result) {
                                    var item = $$("companyDT").getItem(context.id.row);
                                    $$("companyDT").detachEvent("onBeforeDelete");
                                    connection.sendAjax("DELETE", "api/company/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("companyDT").remove(context.id.row);
                                            $$("companyDT").select(-1);
                                            $$("userDT").clearAll();
                                            $$("userDT").load("api/user/company/0");
                                            util.messages.showMessage("Success delete");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage(text);
                                    }, item);
                                }
                            };
                            webix.confirm(delBox);
                            break;
                        case "update":
                            companyView.showEditCompanyDialog($$("companyDT").getSelectedItem());
                            break;
                        case "addUser":
                            var item = $$("companyDT").getItem(context.id.row);
                            companyView.showAddNewUserDialog(item.id);
                            break;
                    }

                }
            }
        });
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
                    var delBox = (webix.copy(commonViews.deleteConfirm("user", "user")));
                    delBox.callback = function (result) {
                        if (result) {
                            var item = $$("userDT").getItem(context.id.row);
                            $$("userDT").detachEvent("onBeforeDelete");
                            connection.sendAjax("DELETE", "api/user/" + item.id, function (text, data, xhr) {
                                if (text) {
                                    $$("userDT").remove(context.id.row);
                                    util.messages.showMessage("Success delete");
                                }
                            }, function (text, data, xhr) {
                                util.messages.showErrorMessage(text);
                            }, item);
                        }
                    };
                    webix.confirm(delBox);
                }
            }
        });
        $$("companyDT").select(-1);
        $$("userDT").load("api/user/company/0");
    },

    addCompanyDialog: {
        view: "popup",
        id: "addCompanyDialog",
        modal: true,
        position: "center",
        body: {
            id: "addCompanyInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-briefcase'></span> Add new company",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addCompanyDialog');"
                        }
                    ]
                },
                {
                    view: "form",
                    id: "addCompanyForm",
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
                            invalidMessage: "Please enter company name.",
                            required: true
                        },
                        {
                            height:50,
                            cols:[
                                {
                                    view:"label",
                                    width:200,
                                    bottomPadding:18,
                                    leftPadding:3,
                                    required:true,
                                    label:"Logo : <span style='color:#e32'>*</span>"
                                },
                                {
                                    view:"list",
                                    name:"companyLogoList",
                                    rules:{
                                        content:webix.rules.isNotEmpty
                                    },
                                    scroll:false,
                                    id:"companyLogoList",
                                    width:290,
                                    type: {
                                        height: "auto"
                                    },
                                    css:"relative image-upload",
                                    template:"<img src='data:image/jpg;base64,#content#'/> <span class='delete-file'><span class='webix fa fa-close'/></span>",
                                    onClick:{
                                        'delete-file':function (e,id) {
                                            this.remove(id);
                                            return false;
                                        }
                                    }
                                },{},
                                {
                                    view:"uploader",
                                    id:"photoUploader",
                                    width:24,
                                    height:24,
                                    css:"upload-photo",
                                    template:"<span class='webix fa fa-upload' /></span>",
                                    on: {
                                        onBeforeFileAdd: function (upload) {
                                            var type = upload.type.toLowerCase();
                                            if (type != "jpg" && type != "png"){
                                                util.messages.showErrorMessage("Only jpg and png formats allowed!")
                                                return false;
                                            }
                                            var file = upload.file;
                                            var reader = new FileReader();
                                            reader.onload = function (event) {
                                                var img=new Image();
                                                img.onload=function (ev) {
                                                    if (img.width===220&& img.height===50) {
                                                        var newDocument = {
                                                            name: file['name'],
                                                            content: event.target.result.split("base64,")[1],
                                                        };
                                                        $$("companyLogoList").clearAll();
                                                        $$("companyLogoList").add(newDocument);
                                                    }else{
                                                        util.messages.showErrorMessage("220x50 px! dimension only")
                                                    }
                                                };
                                                img.src=event.target.result;
                                            };
                                            reader.readAsDataURL(file);
                                            return false;
                                        }
                                    }
                                },
                            ]
                        },
                        {
                            height:18,
                            cols:[

                                {},
                                {
                                    id:"invalidLabel",
                                    view:"label",
                                    label:"Company logo required!",
                                    css:" invalid-message-photo-alignment",
                                    hidden:true

                                },
                                {}
                            ]
                        }
                        ,
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "saveCompany",
                                    view: "button",
                                    value: "Save",
                                    type: "form",
                                    click: "companyView.addCompany",
                                    hotkey: "enter",
                                    width: 150
                                }
                            ]
                        }
                    ],
                    rules: {
                        "name": function (value) {
                            if (!value)
                                return false;
                            if (value.length > 100) {
                                $$('addCompanyForm').elements.name.config.invalidMessage = 'Name maximum length is 100!';
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
        if (util.popupIsntAlreadyOpened("addCompanyDialog")) {
            webix.ui(webix.copy(companyView.addCompanyDialog)).show();
            webix.UIManager.setFocus("name");
        }
    },

    addCompany: function () {
        var form = $$("addCompanyForm");
        var logo=$$("companyLogoList");
        var photoValidation=logo.count()===1;
        if (!photoValidation){
            webix.html.addCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").show();
        }else{
            webix.html.removeCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").hide();
        }
        var validation=form.validate();
        if (validation && photoValidation) {
            var newCompany = {
                name: form.getValues().name,
                logo: logo.getItem(logo.getLastId()).content,
                deleted: 0
            };
            $$("companyDT").add(newCompany);
            util.dismissDialog('addCompanyDialog');
        }
    },
    editCompanyDialog: {
        view: "popup",
        id: "editCompanyDialog",
        modal: true,
        position: "center",

        body: {
            id: "editCompanyInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> Edit company",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('editCompanyDialog');"
                }]
            }, {
                view: "form",
                id: "editCompanyForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [{
                    view: "text",
                    name: "id",
                    hidden: true
                },
                    {
                        view: "text",
                        name: "deleted",
                        hidden: true,
                    }
                    ,
                    {
                    view: "text",
                    id: "name",
                    name: "name",
                    label: "Name:",
                    invalidMessage: "Please enter company name!",
                    required: true
                },
                    {
                        height:50,
                        cols:[
                            {
                                view:"label",
                                width:200,
                                bottomPadding:18,
                                leftPadding:3,
                                required:true,
                                label:"Logo: <span style='color:#e32'>*</span>"
                            },
                            {
                                view:"list",
                                id:"editCompanyLogoList",
                                name:"editCompanyLogoList",
                                rules:{
                                    content:webix.rules.isNotEmpty
                                },
                                scroll:false,
                                id:"editCompanyLogoList",
                                width:290,
                                type: {
                                    height: "auto"
                                },
                                css:"relative image-upload",
                                template:"<img src='data:image/jpg;base64,#content#'/> <span class='delete-file'><span class='webix fa fa-close'/></span>",
                                onClick:{
                                    'delete-file':function (e,id) {
                                        this.remove(id);
                                        return false;
                                    }
                                }
                            },{},
                            {
                                view:"uploader",
                                id:"photoUploader",
                                width:24,
                                height:24,
                                css:"upload-photo",
                                template:"<span class='webix fa fa-upload' /></span>",
                                on: {
                                    onBeforeFileAdd: function (upload) {
                                        var type = upload.type.toLowerCase();
                                        if (type != "jpg" && type != "png"){
                                            util.messages.showErrorMessage("Only jpg and png extensions allowed")
                                            return false;
                                        }
                                        var file = upload.file;
                                        var reader = new FileReader();
                                        reader.onload = function (event) {
                                            var img=new Image();
                                            img.onload=function (ev) {
                                                if (img.width===220&& img.height===50) {
                                                    var newDocument = {
                                                        name: file['name'],
                                                        content: event.target.result.split("base64,")[1],
                                                    };
                                                    $$("editCompanyLogoList").clearAll();
                                                    $$("editCompanyLogoList").add(newDocument);
                                                }else{
                                                    util.messages.showErrorMessage("Only 220x50 px resolution allowed!")
                                                }
                                            };
                                            img.src=event.target.result;
                                        };
                                        reader.readAsDataURL(file);
                                        return false;
                                    }
                                }
                            },
                        ]
                    },
                    {
                        height:18,
                        cols:[

                            {},
                            {
                                id:"invalidLabel",
                                view:"label",
                                label:"Please select company logo!",
                                css:" invalid-message-photo-alignment",
                                hidden:true

                            },
                            {}
                        ]
                    }
                    ,

                    {
                        margin: 5,
                        cols: [{}, {
                            id: "changeCompany",
                            view: "button",
                            value: "Save",
                            type: "form",
                            click: "companyView.saveChangedCompany",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "name": function (value) {
                        if (!value)
                            return false;
                        if (value.length > 100) {
                            $$('editCompanyForm').elements.name.config.invalidMessage = 'Maximum length is 100!';
                            return false;
                        }
                        return true;
                    }
                }
            }]
        }
    },


    showEditCompanyDialog: function (company) {
        webix.ui(webix.copy(companyView.editCompanyDialog));
        var form = $$("editCompanyForm");
        form.elements.id.setValue(company.id);
        form.elements.name.setValue(company.name);
        form.elements.deleted.setValue(company.deleted);

        setTimeout(function () {
            $$("editCompanyDialog").show();
            webix.UIManager.setFocus("name");
            var newDocument = {
                name: '',
                content: company.logo,
            };
            $$("editCompanyLogoList").clearAll();
            $$("editCompanyLogoList").add(newDocument);
        }, 0);


    },


    saveChangedCompany: function () {
        var form = $$("editCompanyForm");
        var logo=$$("editCompanyLogoList");
        var photoValidation=logo.count()===1;
        if (!photoValidation){
            webix.html.addCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").show();
        }else{
            webix.html.removeCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").hide();
        }
        var validation=form.validate();
        if (validation && photoValidation) {
            var newCompany = {
                id: form.getValues().id,
                name: form.getValues().name,
                logo: logo.getItem(logo.getLastId()).content,
                deleted: form.getValues().deleted
            };
            console.log(newCompany);
            connection.sendAjax("PUT", "api/company/" + newCompany.id,
                function (text, data, xhr) {
                    if (text) {
                        util.messages.showMessage("Company updated successfully.");
                        $$("companyDT").updateItem(newCompany.id, newCompany);
                    } else
                        util.messages.showErrorMessage("Company edit failed.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, newCompany);

            util.dismissDialog('editCompanyDialog');
        }

    },

    //ADD NEW USER

    addNewUserDialog: {
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
                        view: "richselect",
                        id: "roleId",
                        name: "roleId",
                        label: "Role:",
                        required:true,
                        invalidMessage:"Select role"
                    },
                    {
                        view: "text",
                        id: "companyId",
                        name: "companyId",
                        hidden:true,
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
                        value:2,
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
                            click: "companyView.addUser",
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



    showAddNewUserDialog: function (companyId) {
        console.log("companyID from showAddNewUserDialog"+companyId)
        if (util.popupIsntAlreadyOpened("addUserDialog")) {
            webix.ui(webix.copy(companyView.addNewUserDialog)).show();
            webix.UIManager.setFocus("email");


            var locations=[];
            connection.sendAjax("GET", "api/location/company/" + companyId,
                function (text, data, xhr) {
                    if (text) {
                        var array = data.json();
                        array.forEach(function (obj) {
                            locations.push({
                                id: obj.id,
                                value: obj.name
                            });

                        });
                        $$("locationId").define("options",locations);
                        $$("locationId").refresh();
                        $$("companyId").define("value",companyId);
                    }
                }, function (text, data, xhr) {
                });

            var roles=[];
            connection.sendAjax("GET", "api/role/user/",
                function (text, data, xhr) {
                    if (text) {
                        var array = data.json();
                        array.forEach(function (obj) {
                            roles.push({
                                id: obj.id,
                                value: obj.role
                            });

                        });
                        $$("roleId").define("options",roles);
                        $$("roleId").refresh();
                    }
                }, function (text, data, xhr) {
                });

        }
    },

    addUser:function () {
        var form=$$("addUserForm");
        if (form.validate()){
            var user=form.getValues();
            onCompanyClick(user.companyId).then(function () {
                $$("userDT").add(user);
                util.dismissDialog('addUserDialog');
            });

        }
    },

    //ADDING NEW SYSTEM ADMIN

    addNewSystemAdminDialog: {
        view: "popup",
        id: "addNewSystemAdminDialog",
        modal: true,
        position: "center",
        body: {
            id: "addNewSystemAdminInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> Create system admin",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('addNewSystemAdminDialog');"
                }]
            }, {
                view: "form",
                id: "addSystemAdminForm",
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
                        invalidMessage: "Please enter mail"
                    },
                    {
                        view: "text",
                        id: "roleId",
                        name: "roleId",
                        value: 1,
                        hidden: true,
                    },
                    {
                        view: "text",
                        id: "deleted",
                        name: "deleted",
                        value: 0,
                        hidden: true,
                    },
                    {
                        view: "text",
                        id: "statusId",
                        name: "statusId",
                        value: 2,
                        hidden: true,
                    },
                    {
                        margin: 5,
                        cols: [{}, {
                            id: "saveSystemAdmin",
                            view: "button",
                            value: "Save",
                            type: "form",
                            click: "companyView.addSystemAdmin",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "email": function (value) {
                        if (!value) {
                            $$('addSystemAdminForm').elements.email.config.invalidMessage = 'Enter email!';
                            return false;
                        }
                        if (value.length > 100) {
                            $$('addSystemAdminForm').elements.email.config.invalidMessage = 'Maximum length 100';
                            return false;
                        }
                        if (!webix.rules.isEmail(value)) {
                            $$('addSystemAdminForm').elements.email.config.invalidMessage = 'E-mail not valid.';
                            return false;
                        }

                        return true;
                    }
                }
            }]
        }
    },

    showAddNewSystemAdminDialog: function () {
        if (util.popupIsntAlreadyOpened("addNewSystemAdminDialog")) {
            webix.ui(webix.copy(companyView.addNewSystemAdminDialog)).show();
            webix.UIManager.setFocus("email");
        }
    },

    addSystemAdmin:function () {
        var form=$$("addSystemAdminForm");
        if (form.validate()){
            var user=form.getValues();
            onCompanyClick(-1).then(function () {
                $$("userDT").add(user);
                util.dismissDialog('addNewSystemAdminDialog');
            });

        }
    }

    // dwTODO NE UCITA SVE
};
