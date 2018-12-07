var mainLayout = {
    id: "app",
    width: "auto",
    height: "auto",
    rows: [
        {
            cols: [
                {
                    id: "companyLogo",
                    view: "template",
                    width: 240,
                    css: "logoInside",
                    adjust: true,
                    template: function (obj) {
                        if (obj.companyLogo !== null) {
                            return '<img src="data:image/jpeg;charset=utf-8;base64, ' + obj.companyLogo + '" width="240px" height="50px">'
                        }
                        return '<img  src="../../img/system-admin.jpg" width="240px" height="50px"/>'

                    }
                },
                {
                    view: "toolbar",
                    css: "mainToolbar",
                    height: 50,
                    cols: [
                        {
                            id: "appNameLabel",
                            view: "label",
                            css: "appNameLabel",
                            label: "Car Reservation System"
                        },
                        {},

                        {
                            id: "userLabel",
                            view: "label",
                            align: "right",
                            label: ""
                        },
                        {
                            id: "userAvatar",
                            view: "template",
                            align: "right",
                            css: "profile-image",
                            borderless: "true",
                            height: 40,
                            width: 40,
                            template: function (obj) {
                                if (obj.avatar !== null) {
                                    return '<img src="data:image/jpeg;charset=utf-8;base64, ' + obj.avatar + '" width="40px" height="40px">';
                                }
                                return '<img  src="../../img/avatar-default.png" width="40px" height="40px">'
                            }
                        },

                        {
                            view: "menu",
                            id: "userMenu",
                            width: 60,
                            openAction: "click",
                            submenuConfig: {
                                width: 200
                            }
                        }
                    ]
                }
            ]
        },
        {
            id: "main",
            cols: [
                {
                    rows: [
                        {
                            id: "mainMenu",
                            css: "mainMenu",
                            view: "sidebar",
                            gravity: 0.01,
                            minWidth: 41,
                            collapsed: true
                        },
                        {
                            id: "sidebarBelow",
                            css: "sidebar-below",
                            view: "template",
                            template: "",
                            height: 50,
                            gravity: 0.01,
                            minWidth: 41,
                            width: 41,
                            type: "clean"
                        }
                    ]
                },
                {
                    id: "emptyRightPanel"
                }
            ]
        }
    ]
};


var loginLayout = {
    id: "login",
    width: "auto",
    height: "auto",
    rows: [
        {
            height: 50
        },
        {
            cols: [
                {},
                {
                    rows: [
                        {
                            height: 50,
                        },
                        {
                            view: "form",
                            id: "loginForm",
                            borderless: true,
                            width: 400,
                            elementsConfig: util.elementsConfig,
                            elements: [
                                {
                                    id: "username",
                                    name: "username",
                                    view: "text",
                                    label: "Username:",
                                    invalidMessage: "Username required!",
                                    required: true
                                },
                                {
                                    id: "password",
                                    name: "password",
                                    view: "text",
                                    type: "password",
                                    label: "Password:",
                                    invalidMessage: "Password required!",
                                    required: true
                                },
                                {
                                    id: "company",
                                    name: "company",
                                    view: "text",
                                    label: "Company:",
                                    icon: ""
                                },
                                {
                                    id: "loginBtn",
                                    view: "button",
                                    value: "Log in",
                                    type: "form",
                                    click: "login",
                                    align: "right",
                                    hotkey: "enter",
                                    width: 150
                                },
                                {
                                    margin: 5,
                                    cols: [
                                        {},
                                        {
                                            width: 160,
                                            view: "button",
                                            align: "right",
                                            type:"icon",
                                            label: "<b style='color:blue;'>Forgot my password</b>",
                                            click:'showForgottenPasswordPopup'
                                        }

                                    ]
                                }
                            ]
                        },


                    ]
                },
                {},
                {
                    view: "template",
                    borderless: true,
                    height: 500,
                    width: 700,
                    template: '<img  height="500" width="500" src="../../img/app-logo.png"/>'
                },
                {}
            ]
        },


    ]
};

var login = function () {
    var form = $$("loginForm");
    if (form.validate()) {
        webix.ajax().header({
            "Content-Type": "application/json"
        }).post("/api/user/login", form.getValues()).then(function (data) {
            userData = data.json();
            showApp();

        }).fail(function (err) {
            util.messages.showErrorMessage("Login failed!");
        });
    }
};


