package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

abstract class Entity(val pos: Vector2) {
  val vel: Vector2 = new Vector2(0,0)
  var oldpos: Vector2 = new Vector2(0,0)
  var isVisible = false
  
  def position = pos
  def velocity = vel
  
  def update(delta: Float) {
    oldpos = pos.cpy()
    pos.add(vel.cpy().mul(delta))
  }
  
  def draw(sb: SpriteBatch)
  
  def box: Rectangle
  
  def collided(other: Entity)
  
  def debug(sr: ShapeRenderer)

}