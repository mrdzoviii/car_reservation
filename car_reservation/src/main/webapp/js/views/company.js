
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
                                template: "<span class='fa fa-user'/> Korisnici"
                            },
                            {},
                            {
                                id: "addUserBtn",
                                view: "button",
                                type: "iconButton",
                                label: "Dodajte",
                                icon: "plus-circle",
                                click: 'companyView.showAddUserDialog',
                                autowidth: true
                            }
                        ]

                    },
                    {
                        // TODO richSelectFilter treba prepraviti sa integera na podatke,
                        id: "userDT",
                        view: "datatable",
                        css:"webixDatatable",
                        select: true,
                        navigation: true,
                        fillspace: true,
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
                                    "Korisničko ime",
                                    {
                                        content: "textFilter",
                                        sort: "string"
                                    }
                                ]
                            },
                            {
                                id: "name",
                                template: function(obj) {
                                    if (obj.firstName){
                                        return obj.firstName+" "+obj.lastName;
                                    } else return "";
                                },
                                fillspace:true,
                                header: [
                                    "Ime i prezime",
                                    {
                                        content: "textFilter",
                                        sort: "string"
                                    }
                                ]
                            },
                            {
                                id: "statusId",
                                fillspace:true,
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
                                width:230,
                                template: function (obj) {
                                    return dependencyMap['role'][obj.roleId];
                                },
                                header: [
                                    "Tip korisnika",
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
                                    "Lokacija",
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
                    id: "delete",
                    value: "Delete",
                    icon: "trash-alt"
                }
            ],
            master: $$("companyDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    var delBox = (webix.copy(commonViews.deleteConfirm("company")));
                    delBox.callback = function (result) {
                        if (result) {
                            $$("companyDT").remove(context.id.row);
                            $$("companyDT").select(-1);
                            $$("userDT").clearAll();
                            $$("userDT").load("api/user/company/0");
                        }
                    };
                    webix.confirm(delBox);
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
                    icon: "trash-alt"
                }
            ],
            master: $$("userDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    var delBox = (webix.copy(commonViews.deleteConfirmSerbian("korisnika", "korisnika")));
                    delBox.callback = function (result) {
                        if (result) {
                            $$("userDT").remove(context.id.row);
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
    addUserDialog: {
        id: "addUserDialog",
        view: "popup",
        modal: true,
        position: "center",
        body: {
            rows: [
                {
                    view: "toolbar",
                    css: "panelToolbar",
                    cols: [
                        {
                            view: "label",
                            width: 300,
                            template: "<span class='fa fa-user'/> Dodavanje korisnika"
                        },
                        {},
                        {
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addUserDialog');"
                        }
                    ]
                },
                {
                    id: "addUserForm",
                    view: "form",
                    elementsConfig: {
                        labelWidth: 100,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "email",
                            name: "email",
                            view: "text",
                            label: "E-mail:",
                            required: true,
                            invalidMessage: "E-mail je obavezan!"
                        },
                        {
                            id:"roleId",
                            name:"roleId",
                            label: "Uloga:",
                            view:"richselect",
                            required:true,
                            invalidMessage:"Uloga je obavezna!",
                            on:{
                                onChange:function (newv,oldv) {
                                    if (newv===role.systemAdministrator){
                                        $$("companyId").define("required",false);
                                        $$("companyId").define("disabled",true);
                                        $$("companyId").setValue(null);

                                    }else{
                                        $$("companyId").define("required",true);
                                        $$("companyId").define("disabled",false);


                                    }
                                    $$("companyId").refresh();


                                }
                            }
                        },
                        {
                            id:"companyId",
                            name:"companyId",
                            label: "Kompanija:",
                            view:"richselect",
                            required:false,
                            disabled:true,
                            invalidMessage:"Kompanija je obavezna!",
                            on: {
                                onChange: function (newv, oldv) {
                                    if (newv){
                                        var locations=[];
                                        webix.ajax().get("api/location/byCompany/"+newv).then(function (data) {
                                            var array=data.json();
                                            array.forEach(function (obj) {
                                                locations.push({
                                                    id:obj.id,
                                                    value:obj.label
                                                });

                                            });
                                            $$("locationId").define("options",locations);
                                            $$("locationId").define("disabled",false);
                                            $$("locationId").refresh();
                                        });

                                    }else{
                                        $$("locationId").setValue(null);
                                        $$("locationId").define("options",[]);
                                        $$("locationId").define("disabled",true);
                                    }
                                    $$("locationId").refresh();
                                }
                            }
                        },
                        {
                            id:"locationId",
                            name:"locationId",
                            label: "Lokacija:",
                            view:"richselect",
                            disabled:true,
                        },
                        {
                            id: "addUserBtn",
                            view: "button",
                            value: "Dodajte korisnika",
                            type: "form",
                            click: "companyView.addUser",
                            align: "right",
                            hotkey: "enter",
                            width: 150
                        }

                    ],
                    rules: {
                        "email": function (value) {
                            if (!value) {
                                $$('addUserForm').elements.email.config.invalidMessage = 'E-mail je obavezan!';
                                return false;
                            }
                            if (!webix.rules.isEmail(value)) {
                                $$('addUserForm').elements.email.config.invalidMessage = 'E-mail nije u validnom formatu!';
                                return false;
                            }
                            return true;
                        }
                    }
                }
            ]
        }
    },


    showAddUserDialog: function () {
        if (util.popupIsntAlreadyOpened("addUserDialog")) {
            webix.ui(webix.copy(companyView.addUserDialog)).show();
            webix.UIManager.setFocus("username");
            $$("roleId").define("options",dependency.role);
            $$("roleId").refresh();

            var currentCompanies=[];
            $$("companyDT").eachRow(function (row){
                if (row!=-1) {
                    currentCompanies.push({
                        id: row,
                        value: $$("companyDT").getItem(row).name
                    })
                }
            });

            $$("roleId").define("options",dependency.role);
            $$("roleId").refresh();
            $$("companyId").define("options",currentCompanies);
            $$("companyId").refresh();
        }
    },

    addUser:function () {
        var form=$$("addUserForm");
        if (form.validate()){
            var user=form.getValues();
            user.statusId=userStatus.onHold;
            var companyId=user.companyId?user.companyId:-1;
            onCompanyClick(companyId).then(function () {
                $$("userDT").add(user);
                util.dismissDialog('addUserDialog');
            });

        }
    },

    // dwTODO NE UCITA SVE
};
