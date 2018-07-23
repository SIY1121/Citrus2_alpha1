package space.siy.citrus.view.panel.media.timeline

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLJPanel
import javafx.embed.swing.SwingNode
import space.siy.citrus.controllers.MainController
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAllAnchorToZero

class TimelinePreviewPanel(mc : MainController) : MovablePane(mc) , GLEventListener {

    val swingNode = SwingNode()
    val glPanel = GLJPanel(GLCapabilities(GLProfile.get(GLProfile.GL3)))

    init{
        glPanel.addGLEventListener(this)
        swingNode.content = glPanel
        swingNode.setAllAnchorToZero()
        content.children.add(swingNode)
    }

    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun display(drawable: GLAutoDrawable) {

    }

    override fun init(drawable: GLAutoDrawable) {

    }

    override fun dispose(drawable: GLAutoDrawable) {

    }


}