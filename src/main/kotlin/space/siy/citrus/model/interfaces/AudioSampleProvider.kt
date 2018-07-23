package space.siy.citrus.model.interfaces

/**
 * オーディオ・サンプルを読み出せるインターフェース
 */
interface AudioSampleProvider {
    enum class Mode {
        Preview, Seek, Final
    }

    fun readSamples(mode: Mode, frame: Int): FloatArray
}