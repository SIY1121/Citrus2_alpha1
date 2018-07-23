package space.siy.citrus.view.panel.timeline

import space.siy.citrus.controllers.MainController
import space.siy.citrus.view.panel.MovablePane

class TimelinePanel(mc : MainController) : MovablePane(mc) {

    init{
        title = "タイムライン"
    }
}