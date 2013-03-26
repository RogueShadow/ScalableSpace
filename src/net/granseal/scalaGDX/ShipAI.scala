package net.granseal.scalaGDX


class ShipAI {
  
  var oldState: ShipState = new ShipState()
  // absolutely no editing ShipState ... I should enforce this somehow. 
  //TODO that.
  def update(s: ShipState): ShipControlState = {
    val cs = new ShipControlState(s.id)
    s.pos
    if ((oldState.rot - s.rot) < 10)cs.Left_Thruster = true
    if (s.vel.len2() < 20)cs.Main_Thruster = true

    oldState = s
    cs
  }
  
}