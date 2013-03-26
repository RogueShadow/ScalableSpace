package net.granseal.scalaGDX

class ShipState {
  var armor = 0
  var shield = 0
  
  def cpy(): ShipState = {
    var s = new ShipState
    s.armor = this.armor
    s.shield = this.shield
    s
  }
}