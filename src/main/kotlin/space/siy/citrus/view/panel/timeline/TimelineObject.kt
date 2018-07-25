package space.siy.citrus.view.panel.timeline

import javafx.scene.layout.Pane
import space.siy.citrus.model.objects.CitrusObject

class TimelineObject(val tp: TimelinePanel, val cObj: CitrusObject) : Pane() {
    init {
        minHeightProperty().bind(tp.layerHeight)
        layoutXProperty().bind(tp.pixelPerFrame.multiply(cObj.start))
        minWidthProperty().bind(tp.pixelPerFrame.multiply(cObj.end.subtract(cObj.start)))
        style = "-fx-background-color:red"

        setOnMousePressed {
            tp.selectedObjects.add(this)
        }
        setOnMouseMoved {
            when{
                it.x < 10 -> tp.editMode = TimelinePanel.EditMode.Decrement
                it.x > width - 10 -> tp.editMode = TimelinePanel.EditMode.Increment
                else -> tp.editMode = TimelinePanel.EditMode.Move
            }
            it.consume()
        }

    }
}