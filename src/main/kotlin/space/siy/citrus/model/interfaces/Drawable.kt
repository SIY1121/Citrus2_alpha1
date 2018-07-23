package space.siy.citrus.model.interfaces

import com.jogamp.opengl.GL3

/**
 * 描画可能オブジェクトのインターフェース
 */
interface Drawable {
    enum class Mode {
        Preview, Seek, Final
    }

    fun draw(gl: GL3, mode: Mode, frame: Int)
}