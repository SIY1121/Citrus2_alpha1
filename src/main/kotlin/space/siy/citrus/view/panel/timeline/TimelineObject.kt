package space.siy.citrus.view.panel.timeline

import javafx.scene.control.Label
import javafx.scene.layout.Pane
import space.siy.citrus.model.objects.CitrusObject

class TimelineObject(val tp: TimelinePanel, val cObj: CitrusObject) : Pane() {

    /**
     * マウスが押されたときのオフセット
     */
    var pressedOffset = 0.0

    val label = Label("Label")

    init {
        minHeightProperty().bind(tp.layerHeight)
        layoutXProperty().bind(tp.pixelPerFrame.multiply(cObj.start))
        minWidthProperty().bind(tp.pixelPerFrame.multiply(cObj.end.subtract(cObj.start)))
        style = "-fx-background-color:red"

        label.minHeightProperty().bind(tp.layerHeight)
        label.textProperty().bind(cObj.displayName)
        children.add(label)

        //マウスが押されたときに親の選択オブジェクトリストに追加
        setOnMousePressed {
            pressedOffset = it.x
            tp.selectedObjects.add(this)
        }
        //編集モードを設定
        setOnMouseMoved {
            when {
                it.x < 10 -> tp.editMode = TimelinePanel.EditMode.Decrement
                it.x > width - 10 -> tp.editMode = TimelinePanel.EditMode.Increment
                else -> tp.editMode = TimelinePanel.EditMode.Move
            }
            it.consume()
        }

    }
}