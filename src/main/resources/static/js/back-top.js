window.onload = function () {
    var top = document.getElementById('back-top');
    var isTop = true;
    var timer = null;
    window.onscroll = function () {
        // 获取滚动条滚动高度
        var osTop = document.documentElement.scrollTop || document.body.scrollTop;
        if (osTop > 15) {
            top.style.display = 'block'
        } else {
            top.style.display = 'none'
        }
        if (!isTop) {
            clearInterval(timer);
        }
        isTop = false;
    };

    top.onclick = function () {
        //设置一个定时器
        timer = setInterval(function () {
            //获取滚动条的滚动高度
            var osTop = document.documentElement.scrollTop || document.body.scrollTop;
            //用于设置速度差，产生缓动的效果
            var speed = Math.floor(-osTop / 3);
            document.documentElement.scrollTop = document.body.scrollTop = osTop + speed;
            //阻止滚动事件清除定时器
            isTop = true;
            if (osTop === 0) {
                clearInterval(timer);
            }
        }, 30);
    }
    /*top.onclick = function () {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }*/
};