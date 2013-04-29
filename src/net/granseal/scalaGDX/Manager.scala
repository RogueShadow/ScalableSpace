package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import scala.collection.mutable.HashMap
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle

object Manager {

  var currentTime: Double = 0
  
  var screen = new Rectangle()

  val addList = new ArrayBuffer[Entity]
  val removeList = new ArrayBuffer[Entity]
  val grid = new EntityGrid(250,250,250)
  
  val controlStates = new HashMap[Int,ShipControlState]
  
  def init() = {
    
    
  }
  
  def getShips(): List[Ship] = {
    val ships = grid.list.filter( e => e.isInstanceOf[Ship]).asInstanceOf[List[Ship]]
    ships
  }
  
  def getShip(id: Int):Option[Ship] = {
    val ships: ArrayBuffer[Ship] = grid.list.filter( e => e.isInstanceOf[Ship]).asInstanceOf[ArrayBuffer[Ship]]
    ships.find(s => s.state.id == id)
  }
  
  def add[T <: Entity](e: T) = {
    addList += e.asInstanceOf[Entity]
    if (e.isInstanceOf[Ship]){
      val s = e.asInstanceOf[Ship]
      controlStates(s.id) = new ShipControlState(s.id)
    }
  }
  
  
  def remove[T <: Entity](e: T) = removeList += e.asInstanceOf[Entity]
  
  def doLists() = {
    addGrid(addList)
    removeGrid(removeList)
  }
  
  def addGrid(list: ArrayBuffer[Entity]){
    list.foreach(e =>
      grid.add(e.pos.x, e.pos.y, e)
    )
    list.clear
  }
  
  def removeGrid(list: ArrayBuffer[Entity]){
    list.foreach(e =>
      grid.remove(e.pos.x, e.pos.y, e)
    )
    list.clear
  }
  
  def collision() = {
    var e1: Entity = null
    var e2: Entity = null
    for (i <- 0 to grid.list.size){
      for (j <- i to grid.list.size-1){
        if (i != j){
           e1 = grid.list(i)
           e2 = grid.list(j)
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
    currentTime += delta
    grid.list.foreach(x => 
      {
        if (x.isInstanceOf[Ship])(x.asInstanceOf[Ship]).control = controlStates((x.asInstanceOf[Ship]).state.id)
        x.update(delta)
        grid.update(x)
        if (screen.overlaps(new Rectangle(x.pos.x, x.pos.y,  32, 32)))x.isVisible = true else x.isVisible = false
      }
    )

    collision()
    doLists()
  }
  
  def draw(sb: SpriteBatch) = {
    grid.list.foreach(e => if (e.isVisible)e.draw(sb))
  }
  
  def debug(sr: ShapeRenderer) = {
    grid.list.foreach(_.debug(sr))
    for (x <- 0 to grid.width ; y <- 0 to grid.height){
        sr.setColor(Color.RED)
        sr.rect(x*grid.cellSize, y*grid.cellSize, grid.cellSize,grid.cellSize)
      
    }
  }
  
}