package net.granseal.scalaGDX

import scala.collection.mutable.ArrayBuffer

class EntityGrid(width: Int, height: Int, size: Int) extends Grid[Entity](width: Int, height: Int, size: Int) {
  
  def getEntities(minx: Int, miny: Int, maxx: Int, maxy: Int): List[Entity] = {
    val list = ArrayBuffer.empty[Entity]
    for (e <- getSubList(minx, miny, maxx, maxy)){
      if (e.pos.x >= minx){
        if (e.pos.x < maxx){
          if (e.pos.y >= miny){
            if (e.pos.y < maxy){
              list += e
            }
          }
        }
      }
    }
    list.toList
  }
  
  def update(e: Entity){
    updatePosition(e.oldpos.x, e.oldpos.y, e.pos.x, e.pos.y, e)
  }
  

}

object EntityGrid {
  def apply(width: Int, height: Int, size: Int) = {
    new EntityGrid(width,height,size)
  }
  
  
}