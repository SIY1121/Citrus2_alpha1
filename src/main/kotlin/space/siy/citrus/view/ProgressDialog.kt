package space.siy.citrus.view

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.stage.Modality
import javafx.stage.Stage

class ProgressDialog : Stage() {
    private val messageLabel: Label
    private val progressBar: ProgressBar

    var message: String
        set(value) {
            messageLabel.text = value
        }
        get() = messageLabel.text

    var progress: Double
        set(value) {
            progressBar.progress = value
        }
        get() = progressBar.progress

    init {
        initModality(Modality.APPLICATION_MODAL)
        isAlwaysOnTop = true
        scene = Scene(FXMLLoader.load<Parent>(ClassLoader.getSystemResource("layout/progressDialog.fxml")))
        messageLabel = scene.lookup("#messageLabel") as Label
        progressBar = scene.lookup("#progressBar") as ProgressBar

    }
}