package net.granseal.scalaGDX

class ShipControlState(val shipID: Int) {
  var Main_Thruster = false
  var Left_Thruster = false
  var Right_Thruster = false
  var Space_Brake = false
  var PrimaryWeaponActive = false
}