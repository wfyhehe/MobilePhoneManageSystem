"use strict";

var v = new Vue({
    el: "#menu",
    data: {
        menuData: null,
        menuLoad: false,
        menuItems: {}
    },
    methods: {
        show: function (id) {
            var subMenus = document.getElementById(id).getElementsByTagName("div");
            for (var i = 0; i < subMenus.length; i++) {
                subMenus[i].setAttribute("style", "display: block;");
            }
        },
        hide: function (id) {
            var subMenus = document.getElementById(id).getElementsByTagName("div");
            for (var i = 0; i < subMenus.length; i++) {
                subMenus[i].setAttribute("style", "display: none;");
            }
        },
        action: function (action) {
            window.location.href = action;
        },
        drawMenu: function (data) {
            var id;
            var menuName;
            for (id in data) {
                menuName = data[id]["name"];
            }
        },
        getJson: function (callback) {
            var url = "menu.json";
            $.getJSON(url, null, function (data) {
                this.menuData = data;
                this.menuLoad = true;
                callback(data);
            });
        }
    }
});
