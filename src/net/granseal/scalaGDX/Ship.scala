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
  var engineType = 3
  var brakeType = 0
  var shotTimer = 0.0f
  val shotDelay = 0.1f
  val state: ShipState = new ShipState
  def acceleration = ShipRef.thruster(engineType)
  var deceleration = 200f
  var control: ShipControlState = new ShipControlState(id)
  control.lastState = state.cpy
  var width = ShipRef.hull(shipType).getWidth()
  var height = ShipRef.hull(shipType).getHeight()

  state.pos = pos
  state.vel = vel
  state.id = id
  state.armor = 100
  

  def box: Rectangle = {
    new Rectangle(pos.x, pos.y, width, height)
  }
  
  def collided(other: Entity) {
    if (other.isInstanceOf[Bullet]){
      if ((other.asInstanceOf[Bullet]).owner != this){
      ParticleEngine.Explode(other.pos.cpy())

      if (state.armor <= 0){
        val p = getPos(0,0)
        for (i <- 1 to 28){
          var x = Math.sin(Assets.r*Math.PI*2).toFloat*Assets.r*30f
          var y = Math.cos(Assets.r*Math.PI*2).toFloat*Assets.r*30f
          ParticleEngine.Explode(p.cpy().add(x, y))
        }
        Assets.sounds("blast").play()
        Manager remove this
           
      }else{
         state.armor -= 20    
      }
      }
    }
  }
  
  def draw(sb: SpriteBatch) {
    import ShipRef._
    
    val sprite = hull(shipType)
    sprite.setPosition(position.x, position.y)
    sprite.setRotation(state.rot)
    sprite.draw(sb)  

    
  }
  
  def debug(sr: ShapeRenderer){
    sr.box(pos.x, pos.y, 0, 32, 32, 0)
  }
  
  def getPos(offsetX: Float, offsetY: Float): Vector2 = {
    origin.add(new Vector2(offsetX,offsetY).rotate(state.rot)).cpy()
  }
  
  def origin: Vector2 = pos.cpy().add(width/2, height/2)
  
  override def update(delta: Float) {
    if (control.Main_Thruster) {
        val accel = new Vector2(1,1).nor()
        accel.mul(acceleration)
        accel.mul(delta)
        accel.setAngle(state.rot + 90)
    	velocity.add(accel)
    	
    }else{

      if (velocity.len2() > 0){
        val decel = new Vector2(1,1).nor()
	    decel.mul(deceleration)
	    decel.mul(delta)
	    decel.setAngle(velocity.angle())
	    velocity.sub(decel)
      }else{
        velocity.set(0,0)
      }
    }
    if (control.Left_Thruster) {
      state.rot -= state.turnSpeed * delta
    }
    if (control.Space_Brake) {
      if (velocity.len2() > 0){
        val decel = new Vector2(1,1).nor()
	    decel.mul(ShipRef.brakes(brakeType))
	    decel.mul(delta)
	    decel.setAngle(velocity.angle())
	    velocity.sub(decel)
      }else{
        velocity.set(0,0)
      }
    }
    if (control.Right_Thruster) {
      state.rot += state.turnSpeed * delta
    }
    if (control.PrimaryWeaponActive){
      shootBullet(delta)
    }
    super.update(delta)
  }
  
  def shootBullet(delta: Float): Boolean = {
    if (shotTimer >= shotDelay) {
      shotTimer = 0
      val v = new Vector2(1,1)
      v.setAngle(state.rot+90)
      v.mul(400)
      v.add(velocity)
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