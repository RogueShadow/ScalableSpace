package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Bullet(pos: Vector2, v: Vector2,lifetime: Int) extends Entity(pos: Vector2) {
  
  velocity.set(v)
  var life = 0.0f
  
  override def update(delta: Float){
    life += delta
    if (life >= lifetime){
      Manager remove this
    }
    super.update(delta)
  }
  
  def draw(sb: SpriteBatch) {
    val sprite = Assets.getSprite("bullet_0")
    sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2)
    sprite.draw(sb)
  }
  def debug(sr: ShapeRenderer){
    
  }
}

object Bullet {
  
  Assets.addSpriteTexture("assets/bullet.png", "bullet_0")
  
  def apply(pos: Vector2, vel: Vector2) = new Bullet(pos, vel,2)
}