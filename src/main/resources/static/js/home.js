var vmhome = new Vue({
    el: '#all-home',
    data: {
        head: '',
        msg: '',
        sender: '',
        theme: 'light',
        theme1: '',
        fontcolor: '',
        more: '加载更多',
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
    methods: {
        handleView() {
            this.visible = true;
        },
        changecolor(type) {
            this.theme = type;
        },
        go(link) {
            if (link === 'me') {
                vmhome.personinfo = true;
                $.get("stu/stu-username/" + username, function (data_my) {
                    vmhome.myinfo = data_my;
                    vmhome.spinShow = false;
                });
                vmhome.msgcount = 0;
            } else window.location = link;
        },
        selectMenu(name) {
            if (name === 'light' || name === 'dark' || name === 'primary')
                vmhome.changecolor(name);
            if (name.substr(0, 1) === '#') {
                vmhome.theme1 = name;
                vmhome.fontcolor = 'font-light';
            }
        },
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
        showallhismsg() {
            if (this.more === '到底啦~') return;
            let last = this.hismsglist.length - 1;
            for (let i = 1; i < 4; i++) {
                vmhome.hismsglist.push(this.allhismsglist.slice(last + i)[0]);
                // last + i === vmhome.allhismsglist.length - 1
                if (vmhome.hismsglist.length === vmhome.allhismsglist.length) {
                    vmhome.more = '到底啦~';
                    break;
                }
            }
        }
    },
    computed: {
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