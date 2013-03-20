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


class Game extends ApplicationListener with InputProcessor{
  
  var sb: SpriteBatch = _
  var shapeRender: ShapeRenderer = _
  var gameCam: OrthographicCamera = _
  var screenCam: OrthographicCamera = _
  var fps = new FPSLogger()
  
  def create(): Unit = {
    
    Gdx.input.setCursorCatched(true)
    Gdx.input.setInputProcessor(this)
    	  
    //Load Assets related to ships.
    ShipRef.loadAssets()
    
    Gdx.graphics.setVSync(false)
    
    sb = new SpriteBatch
    shapeRender = new ShapeRenderer
    
    screenCam = new OrthographicCamera
    screenCam.setToOrtho(true,800,600)
    gameCam = new OrthographicCamera
    gameCam.setToOrtho(true,800,600)

    Manager add Enemy(new Vector2(400,300))
    Manager add Player(new Vector2(400,300), 1)
   
  }
  def dispose(): Unit = {
    Assets.dispose
    sb.dispose()
    shapeRender.dispose()
    Console.println("Disposing resources")
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
    shapeRender.begin(ShapeRenderer.ShapeType.Line)
    Manager.draw(sb, shapeRender)
    

    shapeRender.end()
    sb.end()
    
    //use screenCam matrices so sprites can be drawn on the screen space properly
    sb.setProjectionMatrix(screenCam.combined)
   
    sb.begin()
    	msg("Entities: " + Manager.entities.size, 30, 30) 
    	sb.draw(Assets.box,mx,my)
    sb.end()
    
    fps.log()
  }
  def resize(x: Int, y: Int): Unit = {}
  def resume(): Unit = {}

  

  def update(delta: Float): Unit = {
    // Import keys localy for easy configs!
    // Will have to do a proper key binding system later on.
    // process input events elsewhere, polling input here.
	import com.badlogic.gdx.Input.Keys._
    def key(k: Int) = Gdx.input.isKeyPressed(k)
    
    Manager.update(delta)
    
    if (key(Z)){
      gameCam.zoom -= 1f*delta
    }
	if (key(X)){
	  gameCam.zoom += 1f*delta
	}
	
	// track player to camera, maybe there's a better way?
    val player = Manager.getPlayer(1)
    if (player.isDefined){
      val x = player.get.position.x
      val y = player.get.position.y
      gameCam.position.set(x, y, 0)
    }else{
      Console.out.println("Player not found.")
    }
    
  }
  
  def mx = Gdx.input.getX().toFloat
  def my = Gdx.input.getY().toFloat
  
  //Process input Events here. VVV
  def touchDown(x: Int, y: Int, p: Int, button: Int) = {
    Gdx.input.setCursorCatched(true)
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
  def keyUp(keycode: Int) = false
  def keyTyped(character: Char) = false
  def touchUp(x: Int, y: Int, p: Int, button: Int) = false
  def touchDragged(x: Int, y: Int, p: Int) = false
  def mouseMoved(x: Int, y: Int) = false
  def scrolled(amount: Int) = false
  
}