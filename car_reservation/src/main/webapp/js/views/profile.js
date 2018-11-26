var profileView={
    changeProfileDialog: {
        view: "popup",
        id: "changeProfileDialog",
        modal: true,
        position: "center",
        body: {
            rows: [{
                view: "toolbar",
                padding: 8,
                cols: [{
                    view: "label",
                    label: "<span class='fa fa-user'></span> Profile"
                },{}, {
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('changeProfileDialog');"

                }]
            }, {
                cols: [{"view": "template",
                    borderless:true,
                    width: 30,
                    height: 30,
                    "template": "<p></p>"},{rows:[{"view": "template",
                        borderless:true,
                        height: 50,
                        "template": "<p>Profile photo</p>"},
                        {
                            "view": "template",
                            borderless:true,
                            id: "photo",
                            name: "photo",
                            width: 200,
                            height: 200,
                            "template": "<img src='#src#' class='photo-alignment'/>",
                            onClick:{
                                "photo-alignment":function(e, id, trg){
                                    $$("uploadAPI").fileDialog();
                                    return false; // here it blocks the default behavior

                                }
                            },},{}
                    ]
                },{"view": "template",
                    borderless:true,
                    width: 30,
                    "template": "<p></p>"}
                    ,
                    {
                        view: "form",
                        borderless:true,
                        id: "profileForm",
                        elementsConfig: {
                            bottomPadding: 20
                        },
                        elements: [{
                            view: "text",
                            width:400,
                            align:"left",
                            id: "username",
                            name: "username",
                            readonly:true,
                            label: "Username:",
                            labelAlign:'left',
                            labelWidth:118,
                            invalidMessage: "Enter username!",
                            required: true
                        },{
                            view: "text",
                            name: "base64ImageUser",
                            hidden: true
                        },
                            {view: "text",
                                id: "firstName",
                                name: "firstName",
                                width:400,
                                align:"left",
                                label: "Ime:",
                                readonly:false,

                                invalidMessage: "Enter first name!",
                                labelAlign:'left',
                                labelWidth:118,
                                required: true
                            },
                            {view: "text",
                                id: "lastName",
                                name: "lastName",
                                width:400,
                                align:"left",
                                label: "Last name:",
                                readonly:false,
                                editaction:"dblclick",
                                invalidMessage: "Enter last name!",
                                labelAlign:'left',
                                labelWidth:118,
                                required: true
                            },
                            {
                                view: "text",
                                id: "email",
                                name: "email",
                                width:400,
                                align:"left",
                                label: "E-mail:",
                                readonly:true,
                                labelAlign:'left',
                                labelWidth:118

                                // required: true
                            },{
                                cols:[

                                    {
                                        margin:5,
                                        id: "saveProfile",
                                        view: "button",
                                        align:"right",
                                        value: "Save",
                                        type: "form",
                                        click: "profileView.saveChanges",
                                        hotkey: "enter",
                                        width:200,
                                        //  hidden:true

                                    }

                                ]}],
                        rules: {

                            "firstName": function (value) {
                                if (!value)
                                {return false;}
                                if (value.length > 100) {
                                    $$('profileForm').elements.firstName.config.invalidMessage = 'Maximum length is 100!';
                                    return false;
                                }
                                return true;
                            },
                            "lastName": function (value) {
                                if (!value)
                                {return false;}
                                if (value.length > 100) {
                                    $$('profileForm').elements.lastName.config.invalidMessage = 'Maximum length is 100!';
                                    return false;
                                }
                                return true;
                            },

                        }}]}]
        }},
    saveChanges:function() {
        if ($$("profileForm").validate()) {

            var newUser = userData;
            newUser.firstName = $$("profileForm").getValues().firstName;
            newUser.lastName = $$("profileForm").getValues().lastName;
            newUser.fullName=newUser.firstName+" "+newUser.lastName;
            newUser.avatar = $$("photo").getValues()['src'].split("base64,")[1];
            connection.sendAjax("PUT", "api/user/" + newUser.id,
                function (text, data, xhr) {
                    if (text) {
                        util.dismissDialog('changeProfileDialog');
                        util.messages.showMessage("User data changed.");
                        userData = newUser;
                        $$("userAvatar").define("data", {avatar:userData.avatar});
                        $$("userDT").updateItem(userData.id, userData);
                        if (userData.roleId === role.systemAdministrator)
                            $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br> System admin</p>");

                            else if (userData.roleId === role.companyAdministrator)
                            $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br> Company admin</p>");
                        else $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br>User</p>");
                    } else
                        util.messages.showErrorMessage("Profile update failed.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, newUser);
        }
    },
    changePasswordDialog: {
        view: "popup",
        id: "changePasswordDialog",
        modal: true,
        position: "center",
        body: {
            id: "changePasswordInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='fa fa-key'></span> Change password",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('changePasswordDialog');"
                }]
            }, {
                view: "form",
                id: "changePasswordForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [{
                    view: "text",
                    type:"password",
                    id: "oldPassword",
                    name: "oldPassword",
                    label: "Old password: ",
                    invalidMessage: "Enter password!",
                    required: true

                },
                    {
                        id: "newPassword",
                        name: "newPassword",
                        view: "text",
                        type:"password",
                        label: "New password: ",
                        invalidMessage: "Enter password!",
                        required: true
                    }, {
                        id: "newPasswordRepeated",
                        name: "newPasswordRepeated",
                        view: "text",
                        type:"password",
                        label: "Re-enter password: ",
                        invalidMessage: "Password mismatch!",
                        required: true
                    }, {
                        margin: 5,
                        cols: [{}, {
                            id: "savePassword",
                            view: "button",
                            value: "Save",
                            type: "form",
                            click: "profileView.save",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "oldPassword":function (value) {
                        if (!value)
                            return false;
                        return true;
                    },
                    "newPassword":function (value) {
                        var re1 = /[0-9]/;
                        var re2 = /[a-z]/;
                        var re3 = /[A-Z]/;
                        var re4=/[@#$%^&+=]/;
                        if (!value)
                            return false;
                        if(value.length<8) {
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password must be at lease 8 characters!';
                            return false;
                        }
                        if(!re1.test(value)){
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password must contains digit!';
                            return false;
                        }
                        if(!re2.test(value)){
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password must contains lowercase letter!';
                            return false;
                        }
                        if(!re3.test(value)){
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password must contains uppercase letter!';
                            return false;
                        }
                        if(!re4.test(value)){
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password must contains special character: (@ # $ % ^ & + =) !';
                            return false;
                        }

                        return true;
                    },
                    "newPasswordRepeated":function (value) {
                        if (!value)
                            return false;
                        if(value!=$$("changePasswordForm").getValues().newPassword)
                        {
                            $$('changePasswordForm').elements.newPassword.config.invalidMessage = 'Password mismatch!';
                            return false;}

                        return true;
                    },
                }
            }]
        }
    },


    hide:function(){
        $$("tmpUser").hide();
    }
    ,
    save:function(){
        if ($$("changePasswordForm").validate()) {
            var passwordInformation={
                oldPassword:$$("changePasswordForm").getValues().oldPassword,
                newPassword:$$("changePasswordForm").getValues().newPassword,
                newPasswordRepeated:$$("changePasswordForm").getValues().newPasswordRepeated,
            };

            connection.sendAjax("POST", "api/user/password",
                function (text, data, xhr) {
                    if (text) {
                        util.messages.showMessage("Password changed.");
                    } else
                        util.messages.showErrorMessage("Password not changed.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, passwordInformation);
            util.dismissDialog('changePasswordDialog');


        }
    }
};
webix.ui(
    {
        view:"uploader",
        id:"uploadAPI",
        accept:"image/jpeg, image/png",
        autosend:false,
        width:200,
        apiOnly: true,
        align:"center",
        multiple:false,
        on:{
            onBeforeFileAdd: function(upload){
                var file = upload.file;
                var reader = new FileReader();
                reader.onload = function(event) {
                    $$("photo").setValues({src:event.target.result});

                };
                reader.readAsDataURL(file)
                return false;
            }
        }
    }
)
