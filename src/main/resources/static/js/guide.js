var mdinit = '## 起步\n' +
    '\n' +
    '---\n' +
    '\n' +
    '<br>\n' +
    '\n' +
    '点击**左侧选项卡**查看校园指南\n' +
    '\n' +
    '若有 bug 或者没有找到你想要的,  可以拉到下方点击**反馈**,  我会尽量第一时间更新。\n' +
    '\n' +
    '为了能方便同学们的校园生活，希望能将指南做的越发完善，谢谢~！\n' +
    '\n' +
    '<br>\n' +
    '\n' +
    '扫下方的**加入我们**二维码，欢迎同学加入项目开发 (因为我实在是肝不动了:)\n';

var all2home = new Vue({
    el: '#all-home',
    data: {
        theme1: 'light',
        md: marked(mdinit),
        bread1: ["<-点此开始"],
        bread2: "",
    },
    methods: {
        go(link) {
            window.location = link;
        },
        addbread(parent_name) {
            this.bread1 = parent_name;
        },
        getmdpage(name) {
            this.bread2 =  name;
            $.ajax({
                type: 'get',
                url: '../md/'+ name +'.md',
                success(data_md) {
                    all2home.md = marked(data_md);
                },
                error() {
                    all2home.md = '<p>数据访问出错!  请刷新重试,  如果还是不能访问,  下方联系反馈,  谢谢</p>';
                }
            });
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
                return ;
            vmfooter.loading = true;
            $.ajax({
                type: 'post',
                url: '/sendmail/feedback',
                data: {type:this.bugtype, content:this.bugcontent},
                success(data_feedback) {
                    vmfooter.success(data_feedback);
                    vmfooter.loading = false;
                },
                error() {
                    vmfooter.error('糟糕! 反馈好像发送失败了...:(');
                    vmfooter.loading = false;
                },
            });
        },
        success (info) {
            this.$Message.success({
                content: info,
                duration: 3
            });
        },
        error (info) {
            this.$Message.error({
                content:info,
                duration: 3
            });
        }
    }
});

var rendererMD = new marked.Renderer();
marked.setOptions({
    renderer: rendererMD,
    gfm: true,
    tables: true,
    breaks: false,
    pedantic: false,
    sanitize: false,
    smartLists: true,
    smartypants: false
});//基本设置
marked.setOptions({
    highlight: function (code) {
        return hljs.highlightAuto(code).value;
    }
});
// 定制<h#>
rendererMD.heading = function (text, level) {
    var escapedText = text.toLowerCase().replace(/\s/g, '-');
    return '<br><h' + level + '>' +
        '<a id="' + text + '" class="md-header" href="#' + escapedText +
        '"><span class="md-header-text">' +
        text + '</span></a></h' + level + '>';
};
// 定制<a>标签
rendererMD.link = function(href, title, text) {
    return '<a class="md-link" title="' + text + '" target="_blank" href="' + href +
        '">'+ text +'</a>'
};
// 定制加粗
rendererMD.strong = function (text) {
    return '<strong class="md-strong">' + text + '</strong>'
};
// 定制
rendererMD.list = function (body) {
    return '<ul class="md-list">'+ body +'</ul>'
};

$(".md-header").click(function () {
    this.scrollIntoView();
});