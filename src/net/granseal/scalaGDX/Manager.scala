package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import scala.collection.mutable.HashMap

object Manager {

  val entities = new ArrayBuffer[Entity]
  val addList = new ArrayBuffer[Entity]
  val removeList = new ArrayBuffer[Entity]
  
  val controlStates = new HashMap[Int,ShipControlState]
  
  def init() = {
    
    
  }
  
  def add[T <: Entity](e: T) = {
    addList += e
    if (e.isInstanceOf[Ship]){
      val s = e.asInstanceOf[Ship]
      controlStates(s.id) = new ShipControlState(s.id)
    }
  }
  
  
  def remove(e: Entity) = removeList += e
  
  def doLists() = {
    entities ++= addList
    entities --= removeList
    addList.clear()
    removeList.clear()
  }
  
  def collision() = {
    var e1: Entity = null
    var e2: Entity = null
    for (i <- 0 to entities.length){
      for (j <- i to entities.length-1){
        if (i != j){
           e1 = entities(i)
           e2 = entities(j)
           if (e1.box.overlaps(e2.box)){
             e1.collided(e2)
             e2.collided(e1)
           }
        }
      }
    }    
  }
  
  def update(cs: ShipControlState){
    controlStates(cs.shipID) = cs
  }
  def update(delta: Float) {
    entities.foreach(x => 
      {
	    if (x.isInstanceOf[Ship]){
	      val s = x.asInstanceOf[Ship]
	      val cstate = controlStates(s.id)
	      if (cstate == null)Console.println("Error: Couldn't find control state for shipID(" + s.id + ")")
	      s.cState = cstate
	    }
        x.update(delta)
      }
    )
    

    collision()
    doLists()
  }
  
  def draw(sb: SpriteBatch) = {
    entities.foreach(_.draw(sb))
  }
  
  def debug(sr: ShapeRenderer) = {
    entities.foreach(_.debug(sr))
  }
  
}