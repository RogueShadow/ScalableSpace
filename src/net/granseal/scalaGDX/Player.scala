package net.granseal.scalaGDX
import com.badlogic.gdx.math.Vector2

class Player(pos: Vector2, id: Int) extends Entity(pos: Vector2, id: Int) {

}

object Player {
  def apply(pos: Vector2, id: Int) = new Player(pos,id)
}