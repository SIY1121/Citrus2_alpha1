package space.siy.citrus.view.panel

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.input.MouseDragEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

class AcceptablePane : AnchorPane() {
    init {
        addEventHandler(MouseDragEvent.ANY) {
            println(it.eventType)
            when {
                it.eventType == MouseDragEvent.MOUSE_DRAG_RELEASED -> Platform.runLater {
                    if(children.size==1)
                        ((it.gestureSource as Node).parent as AcceptablePane).children.add(children[0])

                    children.add(it.gestureSource as Node)
                }
                it.eventType == MouseDragEvent.MOUSE_DRAG_ENTERED -> background = Background(BackgroundFill(Color.GRAY, CornerRadii(0.0), Insets(0.0)))
                it.eventType == MouseDragEvent.MOUSE_DRAG_EXITED -> background = Background(BackgroundFill(Color.TRANSPARENT, CornerRadii(0.0), Insets(0.0)))
            }
        }
    }
}