package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


class Entity(var x: Float, var y: Float) {
  
  var speed = 7f*Assets.rnd.nextFloat+1
  var direction = Assets.rnd.nextFloat*Math.PI.toFloat*2;
  var rotation = direction
  val spin = if (Assets.rnd.nextBoolean) -1 else 1
  
  def update(delta: Float) = {
    x = x + (speed*Math.cos((direction)).toFloat)*delta
    y = y + (speed*Math.sin((direction)).toFloat)*delta

    speed = speed + 0.03f*Assets.rnd.nextFloat
    direction += 0.0005f*Assets.rnd.nextFloat*spin
    rotation = direction.toDegrees.toFloat
  }
  
  def draw(sr: ShapeRenderer) = {

    sr.begin(ShapeRenderer.ShapeType.FilledTriangle)
      sr.identity()
      sr.translate(x, y, 0)
      sr.rotate(0, 0, 1, rotation-90)
      sr.filledTriangle(0,+32,-32,-32,+32,-32)
    sr.end()

  }
  
}