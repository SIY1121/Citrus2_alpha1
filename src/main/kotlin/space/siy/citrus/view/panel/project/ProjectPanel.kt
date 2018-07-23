package space.siy.citrus.view.panel.project

import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import space.siy.citrus.controllers.MainController
import space.siy.citrus.main.Main
import space.siy.citrus.view.fitToParentW
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAllAnchorToZero
import java.io.File


class ProjectPanel(mc: MainController) : MovablePane(mc) {

    var onItemSelected: ((it: File) -> Unit)? = null

    val scrollPane = ScrollPane().apply {
        setAllAnchorToZero()
        setOnMouseClicked { println(it) }
    }

    val flowPane = FlowPane().apply {
        //background = Background(BackgroundFill(Color(0.0,1.0,0.0,0.3),CornerRadii(0.0), Insets(0.0)))
    }

    init {
        title = "プロジェクト"
        content.children.add(scrollPane)
        scrollPane.content = flowPane
        flowPane.fitToParentW(content)
        loadDir(Main.project.root)
    }

    private fun loadDir(dir: File) {
        flowPane.children.clear()

        flowPane.children.add(FileItem(dir.parentFile).apply {
            setOnMouseClicked {
                if (it.clickCount == 2 && this.file.isDirectory) {
                    loadDir(this.file)
                }
            }
        })

        dir.listFiles().forEach {
            flowPane.children.add(FileItem(it).apply {
                addEventHandler(MouseEvent.MOUSE_CLICKED) {
                    if (it.button != MouseButton.PRIMARY) return@addEventHandler

                    if (it.clickCount == 2 && this.file.isDirectory) {
                        loadDir(this.file)
                    } else {
                        onItemSelected?.invoke(this.file)
                    }
                }
            })
        }
    }
}