new Vue({
    el: "#menu",
    data () {
        return {
            menuData: null,
            menuLoad: true,
            menuItems: {},
        }
    },
    methods: {
        show: function (id) {
            var subMenus = document.getElementById(id).getElementsByTagName("div");
            console.log(id);
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
            var self = this;
            $.getJSON(url, null, function (data) {
                self.menuItems = data;
                self.menuLoad = true;
                callback(data);
            });
        }
    },
    created: function () {
        this.getJson(console.log);
    }
});
