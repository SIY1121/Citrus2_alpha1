package space.siy.citrus.view.panel

import javafx.geometry.Insets
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import space.siy.citrus.controllers.MainController
import space.siy.citrus.view.setAllAnchorToZero

/**
 * AcceptablePane間を移動可能なペイン
 */
abstract class MovablePane(val mainController : MainController) : AnchorPane() {

    /**
     * タイトルラベルとコンテンツを縦に並べるペイン
     */
    val vBox = VBox()

    /**
     * タイトル
     */
    var title: String
        get() = titleLabel.text
        set(value) {
            titleLabel.text = value
        }

    /**
     * タイトル表示用ラベル
     */
    val titleLabel = Label("Title").apply {
        font = Font.font(16.0)
        maxWidth = Double.POSITIVE_INFINITY//ラベルを最大幅にする
        background = Background(BackgroundFill(Color(0.1, 0.1, 0.1, 1.0), CornerRadii(0.0), Insets(0.0)))
    }

    /**
     * ドラッグ時にペイン全体の色を変化させるペイン
     */
    val overlayPane = Pane().apply {
        background = Background(BackgroundFill(Color(0.0, 0.0, 1.0, 0.3), CornerRadii(0.0), Insets(0.0)))
        isVisible = false
    }

    /**
     * コンテンツ
     */
    val content = AnchorPane().apply {
        //background = Background(BackgroundFill(Color(1.0, 0.0, 0.0, 0.3), CornerRadii(0.0), Insets(0.0)))
    }

    init {
        //親ペインに張り付く
        this.setAllAnchorToZero()

        children.add(vBox)
        vBox.setAllAnchorToZero()//VBoxを全画面に

        vBox.children.add(titleLabel)
        vBox.children.add(content)
        VBox.setVgrow(content,Priority.ALWAYS)

        children.add(overlayPane)
        overlayPane.setAllAnchorToZero()

        //ドラッグ関係の処理
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