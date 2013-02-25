package net.granseal.scalaGDX

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.Gdx
import scala.util.Random

object Assets {
	val box = new Texture("assets/box.png")
	val font = new BitmapFont(Gdx.files.internal("assets/thefont.fnt"),Gdx.files.internal("assets/thefont.png"),false)
	val rnd = new Random()
}