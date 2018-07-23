package space.siy.citrus.view.panel.media.source

import com.jogamp.opengl.*
import com.jogamp.opengl.awt.GLJPanel
import javafx.embed.swing.SwingNode
import javafx.scene.layout.HBox
import org.bytedeco.javacv.FFmpegFrameGrabber
import space.siy.citrus.controllers.MainController
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAnchor

/**
 * プロジェクトパネルで選択したファイルをプレビューするパネル
 */
class SourcePreviewPanel(mc: MainController) : MovablePane(mc), GLEventListener {

    val swingNode = SwingNode()
    val previewGLPanel = GLJPanel(GLCapabilities(GLProfile.get(GLProfile.GL3)))

    val playerControl = HBox()

    var grabber: FFmpegFrameGrabber? = null

    init {
        title = "ソースのプレビュー"

        //GLのセットアップ
        previewGLPanel.addGLEventListener(this)
        swingNode.content = previewGLPanel
        swingNode.setAnchor(0.0, 30.0, 0.0, 0.0)
        content.children.add(swingNode)


        mc.projectPanel?.onItemSelected = {

        }
    }

    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun display(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL3
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT)
    }

    override fun init(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL3
        gl.glClearColor(1f, 0f, 0f, 0f)

    }

    override fun dispose(drawable: GLAutoDrawable) {

    }
}