THREE.TrackballControls = function ( object, domElement ) {

	var _this = this;
	var STATE = { NONE: - 1, ROTATE: 0, ZOOM: 1, PAN: 2, TOUCH_ROTATE: 3, TOUCH_ZOOM_PAN: 4 };

	this.object = object;
	this.domElement = ( domElement !== undefined ) ? domElement : document;

	// API

	this.enabled = true;

	this.screen = { left: 0, top: 0, width: 0, height: 0 };

	this.rotateSpeed = 1.0;
	this.zoomSpeed = 1.2;
	this.panSpeed = 0.3;

	this.noRotate = false;
	this.noZoom = false;
	this.noPan = false;

	this.staticMoving = false;
	this.dynamicDampingFactor = 0.2;

	this.minDistance = 0;
	this.maxDistance = Infinity;

	this.keys = [ 65 /*A*/, 83 /*S*/, 68 /*D*/ ];

	// internals

	this.target = new THREE.Vector3();

	var EPS = 0.000001;

	var lastPosition = new THREE.Vector3();

	var _state = STATE.NONE,
	_prevState = STATE.NONE,

	_eye = new THREE.Vector3(),

	_movePrev = new THREE.Vector2(),
	_moveCurr = new THREE.Vector2(),

	_lastAxis = new THREE.Vector3(),
	_lastAngle = 0,

	_zoomStart = new THREE.Vector2(),
	_zoomEnd = new THREE.Vector2(),

	_touchZoomDistanceStart = 0,
	_touchZoomDistanceEnd = 0,

	_panStart = new THREE.Vector2(),
	_panEnd = new THREE.Vector2();

	// for reset

	this.target0 = this.target.clone();
	this.position0 = this.object.position.clone();
	this.up0 = this.object.up.clone();

	// events

	var changeEvent = { type: 'change' };
	var startEvent = { type: 'start' };
	var endEvent = { type: 'end' };


	// methods

	this.handleResize = function () {

		if ( this.domElement === document ) {

			this.screen.left = 0;
			this.screen.top = 0;
			this.screen.width = window.innerWidth;
			this.screen.height = window.innerHeight;

		} else {

			var box = this.domElement.getBoundingClientRect();
			// adjustments come from similar code in the jquery offset() function
			var d = this.domElement.ownerDocument.documentElement;
			this.screen.left = box.left + window.pageXOffset - d.clientLeft;
			this.screen.top = box.top + window.pageYOffset - d.clientTop;
			this.screen.width = box.width;
			this.screen.height = box.height;

		}

	};

	this.handleEvent = function ( event ) {

		if ( typeof this[ event.type ] == 'function' ) {

			this[ event.type ]( event );

		}

	};

	var getMouseOnScreen = ( function () {

		var vector = new THREE.Vector2();

		return function getMouseOnScreen( pageX, pageY ) {

			vector.set(
				( pageX - _this.screen.left ) / _this.screen.width,
				( pageY - _this.screen.top ) / _this.screen.height
			);

			return vector;

		};

	}() );

	var getMouseOnCircle = ( function () {

		var vector = new THREE.Vector2();

		return function getMouseOnCircle( pageX, pageY ) {

			vector.set(
				( ( pageX - _this.screen.width * 0.5 - _this.screen.left ) / ( _this.screen.width * 0.5 ) ),
				( ( _this.screen.height + 2 * ( _this.screen.top - pageY ) ) / _this.screen.width ) // screen.width intentional
			);

			return vector;

		};

	}() );

	this.rotateCamera = ( function() {

		var axis = new THREE.Vector3(),
			quaternion = new THREE.Quaternion(),
			eyeDirection = new THREE.Vector3(),
			objectUpDirection = new THREE.Vector3(),
			objectSidewaysDirection = new THREE.Vector3(),
			moveDirection = new THREE.Vector3(),
			angle;

		return function rotateCamera() {

			moveDirection.set( _moveCurr.x - _movePrev.x, _moveCurr.y - _movePrev.y, 0 );
			angle = moveDirection.length();

			if ( angle ) {

				_eye.copy( _this.object.position ).sub( _this.target );

				eyeDirection.copy( _eye ).normalize();
				objectUpDirection.copy( _this.object.up ).normalize();
				objectSidewaysDirection.crossVectors( objectUpDirection, eyeDirection ).normalize();

				objectUpDirection.setLength( _moveCurr.y - _movePrev.y );
				objectSidewaysDirection.setLength( _moveCurr.x - _movePrev.x );

				moveDirection.copy( objectUpDirection.add( objectSidewaysDirection ) );

				axis.crossVectors( moveDirection, _eye ).normalize();

				angle *= _this.rotateSpeed;
				quaternion.setFromAxisAngle( axis, angle );

				_eye.applyQuaternion( quaternion );
				_this.object.up.applyQuaternion( quaternion );

				_lastAxis.copy( axis );
				_lastAngle = angle;

			} else if ( ! _this.staticMoving && _lastAngle ) {

				_lastAngle *= Math.sqrt( 1.0 - _this.dynamicDampingFactor );
				_eye.copy( _this.object.position ).sub( _this.target );
				quaternion.setFromAxisAngle( _lastAxis, _lastAngle );
				_eye.applyQuaternion( quaternion );
				_this.object.up.applyQuaternion( quaternion );

			}

			_movePrev.copy( _moveCurr );

		};

	}() );


	this.zoomCamera = function () {

		var factor;

		if ( _state === STATE.TOUCH_ZOOM_PAN ) {

			factor = _touchZoomDistanceStart / _touchZoomDistanceEnd;
			_touchZoomDistanceStart = _touchZoomDistanceEnd;
			_eye.multiplyScalar( factor );

		} else {

			factor = 1.0 + ( _zoomEnd.y - _zoomStart.y ) * _this.zoomSpeed;

			if ( factor !== 1.0 && factor > 0.0 ) {

				_eye.multiplyScalar( factor );

				if ( _this.staticMoving ) {

					_zoomStart.copy( _zoomEnd );

				} else {

					_zoomStart.y += ( _zoomEnd.y - _zoomStart.y ) * this.dynamicDampingFactor;

				}

			}

		}

	};

	this.panCamera = ( function() {

		var mouseChange = new THREE.Vector2(),
			objectUp = new THREE.Vector3(),
			pan = new THREE.Vector3();

		return function panCamera() {

			mouseChange.copy( _panEnd ).sub( _panStart );

			if ( mouseChange.lengthSq() ) {

				mouseChange.multiplyScalar( _eye.length() * _this.panSpeed );

				pan.copy( _eye ).cross( _this.object.up ).setLength( mouseChange.x );
				pan.add( objectUp.copy( _this.object.up ).setLength( mouseChange.y ) );

				_this.object.position.add( pan );
				_this.target.add( pan );

				if ( _this.staticMoving ) {

					_panStart.copy( _panEnd );

				} else {

					_panStart.add( mouseChange.subVectors( _panEnd, _panStart ).multiplyScalar( _this.dynamicDampingFactor ) );

				}

			}

		};

	}() );

	this.checkDistances = function () {

		if ( ! _this.noZoom || ! _this.noPan ) {

			if ( _eye.lengthSq() > _this.maxDistance * _this.maxDistance ) {

				_this.object.position.addVectors( _this.target, _eye.setLength( _this.maxDistance ) );
				_zoomStart.copy( _zoomEnd );

			}

			if ( _eye.lengthSq() < _this.minDistance * _this.minDistance ) {

				_this.object.position.addVectors( _this.target, _eye.setLength( _this.minDistance ) );
				_zoomStart.copy( _zoomEnd );

			}

		}

	};

	this.update = function () {

		_eye.subVectors( _this.object.position, _this.target );

		if ( ! _this.noRotate ) {

			_this.rotateCamera();

		}

		if ( ! _this.noZoom ) {

			_this.zoomCamera();

		}

		if ( ! _this.noPan ) {

			_this.panCamera();

		}

		_this.object.position.addVectors( _this.target, _eye );

		_this.checkDistances();

		_this.object.lookAt( _this.target );

		if ( lastPosition.distanceToSquared( _this.object.position ) > EPS ) {

			_this.dispatchEvent( changeEvent );

			lastPosition.copy( _this.object.position );

		}

	};

	this.reset = function () {

		_state = STATE.NONE;
		_prevState = STATE.NONE;

		_this.target.copy( _this.target0 );
		_this.object.position.copy( _this.position0 );
		_this.object.up.copy( _this.up0 );

		_eye.subVectors( _this.object.position, _this.target );

		_this.object.lookAt( _this.target );

		_this.dispatchEvent( changeEvent );

		lastPosition.copy( _this.object.position );

	};

	// listeners

	this.handleResize();

	// force an update at start
	this.update();

};

