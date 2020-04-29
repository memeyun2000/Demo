$(function(){
function g(e) {
        return document.getElementById(e);
    }
    /* 一个插值算法 */
function Cubic(t, b, c, d) {
    return c * (t /= d) * t * t + b;
}
var ctx = g('pad').getContext('2d');
ctx.scale(100, 100);
ctx.translate(3, 3);
var theta = 4.2; //转角
var eleva = 0.6; //仰角
/* 投影算法 */
function iso(x, y, z) {
        var dist = Math.sqrt(x * x + y * y);
        var angle = (x == 0 && y == 0) ? 0 : Math.atan(y / x) + theta + ((x < 0) ? Math.PI : 0);
        x = Math.cos(angle) * dist;
        y = -Math.sin(angle) * dist;
        var fact = (y * Math.cos(eleva) + z * Math.sin(eleva) + 8) / 8;
        y = y * Math.sin(eleva) - z * Math.cos(eleva);
        x *= fact;
        y *= fact;
        return {
            x: x,
            y: y
        };
    }
    /* 球方程 x^2+y^2+z^2=r^2 */
function sphere(r) {
    var x, y, z;
    var t;
    z = -r;
    while (z < r) {
        x = -Math.sqrt(r * r - z * z);
        y = 0;
        t = 1;
        co = iso(x, y, z);
        ctx.moveTo(co.x, co.y);
        while (true) {
            y = Math.sqrt((r * r - x * x - z * z)) * t;
            if (isNaN(y)) { /* 此时，x值为极值,y的绝对值已经不能再小 */
                if (t == 1) {
                    t = -t;
                    x = Math.sqrt(r * r - z * z);
                    continue;
                } /* x值达到最大 */
                else break;
            };
            co = iso(x, y, z);
            ctx.lineTo(co.x, co.y);
            x += 0.1 * t;
        }
        ctx.closePath();
        z = Cubic(1, z, 2 * r, 4); //应用插值算法（分布均匀些）
    }
}

function preview() {
    ctx.clearRect(-3, -3, 10, 10);
    ctx.lineWidth = 0.001;
    ctx.lineJoin = "round";
    ctx.strokeStyle = 'rgba(0,0,100,0.8)';
    var co;
    ctx.beginPath();
    sphere(2);
    ctx.stroke();
}
preview();
/* 鼠标控制 */
g('pad').onmousedown = function(e) {
    var x0 = e.clientX,
        y0 = e.clientY;
    document.onmousemove = function(e) {
        theta -= (x0 - (x0 = e.clientX)) / 100;
        eleva -= (y0 - (y0 = e.clientY)) / 100;
        theta %= Math.PI * 2;
        if (theta < 0) theta += Math.PI * 2;
        eleva %= Math.PI * 2;
        if (eleva < 0) eleva += Math.PI * 2;
        preview();
    }
    document.onmouseup = function(e) {
        document.onmousemove = null;
    }
};
});