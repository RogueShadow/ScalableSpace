package net.granseal.scalaGDX

import com.badlogic.gdx.math.Rectangle
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

object ParticleEngine {
  
  val particles = new ArrayBuffer[Particle]
  val addList = new ArrayBuffer[Particle]
  val removeList = new ArrayBuffer[Particle]
  
  def remove(p: Particle){
    removeList += p
  }
  
  def add(p: Particle){
    addList += p
  }
  
  def update(delta: Float){
    particles.foreach(_.update(delta))
    particles ++= addList
    particles --= removeList
    addList.clear
    removeList.clear
  }
  
  def draw(sb: SpriteBatch){
    particles.foreach(_.draw(sb))
  }

  def randVel(mag: Float): Vector2 = {
    val angle: Float = 360*Assets.r
    val v = new Vector2(1,1)
    v.mul(mag)
    v.setAngle(angle)
    v
    
  }
  def Explode(pos: Vector2){
    for (i <- 1 to 10){
      add( Particle(pos.cpy(), randVel(Assets.r*90), Assets.getSprite("star1"), 1, 0, Assets.r* .2f * (if (Assets.r > 0.5) 1 else -1), 1, 1.001f))
    }
  }
  
  def Smoke(pos: Vector2){
    add( Particle(pos, randVel(Assets.r*20), Assets.getSprite("smoke"), .75f, 0, 0.01f, 1, 1.000f))
  }
  
}