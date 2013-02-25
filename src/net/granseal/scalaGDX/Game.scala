package net.granseal.scalaGDX

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.FPSLogger
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Game extends ApplicationListener {
  
  var x: Float = _
  var y: Float = _
  var sb: SpriteBatch = _
  var cam: OrthographicCamera = _
  var totalTime: Double = 0
  var lastTimeDraw = 0
  var fps = new FPSLogger()
  val entities = new ArrayBuffer[Entity]

  
  def create(): Unit = {
    
    Gdx.graphics.setVSync(false)
    sb = new SpriteBatch()
    cam = new OrthographicCamera()
    cam.setToOrtho(false,800,600)

    for (i <- 1 to 100)  {
        val x = Assets.rnd.nextFloat*Gdx.graphics.getWidth()
        val y = Assets.rnd.nextFloat*Gdx.graphics.getHeight()
    	entities += new Entity(x,y,new Sprite(Assets.box))
    }
   
  }
  def dispose(): Unit = {}
  def pause(): Unit = {}
  def render(): Unit = {
      Gdx.gl.glClearColor(0.2f, 0.5f, 0.5f, 1);
      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    sb.begin()
        entities.foreach(_.draw(sb))
    	Assets.font.setColor(0,1,1,1)
    	Assets.font.draw(sb, "Time: " + totalTime.toInt, 30, 30)
    sb.end()
    update(Gdx.graphics.getDeltaTime())
    entities.foreach(_.update(Gdx.graphics.getDeltaTime()))
    fps.log()
  }
  def resize(x: Int, y: Int): Unit = {}
  def resume(): Unit = {}

  def update(delta: Float): Unit = {
    totalTime += delta
    x = Gdx.input.getX().toFloat
    y = -Gdx.input.getY().toFloat + 600

    if (totalTime.toInt != lastTimeDraw){
      lastTimeDraw = totalTime.toInt
      System.out.println("Time: " + totalTime.toInt)
    }
    
  }
  
}