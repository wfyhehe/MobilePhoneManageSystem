"use strict";

new Vue({
    el: "#form-validation",
    data: {
        username: "",
        usernameRegex: /^\w{6,18}$/,
        password: "",
        passwordRegex: /^.{6,18}$/,
        verificationCode: "",
        verificationCodeRegex: /^\w{4}$/,
        vCodeId: "",
        anyFocus: false
    },
    methods: {
        changeImg: function (img) {
            this.vCodeId = Math.floor(10000 * Math.random());
        },
        usernameCheck: function () {
            return this.usernameRegex.test(this.username);
        },
        passwordCheck: function () {
            return this.passwordRegex.test(this.password);
        },
        verificationCodeCheck: function () {
            return this.verificationCodeRegex.test(this.verificationCode);
        },
        valid: function () {
            return this.usernameCheck() && this.passwordCheck() && this.verificationCodeCheck();
        },
        handleFocus: function () {
            this.anyFocus = true;
        },
        submitClick: function () {

            var url = "login_process.do"
            var dataObj = {
                username: this.username,
                password: this.password,
                verificationCode: this.verificationCode
            };
            $.post(url, dataObj, function (data, status) {

            });
        }
    }
});


