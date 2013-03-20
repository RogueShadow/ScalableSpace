package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

object Manager {

  val entities = new ArrayBuffer[Entity]
  val addList = new ArrayBuffer[Entity]
  val removeList = new ArrayBuffer[Entity]
  val playerList = new ArrayBuffer[Player]
  
  def init() = {
    
    
  }
  
  def getPlayer(id: Int): Option[Player] = {
    playerList.find(e => e.id == id)
  }
  
  def add[T <: Entity](e: T) = {
    addList += e
    if (e.isInstanceOf[Player])playerList += e.asInstanceOf[Player]
  }

  def remove(e: Entity) = removeList += e
  
  def doLists() = {
    entities ++= addList
    entities --= removeList
    addList.clear()
    removeList.clear()
  }
  
  def update(delta: Float) {
    entities.foreach(_.update(delta))
    doLists()
  }
  
  def draw(sb: SpriteBatch, sr: ShapeRenderer) = {
    entities.foreach(_.draw(sb,sr))
  }
  
}