var registrationLayout = {
    id: "registration",
    width: "auto",
    height: "auto",
    userId: null,
    rows: [
        {
            height: 50
        },
        {
            cols: [
                {
                    view: "template",
                    borderless: true,
                    height: 700,
                    width: 700,
                    fillspace: true,
                    template: '<img  src="../../img/app-logo.png" alt="logo missing"/>'
                },
                {},
                {
                    rows: [
                        {
                            height: 50,
                            view: "label",
                            css: "registration-label",
                            label: "Registration"
                        },
                        {},
                        {
                            view: "form",
                            id: "registrationForm",
                            borderless: true,
                            width: 400,
                            elementsConfig: util.elementsConfig,
                            elements: [
                                {
                                    id: "id",
                                    name: "id",
                                    view: "text",
                                    hidden: true

                                },
                                {
                                    id: "username",
                                    name: "username",
                                    view: "text",
                                    label: "Username:",
                                    invalidMessage: "Username required!",
                                    required: true
                                },
                                {
                                    id: "firstName",
                                    name: "firstName",
                                    view: "text",
                                    label: "First name:",
                                    invalidMessage: "First name required!",
                                    required: true
                                },
                                {
                                    id: "lastName",
                                    name: "lastName",
                                    view: "text",
                                    label: "Last name:",
                                    invalidMessage: "Last name required!",
                                    required: true
                                },
                                {
                                    id: "password",
                                    name: "password",
                                    view: "text",
                                    type: "password",
                                    label: "Password:",
                                    invalidMessage: "Password required!",
                                    required: true
                                },

                                {
                                    id: "registrationBtn",
                                    view: "button",
                                    value: "Register",
                                    type: "form",
                                    click: "register",
                                    align: "right",
                                    hotkey: "enter",
                                    width: 150
                                }
                            ],
                            rules: {
                                "password": function (value) {
                                    var re1 = /[0-9]/;
                                    var re2 = /[a-z]/;
                                    var re3 = /[A-Z]/;
                                    var re4 = /[@#$%^&+=]/;
                                    if (!value)
                                        return false;
                                    if (value.length < 8) {
                                        $$('registrationForm').elements.password.config.invalidMessage = 'Password must be at lease 8 characters!';
                                        return false;
                                    }
                                    if (!re1.test(value)) {
                                        $$('registrationForm').elements.password.config.invalidMessage = 'Password must contains digit!';
                                        return false;
                                    }
                                    if (!re2.test(value)) {
                                        $$('registrationForm').elements.password.config.invalidMessage = 'Password must contains lowercase letter!';
                                        return false;
                                    }
                                    if (!re3.test(value)) {
                                        $$('registrationForm').elements.password.config.invalidMessage = 'Password must contains uppercase letter!';
                                        return false;
                                    }
                                    if (!re4.test(value)) {
                                        $$('registrationForm').elements.password.config.invalidMessage = 'Password must contains special character: (@ # $ % ^ & + =) !';
                                        return false;
                                    }

                                    return true;
                                },
                                "username": function (value) {
                                    if (value.length > 80) {
                                        $$('registrationForm').elements.username.config.invalidMessage = 'Maximum length is 128.';
                                        return false;
                                    }
                                    return true;
                                },
                                "firstName": function (value) {
                                    if (value.length > 255) {
                                        $$('registrationForm').elements.firstName.config.invalidMessage = 'Maximum length is 128.';
                                        return false;
                                    }

                                    return true;
                                },
                                "lastName": function (value) {
                                    if (value.length > 255) {
                                        $$('registrationForm').elements.lastName.config.invalidMessage = 'Maximum length is 128.';
                                        return false;
                                    }
                                    return true;
                                },
                            }
                        },
                        {}

                    ]
                },
                {}
            ]
        }
    ]
};

var register = function () {
    var form = $$("registrationForm");
    if (form.validate()) {
        webix.ajax().header({
            "Content-Type": "application/json"
        }).post("api/user/register", form.getValues()).then(function (result) {
            util.messages.showMessage("Successful registration.Now you can try to log in!");

            setTimeout(function () {
                webix.ajax().get("api/user/state").then(function (data) {
                    return webix.ajax().get("/api/user/logout");
                }).then(function (value) {
                    var url = window.location;
                    url.replace(url.protocol + "//" + url.host);
                }).fail(function (err) {
                    var url = window.location;
                    url.replace(url.protocol + "//" + url.host);
                });
                /*if (userData)
                    logout();
                else{
                    var url=window.location;
                    url.replace(url.protocol+"//"+url.host);
                }*/
            }, 2000);

        }).fail(function (err) {
            util.messages.showErrorMessage(err.responseText);
        })
    }
};


var showForgottenPasswordPopup = function () {
    if (util.popupIsntAlreadyOpened("forgottenPasswordPopup")) {
        webix.ui(webix.copy(forgottenPasswordPopup)).show();
        $$("forgottenPasswordForm").focus();
    }
};

var forgottenPasswordPopup = {
    view: "popup",
    id: "forgottenPasswordPopup",
    modal: true,
    position: "center",
    body: {
        rows: [
            {
                view: "toolbar",
                cols: [
                    {
                        view: "label",
                        label: "Forgot my password",
                        width: 400
                    },
                    {},
                    {
                        view: "icon",
                        icon: "close",
                        align: "right",
                        hotkey: "esc",
                        click: "util.dismissDialog('forgottenPasswordPopup');"
                    }
                ]
            },
            {
                width: 400,
                view: "form",
                id: "forgottenPasswordForm",
                elementsConfig: {
                    labelWidth: 150,
                    bottomPadding: 18
                },
                elements: [
                    {

                        view: "text",
                        id: "username",
                        name: "username",
                        required: "true",
                        label: "Username:",
                        invalidMessage: "Username required!"
                    },
                    {

                        view: "text",
                        id: "company",
                        name: "company",
                        required: "true",
                        label: "Company:",
                        invalidMessage: "Company required!"
                    },
                    {
                        cols: [
                            {},
                            {
                                view: "button",
                                value: "Reset my password",
                                type: "form",
                                click: "generatePassword",
                                hotkey: "enter",
                            }
                        ]
                    }
                ]
            }
        ]
    }
};

var generatePassword = function () {
    var form = $$("forgottenPasswordForm");
    if (form.validate()) {
        var loginInformation = JSON.stringify(form.getValues());
        webix.ajax().headers({
            "Content-type": "application/json"
        }).post("api/user/reset", loginInformation).then(function (result) {
            if (result.text()) {
                util.messages.showMessage("Reset password successful. Check your e-mail.");
            } else {
                util.messages.showErrorMessage("Reset password fail!");
            }
        }).fail(function (error) {
            util.messages.showErrorMessage(error.responseText);
        });
        util.dismissDialog("forgottenPasswordPopup");
    }
};


var logout = function () {

    webix.ajax().get("/api/user/logout", function (xhr) {
        if (xhr.status = "200") {
            userData = null;
            util.messages.showLogoutMessage();
            connection.reload();
        } else {
            util.messages.showLogoutErrorMessage();
            connection.reload();
        }
    });
};