THREE.TrackballControls.prototype = Object.create( THREE.EventDispatcher.prototype );
THREE.TrackballControls.prototype.constructor = THREE.TrackballControls;

var t=0,z=0,scanPulse=false,destroyPulse=false;
var howMuch=0,times=0,val=0;

setScene();
animate();
/** FUNCTIONS **/

//galaxy generator



function newGalaxy (_n, _axis1, _axis2, _armsAngle, _bulbSize, _ellipticity){
  
  //NOTE : this function misses a better implementation of galactic bulbs. 
  //It's not visible with additive blending but the bulb does not have a correct shape yet.
  //(haven't yet found a function that provides the correct z-profile of the 'ellipticity' degree of the different Hubble galaxies'types)
  //see 'ellipticity'
  
  //number of particles.
  var n=(typeof _n === 'undefined')?10000:_n;
  
  //to get 'arms', the main galaxy shape has to be an ellipse, i.e. axis1/axis2 must raise over a certain % 
  //otherwise, because of the 'ellipticity' z-profile problem, you get a potatoe
  var axis1=(typeof _axis1 === 'undefined')?(60+Math.random()*20):_axis1;
  var axis2=(typeof _axis2 === 'undefined')?(axis1+20+Math.random()*40):_axis2;
  //make sure axis1 is the biggest (excentricity equation fails if they are inverted), and allow the coder no to care about axis order
  var maja,mina;
  axis1>axis2?(maja=axis1,mina=axis2):
    axis1==axis2?(maja=axis1+1,mina=axis2):(maja=axis2,mina=axis1);

  //radians from the center to the end of each arm, proposed value range : between 3 and 13
  var armsAngle=(typeof _armsAngle === 'undefined')?((Math.random()*2-1)>0?1:-1)*12+3:_armsAngle;

  //core proportion in the (x,y) plane, between 0 and 1, proposed value range : between .1 and .8
  var bulbSize=(typeof _bulbSize === 'undefined')?Math.random()*.6:_bulbSize>1?1:_bulbSize<0?0:_bulbSize;

  //'ellipticity' : not found a better word to name the degree of 'elliptic' Hubble type.
  //'ellipticity' is what is mainly responsible of the z-profile in this experiment.
  //Range : between 0 and 1. Proposed : .2 to .4
  //TODO: implement string handling (or value from spacename ?) to create Hubble-class galaxy ala 'SBb'...
  var ellipticity=(typeof _ellipticity === 'undefined')?.2+Math.random()*.2:_ellipticity>1?1:_ellipticity<0?0:_ellipticity;

  var stars=[];

  for(var i=0;i<n;i++){

    var dist=Math.random();
    var angle=(dist-bulbSize)*armsAngle;

    //ellipse parameters
    var a=maja*dist;
    var b=mina*dist;
    var e=Math.sqrt(a*a-b*b)/a;
    var phi=ellipticity*Math.PI/2*(1-dist)*(Math.random()*2-1);

    //create point on the ellipse with polar coordinates
    //1. random angle from the center
    var theta=Math.random()*Math.PI*2;
    //2. deduce radius from theta in polar coordinates, from the CENTER of an ellipse, plus variations
    var radius=Math.sqrt(b*b/(1-e*e*Math.pow(Math.cos(theta),2)))*(1+Math.random()*.1);
    //3. then shift theta with the angle offset to get arms, outside the bulb
    if(dist>bulbSize)theta+=angle;
    
    //convert to cartesian coordinates
    stars.push({
      x:Math.cos(phi)*Math.cos(theta)*radius,
      y:Math.cos(phi)*Math.sin(theta)*radius,
      z:Math.sin(phi)*radius
    });
  }

  return stars;

}

