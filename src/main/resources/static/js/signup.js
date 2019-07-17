import { formatDate } from "../js/date.js";

$(function () {
    // 加载右侧五位学生列表
    $.get("/signup/getFive", function (data_five) {
        stulist.allstus = data_five.allList;
        stulist.fivestus = data_five.randomList;
        stulist.length = data_five.len;
        stulist.loading1 = false;
    });
});

// 提示信息
function tip(data) {
    stulist.tip = data;
    stulist.showtips = true;
    setTimeout(function () {
        stulist.showtips = false;
    }, 2000);
}

// 右边查看已注册用户及个人主页信息
var stulist = new Vue({
    el: '#getFiveStus',
    data: {
        fivestus: [],
        allstus: [],
        length: 0,
        loading1: true,
        keywords: '',
        showtips: false,
        tip: ''
    },
    methods: {
        displays(getName) {
            personList.frame = true;
            personList.loading2 = true;
            $.get("stu/stu-id/" + getName, function (data_person) {
                personList.person = data_person;
                personList.loading2 = false;
            });
        },
        // 查找同学
        search(keywords) {
            if (keywords === '') {
                return this.fivestus;
            }
            return this.allstus.filter(user => {
                var id = String(user.stuId);
                var name = user.stuUsername.toLowerCase();
                if (id.includes(keywords) || name.includes(keywords.toLowerCase())) {
                    return user;
                }
            });
        }
    },
    filters: {
        formatDateTime (time) {
            let date = new Date(time);
            return formatDate(date, 'MM-dd hh:mm')
        }
    }
});

// 查看一位学生的个人资料
var personList = new Vue({
    el: '#allPersonInformation',
    data: {
        frame: false,
        loading2: true,
        person: []
    },

    // 关闭个人信息的窗口 close frame
    methods: {
        close() {
            this.frame = false;
        }
    },
    filters: {
        formatDate (time) {
            let date = new Date(time);
            return formatDate(date, 'yyyy/MM/dd')
        },
        formatDateTime (time) {
            let date = new Date(time);
            return formatDate(date, 'yyyy/MM/dd hh:mm')
        }
    }
});

var vmform = new Vue({
    el: '#signupForm',
    data: {
        stuId: '',
        stuName: '',
        stuUsername: '',
        stuPhone: '',
        stuEmail: '',
        stuBirthday: '',
        stuProvince: '',
        stuCity:  '',
        stuInfo:  '',
        stuPassword:  '',
        stuRePassword:  '',
        stuCode:  '',
        disabled:  false,
        btntext: '获取验证码',
        btnstyle: ''
    },
    methods: {

        // 校验学号是否可用
        checkId() {
            var stuid = this.stuId;
            $.get("stu/stu-id/" + stuid, function (data_id) {
                if (data_id === '') {
                    tip("该学号可以使用!");
                } else {
                    tip("该学号已被注册了!");
                }
            })
        },

        // 校验用户名是否可用
        checkUsername() {
            var username = this.stuUsername;
            $.get("stu/stu-username/" + username, function (data_username) {
                if (data_username === '') {
                    tip("该用户名可以使用!");
                } else {
                    tip("该用户名已被注册了!")
                }
            })
        },

        // 发送邮件验证码 send a mail to get code
        sendMail() {
            var email = this.stuEmail;
            $.post("sendmail", {email: email}, function (data_mail) {
                tip(data_mail);

                // 发送成功禁用按钮/倒计时/解禁按钮
                if (data_mail.length > 11) {
                    var time = 30;
                    vmform.disabled = true;
                    vmform.btnstyle = {'cursor': 'not-allowed'};
                    var interval  = setInterval(function () {
                        vmform.btntext =  '已发送(' + time + 's)';
                        if (time === 0) {
                            vmform.disabled = false;
                            vmform.btntext = '获取验证码';
                            vmform.btnstyle = '';
                            clearInterval(interval);
                        }
                        time--;
                    }, 1000);
                }
            })
        },

        // 注册账号
        submit() {
            var data = {
                stuId: this.stuId,
                stuName: this.stuName,
                stuUsername: this.stuUsername,
                stuPhone: this.stuPhone,
                stuEmail: this.stuEmail,
                stuBirthday: this.stuBirthday,
                stuProvince: this.stuProvince,
                stuCity:  this.stuCity,
                stuInfo:  this.stuInfo,
                stuPassword:  this.stuPassword,
                stuRePassword:  this.stuRePassword,
                stuCode:  this.stuCode
            };
            $.ajax({
                url: "/signup/regist",
                type: "post",
                data: data,
                dataType: "text",
                success: function (msg) {
                    tip(msg);
                    if (msg === "注册成功!") {
                        setTimeout(function () {
                            location.href = "/login";
                        }, 2000);
                    }
                },
                error: function () {
                    tip("注册失败!")
                }
            })
        }
    }
});
