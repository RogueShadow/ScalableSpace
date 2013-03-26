package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2


class ShipAI {

  //TODO Make ship state read only in this context.
  def update(s: ShipState, old: ShipState): ShipControlState = {
    val cs = new ShipControlState(s.id)
    s.pos
    if ((old.rot - s.rot) < 10)cs.Left_Thruster = true
    if (old.pos.dst(s.pos) < 20)cs.Main_Thruster = true
    cs.lastState = s
    cs
  }
  
}