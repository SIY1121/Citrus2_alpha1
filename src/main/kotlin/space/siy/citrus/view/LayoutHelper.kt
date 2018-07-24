package space.siy.citrus.view

import javafx.scene.Node
import javafx.scene.control.Control
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane

fun Control.fitToParent(parent: Pane) {
    parent.widthProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
    parent.heightProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minHeight = v
        this.prefHeight = v
        this.maxHeight = v
    }
}

fun Pane.fitToParent(parent: Pane) {
    parent.widthProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
    parent.heightProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minHeight = v
        this.prefHeight = v
        this.maxHeight = v
    }
}

fun Control.fitToParentW(parent: Pane) {
    parent.widthProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
}

fun Pane.fitToParentW(parent: Pane) {
    parent.widthProperty().addListener { _, _, n ->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
}

fun Node.setAllAnchorToZero() {
    AnchorPane.setTopAnchor(this, 0.0)
    AnchorPane.setBottomAnchor(this, 0.0)
    AnchorPane.setLeftAnchor(this, 0.0)
    AnchorPane.setRightAnchor(this, 0.0)
}

fun Node.setAnchor(top: Double, bottom: Double, left: Double, right: Double) {
    if (top != Double.NaN)
        AnchorPane.setTopAnchor(this, top)
    if (bottom != Double.NaN)
        AnchorPane.setBottomAnchor(this, bottom)
    if (left != Double.NaN)
        AnchorPane.setLeftAnchor(this, left)
    if (right != Double.NaN)
        AnchorPane.setRightAnchor(this, right)
}
