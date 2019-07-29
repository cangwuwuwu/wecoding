var vmform = new Vue({
    el: '#signupForm',
    data: {
        stuId: '',
        stuName: '',
        stuUsername: '',
        stuPhone: '',
        stuGender: '秘密',
        stuEmail: '',
        stuBirthday: '',
        stuArea: [],
        stuInfo:  '',
        stuPassword:  '',
        stuRePassword:  '',
        stuCode:  '',
        disabled:  false,
        btntext: '获取验证码',
        btnstyle: '',
        dataValue: null,
        data2: [],
        // 省市
        provs: [
            {
                label: '北京市',
                value: '北京市'
            },
            {
                label: '天津市',
                value: '天津市'
            },
            {
                label: '河北省',
                value: '河北省',
                children: [
                    {
                        prov: '河北省',
                        value: '石家庄市',
                        label: '石家庄市'
                    }, {
                        prov: '河北省',
                        value: '唐山市',
                        label: '唐山市'
                    }, {
                        prov: '河北省',
                        value: '秦皇岛市',
                        label: '秦皇岛市'
                    }, {
                        prov: '河北省',
                        value: '邯郸市',
                        label: '邯郸市'
                    }, {
                        prov: '河北省',
                        value: '邢台市',
                        label: '邢台市'
                    }, {
                        prov: '河北省',
                        value: '保定市',
                        label: '保定市'
                    }, {
                        prov: '河北省',
                        value: '张家口市',
                        label: '张家口市'
                    }, {
                        prov: '河北省',
                        value: '承德市',
                        label: '承德市'
                    }, {
                        prov: '河北省',
                        value: '沧州市',
                        label: '沧州市'
                    }, {
                        prov: '河北省',
                        value: '廊坊市',
                        label: '廊坊市'
                    }, {
                        prov: '河北省',
                        value: '衡水市',
                        label: '衡水市'
                    }
                ]
            },
            {
                label: '山西省',
                value: '山西省',
                children: [
                    {
                        prov: '山西省',
                        value: '太原市',
                        label: '太原市'
                    }, {
                        prov: '山西省',
                        value: '大同市',
                        label: '大同市'
                    }, {
                        prov: '山西省',
                        value: '阳泉市',
                        label: '阳泉市'
                    }, {
                        prov: '山西省',
                        value: '长治市',
                        label: '长治市'
                    }, {
                        prov: '山西省',
                        value: '晋城市',
                        label: '晋城市'
                    }, {
                        prov: '山西省',
                        value: '朔州市',
                        label: '朔州市'
                    }, {
                        prov: '山西省',
                        value: '晋中市',
                        label: '晋中市'
                    }, {
                        prov: '山西省',
                        value: '运城市',
                        label: '运城市'
                    }, {
                        prov: '山西省',
                        value: '忻州市',
                        label: '忻州市'
                    }, {
                        prov: '山西省',
                        value: '临汾市',
                        label: '临汾市'
                    }, {
                        prov: '山西省',
                        value: '吕梁市',
                        label: '吕梁市'
                    }
                ]
            },
            {
                label: '内蒙古自治区',
                value: '内蒙古自治区',
                children: [
                    {
                        prov: '内蒙古自治区',
                        value: '呼和浩特市',
                        label: '呼和浩特市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '包头市',
                        label: '包头市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '乌海市',
                        label: '乌海市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '赤峰市',
                        label: '赤峰市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '通辽市',
                        label: '通辽市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '鄂尔多斯市',
                        label: '鄂尔多斯市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '呼伦贝尔市',
                        label: '呼伦贝尔市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '巴彦淖尔市',
                        label: '巴彦淖尔市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '乌兰察布市',
                        label: '乌兰察布市'
                    }, {
                        prov: '内蒙古自治区',
                        value: '兴安盟',
                        label: '兴安盟'
                    }, {
                        prov: '内蒙古自治区',
                        value: '锡林郭勒盟',
                        label: '锡林郭勒盟'
                    }, {
                        prov: '内蒙古自治区',
                        value: '阿拉善盟',
                        label: '阿拉善盟'
                    }
                ]
            },
            {
                label: '辽宁省',
                value: '辽宁省',
                children: [
                    {
                        prov: '辽宁省',
                        value: '沈阳市',
                        label: '沈阳市'
                    }, {
                        prov: '辽宁省',
                        value: '大连市',
                        label: '大连市'
                    }, {
                        prov: '辽宁省',
                        value: '鞍山市',
                        label: '鞍山市'
                    }, {
                        prov: '辽宁省',
                        value: '抚顺市',
                        label: '抚顺市'
                    }, {
                        prov: '辽宁省',
                        value: '本溪市',
                        label: '本溪市'
                    }, {
                        prov: '辽宁省',
                        value: '丹东市',
                        label: '丹东市'
                    }, {
                        prov: '辽宁省',
                        value: '锦州市',
                        label: '锦州市'
                    }, {
                        prov: '辽宁省',
                        value: '营口市',
                        label: '营口市'
                    }, {
                        prov: '辽宁省',
                        value: '阜新市',
                        label: '阜新市'
                    }, {
                        prov: '辽宁省',
                        value: '辽阳市',
                        label: '辽阳市'
                    }, {
                        prov: '辽宁省',
                        value: '盘锦市',
                        label: '盘锦市'
                    }, {
                        prov: '辽宁省',
                        value: '铁岭市',
                        label: '铁岭市'
                    }, {
                        prov: '辽宁省',
                        value: '朝阳市',
                        label: '朝阳市'
                    }, {
                        prov: '辽宁省',
                        value: '葫芦岛市',
                        label: '葫芦岛市'
                    }
                ]
            },
            {
                label: '吉林省',
                value: '吉林省',
                children: [
                    {
                        prov: '吉林省',
                        value: '长春市',
                        label: '长春市'
                    }, {
                        prov: '吉林省',
                        value: '吉林市',
                        label: '吉林市'
                    }, {
                        prov: '吉林省',
                        value: '四平市',
                        label: '四平市'
                    }, {
                        prov: '吉林省',
                        value: '辽源市',
                        label: '辽源市'
                    }, {
                        prov: '吉林省',
                        value: '通化市',
                        label: '通化市'
                    }, {
                        prov: '吉林省',
                        value: '白山市',
                        label: '白山市'
                    }, {
                        prov: '吉林省',
                        value: '松原市',
                        label: '松原市'
                    }, {
                        prov: '吉林省',
                        value: '白城市',
                        label: '白城市'
                    }, {
                        prov: '吉林省',
                        value: '延边朝鲜族自治州',
                        label: '延边朝鲜族自治州'
                    }
                ]
            },
            {
                label: '黑龙江省',
                value: '黑龙江省',
                children: [
                    {
                        prov: '黑龙江省',
                        value: '哈尔滨市',
                        label: '哈尔滨市'
                    }, {
                        prov: '黑龙江省',
                        value: '齐齐哈尔市',
                        label: '齐齐哈尔市'
                    }, {
                        prov: '黑龙江省',
                        value: '鸡西市',
                        label: '鸡西市'
                    }, {
                        prov: '黑龙江省',
                        value: '鹤岗市',
                        label: '鹤岗市'
                    }, {
                        prov: '黑龙江省',
                        value: '双鸭山市',
                        label: '双鸭山市'
                    }, {
                        prov: '黑龙江省',
                        value: '大庆市',
                        label: '大庆市'
                    }, {
                        prov: '黑龙江省',
                        value: '伊春市',
                        label: '伊春市'
                    }, {
                        prov: '黑龙江省',
                        value: '佳木斯市',
                        label: '佳木斯市'
                    }, {
                        prov: '黑龙江省',
                        value: '七台河市',
                        label: '七台河市'
                    }, {
                        prov: '黑龙江省',
                        value: '牡丹江市',
                        label: '牡丹江市'
                    }, {
                        prov: '黑龙江省',
                        value: '黑河市',
                        label: '黑河市'
                    }, {
                        prov: '黑龙江省',
                        value: '绥化市',
                        label: '绥化市'
                    }, {
                        prov: '黑龙江省',
                        value: '大兴安岭地区',
                        label: '大兴安岭地区'
                    }
                ]
            },
            {
                label: '上海市',
                value: '上海市',
                children: [
                    {
                        prov: '上海市',
                        value: '上海市',
                        label: '上海市'
                    }
                ]
            },
            {
                label: '江苏省',
                value: '江苏省',
                children: [
                    {
                        prov: '江苏省',
                        value: '南京市',
                        label: '南京市'
                    }, {
                        prov: '江苏省',
                        value: '无锡市',
                        label: '无锡市'
                    }, {
                        prov: '江苏省',
                        value: '徐州市',
                        label: '徐州市'
                    }, {
                        prov: '江苏省',
                        value: '常州市',
                        label: '常州市'
                    }, {
                        prov: '江苏省',
                        value: '苏州市',
                        label: '苏州市'
                    }, {
                        prov: '江苏省',
                        value: '南通市',
                        label: '南通市'
                    }, {
                        prov: '江苏省',
                        value: '连云港市',
                        label: '连云港市'
                    }, {
                        prov: '江苏省',
                        value: '淮安市',
                        label: '淮安市'
                    }, {
                        prov: '江苏省',
                        value: '盐城市',
                        label: '盐城市'
                    }, {
                        prov: '江苏省',
                        value: '扬州市',
                        label: '扬州市'
                    }, {
                        prov: '江苏省',
                        value: '镇江市',
                        label: '镇江市'
                    }, {
                        prov: '江苏省',
                        value: '泰州市',
                        label: '泰州市'
                    }, {
                        prov: '江苏省',
                        value: '宿迁市',
                        label: '宿迁市'
                    }
                ]
            },
            {
                label: '浙江省',
                value: '浙江省',
                children: [
                    {
                        prov: '浙江省',
                        value: '杭州市',
                        label: '杭州市'
                    }, {
                        prov: '浙江省',
                        value: '宁波市',
                        label: '宁波市'
                    }, {
                        prov: '浙江省',
                        value: '温州市',
                        label: '温州市'
                    }, {
                        prov: '浙江省',
                        value: '嘉兴市',
                        label: '嘉兴市'
                    }, {
                        prov: '浙江省',
                        value: '湖州市',
                        label: '湖州市'
                    }, {
                        prov: '浙江省',
                        value: '绍兴市',
                        label: '绍兴市'
                    }, {
                        prov: '浙江省',
                        value: '金华市',
                        label: '金华市'
                    }, {
                        prov: '浙江省',
                        value: '衢州市',
                        label: '衢州市'
                    }, {
                        prov: '浙江省',
                        value: '舟山市',
                        label: '舟山市'
                    }, {
                        prov: '浙江省',
                        value: '台州市',
                        label: '台州市'
                    }, {
                        prov: '浙江省',
                        value: '丽水市',
                        label: '丽水市'
                    }
                ]
            },
            {
                label: '安徽省',
                value: '安徽省',
                children: [
                    {
                        prov: '安徽省',
                        value: '合肥市',
                        label: '合肥市'
                    }, {
                        prov: '安徽省',
                        value: '芜湖市',
                        label: '芜湖市'
                    }, {
                        prov: '安徽省',
                        value: '蚌埠市',
                        label: '蚌埠市'
                    }, {
                        prov: '安徽省',
                        value: '淮南市',
                        label: '淮南市'
                    }, {
                        prov: '安徽省',
                        value: '马鞍山市',
                        label: '马鞍山市'
                    }, {
                        prov: '安徽省',
                        value: '淮北市',
                        label: '淮北市'
                    }, {
                        prov: '安徽省',
                        value: '铜陵市',
                        label: '铜陵市'
                    }, {
                        prov: '安徽省',
                        value: '安庆市',
                        label: '安庆市'
                    }, {
                        prov: '安徽省',
                        value: '黄山市',
                        label: '黄山市'
                    }, {
                        prov: '安徽省',
                        value: '滁州市',
                        label: '滁州市'
                    }, {
                        prov: '安徽省',
                        value: '阜阳市',
                        label: '阜阳市'
                    }, {
                        prov: '安徽省',
                        value: '宿州市',
                        label: '宿州市'
                    }, {
                        prov: '安徽省',
                        value: '六安市',
                        label: '六安市'
                    }, {
                        prov: '安徽省',
                        value: '亳州市',
                        label: '亳州市'
                    }, {
                        prov: '安徽省',
                        value: '池州市',
                        label: '池州市'
                    }, {
                        prov: '安徽省',
                        value: '宣城市',
                        label: '宣城市'
                    }
                ]
            },
            {
                label: '福建省',
                value: '福建省',
                children: [
                    {
                        prov: '福建省',
                        value: '福州市',
                        label: '福州市'
                    }, {
                        prov: '福建省',
                        value: '厦门市',
                        label: '厦门市'
                    }, {
                        prov: '福建省',
                        value: '衡水市',
                        label: '莆田市'
                    }, {
                        prov: '福建省',
                        value: '三明市',
                        label: '三明市'
                    }, {
                        prov: '福建省',
                        value: '泉州市',
                        label: '泉州市'
                    }, {
                        prov: '福建省',
                        value: '漳州市',
                        label: '漳州市'
                    }, {
                        prov: '福建省',
                        value: '南平市',
                        label: '南平市'
                    }, {
                        prov: '福建省',
                        value: '龙岩市',
                        label: '龙岩市'
                    }, {
                        prov: '福建省',
                        value: '宁德市',
                        label: '宁德市'
                    }
                ]
            },
            {
                label: '江西省',
                value: '江西省',
                children: [
                    {
                        prov: '江西省',
                        value: '南昌市',
                        label: '南昌市'
                    }, {
                        prov: '江西省',
                        value: '景德镇市',
                        label: '景德镇市'
                    }, {
                        prov: '江西省',
                        value: '萍乡市',
                        label: '萍乡市'
                    }, {
                        prov: '江西省',
                        value: '九江市',
                        label: '九江市'
                    }, {
                        prov: '江西省',
                        value: '新余市',
                        label: '新余市'
                    }, {
                        prov: '江西省',
                        value: '鹰潭市',
                        label: '鹰潭市'
                    }, {
                        prov: '江西省',
                        value: '赣州市',
                        label: '赣州市'
                    }, {
                        prov: '江西省',
                        value: '吉安市',
                        label: '吉安市'
                    }, {
                        prov: '江西省',
                        value: '宜春市',
                        label: '宜春市'
                    }, {
                        prov: '江西省',
                        value: '抚州市',
                        label: '抚州市'
                    }, {
                        prov: '江西省',
                        value: '上饶市',
                        label: '上饶市'
                    }
                ]
            },
            {
                label: '山东省',
                value: '山东省',
                children: [
                    {
                        prov: '山东省',
                        value: '济南市',
                        label: '济南市'
                    }, {
                        prov: '山东省',
                        value: '青岛市',
                        label: '青岛市'
                    }, {
                        prov: '山东省',
                        value: '淄博市',
                        label: '淄博市'
                    }, {
                        prov: '山东省',
                        value: '枣庄市',
                        label: '枣庄市'
                    }, {
                        prov: '山东省',
                        value: '东营市',
                        label: '东营市'
                    }, {
                        prov: '山东省',
                        value: '烟台市',
                        label: '烟台市'
                    }, {
                        prov: '山东省',
                        value: '潍坊市',
                        label: '潍坊市'
                    }, {
                        prov: '山东省',
                        value: '济宁市',
                        label: '济宁市'
                    }, {
                        prov: '山东省',
                        value: '泰安市',
                        label: '泰安市'
                    }, {
                        prov: '山东省',
                        value: '威海市',
                        label: '威海市'
                    }, {
                        prov: '山东省',
                        value: '日照市',
                        label: '日照市'
                    }, {
                        prov: '山东省',
                        value: '莱芜市',
                        label: '莱芜市'
                    }, {
                        prov: '山东省',
                        value: '临沂市',
                        label: '临沂市'
                    }, {
                        prov: '山东省',
                        value: '德州市',
                        label: '德州市'
                    }, {
                        prov: '山东省',
                        value: '聊城市',
                        label: '聊城市'
                    }, {
                        prov: '山东省',
                        value: '滨州市',
                        label: '滨州市'
                    }, {
                        prov: '山东省',
                        value: '菏泽市',
                        label: '菏泽市'
                    }
                ]
            },
            {
                label: '河南省',
                value: '河南省',
                children: [
                    {
                        prov: '河南省',
                        value: '郑州市',
                        label: '郑州市'
                    }, {
                        prov: '河南省',
                        value: '开封市',
                        label: '开封市'
                    }, {
                        prov: '河南省',
                        value: '洛阳市',
                        label: '洛阳市'
                    }, {
                        prov: '河南省',
                        value: '平顶山市',
                        label: '平顶山市'
                    }, {
                        prov: '河南省',
                        value: '安阳市',
                        label: '安阳市'
                    }, {
                        prov: '河南省',
                        value: '鹤壁市',
                        label: '鹤壁市'
                    }, {
                        prov: '河南省',
                        value: '新乡市',
                        label: '新乡市'
                    }, {
                        prov: '河南省',
                        value: '焦作市',
                        label: '焦作市'
                    }, {
                        prov: '河南省',
                        value: '濮阳市',
                        label: '濮阳市'
                    }, {
                        prov: '河南省',
                        value: '许昌市',
                        label: '许昌市'
                    }, {
                        prov: '河南省',
                        value: '漯河市',
                        label: '漯河市'
                    }, {
                        prov: '河南省',
                        value: '三门峡市',
                        label: '三门峡市'
                    }, {
                        prov: '河南省',
                        value: '南阳市',
                        label: '南阳市'
                    }, {
                        prov: '河南省',
                        value: '商丘市',
                        label: '商丘市'
                    }, {
                        prov: '河南省',
                        value: '信阳市',
                        label: '信阳市'
                    }, {
                        prov: '河南省',
                        value: '周口市',
                        label: '周口市'
                    }, {
                        prov: '河南省',
                        value: '驻马店市',
                        label: '驻马店市'
                    }, {
                        prov: '河南省',
                        value: '省直辖县级行政区划',
                        label: '省直辖县级行政区划'
                    }
                ]
            },
            {
                label: '湖北省',
                value: '湖北省',
                children: [
                    {
                        prov: '湖北省',
                        value: '武汉市',
                        label: '武汉市'
                    }, {
                        prov: '湖北省',
                        value: '黄石市',
                        label: '黄石市'
                    }, {
                        prov: '湖北省',
                        value: '十堰市',
                        label: '十堰市'
                    }, {
                        prov: '湖北省',
                        value: '宜昌市',
                        label: '宜昌市'
                    }, {
                        prov: '湖北省',
                        value: '襄阳市',
                        label: '襄阳市'
                    }, {
                        prov: '湖北省',
                        value: '鄂州市',
                        label: '鄂州市'
                    }, {
                        prov: '湖北省',
                        value: '荆门市',
                        label: '荆门市'
                    }, {
                        prov: '湖北省',
                        value: '孝感市',
                        label: '孝感市'
                    }, {
                        prov: '湖北省',
                        value: '荆州市',
                        label: '荆州市'
                    }, {
                        prov: '湖北省',
                        value: '黄冈市',
                        label: '黄冈市'
                    }, {
                        prov: '湖北省',
                        value: '咸宁市',
                        label: '咸宁市'
                    }, {
                        prov: '湖北省',
                        value: '随州市',
                        label: '随州市'
                    }, {
                        prov: '湖北省',
                        value: '恩施土家族苗族自治州',
                        label: '恩施土家族苗族自治州'
                    }, {
                        prov: '湖北省',
                        value: '省直辖县级行政区划',
                        label: '省直辖县级行政区划'
                    }, {
                        prov: '湖北省',
                        value: '仙桃市',
                        label: '仙桃市'
                    }, {
                        prov: '湖北省',
                        value: '潜江市',
                        label: '潜江市'
                    }, {
                        prov: '湖北省',
                        value: '天门市',
                        label: '天门市'
                    }, {
                        prov: '湖北省',
                        value: '神农架林区',
                        label: '神农架林区'
                    }
                ]
            },
            {
                label: '湖南省',
                value: '湖南省',
                children: [
                    {
                        prov: '湖南省',
                        value: '长沙市',
                        label: '长沙市'
                    }, {
                        prov: '湖南省',
                        value: '株洲市',
                        label: '株洲市'
                    }, {
                        prov: '湖南省',
                        value: '湘潭市',
                        label: '湘潭市'
                    }, {
                        prov: '湖南省',
                        value: '衡阳市',
                        label: '衡阳市'
                    }, {
                        prov: '湖南省',
                        value: '邵阳市',
                        label: '邵阳市'
                    }, {
                        prov: '湖南省',
                        value: '岳阳市',
                        label: '岳阳市'
                    }, {
                        prov: '湖南省',
                        value: '常德市',
                        label: '常德市'
                    }, {
                        prov: '湖南省',
                        value: '张家界市',
                        label: '张家界市'
                    }, {
                        prov: '湖南省',
                        value: '益阳市',
                        label: '益阳市'
                    }, {
                        prov: '湖南省',
                        value: '郴州市',
                        label: '郴州市'
                    }, {
                        prov: '湖南省',
                        value: '永州市',
                        label: '永州市'
                    }, {
                        prov: '湖南省',
                        value: '怀化市',
                        label: '怀化市'
                    }, {
                        prov: '湖南省',
                        value: '娄底市',
                        label: '娄底市'
                    }, {
                        prov: '湖南省',
                        value: '湘西土家族苗族自治州',
                        label: '湘西土家族苗族自治州'
                    }
                ]
            },
            {
                label: '广东省',
                value: '广东省',
                children: [
                    {
                        prov: '广东省',
                        value: '广州市',
                        label: '广州市'
                    }, {
                        prov: '广东省',
                        value: '韶关市',
                        label: '韶关市'
                    }, {
                        prov: '广东省',
                        value: '深圳市',
                        label: '深圳市'
                    }, {
                        prov: '广东省',
                        value: '珠海市',
                        label: '珠海市'
                    }, {
                        prov: '广东省',
                        value: '汕头市',
                        label: '汕头市'
                    }, {
                        prov: '广东省',
                        value: '佛山市',
                        label: '佛山市'
                    }, {
                        prov: '广东省',
                        value: '江门市',
                        label: '江门市'
                    }, {
                        prov: '广东省',
                        value: '湛江市',
                        label: '湛江市'
                    }, {
                        prov: '广东省',
                        value: '茂名市',
                        label: '茂名市'
                    }, {
                        prov: '广东省',
                        value: '肇庆市',
                        label: '肇庆市'
                    }, {
                        prov: '广东省',
                        value: '惠州市',
                        label: '惠州市'
                    }, {
                        prov: '广东省',
                        value: '梅州市',
                        label: '梅州市'
                    }, {
                        prov: '广东省',
                        value: '汕尾市',
                        label: '汕尾市'
                    }, {
                        prov: '广东省',
                        value: '河源市',
                        label: '河源市'
                    }, {
                        prov: '广东省',
                        value: '阳江市',
                        label: '阳江市'
                    }, {
                        prov: '广东省',
                        value: '清远市',
                        label: '清远市'
                    }, {
                        prov: '广东省',
                        value: '东莞市',
                        label: '东莞市'
                    }, {
                        prov: '广东省',
                        value: '中山市',
                        label: '中山市'
                    }, {
                        prov: '广东省',
                        value: '潮州市',
                        label: '潮州市'
                    }, {
                        prov: '广东省',
                        value: '揭阳市',
                        label: '揭阳市'
                    }, {
                        prov: '广东省',
                        value: '云浮市',
                        label: '云浮市'
                    }
                ]
            },
            {
                label: '广西壮族自治区',
                value: '广西壮族自治区',
                children: [
                    {
                        prov: '广西壮族自治区',
                        value: '南宁市',
                        label: '南宁市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '柳州市',
                        label: '柳州市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '桂林市',
                        label: '桂林市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '梧州市',
                        label: '梧州市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '北海市',
                        label: '北海市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '防城港市',
                        label: '防城港市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '钦州市',
                        label: '钦州市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '贵港市',
                        label: '贵港市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '玉林市',
                        label: '玉林市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '百色市',
                        label: '百色市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '贺州市',
                        label: '贺州市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '河池市',
                        label: '河池市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '来宾市',
                        label: '来宾市'
                    }, {
                        prov: '广西壮族自治区',
                        value: '崇左市',
                        label: '崇左市'
                    }
                ]},
            {
                label: '海南省',
                value: '海南省',
                children: [
                    {
                        prov: '海南省',
                        value: '海口市',
                        label: '海口市'
                    }, {
                        prov: '海南省',
                        value: '三亚市',
                        label: '三亚市'
                    }, {
                        prov: '海南省',
                        value: '三沙市',
                        label: '三沙市'
                    }, {
                        prov: '海南省',
                        value: '省直辖县级行政区划',
                        label: '省直辖县级行政区划'
                    }, {
                        prov: '海南省',
                        value: '五指山市',
                        label: '五指山市'
                    }, {
                        prov: '海南省',
                        value: '琼海市',
                        label: '琼海市'
                    }, {
                        prov: '海南省',
                        value: '儋州市',
                        label: '儋州市'
                    }, {
                        prov: '海南省',
                        value: '文昌市',
                        label: '文昌市'
                    }, {
                        prov: '海南省',
                        value: '万宁市',
                        label: '万宁市'
                    }, {
                        prov: '海南省',
                        value: '东方市',
                        label: '东方市'
                    }, {
                        prov: '海南省',
                        value: '定安县',
                        label: '定安县'
                    }, {
                        prov: '海南省',
                        value: '屯昌县',
                        label: '屯昌县'
                    }, {
                        prov: '海南省',
                        value: '澄迈县',
                        label: '澄迈县'
                    }, {
                        prov: '海南省',
                        value: '临高县',
                        label: '临高县'
                    }, {
                        prov: '海南省',
                        value: '白沙黎族自治县',
                        label: '白沙黎族自治县'
                    }, {
                        prov: '海南省',
                        value: '昌江黎族自治县',
                        label: '昌江黎族自治县'
                    }, {
                        prov: '海南省',
                        value: '乐东黎族自治县',
                        label: '乐东黎族自治县'
                    }, {
                        prov: '海南省',
                        value: '陵水黎族自治县',
                        label: '陵水黎族自治县'
                    }, {
                        prov: '海南省',
                        value: '保亭黎族苗族自治县',
                        label: '保亭黎族苗族自治县'
                    }, {
                        prov: '海南省',
                        value: '琼中黎族苗族自治县',
                        label: '琼中黎族苗族自治县'
                    }
                ]
            },
            {
                label: '重庆市',
                value: '重庆市'
            },
            {
                label: '四川省',
                value: '四川省',
                children: [
                    {
                        prov: '四川省',
                        value: '成都市',
                        label: '成都市'
                    }, {
                        prov: '四川省',
                        value: '自贡市',
                        label: '自贡市'
                    }, {
                        prov: '四川省',
                        value: '攀枝花市',
                        label: '攀枝花市'
                    }, {
                        prov: '四川省',
                        value: '泸州市',
                        label: '泸州市'
                    }, {
                        prov: '四川省',
                        value: '德阳市',
                        label: '德阳市'
                    }, {
                        prov: '四川省',
                        value: '绵阳市',
                        label: '绵阳市'
                    }, {
                        prov: '四川省',
                        value: '广元市',
                        label: '广元市'
                    }, {
                        prov: '四川省',
                        value: '遂宁市',
                        label: '遂宁市'
                    }, {
                        prov: '四川省',
                        value: '内江市',
                        label: '内江市'
                    }, {
                        prov: '四川省',
                        value: '乐山市',
                        label: '乐山市'
                    }, {
                        prov: '四川省',
                        value: '南充市',
                        label: '南充市'
                    }, {
                        prov: '四川省',
                        value: '眉山市',
                        label: '眉山市'
                    }, {
                        prov: '四川省',
                        value: '宜宾市',
                        label: '宜宾市'
                    }, {
                        prov: '四川省',
                        value: '广安市',
                        label: '广安市'
                    }, {
                        prov: '四川省',
                        value: '达州市',
                        label: '达州市'
                    }, {
                        prov: '四川省',
                        value: '雅安市',
                        label: '雅安市'
                    }, {
                        prov: '四川省',
                        value: '巴中市',
                        label: '巴中市'
                    }, {
                        prov: '四川省',
                        value: '资阳市',
                        label: '资阳市'
                    }, {
                        prov: '四川省',
                        value: '阿坝藏族羌族自治州',
                        label: '阿坝藏族羌族自治州'
                    }, {
                        prov: '四川省',
                        value: '甘孜藏族自治州',
                        label: '甘孜藏族自治州'
                    }, {
                        prov: '四川省',
                        value: '凉山彝族自治州',
                        label: '凉山彝族自治州'
                    }
                ]},
            {
                label: '贵州省',
                value: '贵州省',
                children: [
                    {
                        prov: '贵州省',
                        value: '贵阳市',
                        label: '贵阳市'
                    }, {
                        prov: '贵州省',
                        value: '六盘水市',
                        label: '六盘水市'
                    }, {
                        prov: '贵州省',
                        value: '遵义市',
                        label: '遵义市'
                    }, {
                        prov: '贵州省',
                        value: '安顺市',
                        label: '安顺市'
                    }, {
                        prov: '贵州省',
                        value: '毕节市',
                        label: '毕节市'
                    }, {
                        prov: '贵州省',
                        value: '铜仁市',
                        label: '铜仁市'
                    }, {
                        prov: '贵州省',
                        value: '黔西南布依族苗族自治州',
                        label: '黔西南布依族苗族自治州'
                    }, {
                        prov: '贵州省',
                        value: '黔东南苗族侗族自治州',
                        label: '黔东南苗族侗族自治州'
                    }, {
                        prov: '贵州省',
                        value: '黔南布依族苗族自治州',
                        label: '黔南布依族苗族自治州'
                    }
                ]
            },
            {
                label: '云南省',
                value: '云南省',
                children: [
                    {
                        prov: '云南省',
                        value: '昆明市',
                        label: '昆明市'
                    }, {
                        prov: '云南省',
                        value: '曲靖市',
                        label: '曲靖市'
                    }, {
                        prov: '云南省',
                        value: '玉溪市',
                        label: '玉溪市'
                    }, {
                        prov: '云南省',
                        value: '保山市',
                        label: '保山市'
                    }, {
                        prov: '云南省',
                        value: '昭通市',
                        label: '昭通市'
                    }, {
                        prov: '云南省',
                        value: '丽江市',
                        label: '丽江市'
                    }, {
                        prov: '云南省',
                        value: '普洱市',
                        label: '普洱市'
                    }, {
                        prov: '云南省',
                        value: '临沧市',
                        label: '临沧市'
                    }, {
                        prov: '云南省',
                        value: '楚雄彝族自治州',
                        label: '楚雄彝族自治州'
                    }, {
                        prov: '云南省',
                        value: '红河哈尼族彝族自治州',
                        label: '红河哈尼族彝族自治州'
                    }, {
                        prov: '云南省',
                        value: '文山壮族苗族自治州',
                        label: '文山壮族苗族自治州'
                    }, {
                        prov: '云南省',
                        value: '西双版纳傣族自治州',
                        label: '西双版纳傣族自治州'
                    }, {
                        prov: '云南省',
                        value: '大理白族自治州',
                        label: '大理白族自治州'
                    }, {
                        prov: '云南省',
                        value: '德宏傣族景颇族自治州',
                        label: '德宏傣族景颇族自治州'
                    }, {
                        prov: '云南省',
                        value: '怒江傈僳族自治州',
                        label: '怒江傈僳族自治州'
                    }, {
                        prov: '云南省',
                        value: '迪庆藏族自治州',
                        label: '迪庆藏族自治州'
                    }
                ]
            },
            {
                label: '西藏自治区',
                value: '西藏治区',
                children: [
                    {
                        prov: '西藏自治区',
                        value: '拉萨市',
                        label: '拉萨市'
                    }, {
                        prov: '西藏自治区',
                        value: '昌都地区',
                        label: '昌都地区'
                    }, {
                        prov: '西藏自治区',
                        value: '山南地区',
                        label: '山南地区'
                    }, {
                        prov: '西藏自治区',
                        value: '日喀则地区',
                        label: '日喀则地区'
                    }, {
                        prov: '西藏自治区',
                        value: '那曲地区',
                        label: '那曲地区'
                    }, {
                        prov: '西藏自治区',
                        value: '阿里地区',
                        label: '阿里地区'
                    }, {
                        prov: '西藏自治区',
                        value: '林芝地区',
                        label: '林芝地区'
                    }
                ]
            },
            {
                label: '陕西省',
                value: '陕西省',
                children: [
                    {
                        prov: '陕西省',
                        value: '西安市',
                        label: '西安市'
                    }, {
                        prov: '陕西省',
                        value: '铜川市',
                        label: '铜川市'
                    }, {
                        prov: '陕西省',
                        value: '宝鸡市',
                        label: '宝鸡市'
                    }, {
                        prov: '陕西省',
                        value: '咸阳市',
                        label: '咸阳市'
                    }, {
                        prov: '陕西省',
                        value: '渭南市',
                        label: '渭南市'
                    }, {
                        prov: '陕西省',
                        value: '延安市',
                        label: '延安市'
                    }, {
                        prov: '陕西省',
                        value: '汉中市',
                        label: '汉中市'
                    }, {
                        prov: '陕西省',
                        value: '榆林市',
                        label: '榆林市'
                    }, {
                        prov: '陕西省',
                        value: '安康市',
                        label: '安康市'
                    }, {
                        prov: '陕西省',
                        value: '商洛市',
                        label: '商洛市'
                    }
                ]
            },
            {
                label: '甘肃省',
                value: '甘肃省',
                children: [
                    {
                        prov: '甘肃省',
                        value: '兰州市',
                        label: '兰州市'
                    }, {
                        prov: '甘肃省',
                        value: '嘉峪关市',
                        label: '嘉峪关市'
                    }, {
                        prov: '甘肃省',
                        value: '金昌市',
                        label: '金昌市'
                    }, {
                        prov: '甘肃省',
                        value: '白银市',
                        label: '白银市'
                    }, {
                        prov: '甘肃省',
                        value: '天水市',
                        label: '天水市'
                    }, {
                        prov: '甘肃省',
                        value: '武威市',
                        label: '武威市'
                    }, {
                        prov: '甘肃省',
                        value: '张掖市',
                        label: '张掖市'
                    }, {
                        prov: '甘肃省',
                        value: '平凉市',
                        label: '平凉市'
                    }, {
                        prov: '甘肃省',
                        value: '酒泉市',
                        label: '酒泉市'
                    }, {
                        prov: '甘肃省',
                        value: '庆阳市',
                        label: '庆阳市'
                    }, {
                        prov: '甘肃省',
                        value: '定西市',
                        label: '定西市'
                    }, {
                        prov: '甘肃省',
                        value: '陇南市',
                        label: '陇南市'
                    }, {
                        prov: '甘肃省',
                        value: '临夏回族自治州',
                        label: '临夏回族自治州'
                    }, {
                        prov: '甘肃省',
                        value: '甘南藏族自治州',
                        label: '甘南藏族自治州'
                    }
                ]
            },
            {
                label: '青海省',
                value: '青海省',
                children: [
                    {
                        prov: '青海省',
                        value: '西宁市',
                        label: '西宁市'
                    }, {
                        prov: '青海省',
                        value: '海东市',
                        label: '海东市'
                    }, {
                        prov: '青海省',
                        value: '海北藏族自治州',
                        label: '海北藏族自治州'
                    }, {
                        prov: '青海省',
                        value: '黄南藏族自治州',
                        label: '黄南藏族自治州'
                    }, {
                        prov: '青海省',
                        value: '海南藏族自治州',
                        label: '海南藏族自治州'
                    }, {
                        prov: '青海省',
                        value: '果洛藏族自治州',
                        label: '果洛藏族自治州'
                    }, {
                        prov: '青海省',
                        value: '玉树藏族自治州',
                        label: '玉树藏族自治州'
                    }, {
                        prov: '青海省',
                        value: '海西蒙古族藏族自治州',
                        label: '海西蒙古族藏族自治州'
                    }
                ]
            },
            {
                label: '宁夏回族自治区',
                value: '宁夏回族自治区',
                children: [
                    {
                        prov: '宁夏回族自治区',
                        value: '银川市',
                        label: '银川市'
                    }, {
                        prov: '宁夏回族自治区',
                        value: '石嘴山市',
                        label: '石嘴山市'
                    }, {
                        prov: '宁夏回族自治区',
                        value: '吴忠市',
                        label: '吴忠市'
                    }, {
                        prov: '宁夏回族自治区',
                        value: '固原市',
                        label: '固原市'
                    }, {
                        prov: '宁夏回族自治区',
                        value: '中卫市',
                        label: '中卫市'
                    }
                ]
            },
            {
                label: '新疆维吾尔自治区',
                value: '新疆维吾尔自治区',
                children: [
                    {
                        prov: '新疆维吾尔自治区',
                        value: '乌鲁木齐市',
                        label: '乌鲁木齐市'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '克拉玛依市',
                        label: '克拉玛依市'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '吐鲁番地区',
                        label: '吐鲁番地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '哈密地区',
                        label: '哈密地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '昌吉回族自治州',
                        label: '昌吉回族自治州'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '博尔塔拉蒙古自治州',
                        label: '博尔塔拉蒙古自治州'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '巴音郭楞蒙古自治州',
                        label: '巴音郭楞蒙古自治州'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '阿克苏地区',
                        label: '阿克苏地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '克孜勒苏柯尔克孜自治州',
                        label: '克孜勒苏柯尔克孜自治州'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '喀什地区',
                        label: '喀什地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '和田地区',
                        label: '和田地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '伊犁哈萨克自治州',
                        label: '伊犁哈萨克自治州'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '塔城地区',
                        label: '塔城地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '阿勒泰地区',
                        label: '阿勒泰地区'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '自治区直辖县级行政区划',
                        label: '自治区直辖县级行政区划'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '石河子市',
                        label: '石河子市'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '阿拉尔市',
                        label: '阿拉尔市'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '图木舒克市',
                        label: '图木舒克市'
                    }, {
                        prov: '新疆维吾尔自治区',
                        value: '五家渠市',
                        label: '五家渠市'
                    }
                ]
            },
            {
                label: '台湾省',
                value: '台湾省',
                children: [
                    {
                        prov: '台湾省',
                        value: '台北市',
                        label: '台北市'
                    }, {
                        prov: '台湾省',
                        value: '高雄市',
                        label: '高雄市'
                    }, {
                        prov: '台湾省',
                        value: '基隆市',
                        label: '基隆市'
                    }, {
                        prov: '台湾省',
                        value: '台中市',
                        label: '台中市'
                    }, {
                        prov: '台湾省',
                        value: '台南市',
                        label: '台南市'
                    }, {
                        prov: '台湾省',
                        value: '新竹市',
                        label: '新竹市'
                    }, {
                        prov: '台湾省',
                        value: '嘉义市',
                        label: '嘉义市'
                    }, {
                        prov: '台湾省',
                        value: '省直辖',
                        label: '省直辖'
                    }
                ]
            },
            {
                label: '香港特别行政区',
                value: '香港特别行政区',
                children: [
                    {
                        prov: '香港特别行政区',
                        value: '香港岛',
                        label: '香港岛'
                    }, {
                        prov: '香港特别行政区',
                        value: '九龙',
                        label: '九龙'
                    }, {
                        prov: '香港特别行政区',
                        value: '新界',
                        label: '新界'
                    }
                ]
            },
            {
                label: '澳门特别行政区',
                value: '澳门特别行政区',
                children: [
                    {
                        prov: '澳门特别行政区',
                        value: '澳门半岛',
                        label: '澳门半岛'
                    }, {
                        prov: '澳门特别行政区',
                        value: '澳门离岛',
                        label: '澳门离岛'
                    }, {
                        prov: '澳门特别行政区',
                        value: '无堂区划分区域',
                        label: '无堂区划分区域'
                    }
                ]
            }
        ]
    },
    methods: {

        // 校验学号是否可用
        checkId() {
            var stuid = this.stuId;
            $.get("stu/stu-id/" + stuid, function (data_id) {
                if (data_id === '') {
                    vmform.success('该学号可以使用!')
                } else {
                    vmform.error('该学号已被注册了!')
                }
            })
        },

        // 校验用户名是否可用
        checkUsername() {
            var username = this.stuUsername;
            $.get("stu/stu-username/" + username, function (data_username) {
                if (data_username === '') {
                    vmform.success('该用户名可以使用!');
                } else {
                    vmform.error('该用户名已被注册了!');
                }
            })
        },

        // 发送邮件验证码 send a mail to get code
        sendMail() {
            var email = this.stuEmail;
            $.post("/sendmail/sign", {email: email}, function (data_mail) {
                vmform.success(data_mail);

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
                    vmform.success(msg);
                    if (msg === "注册成功!") {
                        setTimeout(function () {
                            location.href = "/login";
                        }, 2000);
                    }
                },
                error: function () {
                    vmform.error('注册失败!')
                }
            })
        },
        // 选择生日
        choosebirthday(date) {
            vmform.stuBirthday = date;
        },
        emailSearch (value) {
            this.data2 = !value || value.indexOf('@') >= 0 ? [] : [
                value + '@qq.com',
                value + '@sina.com',
                value + '@163.com'
            ];
        },
        success (info) {
            this.$Message.success({
                content: info,
                duration: 3
            });
        },
        warning (info) {
            this.$Message.warning({
                content:info,
                duration: 3
            });
        },
        error (info) {
            this.$Message.error({
                content:info,
                duration: 3
            });
        },
        info (info) {
            this.$Message.info({
                content:info,
                duration: 3
            });
        }
    }
});
