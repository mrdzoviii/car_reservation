var MENU_STATES = {
    COLLAPSED: 0,
    EXPANDED: 1
};
var menuState = MENU_STATES.COLLAPSED;
var userData = null;
var panel = {id: "empty"};
var rightPanel = null;

var menuAdmin = [
    {
        value: "<span class='fa fa-angle-down'/>",
        icon: "cog",
        submenu: [
            {
                id: "editProfile",
                icon: "fas fa-edit",
                value: "Edit profile",
            },
            {
                id: "changePassword",
                icon: "fas fa-edit",
                value: "Change password",
            },
            {
                $template: "Separator"
            },
            {
                id: "signOut",
                icon: "sign-out",
                value: "Sign out",
            }]
    }
];

var menuUser = [
    {
        value: "<span class='fa fa-angle-down'/>",
        icon: "cog",
        submenu:
            [
                {
                    id: "editProfile",
                    icon: "fas fa-edit",
                    value: "Edit profile"
                },
                {
                    id: "changePassword",
                    icon: "fas fa-edit",
                    value: "Change password"
                },
                {
                    id: "changeMailOption",
                    icon: "fas fa-envelope",
                    value: "Notification settings"
                },
                {
                    $template: "Separator"
                },
                {
                    id: "signOut",
                    icon: "sign-out",
                    value: "Sign out",
                }
            ]
    }
];

var menuActions = function (id) {
    switch (id) {
        case "signOut":
            logout();
            break;
        case "editProfile":
            clickProfile();
            break;
        case "changePassword":
            clickPassword();
            break;
        case "changeMailOption":
            clickNotificationSettings();
            break;
    }
};

var mainMenuSystemAdmin = [
    {
        id: "company",
        icon: "briefcase",
        value: "Company"
    },
    {
        id: "logger",
        icon: "history",
        value: "User logs"
    }
];

var clickProfile = function () {
    if (util.popupIsntAlreadyOpened("changeProfileDialog")) {
        webix.ui(webix.copy(profileView.changeProfileDialog));
        $$("profileForm").load("api/user/" + userData.id);
        if (userData.avatar)
            $$("photo").setValues({src: "data:image/png;base64," + userData.avatar});
        else
            $$("photo").setValues({src: "../../img/avatar-default.png"});
        setTimeout(function () {
            $$("changeProfileDialog").show();
        }, 0);
    }
};
var clickPassword = function () {
    if (util.popupIsntAlreadyOpened("changePasswordDialog")) {
        webix.ui(webix.copy(profileView.changePasswordDialog));

        setTimeout(function () {
            $$("changePasswordDialog").show();
        }, 0);
    }

};

function clickNotificationSettings() {
    if (util.popupIsntAlreadyOpened("notificationSettingsDialog")) {
        webix.ui(webix.copy(profileView.notificationSettingsDialog));

        setTimeout(function () {
            $$("notificationSettingsDialog").show();
            $$("mailOptionId").define("value", userData.mailOptionId);
            $$("mailOptionId").refresh();
        }, 0)
    }
}

var mainMenuCompanyAdmin = [
    {
        id: "home",
        icon: "home",
        value: "Home"
    },
    {
        id: "location",
        icon: "map",
        value:"Locations"
    },
    {
        id:"users",
        icon: "users",
        value:"Users"
    },
    {
        id: "vehicle",
        icon: "car",
        value: "Vehicles"
    },
    {
        id: "logger",
        icon: "history",
        value: "User logs"
    },
    {
        id: "chart",
        value: "Reports",
        icon: "bar-chart"
    }
];

var mainMenuUser = [
    {
        id: "home",
        icon: "home",
        value: "Home"
    },
    {
        id: "location",
        icon: "map",
        value:"Locations"
    },
    {
        id: "vehicle",
        icon: "car",
        value: "Vehicles"
    },
    {
        id: "reservation",
        icon: "bookmark",
        value:"Reservation"
    }
];


var mainMenuActions = function (id) {
    switch (id) {
        case "logger":
            loggerView.selectPanel();
            break;
        case "company":
            companyView.selectPanel();
            break;
        case "vehicle":
            vehicleView.selectPanel();
            break;
        case "home":
            homeView.selectPanel();
            break;
        case "location":
            locationView.selectPanel();
            break;
        case "reservation":
            reservationView.selectPanel();
            break;
        case "chart":
            chartView.selectPanel();
            break;
        case "users":
            userView.selectPanel();
            break;
    }
};

var init = function () {
    if (!webix.env.touch && webix.ui.scrollSize) webix.CustomScroll.init();
    webix.i18n.setLocale("en-US");
    webix.Date.startOnMonday = true;
    webix.ui(panel);
    panel = $$("empty");
    var urlQuery = window.location.search;
    if (urlQuery && urlQuery.startsWith('?q=reg')) {
        var token = urlQuery.split('=')[2];
        webix.ajax().get("api/user/token/" + token).then(function (result) {
            var userId = result.json();
            showRegistration(userId);
        }).fail(function (err) {
            util.messages.showErrorMessage("Token not valid!");
            checkState();
        })
    } else {
        checkState();
    }

};

var checkState = function () {
    webix.ajax().get("api/user/state").then(function (data) {
        userData = data.json();
        showApp();
    }).fail(function (err) {
        showLogin();
    })
};

