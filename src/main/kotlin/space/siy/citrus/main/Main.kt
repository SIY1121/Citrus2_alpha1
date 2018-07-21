package space.siy.citrus.main

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import space.siy.citrus.model.project.Project
import java.io.File

class Main : Application() {
    lateinit var project: Project

    override fun start(primaryStage: Stage) {
        project = Project(File("./"))
        primaryStage.scene = Scene(FXMLLoader.load<Parent>(ClassLoader.getSystemResource("layout/main.fxml")))
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java, *args)
        }
    }
}