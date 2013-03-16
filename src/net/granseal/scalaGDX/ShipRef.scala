package net.granseal.scalaGDX

import scala.collection.immutable.HashMap
import com.badlogic.gdx.graphics.g2d.Sprite

object ShipRef {
  
  def loadAssets() {
    Assets.addSpriteTexture("assets/spaceship.png", "hull_0")
  }
  
  // HashMap[Int, Sprite] Where Int == Type of Ship
  val hullSprites = HashMap(
    0 -> "hull_0"
  )
  
  def hull(shipType: Int): Sprite = Assets.getSprite(hullSprites(shipType))
}