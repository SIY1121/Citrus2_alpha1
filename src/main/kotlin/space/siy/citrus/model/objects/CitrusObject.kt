package space.siy.citrus.model.objects

import javafx.beans.property.SimpleIntegerProperty
import space.siy.citrus.main.Main

open class CitrusObject(defScene: Int, defLayer: Int) {

    /**メンバ**/

    /**
     * このオブジェクトが属するシーン
     */
    var scene = SimpleIntegerProperty().apply {
        value = -1//TODO タイミング要検証
    }

    /**
     * このオブジェクトが属するレイヤー
     */
    var layer = SimpleIntegerProperty().apply {
        value = -1
    }

    /**
     * このオブジェクトの開始位置
     */
    var start = SimpleIntegerProperty()

    /**
     * このオブジェクトの終了位置
     */
    var end = SimpleIntegerProperty().apply {
        value = 100
    }


    /**メンバ終了**/

    /**
     * オブジェクトを既存の位置から削除
     */
    private fun removeFrom(scene: Int, layer: Int) {
        if (scene < 0 || layer < 0) return
        Main.project.scenes[scene][layer].remove(this)
    }

    /**
     * オブジェクトを指定された位置に追加
     */
    private fun addTo(scene: Int, layer: Int) {
        if (scene < 0 || layer < 0) return
        Main.project.scenes[scene][layer].add(this)
    }

    /**
     * オブジェクトが指定のフレームでアクティブかどうか判定
     */
    fun isActive(frame : Int) = (frame in start.value.toInt() until end.value.toInt())

    init {

        /**
         * データのバインディングを設定
         */
        scene.addListener { _, old, new ->
            removeFrom(old.toInt(), layer.value.toInt())
            addTo(new.toInt(), layer.value.toInt())
        }
        layer.addListener { _, old, new ->
            removeFrom(scene.value.toInt(), old.toInt())
            addTo(scene.value.toInt(), new.toInt())
        }


        scene.value = defScene
        layer.value = defLayer
    }
}