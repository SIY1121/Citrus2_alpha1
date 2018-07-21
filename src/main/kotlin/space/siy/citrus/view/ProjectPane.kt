package space.siy.citrus.view

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import java.io.File
import javafx.collections.FXCollections
import javafx.collections.ObservableList


class ProjectPane : MovablePane() {
    val treeView = TreeView<File>()

    init {
        title = "プロジェクト"
        content.children.add(treeView)
        treeView.fitToParent(content)
        treeView.root = FileNode(File("/"))
    }

    class FileNode(f: File) : TreeItem<File>(f) {
        var firstGenerate = true

        override fun getChildren(): ObservableList<TreeItem<File>> {

            if(firstGenerate){
                firstGenerate = false
                super.getChildren().setAll(value.listFiles().filter { it.isDirectory }.map { FileNode(it) })

            }

            return super.getChildren()
        }

        override fun isLeaf(): Boolean {
            return !(value.listFiles()?.any { it.isDirectory } ?: false)//1つでもディレクトリが含まれていたら葉ではない
        }
    }
}