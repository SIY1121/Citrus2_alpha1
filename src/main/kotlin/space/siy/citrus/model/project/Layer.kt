package space.siy.citrus.model.project

import com.jogamp.opengl.GL3
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import space.siy.citrus.model.interfaces.AudioSampleProvider
import space.siy.citrus.model.interfaces.Drawable
import space.siy.citrus.model.objects.CitrusObject

class Layer : SimpleListProperty<CitrusObject>(FXCollections.observableList(ArrayList<CitrusObject>())), Drawable, AudioSampleProvider {

    /**
     * 現在アクティブなオブジェクト
     */
    var activeObject: CitrusObject? = null

    /**
     * アクティブなオブジェクトを検索し、返す
     */
    private fun searchActiveObject(frame: Int): CitrusObject? {
        if (activeObject?.isActive(frame) == true) return activeObject
        forEach {
            if (it.isActive(frame)) return it
        }
        return null
    }

    override fun draw(gl: GL3, mode: Drawable.Mode, frame: Int) {
        activeObject = searchActiveObject(frame)

        if (activeObject == null) return

        val obj = activeObject

        if (obj is Drawable)
            obj.draw(gl, mode, frame)

    }

    override fun readSamples(mode: AudioSampleProvider.Mode, frame: Int): FloatArray {
        activeObject = searchActiveObject(frame)

        if (activeObject == null) FloatArray(0)

        val obj = activeObject

        return if (obj is AudioSampleProvider)
            obj.readSamples(mode, frame)
        else
            FloatArray(0)
    }

}