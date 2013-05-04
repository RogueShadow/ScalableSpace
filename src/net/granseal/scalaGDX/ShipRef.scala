package net.granseal.scalaGDX

import scala.collection.immutable.HashMap
import com.badlogic.gdx.graphics.g2d.Sprite

object ShipRef {
  
  // HashMap[Int, Sprite] Where Int == Type of Ship
  val hullSprites = HashMap(
    0 -> "hull_0"
  )
  
  val shipBrakes = HashMap(
      0 -> 400
  )
  
  val shipThrusters = HashMap(
      0 -> 200,
      1 -> 400,
      2 -> 600,
      3 -> 800
  )
  
  val engineMaxSpeed = HashMap(
      0 -> 100,
      1 -> 150,
      2 -> 200,
      3 -> 250
  )
  
  def hull(shipType: Int): Sprite = Assets.getSprite(hullSprites(shipType))
  def brakes(brakeType: Int): Float = shipBrakes(brakeType)
  def thruster(thrusterType: Int): Float = shipThrusters(thrusterType)
  def maxSpeed(engineType: Int): Float = engineMaxSpeed(engineType)
}