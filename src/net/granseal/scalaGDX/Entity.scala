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
  val sprite: Sprite = new Sprite(Assets("ship"))

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
      rotation += 180f * delta
    }
    if (key(S)) {

    }
    if (key(D)) {
      rotation -= 180f * delta
    }
  }

  def aiUpdate(delta: Float) = {
    val accel = new Vector2(speed,speed).mul(delta)
    accel.setAngle(rotation + 90)
    pos.add(accel)
    
    //x = x + (speed * Math.cos((direction)).toFloat) * delta
    //y = y + (speed * Math.sin((direction)).toFloat) * delta
    
    speed = speed + 0.03f * Assets.rnd.nextFloat
    rotation += 100*delta * Assets.rnd.nextFloat * spin

    if (speed > 400) {
      Manager.add(Entity(pos.x, pos.y))
      Manager.remove(this)
    }
  }

  def draw(sb: SpriteBatch) = {
    sb.begin()
    sprite.setPosition(pos.x, pos.y)
    sprite.setRotation(rotation)
    sprite.draw(sb)
    sb.end()
  }
}

object Entity {
  def apply(x: Float, y: Float) = new Entity(new Vector2(x,y), 0)

  def Player(x: Float, y: Float, id: Int) = {
    new Entity(new Vector2(x,y), id)
  }
}