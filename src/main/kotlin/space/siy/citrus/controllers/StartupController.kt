package space.siy.citrus.controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.Stage
import space.siy.citrus.main.Main
import space.siy.citrus.view.createStage

class StartupController {
    @FXML
    lateinit var root : AnchorPane

    fun onNewProject(actionEvent: ActionEvent) {
        val stage = createStage("layout/newProject.fxml",owner = root.scene.window)
        stage.showAndWait()
        if(Main.initializedProject){
            createStage("layout/main.fxml").show()
            (root.scene.window as Stage).close()
        }
    }

    fun onLoadProject(actionEvent: ActionEvent) {

    }
}