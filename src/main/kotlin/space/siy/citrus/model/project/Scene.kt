package space.siy.citrus.model.project

import com.jogamp.opengl.GL3
import space.siy.citrus.main.Main
import space.siy.citrus.model.interfaces.AudioSampleProvider
import space.siy.citrus.model.interfaces.Drawable

class Scene : ArrayList<Layer>(), Drawable, AudioSampleProvider {

    override fun draw(gl: GL3, mode: Drawable.Mode, frame: Int) {
        forEach {
            it.draw(gl, mode, frame)
        }
    }

    var oldFrame = 0

    override fun readSamples(mode: AudioSampleProvider.Mode, frame: Int): FloatArray {
        val res = FloatArray(
                if (mode == AudioSampleProvider.Mode.Seek)
                    1 * Main.project.sampleRate * Main.project.audioChannels
                else
                    (frame - oldFrame) * Main.project.sampleRate * Main.project.audioChannels
        )
        forEach {
            val samples = it.readSamples(mode, frame)
            samples.forEachIndexed { index, value ->
                res[index] += value
            }
        }

        return res
    }

}