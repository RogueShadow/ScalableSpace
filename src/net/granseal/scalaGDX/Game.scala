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

class Game extends ApplicationListener {
  
  var x: Float = _
  var y: Float = _
  var sb: SpriteBatch = _
  var box: Sprite = _
  var blt: Sprite = _
  var cam: OrthographicCamera = _
  var totalTime: Double = 0
  var lastTimeDraw = 0
  val fps = new FPSLogger()
  
  def create(): Unit = {
    
    Gdx.graphics.setVSync(false)
    sb = new SpriteBatch()
    cam = new OrthographicCamera()
    cam.setToOrtho(false,800,600)
    box = new Sprite(Assets.box)
    box.setScale(2)
    
    blt = new Sprite(Assets.box)
    blt.setScale(0.2f)
   
  }
  def dispose(): Unit = {}
  def pause(): Unit = {}
  def render(): Unit = {
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    sb.begin()
    	Assets.font.setColor(0,1,1,1)
    	Assets.font.draw(sb, "Hello World", 30, 30)
    	box.draw(sb)
    	blt.draw(sb)
    sb.end()
    update(Gdx.graphics.getDeltaTime())
    fps.log()
  }
  def resize(x: Int, y: Int): Unit = {}
  def resume(): Unit = {}

  def update(delta: Float): Unit = {
    totalTime += delta
    x = Gdx.input.getX().toFloat
    y = -Gdx.input.getY().toFloat + 600
    box.setPosition(x, y)
    blt.setPosition(x, y+100)
    if (totalTime.toInt != lastTimeDraw){
      lastTimeDraw = totalTime.toInt
      System.out.println("Time: " + totalTime.toInt)
    }
    
  }
  
}