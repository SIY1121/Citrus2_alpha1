package space.siy.citrus.model.project

import java.io.File

class Project(val root : File) {
    var width = 1920
    var height = 1080

    var fps = 30

    var sampleRate = 48000
    var audioChannels = 2

    var scenes = ArrayList<Scene>()
}