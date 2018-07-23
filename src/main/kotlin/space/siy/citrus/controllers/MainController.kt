package space.siy.citrus.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Control
import javafx.scene.control.Label
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import space.siy.citrus.view.fitToParent
import space.siy.citrus.view.panel.AcceptablePane
import space.siy.citrus.view.panel.TimelinePane
import space.siy.citrus.view.panel.project.ProjectPane
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

        Platform.runLater{
            (bottomHorizontalSplitPane.items[0] as AcceptablePane).children.add(ProjectPane())
            (bottomHorizontalSplitPane.items[1] as AcceptablePane).children.add(TimelinePane())
        }

    }

}