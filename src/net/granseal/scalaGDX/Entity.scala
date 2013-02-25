package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Color


class Entity(var x: Float, var y: Float, var sprite: Sprite) {
  
  var speed = 5f*Assets.rnd.nextFloat+1
  var direction = Assets.rnd.nextFloat*Math.PI.toFloat*2;
  var rotation = direction
  val spin = if (Assets.rnd.nextBoolean) -1 else 1
  
  def update(delta: Float) = {
    x = x + (speed*Math.cos((direction)).toFloat)*delta
    y = y + (speed*Math.sin((direction)).toFloat)*delta
    sprite
    speed = speed + 0.01f*Assets.rnd.nextFloat
    direction += 0.001f*Assets.rnd.nextFloat*spin
    rotation = direction
  }
  
  def draw(sb: SpriteBatch) = {
    sprite.setPosition(x, y)
    sprite.setRotation(Math.toDegrees(rotation).toFloat)
    sprite.draw(sb)
    val head = new Sprite(sprite)
    head.setScale(0.2f)
    head.setColor(Color.MAGENTA)
    val hx = x + Math.cos(direction).toFloat*20
    val hy = y + Math.sin(direction).toFloat*20
    head.setPosition(hx, hy)
    head.draw(sb)
  }
  
}