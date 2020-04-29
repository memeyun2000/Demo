listView.handleDragAndDrop = function (e, state) {//该函数默认为空，若该函数被重载，则pan平移组件功能将被关闭
if(state === 'prepare'){//state当前状态，先后会有prepare-begin-between-end四种过程
var data = listView.getDataAt(e);//传入逻辑坐标点或者交互event事件参数，返回当前点下的数据元素
listView.sm().ss(data);//设置选中当前事件所在的数据元素
if(dragImage && dragImage.parentNode){ document.body.removeChild(dragImage); }
dragImage = ht.Default.toCanvas('productIcon', 30, 30, 'uniform', data);
// toCanvas(image, width, height, stretch, data, view, color)将图片转换成Canvas对象
productId = data.a('ProductId'); } else if(state === 'begin'){ if(dragImage){ var pagePoint = ht.Default.getPagePoint(e);
//返回page属性坐标
dragImage.style.left = pagePoint.x - dragImage.width/2 + 'px';
//实时更新拖拽时的图标的位置
dragImage.style.top = pagePoint.y - dragImage.height/2 + 'px'; document.body.appendChild(dragImage);
//在 html body 体中添加这个拖拽的图片
} } else if(state === 'between'){ if(dragImage){ var pagePoint = ht.Default.getPagePoint(e);
//返回page属性坐标
dragImage.style.left = pagePoint.x - dragImage.width/2 + 'px'; dragImage.style.top = pagePoint.y - dragImage.height/2 + 'px'; if(ht.Default.containedInView(e, g3d)){
//判断交互事件所处位置是否在View组件之上，一般用于Drog And Drop的拖拽操作判断
//这边做了两个判断，一个是鼠标在拖拽的时候未松开，一个是鼠标拖拽的时候松开了。
if(lastFaceInfo){
//鼠标未松开的情况下，贴图显示旧值
//data.face 默认值为front，图标在3D下的朝向，可取值left|right|top|bottom|front|back|center
lastFaceInfo.data.s(lastFaceInfo.face + '.image', lastFaceInfo.oldValue); lastFaceInfo = null; }
//鼠标松开时，将新值赋给这个面
var faceInfo = g3d.getHitFaceInfo(e);
//获取鼠标所在面信息
if(faceInfo){ faceInfo.oldValue = faceInfo.data.s(faceInfo.face + '.image');
//获取面的“老值”
faceInfo.data.s(faceInfo.face + '.image', productId);
//front/back/top/bottom/left/right.image 设置这些面的贴图
lastFaceInfo = faceInfo; } } } } else{
//拖拽结束之后，所有值都回到初始值
if(dragImage){
//有从列表中拖拽图片
if(lastFaceInfo){
//有赋“图片”到 3d 中的节点上
lastFaceInfo.data.s(lastFaceInfo.face + '.image', lastFaceInfo.oldValue); lastFaceInfo = null; } if(ht.Default.containedInView(e, g3d)){ var faceInfo = g3d.getHitFaceInfo(e); if(faceInfo){ faceInfo.data.s(faceInfo.face + '.image', productId); } } if(dragImage.parentNode){ document.body.removeChild(dragImage); } dragImage = null; productId = null; } } };
}
}
}
}