//threejs functions
function setScene(){
  scene=new THREE.Scene();

  camera=new THREE.PerspectiveCamera(70,innerWidth/innerHeight,.5,1500);
  camera.position.set(-20,-155,90);

  renderTarget=new THREE.WebGLRenderTarget(innerWidth,innerHeight);

  renderer=new THREE.WebGLRenderer();
  renderer.setSize(innerWidth,innerHeight);
  
  renderer.setClearColor(0x0000000);
  document.body.appendChild(renderer.domElement);

  controls=new THREE.TrackballControls(camera,renderer.domElement);
  controls.noPan=true;
  controls.noZoom=true;
  controls.rotateSpeed=20;
  controls.dynamicDampingFactor = .5;
  setGalaxy();
  
	
	window.addEventListener('resize',function(){
		camera.aspect=innerWidth/innerHeight;
		renderer.setSize(innerWidth,innerHeight);
		camera.updateProjectionMatrix();
		renderer.render(scene,camera);
	},false);
}
function setGalaxy(){
  galaxyMaterial=new THREE.ShaderMaterial({
      vertexShader:document.getElementById('vShader').textContent,
      fragmentShader:document.getElementById('fShader').textContent,
      uniforms:{
        size:{type:'f',value:3.3},
        t:{type:"f",value:0},
        z:{type:"f",value:0},
        pixelRatio:{type:"f",value:innerHeight}
      },
      transparent:true,
      depthTest:false,
      blending:THREE.AdditiveBlending
    });
  var stars1=new THREE.Geometry();
  stars1.vertices=newGalaxy();
  galaxy=new THREE.Points(stars1,galaxyMaterial);
  scene.add(galaxy);
}
function animate(){
  if(scanPulse)t+=.7;
  if(destroyPulse)z+=.7;
  galaxyMaterial.uniforms.t.value=t;
  galaxyMaterial.uniforms.z.value=z;
  requestAnimationFrame(animate);
  renderer.render(scene,camera);
  scene.rotation.z+=.001;
  controls.update();
}