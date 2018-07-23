package space.siy.citrus.model.objects

import com.jogamp.opengl.GL3
import space.siy.citrus.model.interfaces.Drawable

class DrawableObject(defScene: Int, defLayer: Int) : CitrusObject(defScene, defLayer), Drawable {
    override fun draw(gl: GL3, mode: Drawable.Mode, frame: Int) {

    }
}