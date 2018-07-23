package space.siy.citrus.view.panel.project

import javafx.scene.control.ScrollPane
import javafx.scene.layout.FlowPane
import space.siy.citrus.main.Main
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.fitToParentW
import space.siy.citrus.view.setAllAnchorToZero


class ProjectPane : MovablePane() {
    val scrollPane = ScrollPane().apply {
        setAllAnchorToZero()
        setOnMouseClicked { println(it) }
    }

    val flowPane = FlowPane().apply{
        //background = Background(BackgroundFill(Color(0.0,1.0,0.0,0.3),CornerRadii(0.0), Insets(0.0)))
    }

    init {
        title = "プロジェクト"
        content.children.add(scrollPane)
        scrollPane.content = flowPane
        flowPane.fitToParentW(content)
        Main.project.root.listFiles().forEach {
            flowPane.children.add(FileItem(it))
        }
    }
}