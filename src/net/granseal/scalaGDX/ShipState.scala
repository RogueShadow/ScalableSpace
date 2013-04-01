package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2

class ShipState {
  var id = 0
  var armor = 0
  var shield = 0
  var pos: Vector2 = new Vector2(0,0)
  var vel: Vector2 = new Vector2(0,0)
  var rot: Float = 0
  var turnSpeed: Int = 310
  
  def cpy(): ShipState = {
    var s = new ShipState
    s.armor = this.armor
    s.shield = this.shield
    s.pos = this.pos
    s.vel = this.vel
    s
  }
}