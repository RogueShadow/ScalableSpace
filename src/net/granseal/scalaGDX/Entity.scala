package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

abstract class Entity(pos: Vector2) {
  val vel: Vector2 = new Vector2(0,0)
  var rotation: Float = 0.0f
  
  def position = pos
  def velocity = vel
  
  def update(delta: Float) {
    pos.add(vel.cpy().mul(delta))
  }
  
  def draw(sb: SpriteBatch)
  
  def debug(sr: ShapeRenderer)
  
  def isPlayer = false
}