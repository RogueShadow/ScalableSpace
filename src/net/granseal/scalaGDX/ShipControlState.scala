package net.granseal.scalaGDX

class ShipControlState(val shipID: Int) {
  var lastState: ShipState = null
  var Main_Thruster = false
  var Left_Thruster = false
  var Right_Thruster = false
  var Space_Brake = false
  var PrimaryWeaponActive = false
  
  def reset(){
    Main_Thruster = false
    Left_Thruster = false
    Right_Thruster = false
    Space_Brake = false
    PrimaryWeaponActive = false
  }
}