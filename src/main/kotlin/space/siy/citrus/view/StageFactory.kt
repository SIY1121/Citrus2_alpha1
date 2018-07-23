package space.siy.citrus.view

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.Window

/**
 * ダイアログ生成関係
 */

fun createStage(fxml : String , style : StageStyle = StageStyle.DECORATED, owner : Window? = null) : Stage{
    val stage = Stage(style)
    stage.scene = Scene(FXMLLoader.load<Parent>(ClassLoader.getSystemResource(fxml)))
    if(owner!=null){
        stage.initModality(Modality.WINDOW_MODAL)
        stage.initOwner(owner)
    }
    return stage
}