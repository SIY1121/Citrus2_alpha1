package space.siy.citrus.view.panel.timeline

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.ListChangeListener
import javafx.geometry.Orientation
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.control.Slider
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.input.TransferMode
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import space.siy.citrus.controllers.MainController
import space.siy.citrus.main.Main
import space.siy.citrus.model.objects.CitrusObject
import space.siy.citrus.model.objects.Image
import space.siy.citrus.view.panel.MovablePane
import space.siy.citrus.view.setAnchor

/**
 * タイムラインコントロール本体
 */
class TimelinePanel(mc: MainController) : MovablePane(mc) {

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

    val slider = Slider(0.05, 1.0, 1.0).apply {

    }

    val currentFrame = SimpleIntegerProperty()

    val pixelPerFrame = SimpleDoubleProperty().apply {
        value = 1.0
    }

    val layerHeight = SimpleDoubleProperty().apply {
        value = 30.0
    }

    val layerPanes: List<Pane>
        get() = timelineVbox.children.map { it as Pane }

    val selectedObjects = ArrayList<TimelineObject>()

    enum class EditMode {
        None, Increment, Decrement, Move
    }

    var editMode = EditMode.None
        set(value) {
            field = value
            timelineWrapper.cursor = when (field) {
                EditMode.Increment -> Cursor.E_RESIZE
                EditMode.Decrement -> Cursor.E_RESIZE
                EditMode.Move -> Cursor.MOVE
                else -> Cursor.DEFAULT
            }
        }


    init {
        setupLayout()
        setupObjectMove()

        loadScene(0)
    }

    private fun setupLayout() {
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

        caret.xProperty().bind(currentFrame.multiply(pixelPerFrame))//currentFrameにバインド
        timelineWrapper.children.add(caret)

        pixelPerFrame.bindBidirectional(slider.valueProperty())
        slider.maxWidth = 80.0
        AnchorPane.setTopAnchor(slider, 0.0)
        content.children.add(slider)
    }

    private fun setupObjectMove() {
        timelineWrapper.setOnMouseDragged { event ->
            if (selectedObjects.isEmpty())
                currentFrame.value = (event.x / pixelPerFrame.value.toDouble()).toInt()
            else {
                selectedObjects.forEach {
                    when (editMode) {
                        EditMode.Decrement -> it.cObj.start.value = (event.x / pixelPerFrame.value.toDouble()).toInt()
                        EditMode.Move -> {
                            val length = it.cObj.end.value.toInt() - it.cObj.start.value.toInt()
                            it.cObj.start.value = ((event.x - it.pressedOffset) / pixelPerFrame.value.toDouble()).toInt()
                            it.cObj.end.value = ((event.x - it.pressedOffset) / pixelPerFrame.value.toDouble()).toInt() + length

                            if ((event.y / layerHeight.value.toDouble()).toInt() != it.cObj.layer.value.toInt()) {
                                it.cObj.layer.value = (event.y / layerHeight.value.toDouble()).toInt()
                            }
                        }
                        EditMode.Increment -> it.cObj.end.value = (event.x / pixelPerFrame.value.toDouble()).toInt()
                    }

                }
            }
        }
        timelineWrapper.setOnMouseReleased {
            if (selectedObjects.isEmpty())
                currentFrame.value = (it.x / pixelPerFrame.value.toDouble()).toInt()
            else
                selectedObjects.clear()
        }
        timelineWrapper.setOnMouseMoved {
            editMode = EditMode.None
        }
        timelineWrapper.setOnKeyPressed {
            if (it.code == KeyCode.ESCAPE) {
                selectedObjects.forEach { it.cObj.remove() }
            }
        }
        timelineScrollPane.setOnKeyPressed {
            println(it)
        }

    }

    private fun loadScene(scene: Int) {
        labelVBox.children.clear()
        timelineVbox.children.clear()//TODO クリア時にリスナの解除

        Main.project.scenes[scene].forEachIndexed { index, layer ->
            labelVBox.children.add(genLayerLabel(scene, index))
            timelineVbox.children.add(genLayerPane(scene, index))
        }
    }

    private fun addObjectToTimeline(cObj: CitrusObject) {
        layerPanes[cObj.layer.value.toInt()].children.add(TimelineObject(this, cObj))
    }

    private fun genLayerLabel(scene: Int, layer: Int): Label = Label("layer$layer").apply {
        if (layer % 2 == 0)
            styleClass.add("even")
        else
            styleClass.add("odd")
        maxWidth = Double.MAX_VALUE
        minHeightProperty().bind(layerHeight)
    }

    private fun genLayerPane(scene: Int, layer: Int): Pane = Pane().apply {
        if (layer % 2 == 0)
            styleClass.add("even")
        else
            styleClass.add("odd")

        minWidth = 2000.0
        minHeightProperty().bind(layerHeight)

        //自分が担当するレイヤーの変更を監視
        Main.project.scenes[scene][layer].addListener { c: ListChangeListener.Change<out CitrusObject> ->
            c.next()
            when {
            //オブジェクトの追加に合わせてTimelineObjectを生成
                c.wasAdded() -> {
                    c.addedSubList.forEach {
                        children.add(TimelineObject(this@TimelinePanel, it))
                    }
                }
            //オブジェクトの削除に合わせてTimelineObjectも破棄
                c.wasRemoved() -> {
                    c.removed.forEach { rObj ->
                        val removeObj = children.first { (it as TimelineObject).cObj == rObj }
                        children.remove(removeObj)
                    }
                }
            }

        }


        setOnDragOver {
            if (it.dragboard.hasFiles()) {
                it.acceptTransferModes(TransferMode.COPY)
            }
            it.consume()
        }

        setOnDragDropped {
            it.consume()
        }

        setOnMouseClicked {
            if (it.button == MouseButton.PRIMARY) {

            } else {
                Image(scene, layer).apply {
                    start.value = 100
                    end.value = 200
                }
            }
        }
    }


}