package space.siy.citrus.view

import javafx.geometry.Insets
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font

abstract class MovablePane : Pane() {

    val vBox = VBox()

    var title: String
        get() = titleLabel.text
        set(value) {
            titleLabel.text = value
        }

    val titleLabel = Label("Title").apply{
        font = Font.font(20.0)
        background = Background(BackgroundFill(Color(0.1,0.1,0.1,1.0), CornerRadii(0.0),Insets(0.0)))
    }

    val overlayPane = Pane().apply {
        background = Background(BackgroundFill(Color(0.0,0.0,1.0,0.3),CornerRadii(0.0), Insets(0.0)))
        isVisible = false
    }

    val content = Pane()

    init {
        children.add(vBox)
        children.add(overlayPane)
        overlayPane.fitToParent(this)
        vBox.children.add(titleLabel)
        vBox.children.add(content)
        VBox.setVgrow(content,Priority.ALWAYS)

        AnchorPane.setTopAnchor(this,0.0)
        AnchorPane.setBottomAnchor(this,0.0)
        AnchorPane.setLeftAnchor(this,0.0)
        AnchorPane.setRightAnchor(this,0.0)

        titleLabel.fitToParentW(this)

        titleLabel.setOnDragDetected {
            println("start drag")
            startFullDrag()
            overlayPane.isVisible = true
            cursor = Cursor.CLOSED_HAND
            it.consume()
        }
        titleLabel.setOnMousePressed {
            isMouseTransparent = true
            it.consume()
        }
        titleLabel.setOnMouseReleased {
            isMouseTransparent = false
            overlayPane.isVisible = false
            cursor = Cursor.DEFAULT
            println("released")
            it.consume()
        }

    }
}