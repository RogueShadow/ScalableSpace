package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.Rectangle

class Ship(pos: Vector2,val id: Int) extends Entity(pos: Vector2) {
  var shipType = 0
  var shotTimer = 0.0f
  val shotDelay = 0.1f
  val sState: ShipState = new ShipState
  var cState: ShipControlState = new ShipControlState(id)
  var width = ShipRef.hull(shipType).getWidth()
  var height = ShipRef.hull(shipType).getHeight()

  sState.pos = pos
  sState.vel = vel
  sState.id = id
  

  def box: Rectangle = {
    new Rectangle(pos.x, pos.y, width, height)
  }
  
  def collided(other: Entity) {
    if (other.isInstanceOf[Bullet]){
      if ((other.asInstanceOf[Bullet]).owner != this){
      ParticleEngine.Explode(other.pos.cpy())
      Manager remove other
      }
    }
  }
  
  def draw(sb: SpriteBatch) {
    import ShipRef._
    
    val sprite = hull(shipType)
    sprite.setPosition(position.x, position.y)
    sprite.setRotation(sState.rot)
    sprite.draw(sb)  

  }
  
  def debug(sr: ShapeRenderer){
    sr.box(pos.x, pos.y, 0, 32, 32, 0)
  }
  
  def getPos(offsetX: Float, offsetY: Float): Vector2 = {
    origin.add(new Vector2(offsetX,offsetY).rotate(sState.rot)).cpy()
  }
  
  def origin: Vector2 = pos.cpy().add(width/2, height/2)
  
  override def update(delta: Float) {
    
    if (cState.Main_Thruster) {
    	velocity.set(new Vector2(100,100))
    	velocity.setAngle(sState.rot + 90)
    	ParticleEngine.Smoke(getPos(0,-19))
    }else{
      velocity.mul(0.9995f)
    }
    if (cState.Left_Thruster) {
      sState.rot -= 180f * delta
    }
    if (cState.Space_Brake) {
      velocity.mul(0.995f)
    }
    if (cState.Right_Thruster) {
      sState.rot += 180f * delta
    }
    if (cState.PrimaryWeaponActive){
      shootBullet(delta)
    }
    super.update(delta)
  }
  
  def shootBullet(delta: Float): Boolean = {
    if (shotTimer >= shotDelay) {
      shotTimer = 0
      val v = new Vector2(1,1)
      v.setAngle(sState.rot+90)
      v.mul(400)
      Manager add Bullet(Ship.this, getPos(0,16), v)
      true
    }else{
      shotTimer += delta
      false
    }
  }
  
}

object Ship {
  def apply(pos: Vector2, id: Int) = new Ship(pos, id)
}