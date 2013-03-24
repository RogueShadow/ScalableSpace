package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Color

class Particle(val  pos: Vector2, vel: Vector2, sprite: Sprite, lifetime: Float, var rotation: Float, rotationVel: Float, var scale: Float, scaleFac: Float) {
  var life: Float = 0
  var color: Color = new Color(1,1,1,1) 
  
  pos.x -= sprite.getWidth()/2
  pos.y -= sprite.getHeight()/2
  
  def update(delta: Float){
    
    pos.add(vel.cpy().mul(delta))
    
    rotation += rotationVel
    scale *= scaleFac*delta
    color.a = 0.5f - (life/lifetime)*0.5f
    
    if (life >= lifetime){
      ParticleEngine remove this
    }else{
      life += delta
    }
    
  }
  
  def draw(sb: SpriteBatch){
    sprite.setPosition(pos.x, pos.y)
    sprite.setRotation(rotation)
    //sprite.setScale(scale)
    sprite.setColor(color)
    sprite.draw(sb)
  }
  
}

object Particle{
  
  def apply(pos: Vector2, vel: Vector2, sprite: Sprite, lifetime: Float, rotation: Float, rotationVel: Float, scale: Float, scaleFac: Float) = {
    new Particle(pos, vel, sprite, lifetime, rotation, rotationVel, scale, scaleFac)
  }
}