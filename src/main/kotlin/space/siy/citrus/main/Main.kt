package space.siy.citrus.main

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.experimental.launch
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacv.FFmpegFrameGrabber
import space.siy.citrus.controllers.SplashController
import space.siy.citrus.model.project.Project
import java.io.File

class Main : Application() {

    override fun start(primaryStage: Stage) {
        val splash = Stage(StageStyle.UNDECORATED)
        splash.scene = Scene(FXMLLoader.load<Parent>(ClassLoader.getSystemResource("layout/splash.fxml")))
        splash.show()

        launch{
            SplashController.text = "OpenCVを初期化中... (1/2)"
            Loader.load(org.bytedeco.javacpp.opencv_java::class.java)
            SplashController.text = "OpenCVを初期化中... (2/2)"
            Loader.load(org.bytedeco.javacpp.opencv_core::class.java)

            SplashController.text = "FFmpegを初期化中..."
            FFmpegFrameGrabber.tryLoad()

            Platform.runLater {

                primaryStage.scene = Scene(FXMLLoader.load<Parent>(ClassLoader.getSystemResource("layout/startup.fxml")))
                primaryStage.show()

                splash.close()
            }
        }
    }

    companion object {
        lateinit var project: Project
        var initializedProject = false

    }
}