package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.audio.AudioDevice
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color

class Game extends ApplicationListener with InputProcessor{
  val WIDTH = 800
  val HEIGHT = 600
  def HWIDTH = WIDTH/2
  def HHEIGHT = HEIGHT/2
  
  var startTime = System.currentTimeMillis()
  var sb: SpriteBatch = _
  var shapeRender: ShapeRenderer = _
  var gameCam: OrthographicCamera = _
  var screenCam: OrthographicCamera = _
  var fps = new FPSLogger()
  var debug: Boolean = false
  val cs: ShipControlState = new ShipControlState(0)
  val testAI: ShipAI = new ShipAI()
  var player: Ship = null
  
  
  def create(): Unit = {
    
    val p = new java.util.Properties
    
    Gdx.input.setCursorCatched(true)
    Gdx.input.setInputProcessor(this)
    	  
    //Load Assets related to ships.
    ShipRef.loadAssets()
    
    Gdx.graphics.setVSync(false)
    
    sb = new SpriteBatch
    shapeRender = new ShapeRenderer
    
    screenCam = new OrthographicCamera
    screenCam.setToOrtho(true,WIDTH,HEIGHT)
    gameCam = new OrthographicCamera
    gameCam.setToOrtho(true,WIDTH,HEIGHT)

    player = Ship(new Vector2(HWIDTH,HHEIGHT), 0)
    Console.println("Adding player to world")
    Manager add player

    var x = 0f
    var y = 0f
    for (i <- 1 to 20) {
      x = Assets.r * WIDTH
      y = Assets.r * HEIGHT
      Manager add Ship(new Vector2(x,y), i)
    }
    startTime = System.currentTimeMillis()
   
  }
  def dispose(): Unit = {
    Console.println("Disposing resources")
    Assets.dispose()
    sb.dispose()
    shapeRender.dispose()
  }
  def pause(): Unit = {}
  def render(): Unit = {
    // Helper funcs
    def msg(msg: String, x: Int, y: Int){
      Assets.font.setColor(0,1,1,1)
      Assets.font.draw(sb, msg, x,y)
    }
    
    // Do update stuffs
    update(Gdx.graphics.getDeltaTime())
    gameCam.update()
    shapeRender.setProjectionMatrix(gameCam.combined)
    sb.setProjectionMatrix(gameCam.combined)
    
    // clear the screen
    Gdx.gl.glClearColor(0,0,0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    //Start the game world rendering
    sb.begin()
      Manager.draw(sb)
      ParticleEngine.draw(sb)
    sb.end()
    shapeRender.begin(ShapeRenderer.ShapeType.Line)
      shapeRender.setColor(Color.GREEN)
      shapeRender.rect(gameCam.position.x-HWIDTH,gameCam.position.y-HHEIGHT,WIDTH,HEIGHT)
      
      if (debug)Manager.debug(shapeRender)
    shapeRender.end()
    
    //use screenCam matrices so sprites can be drawn on the screen space properly
    sb.setProjectionMatrix(screenCam.combined)

    sb.begin()
    	msg("Entities: " + Manager.grid.list.size, 30, 30) 
    	msg("ManagerTime(" + Manager.currentTime.toString + ")", 30, 50)
    	msg("  SystemTime(" + getElapsedTime.toString + ")", 30, 70)
    	sb.draw(Assets.box,mx,my)
    sb.end()
    
    fps.log()
  }
  def resize(x: Int, y: Int): Unit = {}
  def resume(): Unit = {}

  
  def getElapsedTime: Long = {
    System.currentTimeMillis() - startTime
  }
  def update(delta: Float): Unit = {
    // Import keys localy for easy configs!
    // Will have to do a proper key binding system later on.
    // process input events elsewhere, polling input here.
	import com.badlogic.gdx.Input.Keys._
    def key(k: Int) = Gdx.input.isKeyPressed(k)
    
    cs.reset
	if (key(W))cs.Main_Thruster = true
	if (key(A))cs.Left_Thruster = true
	if (key(D))cs.Right_Thruster = true
	if (key(SPACE))cs.PrimaryWeaponActive = true
	if (key(S))cs.Space_Brake = true
	

    
	Manager.getShips().foreach(s => {
	  if (s.state.id != 0) {
	    Manager.update(testAI.update(s.state, s.control.lastState))
	  }
	})
	
	Manager.screen.set(gameCam.position.x - HWIDTH, gameCam.position.y - HHEIGHT, WIDTH, HEIGHT)
    Manager.update(cs)
    Manager.update(delta)
    
    ParticleEngine.update(delta)
    
    
    if (key(Z)){
      gameCam.zoom -= 1f*delta
    }
	
	if (key(X)){
	  gameCam.zoom += 1f*delta
	}
	
	// track player to camera, maybe there's a better way?
    
    gameCam.position.x = player.pos.x
    gameCam.position.y = player.pos.y
    
  }
  
  def mx = Gdx.input.getX().toFloat
  def my = Gdx.input.getY().toFloat
  def worldX(screenX: Int): Float = screenX + gameCam.position.x - HWIDTH
  def worldY(screenY: Int): Float = screenY + gameCam.position.y - HHEIGHT
  
  //Process input Events here. VVV
  def touchDown(screenX: Int, screenY: Int, p: Int, button: Int) = {
    Gdx.input.setCursorCatched(true)
    
    worldClick(worldX(screenX), worldY(screenY), p, button)
  }
  def worldClick(x: Float, y: Float, p: Int, button: Int) = {
    Manager add Ship(new Vector2(x, y),21)
    false
  }
  def keyDown(keycode: Int) = {
	import com.badlogic.gdx.Input.Keys._
	if (keycode == ESCAPE){
	  if (Gdx.app.getInput().isCursorCatched()){
	    Gdx.app.getInput().setCursorCatched(false)
	  }else{
	    Gdx.app.exit()
	  }
	}
    true
  } 
  def keyUp(keycode: Int) = {
    if (keycode == Input.Keys.C) debug = !debug
    false
  }
  def keyTyped(character: Char) = false
  def touchUp(x: Int, y: Int, p: Int, button: Int) = false
  def touchDragged(x: Int, y: Int, p: Int) = false
  def mouseMoved(x: Int, y: Int) = false
  def scrolled(amount: Int) = false
  
}