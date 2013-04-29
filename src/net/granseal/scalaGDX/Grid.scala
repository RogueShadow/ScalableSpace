package net.granseal.scalaGDX
import scala.collection.mutable.ArrayBuffer


class Grid[T](val width: Int,val height: Int,val cellSize: Int, val offsetx: Int = 0, val offsety: Int = 0){
  private val cells = Array.ofDim[Cell](width,height)
  private var allElements = List.empty[T]
  private var isDirty = false
  
  def getSubList(minx: Float, miny: Float, maxx: Float, maxy: Float): List[T] = {
    val sublist = ArrayBuffer.empty[T]
    for (x <- Math.floor(minx).toInt to Math.floor(maxx).toInt){
      for (y <- Math.floor(miny).toInt to Math.floor(maxy).toInt){
        sublist ++= cells(x)(y).elements
      }
    }
    sublist.toList
  }
  
  def list: List[T] = {
    if (isDirty)rebuildList
    allElements
  }
  
  def length: Int = {
    list.length
  }
  
  {
  var c: Int = 0
  for (x <- 0 to (width - 1)){
    for (y <- 0 to (height - 1)){
      cells(x)(y) = new Cell(x*cellSize,y*cellSize,cellSize)
      c += 1
    }
  }
  Console.println("Created Grid with " + c + " cells.")
  }
  
  private class Cell(val x: Int,val y: Int,val size: Int){
    val elements = new ArrayBuffer[T]
  }

  def add(x: Float, y: Float, e: T) = {
    Console.println("Added object to grid, at position: " + x + " / " + y)
    isDirty = true
    (cells(getx(x))(gety(y))).elements.result += e
    
  }
  
  def remove(x: Float, y: Float, e: T) = {
    Console.println("Removed object from grid, at position: " + x + " / " + y)
    isDirty = true
    (cells(getx(x))(gety(y))).elements -= e

  }
  
  def getx(x: Float): Int = {
    val cx = Math.floor(x/cellSize).toInt
    if (cx < 0){
      return 0
    }
    if (cx >= width){
      return width - 1
    }
    cx
  }
  def gety(y: Float): Int = {
    val cy = Math.floor(y/cellSize).toInt
    if (cy < 0){
      return 0
    }
    if (cy >= height){
      return height - 1
    }
    cy
  }
  
  def updatePosition(oldx: Float, oldy: Float, x: Float, y: Float, e: T){
    val oldcx = getx(oldx)
    val oldcy = gety(oldy)
    val cx = getx(x)
    val cy = gety(y)
    
    if (oldcx != cx || oldcy != cy){
      (cells(oldcx)(oldcy)).elements -= e
      (cells(cx)(cy)).elements += e
      isDirty = true
    }
  }
  
  def rebuildList {
    val l = new ArrayBuffer[T]
    for (x <- 0 to (width - 1)){
      for (y <- 0 to (height - 1)){
        l ++=  cells(x)(y).elements
      }
    } 
    isDirty = false
    allElements = l.toList
  }

}