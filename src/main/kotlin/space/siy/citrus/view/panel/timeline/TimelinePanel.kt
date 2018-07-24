package space.siy.citrus.view.panel.timeline

import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import space.siy.citrus.controllers.MainController
import space.siy.citrus.main.Main
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAnchor

class TimelinePanel(mc: MainController) : MovablePane(mc) {

    val layerHeight = 30.0

    val labelScrollPane = ScrollPane()
    val labelVBox = VBox()

    val timelineWrapper = AnchorPane()
    val timelineScrollPane = ScrollPane()
    val timelineVbox = VBox()

    val hbar = ScrollBar().apply {
        orientation = Orientation.HORIZONTAL
        max = 1.0
    }

    val caret = Rectangle().apply {
        width = 2.0
        height = 100.0
        fill = Color.RED
    }

    val currentFrame = SimpleIntegerProperty()

    init {
        title = "タイムライン"

        labelScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        labelScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        labelScrollPane.content = labelVBox
        AnchorPane.setTopAnchor(labelScrollPane, 30.0)
        AnchorPane.setBottomAnchor(labelScrollPane, 0.0)
        labelScrollPane.minWidth = 80.0
        labelVBox.minWidth = 100.0
        content.children.add(labelScrollPane)

        timelineWrapper.children.add(timelineVbox)

        timelineScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        timelineScrollPane.content = timelineWrapper
        timelineScrollPane.setAnchor(30.0, 0.0, 80.0, 0.0)
        content.children.add(timelineScrollPane)

        labelScrollPane.vvalueProperty().bindBidirectional(timelineScrollPane.vvalueProperty())


        AnchorPane.setTopAnchor(hbar, 0.0)
        AnchorPane.setRightAnchor(hbar, 0.0)
        AnchorPane.setLeftAnchor(hbar, 80.0)
        content.children.add(hbar)

        timelineScrollPane.hvalueProperty().bindBidirectional(hbar.valueProperty())

        timelineWrapper.children.add(caret)

        loadScene(0)
    }

    private fun loadScene(scene: Int) {
        labelVBox.children.clear()
        timelineVbox.children.clear()

        Main.project.scenes[scene].forEachIndexed { index, layer ->
            labelVBox.children.add(Label("layer$index").apply {
                if (index % 2 == 0)
                    styleClass.add("even")
                else
                    styleClass.add("odd")
                maxWidth = Double.MAX_VALUE
                minHeight = layerHeight
            })
            timelineVbox.children.add(Pane().apply {
                if (index % 2 == 0)
                    styleClass.add("even")
                else
                    styleClass.add("odd")

                minWidth = 2000.0
                minHeight = layerHeight
            })
        }
    }
}