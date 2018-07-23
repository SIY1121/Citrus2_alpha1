package space.siy.citrus.controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import space.siy.citrus.main.Main
import space.siy.citrus.model.project.Project

import java.io.File
import java.net.URL
import java.util.*

class NewProjectController : Initializable {

    @FXML
    lateinit var root: AnchorPane

    @FXML
    lateinit var projectLocationTextField: TextField

    @FXML
    lateinit var WidthSpinner: Spinner<Int>

    @FXML
    lateinit var HeightSpinner: Spinner<Int>

    @FXML
    lateinit var FPSSpinner: Spinner<Int>

    @FXML
    lateinit var SampleRateSpinner: Spinner<Int>


    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

    fun onSubmit(actionEvent: ActionEvent) {
        Main.project = Project(File(projectLocationTextField.text))
        Main.project.width = WidthSpinner.value
        Main.project.height = HeightSpinner.value
        Main.project.fps = FPSSpinner.value
        Main.project.sampleRate = SampleRateSpinner.value
        Main.initializedProject = true
        (root.scene.window as Stage).close()
    }
}