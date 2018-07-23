package space.siy.citrus.view.panel.media.timeline

import com.jogamp.opengl.*
import com.jogamp.opengl.awt.GLJPanel
import javafx.embed.swing.SwingNode
import space.siy.citrus.controllers.MainController
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAllAnchorToZero

/**
 * タイムラインのプレビューを行う
 */
class TimelinePreviewPanel(mc: MainController) : MovablePane(mc), GLEventListener {

    val swingNode = SwingNode()
    val glPanel = GLJPanel(GLCapabilities(GLProfile.get(GLProfile.GL3)))

    init {
        title = "プレビュー"
        glPanel.addGLEventListener(this)
        swingNode.content = glPanel
        swingNode.setAllAnchorToZero()
        content.children.add(swingNode)
    }

    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun display(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL3
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT)
    }

    override fun init(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL3
        gl.glClearColor(0f, 1f, 0f, 1f)
    }

    override fun dispose(drawable: GLAutoDrawable) {

    }


}