package space.siy.citrus.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ProgressBar
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane
import space.siy.citrus.view.ProgressDialog
import space.siy.citrus.view.panel.AcceptablePane
import space.siy.citrus.view.panel.media.source.SourcePreviewPanel
import space.siy.citrus.view.panel.media.timeline.TimelinePreviewPanel
import space.siy.citrus.view.panel.project.ProjectPanel
import space.siy.citrus.view.panel.timeline.TimelinePanel
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

    var projectPanel: ProjectPanel? = null
    var sourcePreviewPanel: SourcePreviewPanel? = null
    var timelinePanel: TimelinePanel? = null
    var timelinePreviewPanel : TimelinePreviewPanel? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        val dialog = ProgressDialog()
        dialog.message = "プロジェクトを読み込み中..."
        dialog.progress = ProgressBar.INDETERMINATE_PROGRESS
        dialog.show()

        Platform.runLater {
            //パネルの読み込み
            projectPanel = ProjectPanel(this)
            timelinePanel = TimelinePanel(this)
            dialog.message = "OpenGLを初期化中..."
            sourcePreviewPanel = SourcePreviewPanel(this)
            timelinePreviewPanel = TimelinePreviewPanel(this)


            (topHorizontalSplitPane.items[0] as AcceptablePane).children.add(sourcePreviewPanel)
            (topHorizontalSplitPane.items[1] as AcceptablePane).children.add(timelinePreviewPanel)
            (bottomHorizontalSplitPane.items[0] as AcceptablePane).children.add(projectPanel)
            (bottomHorizontalSplitPane.items[1] as AcceptablePane).children.add(timelinePanel)

            dialog.close()
        }

    }

}