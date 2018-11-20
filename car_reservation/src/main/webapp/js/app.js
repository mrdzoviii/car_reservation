var MENU_STATES = {
    COLLAPSED: 0,
    EXPANDED: 1
};
var menuState = MENU_STATES.COLLAPSED;
var userData = null;
var panel = {id: "empty"};
var rightPanel = null;

var menuSystemAdmin = [
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

var menuCompanyAdmin=[
    {
        id:"dashboard",
        icon:"home",
        value:"Home"
    },
    {
        id:"vehicle",
        icon:"car",
        value:"Vehicles"
    },
    {
        id: "logger",
        icon: "history",
        value: "User logs"
    },
    {
        id:"user",
        icon:"user",
        value:"Users"
    }
];

var menuActions = function (id) {
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
        case "dashboard":
            locationView.selectPanel();
            break;
        case "user":
            userView.selectPanel();
            break;
    }
};

var init = function () {
    if (!webix.env.touch && webix.ui.scrollSize) webix.CustomScroll.init();
    webix.i18n.setLocale("sr-SP");
    webix.Date.startOnMonday = true;
    webix.ui(panel);
    panel = $$("empty");
    var urlQuery=window.location.search;
    if (urlQuery && urlQuery.startsWith('?q=reg')){
        var token=urlQuery.split('=')[2];
        webix.ajax().get("api/user/check/"+token).then(function (result) {
            var userId=result.json();
            showRegistration(userId);
        }).fail(function (err) {
            util.messages.showErrorMessage("Token je istekao ili nije validan!");
            checkState();
        })
    }else{
        checkState();
    }

};

var checkState=function(){
    webix.ajax().get("api/user/state").then(function (data) {
        userData = data.json();
        showApp();
    }).fail(function (err) {
        showLogin();
    })
};

var menuEvents = {
    onItemClick: function (item) {
        menuActions(item);
    }
};

var showLogin = function () {
    var login = webix.copy(loginLayout);
    webix.ui(login, panel);
    panel = $$("login");

};

var showRegistration = function (userId) {
    var registration=webix.copy(registrationLayout);
    webix.ui(registration,panel);
    panel=$$("registration");
    $$("registrationForm").setValues({
        id:userId
    });

};

var showApp = function () {
    var promise=preloadDependencies();
    var main = webix.copy(mainLayout);
    webix.ui(main, panel);
    panel = $$("app");
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
            localMenuData = menuSystemAdmin;
            break;
        case role.companyAdministrator:
            localMenuData=menuCompanyAdmin;
            break;
    }
    if(userData.roleId===1)
        $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> System admin</p>");
    else if(userData.roleId===2)
        $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> Company admin</p>");
    else $$("userLabel").setHTML("<p style='margin-top:2px;display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br>User</p>");
    var avatar={"avatar":userData.avatar};
    var companyLogo={"companyLogo":userData.companyLogo};
    $$("userAvatar").define("data",avatar);
    $$("mainMenu").define("data", localMenuData);
    $$("mainMenu").define("on", menuEvents);
    $$("companyLogo").define("data",companyLogo);
    rightPanel = "emptyRightPanel";
    promise.then(function (value) {
        if (userData.roleId === role.systemAdministrator) {
            companyView.selectPanel();
            $$("mainMenu").select("company");
        }else{
            locationView.selectPanel();
            $$("mainMenu").select("dashboard");
        }
    }).fail(function (err) {
     //   connection.reload();
    });

};

var preloadDependencies = function () {
    var promises=[];
    promises.push(webix.ajax().get("api/role").then(function (data) {
        var roles = [];
        var array = [];
        data.json().forEach(function (obj) {
            roles[obj.id] = obj.role;
            array.push(obj);
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
            array.push(obj);
        });
        dependencyMap["cost"] = expenseTypes;
        dependency["cost"] = array;
    }));
    promises.push(webix.ajax().get("api/mail-option").then(function (data) {
        var notificationTypes = [];
        var array = [];
        data.json().forEach(function (obj) {
            notificationTypes[obj.id] = obj.option;
            array.push(obj);
        });
        dependencyMap["mail-option"] = notificationTypes;
        dependency["mail-option"] = array;

    }));
    promises.push(webix.ajax().get("api/fuel").then(function (data) {
        var fuel = [];
        var array = [];
        data.json().forEach(function (obj) {
            fuel[obj.id] = obj.fuel;
            array.push(obj);
        });
        dependencyMap['fuel'] = fuel;
        dependency['fuel'] = array;

    }));
    return webix.promise.all(promises);

};

//main call
window.onload = function () {
    init();
};

