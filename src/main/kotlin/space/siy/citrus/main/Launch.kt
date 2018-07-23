package space.siy.citrus.main

import javafx.application.Application
import org.bytedeco.javacpp.Loader

object Launch {
    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(Main::class.java, *args)
    }
}