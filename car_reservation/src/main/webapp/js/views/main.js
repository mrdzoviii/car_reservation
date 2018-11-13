var mainLayout = {
    id: "app",
    width: "auto",
    height: "auto",
    rows: [
        {
            cols: [
                {
                    view: "template",
                    width: 240,
                    css: "logoInside",
                    template: '<img  src="../../img/telegroup-logo.png"/>'
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
                            label: "Vehicle Management System"
                        },
                        {},
                        {
                            view: "menu",
                            id: "userMenu",
                            width: 60,
                            openAction: "click",
                            data: [
                                {
                                    value: "<span class='fa fa-angle-down'/>",
                                    icon: "cog",
                                    submenu: [
                                        {
                                            id: "1",
                                            icon: "sign-out",
                                            value: "Odjavite se",
                                        }
                                    ]
                                }
                            ],
                            on: {
                                onMenuItemClick: function (id) {
                                    switch (id) {
                                        case "1":
                                            logout();
                                            break;
                                    }
                                }
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
                                    id: "companyName",
                                    name: "companyName",
                                    view: "text",
                                    label: "Company:"
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
                                }
                            ]
                        },
                        {}

                    ]
                },
                {},
                {
                    view: "template",
                    borderless: true,
                    height: 500,
                    width: 700,
                    template: '<img  height="500" width="500" src="../../img/app-logo.png"/>'
                } ,
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

var registrationLayout = {
    id: "registration",
    width: "auto",
    height: "auto",
    userId:null,
    rows: [
        {
            height: 50
        },
        {
            cols: [
                {},
                {
                    view: "template",
                    borderless: true,
                    height: 500,
                    width: 500,
                    template: '<img  src="../../img/telegroup-logo.png"/>' +
                        '<img  src="../../img/app-logo.png"/>'
                },
                {
                    rows: [
                        {
                            height: 50,
                            view:"label",
                            css:"registration-label",
                            label:"Registracija"
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

                                },
                                {
                                    id: "username",
                                    name: "username",
                                    view: "text",
                                    label: "Korisničko ime:",
                                    invalidMessage: "Korisničke ime je obavezno!",
                                    required: true
                                },
                                {
                                    id: "firstName",
                                    name: "firstName",
                                    view: "text",
                                    label: "Ime:",
                                    invalidMessage: "Ime je obavezno!",
                                    required: true
                                },
                                {
                                    id: "lastName",
                                    name: "lastName",
                                    view: "text",
                                    label: "Prezime:",
                                    invalidMessage: "Prezime je obavezno!",
                                    required: true
                                },
                                {
                                    id: "password",
                                    name: "password",
                                    view: "text",
                                    type: "password",
                                    label: "Lozinka:",
                                    invalidMessage: "Lozinka je obavezna!",
                                    required: true
                                },

                                {
                                    id: "registrationBtn",
                                    view: "button",
                                    value: "Registrujte se",
                                    type: "form",
                                    click: "register",
                                    align: "right",
                                    hotkey: "enter",
                                    width: 150
                                }
                            ]
                        },
                        {}

                    ]
                },
                {}
            ]
        }
    ]
};

var register=function () {
    var form=$$("registrationForm");
    if (form.validate()){
        webix.ajax().header({
            "Content-Type": "application/json"
        }).post("api/user/register",form.getValues()).then(function (result) {
            util.messages.showMessage("Uspješna registracija. Sada se možete prijaviti na sistem.");

            setTimeout(function () {
                webix.ajax().get("api/user/state").then(function (data) {
                    return webix.ajax().get("/api/user/logout");
                }).then(function (value) {
                    var url=window.location;
                    url.replace(url.protocol+"//"+url.host);
                }).fail(function (err) {
                        var url=window.location;
                        url.replace(url.protocol+"//"+url.host);
                });
                /*if (userData)
                    logout();
                else{
                    var url=window.location;
                    url.replace(url.protocol+"//"+url.host);
                }*/
            },2000);

        }).fail(function (err) {
            util.messages.showErrorMessage(err.responseText);
        })
    }
};

var userView={
    panel:{
        id:"userDialog",
        view:"popup",
        modal:true,
        position:"center",
        body:{
            rows:[
                {
                    view:"toolbar",
                    css:"panelToolbar",
                    cols:[

                    ]
                },
                {
                    view: "form",
                    id: "registrationForm",
                    borderless: true,
                    width: 400,
                    elementsConfig: util.elementsConfig,
                    elements: [
                        {
                            id:"id",
                            name:"id",
                            hidden:true
                        },
                        {
                            id: "username",
                            name: "username",
                            view: "text",
                            label: "Korisničko ime:",
                            invalidMessage: "Korisničke ime je obavezno!",
                            required: true
                        },
                        {
                            id: "firstName",
                            name: "firstName",
                            view: "text",
                            label: "Ime:",
                            invalidMessage: "Ime je obavezno!",
                            required: true
                        },
                        {
                            id: "lastName",
                            name: "lastName",
                            view: "text",
                            label: "Prezime:",
                            invalidMessage: "Prezime je obavezno!",
                            required: true
                        },
                        {
                            view: "button",
                            value: "Sačuvajte",
                            type: "form",
                            click: "userView.save",
                            align: "right",
                            hotkey: "enter",
                            width: 150
                        }
                    ]
                }
            ]
        }

    }
};
