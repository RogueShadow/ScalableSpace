package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Rectangle

class Bullet(owner: Entity,pos: Vector2, v: Vector2,lifetime: Int) extends Entity(pos: Vector2) {
  
  velocity.set(v)
  var life = 0.0f
  
  override def update(delta: Float){
    life += delta
    if (life >= lifetime){
      Manager remove this
    }
    super.update(delta)
  }
    
  def box: Rectangle = {
    
  new Rectangle(pos.x-4, pos.y-4, 8, 8)
  }
  
  def collided(other: Entity){
   // if (other != owner){
    ParticleEngine.Explode(pos.cpy())

   // }
  }
    
  def draw(sb: SpriteBatch) {
    val sprite = Assets.getSprite("bullet_0")
    sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2)
    sprite.draw(sb)
  }
  def debug(sr: ShapeRenderer){
    sr.box(pos.x-4, pos.y-4, 0, 8, 8, 0)
  }
}

object Bullet {
  
  Assets.addSpriteTexture("assets/bullet.png", "bullet_0")
  
  def apply(owner: Entity, pos: Vector2, vel: Vector2) = new Bullet(owner, pos, vel,2)
}