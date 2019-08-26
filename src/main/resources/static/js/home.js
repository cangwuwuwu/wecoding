import { formatDate } from "./date.js";

var vmhome = new Vue({
    el: '#all-home',
    data: {
        head: '',
        msg: '',
        sender: '',
        theme: 'light',
        theme1: '',
        fontcolor: '',
        modifyData: '',
        more: '加载更多',
        editInput: -1,
        msgcount: 0,
        value1: 0,
        value2: '0',
        myinfo: [],
        msglist: [],
        hismsglist: [],
        allhismsglist: [],
        spinShow: true,
        spinShowHis: false,
        personinfo: false,
        visible: false
    },
    mounted: function() {

    },
    methods: {
        // 显示头像大图
        handleView() {
            this.visible = true;
        },
        // 把对象改写成数组, 便于v-for遍历成一个表格
        changeinfo2list(myinfo) {
            return [
                {title: '学号', value: myinfo.stuId, class: 'not-allow'},
                {title: '用户名', value: myinfo.stuUsername, class: 'not-allow'},
                {title: '姓名', value: myinfo.stuName, class: 'tb-point'},
                {title: '性别', value: myinfo.stuGender, class: 'tb-point'},
                {title: '邮箱', value: myinfo.stuEmail, class: 'tb-point'},
                {title: '籍贯', value: myinfo.stuArea, class: 'tb-point'},
                {title: '电话', value: myinfo.stuPhone, class: 'tb-point'},
                {title: '生日', value: formatDate(new Date(myinfo.stuBirthday), 'yyyy-MM-dd'), class: 'tb-point'},
                {title: '注册日期', value: formatDate(new Date(myinfo.stuRegistTime), 'yyyy-MM-dd hh:mm'), class: 'not-allow'},
                {title: '个人简介', value: myinfo.stuInfo, class: 'tb-point'}
            ];
        },
        // 修改个人信息按钮-显示input框
        modifyInfo(index, value) {
            if (index < 2 || index === 8) return;
            this.modifyData = value;
            this.editInput = index;
        },
        // 提交已修改的个人信息
        saveInfo(index, value) {
            if (this.modifyData === value) {
                this.editInput = -1;
                this.$Message.error('个人信息未更改！');
                return;
            }
            if (this.myinfo[4].value === '') {
                this.$Message.error('邮箱不能为空！');
                return;
            }
            $.ajax({
                url: 'stu/stu-id',
                type: 'post',
                data: {
                    stuId: this.myinfo[0].value,
                    stuUsername: this.myinfo[1].value,
                    stuName: this.myinfo[2].value,
                    stuGender: this.myinfo[3].value,
                    stuEmail: this.myinfo[4].value,
                    stuArea: this.myinfo[5].value,
                    stuPhone: this.myinfo[6].value,
                    stuBirthday: this.myinfo[7].value,
                    stuRegistTime: this.myinfo[8].value,
                    stuInfo: this.myinfo[9].value,
                },
                success(update_info) {
                    vmhome.editInput = -1;
                    if (update_info === 1)
                        vmhome.$Message.success('更新成功!');
                    else
                        vmhome.$Message.error('出错了,请稍后再试');
                },
                error() {
                    vmhome.$Message.error('x_x好像哪里出错了...');
                    vmhome.cancelInfo(index)
                }
            })
        },
        // 取消修改
        cancelInfo(index) {
            this.myinfo[index].value = this.modifyData;
            this.editInput = -1;
        },
        // 页面跳转和弹出右侧抽屉
        go(link) {
            if (link === 'me') {
                vmhome.personinfo = true;
                if (vmhome.myinfo.length !== 0) {
                    return;
                }
                $.get("stu/stu-username/" + username, function (data_my) {
                    console.log(data_my);
                    vmhome.myinfo = vmhome.changeinfo2list(data_my);
                    vmhome.myinfo.stuImg = data_my.stuImg;
                    vmhome.myinfo.stuBigImg = data_my.stuBigImg;
                    vmhome.spinShow = false;
                });
                vmhome.msgcount = 0;
            } else window.location = link;
        },
        // 主题切换
        selectMenu(name) {
            if (name === 'light' || name === 'dark' || name === 'primary')
                vmhome.changecolor(name);
            if (name.substr(0, 1) === '#') {
                vmhome.theme1 = name;
                vmhome.fontcolor = 'font-light';
            }
        },
        changecolor(type) {
            this.theme = type;
        },
        // 显示最新的两条消息
        show2hismsg() {
            vmhome.spinShowHis = true;
            $.ajax({
                url: '/mymsg',
                type: 'get',
                success(data_history_msg) {
                    console.log(data_history_msg);
                    vmhome.allhismsglist = data_history_msg.reverse();
                    vmhome.hismsglist = data_history_msg.slice(0, 2);
                    if (vmhome.hismsglist.length === vmhome.allhismsglist.length) {
                        vmhome.more = '到底啦~';
                    }
                }
            })
        },
        // 每点击一次加载更多 显示三条
        showallhismsg() {
            if (this.more === '到底啦~') return;
            let last = this.hismsglist.length - 1;
            for (let i = 1; i < 4; i++) {
                vmhome.hismsglist.push(this.allhismsglist.slice(last + i)[0]);
                if (vmhome.hismsglist.length === vmhome.allhismsglist.length) {
                    vmhome.more = '到底啦~';
                    break;
                }
            }
        }
    },
    computed: {
        // 翻转数组 让消息列表倒序显示
        revmsglist() {
            return this.msglist.reverse();
        }
    }
});
var vmfooter = new Vue({
    el: '#footer',
    data: {
        bug: false,
        bugtype: '',
        bugcontent: '',
        loading: false
    },
    methods: {
        // 发送反馈信息
        sendfeedback() {
            if (this.bugtype === '' || this.bugcontent === '')
                return;
            vmfooter.loading = true;
            $.ajax({
                type: 'post',
                url: '/sendmail/feedback',
                data: {type: this.bugtype, content: this.bugcontent},
                success(data_feedback) {
                    vmfooter.success(data_feedback);
                    setTimeout(() => {
                        vmfooter.loading = false;
                        vmfooter.bug = false;
                    }, 1000);
                },
                error() {
                    vmfooter.error('糟糕! 反馈好像发送失败了...:(');
                    vmfooter.loading = false;
                },
            });
        },
        success(info) {
            this.$Message.success({
                content: info,
                duration: 3
            });
        },
        error(info) {
            this.$Message.error({
                content: info,
                duration: 3
            });
        }
    }
});
// 连接消息服务器,实时推送消息
var wsinit = function () {
    if (username !== '') {
        var ws = new WebSocket('ws://39.106.85.24:15674/ws');
        var client = Stomp.over(ws);
        var onConnect = function () {
            vmfooter.success('消息服务器连接成功!');
            client.subscribe('/exchange/wecoding.fanout/', function (msg) {
                console.log(msg);
                let jsonMsg = JSON.parse(msg.body);
                vmhome.$Notice.info({
                    title: '收到一条' + jsonMsg.msgType + '消息',
                    desc: jsonMsg.msgHead,
                });
                vmhome.msgcount += 1;
                vmhome.msglist.push(jsonMsg);
            })
        };
        var onError = function (error) {
            vmfooter.error('消息服务器连接失败!')
        };
        client.connect('guest', 'guest', onConnect, onError, '/');
    } else {
        vmfooter.error('未登录!暂时无法连接到消息服务器')
    }
};

var username = $("#username").text();
wsinit();