var mainMenuEvents = {
    onItemClick: function (item) {
        mainMenuActions(item);
    }
};

var menuEvents = {
    onMenuItemClick: function (item) {
        menuActions(item);
    }
}

var showLogin = function () {
    var login = webix.copy(loginLayout);
    webix.ui(login, panel);
    panel = $$("login");

};

var showRegistration = function (userId) {
    var registration = webix.copy(registrationLayout);
    webix.ui(registration, panel);
    panel = $$("registration");
    $$("registrationForm").elements.id.setValue(userId)

}

var showApp = function () {
    var promise = preloadDependencies();
    var main = webix.copy(mainLayout);
    webix.ui(main, panel);
    panel = $$("app");
    var localMainMenuData = null;
    var localMenuData = null;
    webix.ui({
        id: "menu-collapse",
        view: "template",
        template: '<div id="menu-collapse" class="menu-collapse">' +
            '<span></span>' +
            '<span></span>' +
            '<span></span>' +
            '</div>',
        onClick: {
            "menu-collapse": function (e, id, trg) {
                var elem = document.getElementById("menu-collapse");
                if (menuState == MENU_STATES.COLLAPSED) {
                    elem.className = "menu-collapse open";
                    menuState = MENU_STATES.EXPANDED;
                    $$("mainMenu").toggle();
                } else {
                    elem.className = "menu-collapse";
                    menuState = MENU_STATES.COLLAPSED;
                    $$("mainMenu").toggle();
                }
            }
        }
    });
    switch (userData.roleId) {
        case role.systemAdministrator:
            localMainMenuData = mainMenuSystemAdmin;
            localMenuData = menuAdmin;
            break;
        case role.companyAdministrator:
            vehicleView.preloadDependencies();
            localMainMenuData = mainMenuCompanyAdmin;
            localMenuData = menuAdmin;
            break;
        case role.user:
            vehicleView.preloadDependencies();
            localMainMenuData = mainMenuUser;
            localMenuData = menuUser;
            break;
    }
    if (userData.roleId === role.systemAdministrator)
        $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br> System admin</p>");
    else if (userData.roleId === role.companyAdministrator)
        $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br> Company admin</p>");
    else $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>" + userData.firstName + " " + userData.lastName + "<br>User</p>");
    var avatar = {"avatar": userData.avatar};
    var companyLogo = {"companyLogo": userData.companyLogo};
    $$("userAvatar").define("data", avatar);
    $$("mainMenu").define("data", localMainMenuData);
    $$("mainMenu").define("on", mainMenuEvents);
    $$("userMenu").define("data", localMenuData);
    $$("userMenu").define("on", menuEvents);
    $$("companyLogo").define("data", companyLogo);
    rightPanel = "emptyRightPanel";
    promise.then(function (value) {
        if (userData.roleId === role.systemAdministrator) {
            companyView.selectPanel();
            $$("mainMenu").select("company");
        } else {
            homeView.selectPanel();
            $$("mainMenu").select("home");
        }
    }).fail(function (err) {
        //connection.reload();
    });

};

var preloadDependencies = function () {
    var promises = [];
    promises.push(webix.ajax().get("api/role").then(function (data) {
        var roles = [];
        var array = [];
        data.json().forEach(function (obj) {
            roles[obj.id] = obj.role;
            var value = {id: obj.id, value: obj.role};
            array.push(value);
        });
        dependencyMap["role"] = roles;
        dependency["role"] = array;

    }));
    promises.push(webix.ajax().get("api/status").then(function (data) {
        var status = [];
        var array = [];

        data.json().forEach(function (obj) {
            status[obj.id] = obj.status;
            array.push(obj);
        });
        dependencyMap["status"] = status;
        dependency["status"] = array;
    }));
    promises.push(webix.ajax().get("api/cost").then(function (data) {
        var expenseTypes = [];
        var array = [];

        data.json().forEach(function (obj) {
            expenseTypes[obj.id] = obj.cost;
            var value = {id: obj.id, value: obj.cost};
            array.push(value);
        });
        dependencyMap["cost"] = expenseTypes;
        dependency["cost"] = array;
    }));
    promises.push(webix.ajax().get("api/mail-option").then(function (data) {
        var notificationTypes = [];
        var array = [];
        data.json().forEach(function (obj) {
            notificationTypes[obj.id] = obj.option;
            var value = {id: obj.id, value: obj.option};
            array.push(value);
        });
        dependencyMap["mail-option"] = notificationTypes;
        dependency["mail-option"] = array;

    }));
    promises.push(webix.ajax().get("api/fuel").then(function (data) {
        var fuel = [];
        var array = [];
        data.json().forEach(function (obj) {
            fuel[obj.id] = obj.fuel;
            var value = {id: obj.id, value: obj.fuel};
            array.push(value);
        });
        dependencyMap['fuel'] = fuel;
        dependency['fuel'] = array;

    }));
    promises.push(webix.ajax().get("api/state").then(function (data) {
            var state = [];
            var array = [];
            data.json().forEach(function (obj) {
                state[obj.id] = obj.state;
                var value = {id: obj.id, value: obj.state};
                array.push(value);
            });
            dependencyMap['state'] = state;
            dependency['state'] = array;
    }));
    return webix.promise.all(promises);

};


//main call
window.onload = function () {
    init();
};

