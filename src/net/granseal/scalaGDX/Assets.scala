package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.Gdx
import scala.util.Random
import com.badlogic.gdx.graphics.Color
import scala.collection.mutable.HashMap

object Assets {
	val box = new Texture("assets/box.png")
	val font = new BitmapFont(Gdx.files.internal("assets/thefont.fnt"),Gdx.files.internal("assets/thefont.png"),false)
	val rnd = new Random()

	val images = new HashMap[String, Texture]
	
	def r = rnd.nextFloat
	
	def rColor = new Color(r,r,r,1)
	
	def dispose() {
	  box.dispose()
	  font.dispose()
	  for ((key, value) <- images){
	    value.dispose()
	  }
	}
	
	
	def loadImage(img: String, name: String) {
	  if (images.contains(name)){
	    images(name).dispose()
	    images.remove(name)
	    images += ((name, new Texture(img)))
	    Console.println("Reloaded texture named: " + name + " with texture file: " + img)
	  }else{
	    images += ((name, new Texture(img)))
	    Console.println("Loaded Texture("+name+") from file("+img+")")
	  }
	}
	
	def unloadImage(name: String) {
	  images(name).dispose()
	  images.remove(name)
	  Console.println("Disposing image("+name+")")
	}
	
	def apply(name: String): Texture =  if (images.contains(name))images(name) else box

}