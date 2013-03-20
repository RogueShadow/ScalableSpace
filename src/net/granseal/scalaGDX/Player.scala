package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx

class Player(pos: Vector2, pid: Int) extends Entity(pos: Vector2) {
  var shipType = 0
  def id = pid
  
  def draw(sb: SpriteBatch, sr: ShapeRenderer) {
    import ShipRef._
    val sprite = hull(shipType)
    sprite.setPosition(position.x, position.y)
    sprite.setRotation(rotation)
    sprite.draw(sb)
  }
  
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
      
      val p = new Vector2(pos)
      p.rotate(rotation)
      
      val v = new Vector2(1,1)
      v.setAngle(rotation+90)
      v.mul(1000)
      
      Manager add Bullet(p, v)
    }
    super.update(delta)
  }
  
  override def isPlayer = true
}

object Player {
  def apply(pos: Vector2, id: Int) = new Player(pos,id)
}