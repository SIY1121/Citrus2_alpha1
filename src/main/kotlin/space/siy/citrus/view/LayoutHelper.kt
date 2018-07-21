package space.siy.citrus.view

import javafx.scene.Node
import javafx.scene.control.Control
import javafx.scene.layout.Pane
import javax.swing.plaf.synth.Region

fun Control.fitToParent(parent : Pane){
    parent.widthProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
    parent.heightProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minHeight = v
        this.prefHeight = v
        this.maxHeight = v
    }
}
fun Pane.fitToParent(parent : Pane){
    parent.widthProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
    parent.heightProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minHeight = v
        this.prefHeight = v
        this.maxHeight = v
    }
}

fun Control.fitToParentW(parent : Pane){
    parent.widthProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
}
fun Pane.fitToParentW(parent : Pane){
    parent.widthProperty().addListener { _,_,n->
        val v = n.toDouble()
        this.minWidth = v
        this.prefWidth = v
        this.maxWidth = v
    }
}