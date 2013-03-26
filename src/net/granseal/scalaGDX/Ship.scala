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
  val state: ShipState = new ShipState
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
      Manager remove other

      if (state.armor <= 0){
        val p = getPos(0,0)
        for (i <- 1 to 20){
          var x = Math.sin(Assets.r*Math.PI*2)*Assets.r*90
          var y = Math.cos(Assets.r*Math.PI*2)*Assets.r*80
          ParticleEngine.Explode(p.cpy())
        }
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
    	velocity.set(new Vector2(100,100))
    	velocity.setAngle(state.rot + 90)
    	ParticleEngine.Smoke(getPos(0,-19))
    }else{
      velocity.mul(0.9995f)
    }
    if (control.Left_Thruster) {
      state.rot -= 180f * delta
    }
    if (control.Space_Brake) {
      velocity.mul(0.995f)
    }
    if (control.Right_Thruster) {
      state.rot += 180f * delta
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