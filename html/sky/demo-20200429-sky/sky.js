$(function() {

console.log("hello world");
var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");

var distanceScaleFactorX = 0.0005;
var distanceScaleFactorY = 0.0005;
var initialNumberOfPoints = 0.001;

var points;
var originX;
var originY;

var balls = [];

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

originX = canvas.width / 2;
originY = canvas.height / 2;


function clear() {
    context.fillStyle = "rgba(0, 0, 0, 0.3)";
    context.fillRect(0, 0, canvas.width, canvas.height);
}


clear();


function generateRandomPoints(numberOfPoints) {
    var generatedPoints = [];
    for (var i = 0; i < numberOfPoints; i++) {
        var point = generateRandomPoint();
        generatedPoints.push(point);
    }
    return generatedPoints;
}

function generateRandomPoint() {
    var x = Math.floor(Math.random() * canvas.width);
    var y = Math.floor(Math.random() * canvas.height);
    var point = {
        x: x - originX,
        y: y - originY,
        r: 10
    };
    return point;
}

points = generateRandomPoints(initialNumberOfPoints);

function drawBalls(pointsToDraw){
    for (var i = 0; i < pointsToDraw.length; i++) {
        var ball = {
            X: pointsToDraw[i].x + originX,
            Y: pointsToDraw[i].y + originY,
            R: pointsToDraw[i].r
        }
        pointsToDraw[i].r = pointsToDraw[i].r  * 1.00018;
        balls[i] = ball;
        context.fillStyle = "white";
        context.beginPath();
        context.arc(balls[i].X, balls[i].Y, balls[i].R, 0, Math.PI*2);
        context.fill();
   }
}


drawBalls(points);

function movePoints(pointsToMove) {
    for (var i = 0; i < pointsToMove.length; i++) {
        pointsToMove[i].x += +pointsToMove[i].x * distanceScaleFactorX;
        pointsToMove[i].y += +pointsToMove[i].y * distanceScaleFactorY;

        if (pointsToMove[i].x < -(canvas.width / 2) || pointsToMove[i].x > (canvas.width / 2) || pointsToMove[i].y < -(canvas.height / 2) || pointsToMove[i].y > (canvas.height / 2)) {
            pointsToMove.splice(i, 1);
        }
    }
}

    $("#canvas").click(function(event){
        //标准的获取鼠标点击相对于canvas画布的坐标公式
        var x = event.pageX - canvas.getBoundingClientRect().left;
        var y = event.pageY - canvas.getBoundingClientRect().top;
        for(var i = 0; i < balls.length; i++){
            context.beginPath();
            context.arc(balls[i].X, balls[i].Y, balls[i].R, 0, Math.PI*2);
            if(context.isPointInPath(x, y)){
                context.fillStyle = "red";
                context.fill();
                console.log("balls click");
            }
        }
    });

setInterval(function fly() {
    clear();
//    drawPoints(points);
    drawBalls(points);
//    points = points.concat(generateRandomPoints(initialNumberOfPoints));
    movePoints(points);
}, 10);
setInterval(function generateBall() {
    points = points.concat(generateRandomPoints(initialNumberOfPoints));
}, 1000);
});

