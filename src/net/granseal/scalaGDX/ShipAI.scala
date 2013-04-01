package net.granseal.scalaGDX

import com.badlogic.gdx.math.Vector2


class ShipAI {

  //TODO Make ship state read only in this context.
  def update(s: ShipState, old: ShipState): ShipControlState = {
    val cs = new ShipControlState(s.id)
    cs.lastState = s
    if (s.armor < old.armor){
      cs.Main_Thruster = true
      Console.println("Ship was damaged, ID: " + s.id)
    }
    val test = Assets.r*100

    cs
  }
  
}