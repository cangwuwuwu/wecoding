var res = new Vue({
    el: '#res-all',
    data: {
        table_columns: [
            {
                type: 'index',
                width: 60,
                align: 'center'
            },
            {
                title: '分类',
                key: 'resForm',
                width: 100,
                align: 'center',
                render: (h, params) => {
                    const row = params.row;
                    const text = row.resForm === 1 ? '基础学习' :
                        row.resForm === 5 ? '文档/PDF' :
                        row.resForm === 2 ? '进阶学习' :
                        row.resForm === 4 ? '项目实战' : '面试就业';

                    return h('span', {
                        props: {

                        }
                    }, text);
                },
                filters: [
                    {
                        label: '基础学习',
                        value: 1
                    },
                    {
                        label: '进阶学习',
                        value: 2
                    },
                    {
                        label: '面试就业',
                        value: 3
                    },
                    {
                        label: '项目实战',
                        value: 4
                    },
                    {
                        label: '文档/PDF',
                        value: 5
                    }
                ],
                filterMultiple: false,
                filterMethod(value, row) {
                    return row.resForm === value;
                }
            },
            {
                title: '名称',
                key: 'resName',
                align: 'center',
            },
            {
                title: '描述',
                key: 'resDescribe',
                width: 400,
                align: 'center',
                ellipsis: true
            },
            {
                title: '状态',
                key: 'resStatus',
                align: 'center',
                render: (h, params) => {
                    const row = params.row.resMore;
                    const color = row.resStatus === 0 ? 'error' : 'success';
                    const text = row.resStatus === 0 ? '失效' : '有效';

                    return h('Tag', {
                        props: {
                            type: 'dot',
                            color: color
                        }
                    }, text);
                },
                filters: [
                    {
                        label: '有效',
                        value: 1
                    },
                    {
                        label: '失效',
                        value: 0
                    }
                ],
                filterMultiple: false,
                filterMethod(value, row) {
                    if (value === 1) {
                        return row.resMore.resStatus === 1;
                    } else if (value === 0) {
                        return row.resMore.resStatus === 0;
                    }
                }
            },
            {
                title: '上传时间',
                key: 'resUpTime',
                align: 'center',
                sortable: true,
                render: (h, params) => {
                    return h('i-Time', {
                        props: {
                            time: params.row.resUpTime,
                            type: 'date'
                        }
                    })
                }
            },
            {
                title: '点击量',
                key: 'resHeat',
                align: 'center',
                sortable: true,
                render: (h, params) => {
                    return h('Tag', {
                        props: {
                            type: 'border',
                            color: 'primary'
                        }
                    }, params.row.resMore.resHeat)
                }
            },
            {
                title: '评分',
                key: 'resPoint',
                align: 'center',
                width: 180,
                sortable: true,
                render: (h, params) => {
                    return h('Rate', {
                        props: {
                            value: params.row.resMore.resPoint,
                            allowHalf: true,
                            disabled: true,
                        }
                    })
                }
            },
            {
                title: '操作',
                key: 'action',
                width: 130,
                align: 'center',
                render: (h, params) => {
                    return h('div', [
                        h('Button', {
                            props: {
                                type: 'primary',
                                size: 'small'
                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                    res.show(params)
                                }
                            }
                        }, '详情'),
                        h('Button', {
                            props: {
                                type: 'success',
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                    res.download(params)
                                }
                            }
                        }, '下载')
                    ]);
                }
            }
        ],
        total: 0,
        page: 1,
        name: '',
        data_video: [],
        loading: false
    },
    methods: {
        changePage(page) {
            this.$Loading.start();
            this.page = page;
            this.getRes(this.name);
        },
        getRes(name) {
            this.$Loading.start();
            res.loading= true;
            this.name = name;
            $.ajax({
                url: 'resources/language/' + name.toLowerCase(),
                data: {
                    page: this.page,
                    limit: 10
                },
                type: 'get',
                success(data_language) {
                    console.log(data_language);
                    res.data_video = data_language.list;
                    res.total = data_language.total;
                    res.page = data_language.pageNum;
                    res.$Loading.finish();
                    res.loading = false;
                },
                error() {
                    res.$Loading.error();
                    res.loading = false;
                }
            })
        },
        show(param) {
            let arow = param.row;
            const textForm = arow.resForm === 1 ? '基础学习':
                arow.resForm === 5 ? '文档/PDF' :
                arow.resForm === 2 ? '进阶学习' :
                arow.resForm === 4 ? '项目实战' : '面试就业';
            const id = arow.resId;
            const name = arow.resName;
            const describe = arow.resDescribe;
            const url = 'https://' + arow.resUrl;
            const password = arow.resPassword;
            const status = arow.resMore.resStatus === 1 ? '有效' : '失效';
            const colorstatus = arow.resMore.resStatus === 1 ? 'success' : 'error';
            const uptime = arow.resUpTime;
            const failtime = arow.resFailTime;
            const point = arow.resMore.resPoint;
            const count = arow.resMore.resHeat;
            const up = arow.resUploader;
            this.$Modal.info({
                title: '详细信息',
                width: 520,
                render: (h) => {
                    return [
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '资源编号 RES ID: '),
                        h('Tag', {
                            props: {
                                color: 'primary'
                            }
                        }, id),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '类型 TYPE: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, textForm),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '名称 NAME: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, name),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '描述 DESCRIBE: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, describe),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '链接 LINK: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, url),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '密码 PASSWORD: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, password),
                        h('Button', {
                            props: {
                                type: 'primary',
                                size: 'small',
                                icon: 'md-copy'
                            },
                            on: {
                                click: () => {
                                    res.download(param)
                                }
                            }
                        }, '复制'),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '状态 STATUS: '),
                        h('Tag', {
                            props: {
                                color: colorstatus
                            }
                        }, status),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '有效时间 USETIME: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, [
                            h('i-Time', {
                                props: {
                                    time: uptime,
                                    type: 'date'
                                }
                            })
                        ]),
                        h('span', '-  '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, [
                            h('i-Time', {
                                props: {
                                    time: failtime,
                                    type: 'date'
                                }
                            })
                        ]),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '评分 MARK: '),
                        h('Rate', {
                            props: {
                                value: point,
                                allowHalf: true,
                                disabled: true,
                            }
                        }, point),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '点击量 CLICKRATE: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, count),
                        h('br'),
                        h('span', {
                            style: {
                                fontWeight: 'bold',
                                fontSize: 25
                            }
                        }, '上传者 UPLOADER: '),
                        h('Tag', {
                            props: {
                                type: 'border',
                                color: 'primary'
                            }
                        }, up)
                    ]
                }
            })
        },
        // 复制密码并2秒后跳转
        download(param) {
            if (param.row.resStatus === 0) {
                this.$Modal.error({
                    title: '失败',
                    content: '该链接已经失效'
                });
                return;
            }
            this.copy(param.row.resPassword);
            this.$Modal.success({
                title: '成功',
                content: '已将云盘密码复制到粘贴板',
                loading: true,
                okText: '点此跳转',
                closable: true,
                onOk: () => {
                    setTimeout(() => {
                        window.open('https://' + param.row.resUrl);
                        this.$Modal.remove();
                    }, 2000);
                }
            })
        },
        // 复制内容到粘贴板
        copy(str) {
            let oInput = document.createElement('input');
            oInput.value = str;
            document.body.appendChild(oInput);
            oInput.select();
            document.execCommand("Copy");
            oInput.style.display = 'none';
            document.body.removeChild(oInput);
        }
    }
});