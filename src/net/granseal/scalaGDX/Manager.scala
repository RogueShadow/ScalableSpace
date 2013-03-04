package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

object Manager {

  val entities = new ArrayBuffer[Entity]
  val addList = new ArrayBuffer[Entity]
  val removeList = new ArrayBuffer[Entity]
  
  def init() = {
    
    
  }
  
  def add(e: Entity) = addList += e
  def remove(e: Entity) = removeList += e
  
  def doLists() = {
    entities --= removeList
    entities ++= addList
    addList.clear()
    removeList.clear()
  }
  
  def update(delta: Float) {
    entities.foreach(_.update(delta))
    doLists()
  }
  
  def draw(sr: ShapeRenderer) = {
    entities.foreach(_.draw(sr))
  }
  
}