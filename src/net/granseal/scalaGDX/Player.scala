package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3

class Player(pos: Vector2, pid: Int) extends Entity(pos: Vector2) {
  var shipType = 0
  val id = pid
  
  var width = ShipRef.hull(shipType).getWidth()
  var height = ShipRef.hull(shipType).getHeight()
  
  def draw(sb: SpriteBatch) {
    import ShipRef._
    
    val sprite = hull(shipType)
    sprite.setPosition(position.x, position.y)
    sprite.setRotation(rotation)
    sprite.draw(sb)  

  }
  
  def debug(sr: ShapeRenderer){

  }
  
  def getPos(offsetX: Float, offsetY: Float): Vector2 = {
    origin.add(new Vector2(offsetX,offsetY).rotate(rotation))
  }
  
  def origin: Vector2 = pos.cpy().add(width/2, height/2)
  
  override def update(delta: Float) {
    import com.badlogic.gdx.Input.Keys._
    def key(k: Int) = Gdx.input.isKeyPressed(k)

    if (key(W)) {
    	velocity.set(new Vector2(100,100))
    	velocity.setAngle(rotation + 90)
    }else{
      velocity.mul(0.9995f)
    }
    if (key(A)) {
      rotation -= 180f * delta
    }
    if (key(S)) {

    }
    if (key(D)) {
      rotation += 180f * delta
    }
    if (key(SPACE)){
      val v = new Vector2(1,1)
      v.setAngle(rotation+90)
      v.mul(400)
      
      Manager add Bullet(getPos(0,16), v)
    }
    super.update(delta)
  }
  
  override def isPlayer = true
}

object Player {
  def apply(pos: Vector2, id: Int) = new Player(pos,id)
}