package space.siy.citrus.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import java.net.URL
import java.util.*

class SplashController : Initializable {
    @FXML
    lateinit var progreeBar: ProgressBar
    @FXML
    lateinit var label: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
    }

    companion object {
        private lateinit var instance: SplashController
        var text: String
            get() = instance.label.text
            set(value) {
                Platform.runLater { instance.label.text = value }
            }

        var progress: Double
            get() = instance.progreeBar.progress
            set(value) {
                Platform.runLater { instance.progreeBar.progress = value }
            }
    }
}