package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.Gdx
import scala.util.Random
import com.badlogic.gdx.graphics.Color
import scala.collection.mutable.HashMap

object Assets {
  
    // graceful failure. prabably not needed, but nifty!
	val box = new Texture("assets/box.png")
	val boxSprite = new Sprite(box)
	
	// default font, likely to change totally!
	val font = new BitmapFont(Gdx.files.internal("assets/thefont.fnt"),Gdx.files.internal("assets/thefont.png"),true)
	
	// keep a random around!  And a shorthand method to get a float, and one for a color
	val rnd = new Random()
	rnd.setSeed(System.nanoTime())
	def r = rnd.nextFloat
	def rColor = new Color(r,r,r,1)
	
	// resources referenced by friendly string names :D
	val images = new HashMap[String, Texture]
	val sprites = new HashMap[String, Sprite]

	//Gotta dispose of those pesky resources
	def dispose() {
	  box.dispose()
	  font.dispose()
	  for ((key, value) <- images){
	    value.dispose()
	  }
	}

	// Method to add texture and sprite simultaneously. Using friendly name for both resources
	def addSpriteTexture(tex_file: String, name: String){
	  addTexture(tex_file, name)
	  addSprite(name, name)
	}
	// Add Sprites, Texture by friendly reference name, include a friendly name to refrence the sprite by.
	def addSprite(tex: String, name: String) {
	  if (sprites.contains(name)){
	    Console.println("Sprite(" + name + ") already exists, recreating.")
	    sprites.remove(name)
	    sprites += ((name, new Sprite(getTexture(tex))))
	  }else{
	    sprites += ((name, new Sprite(getTexture(tex))))
	    Console.println("Added Sprite(" + name + ") from Texture(" + tex + ")")
	  } 
	}
	
	def removeSprite(name: String) {
	  sprites.remove(name)
	  Console.println("Removed Sprite("+name+")")
	}
	
	def addTexture(img: String, name: String) {
	  if (images.contains(name)) {
	    images(name).dispose()
	    images.remove(name)
	    images += ((name, new Texture(img)))
	    Console.println("Reloaded Texture(" + name + ") with Texture("+img+")")
	  }else{
	    images += ((name, new Texture(img)))
	    Console.println("Loaded Texture("+name+") from File("+img+")")
	  }
	}	
	
	def removeTexture(name: String) {
	  images(name).dispose()
	  images.remove(name)
	  Console.println("Disposing Texture("+name+")")
	}
	
	def getTexture(name: String): Texture = if (images.contains(name))images(name) else box
	def getSprite(name: String): Sprite = if (sprites.contains(name))sprites(name) else boxSprite
	

}