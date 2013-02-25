package net.granseal.scalaGDX
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

object MainClass {

  def main(args: Array[String]): Unit = {
     new LwjglApplication(new Game(), "Scala libGDX Example", 800 , 600, false);

  }
}


