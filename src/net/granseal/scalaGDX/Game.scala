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


class Game extends ApplicationListener {
  
  
  var x: Float = _
  var y: Float = _
  var sb: SpriteBatch = _
  var shapeRender: ShapeRenderer = _
  var cam: OrthographicCamera = _
  var totalTime: Double = 0
  var lastTimeDraw = 0
  var fps = new FPSLogger()

  
  def create(): Unit = {
    
    Gdx.graphics.setVSync(false)
    
    sb = new SpriteBatch
    shapeRender = new ShapeRenderer
    
    cam = new OrthographicCamera
    cam.setToOrtho(false,800,600)

    for (i <- 1 to 100)  { // special!
        val x = Assets.rnd.nextFloat*Gdx.graphics.getWidth
        val y = Assets.rnd.nextFloat*Gdx.graphics.getHeight
    	Manager.add(Entity(x,y))
    }
    Manager.addPlayer(Entity.Player(400, 500, 1))
    
   
  }
  def dispose(): Unit = {
    Assets.dispose
    sb.dispose()
    shapeRender.dispose()
  }
  def pause(): Unit = {}
  def render(): Unit = {
    update(Gdx.graphics.getDeltaTime())
    
    Gdx.gl.glClearColor(0,0,0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)

    Manager.draw(shapeRender)
    
    sb.begin()
    	Assets.font.setColor(0,1,1,1)
    	Assets.font.draw(sb, "Time: " + totalTime.toInt, 30, 30)
    sb.end()
    
    fps.log()
  }
  def resize(x: Int, y: Int): Unit = {}
  def resume(): Unit = {}

  def update(delta: Float): Unit = {
	import com.badlogic.gdx.Input.Keys._
    def key(k: Int) = Gdx.input.isKeyPressed(k)
    Manager.update(delta)
    if (key(Z)){
      cam.zoom -= 1f*delta
    }
	if (key(X)){
	  cam.zoom += 1f*delta
	}
    val player = Manager.getPlayer(1)
    if (player.isDefined){
      player.get.color.set(Assets.r,Assets.r,Assets.r,1)
      val x = player.get.pos.x
      val y = player.get.pos.y
      cam.position.set(x, y, 0)
    }else{
      Console.out.println("Player not found.")
    }
    
    totalTime += delta
    
    x = Gdx.input.getX().toFloat
    y = -Gdx.input.getY().toFloat + 600

    if (totalTime.toInt != lastTimeDraw) lastTimeDraw = totalTime.toInt
    
  }
  
}