import { formatDate } from "../js/date.js";

$(function () {
    // 加载右侧五位学生列表
    stulist.loadstart();
    $.ajax({
        url: '/signup/getFive',
        type: 'get',
        success(data_five) {
            stulist.allstus = data_five.allList;
            stulist.fivestus = data_five.randomList;
            stulist.length = data_five.len;
            stulist.loading1 = false;
            stulist.loadfinish();
        },
        error() {
            stulist.loaderror();
        }
    })
});

// 右边查看已注册用户及个人主页信息
var stulist = new Vue({
    el: '#getFiveStus',
    data: {
        fivestus: [],
        allstus: [],
        color: ['#f56a00', '#7265e6', '#ffbf00', '#00a2ae',
            '#333', '#ccc', '#dc3545', '#007bff',
            '#28a745', '#6f42c1', '#eeaeee', '#00ffff'],
        length: 0,
        loading1: true,
        keywords: ''
    },
    methods: {
        // 获取随机颜色
        ranum() {
            return Math.floor(Math.random()*12)
        },
        displays(getName) {
            stulist.loadstart();
            personList.frame = true;
            personList.loading2 = true;
            $.ajax({
                url: 'stu/stu-id/' + getName,
                type: 'get',
                success(data_person) {
                    personList.person = data_person;
                    personList.loading2 = false;
                    stulist.loadfinish();
                },
                error() {
                    stulist.loaderror();
                }
            })
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
        },
        loadstart() {
            this.$Loading.start();
        },
        loadfinish() {
            this.$Loading.finish();
        },
        loaderror() {
            this.$Loading.error();
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

