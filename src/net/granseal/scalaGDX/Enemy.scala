package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

class Enemy(pos: Vector2) extends Entity(pos: Vector2) {
  var shipType = 0
  var speed = 7f * Assets.rnd.nextFloat + 1
  var direction = Assets.rnd.nextFloat * Math.PI.toFloat * 2;
  val spin = if (Assets.rnd.nextBoolean) -1 else 1
  
  def draw(sb: SpriteBatch, sr: ShapeRenderer) {
    import ShipRef._
    val sprite = hull(shipType)
    sprite.setPosition(pos.x, pos.y)
    sprite.setRotation(rotation)
    sprite.draw(sb)
  }
  
  override def update(delta: Float) {

    val accel = new Vector2(speed,speed).mul(delta)
    accel.setAngle(rotation + 90)
    pos.add(accel)
    
    //x = x + (speed * Math.cos((direction)).toFloat) * delta
    //y = y + (speed * Math.sin((direction)).toFloat) * delta
    
    speed = speed + 0.01f * Assets.rnd.nextFloat
    rotation += 120*delta * Assets.rnd.nextFloat * spin

    if (speed > 100) {
      val e1 = Enemy(pos.cpy())
      val e2 = Enemy(pos.cpy())
      e1.rotation += 180
      e1.direction *= -1
      Manager remove this
      Manager add e1
      Manager add e2
    }
    super.update(delta)
  }
  
  override def isPlayer = true
}

object Enemy {
  def apply(pos: Vector2) = new Enemy(pos)
}