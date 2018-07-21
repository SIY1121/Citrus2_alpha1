package space.siy.citrus.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Control
import javafx.scene.control.Label
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import space.siy.citrus.view.fitToParent
import java.net.URL
import java.util.*

class MainController : Initializable {
    @FXML
    lateinit var root: AnchorPane
    @FXML
    lateinit var verticalSplitPane: SplitPane
    @FXML
    lateinit var topHorizontalSplitPane: SplitPane
    @FXML
    lateinit var bottomHorizontalSplitPane: SplitPane

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

}