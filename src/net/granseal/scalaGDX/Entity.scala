package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

class Entity(var pos: Vector2, id: Int) {


  var speed = 7f * Assets.rnd.nextFloat + 1
  var direction = Assets.rnd.nextFloat * Math.PI.toFloat * 2;
  var rotation = direction
  val spin = if (Assets.rnd.nextBoolean) -1 else 1
  val color = new Color(Assets.rnd.nextFloat, Assets.rnd.nextFloat, Assets.rnd.nextFloat, 1)
  val playerID = id
  val shipType = 0

  def update(delta: Float) = {
    if (playerID != 0) {
      playerUpdate(delta)
    } else {
      aiUpdate(delta)
    }
  }

  def playerUpdate(delta: Float) = {
    import com.badlogic.gdx.Input.Keys._
    def key(k: Int) = Gdx.input.isKeyPressed(k)

    if (key(W)) {
    	val accel = new Vector2(100,100).mul(delta)
    	accel.setAngle(rotation + 90)
    	pos.add(accel)
    }
    if (key(A)) {
      rotation -= 180f * delta
    }
    if (key(S)) {

    }
    if (key(D)) {
      rotation += 180f * delta
    }
  }

  def aiUpdate(delta: Float) = {
    val accel = new Vector2(speed,speed).mul(delta)
    accel.setAngle(rotation + 90)
    pos.add(accel)
    
    //x = x + (speed * Math.cos((direction)).toFloat) * delta
    //y = y + (speed * Math.sin((direction)).toFloat) * delta
    
    speed = speed + 0.01f * Assets.rnd.nextFloat
    rotation += 120*delta * Assets.rnd.nextFloat * spin

    if (speed > 100) {
      val e1 = Entity(pos.x, pos.y)
      val e2 = Entity(pos.x, pos.y)
      e1.rotation += 180
      e1.direction *= -1
      Manager remove this
      Manager add e1
      Manager add e2
    }
  }

  def draw(sb: SpriteBatch, sr: ShapeRenderer) = {
    import ShipRef._
    val sprite = hull(shipType)
    sprite.setPosition(pos.x, pos.y)
    sprite.setRotation(rotation)
    sprite.draw(sb)
    
  }
}

object Entity {
  def apply(x: Float, y: Float) = new Entity(new Vector2(x,y), 0)

  def Player(x: Float, y: Float, id: Int) = {
    new Entity(new Vector2(x,y), id)
  }
}