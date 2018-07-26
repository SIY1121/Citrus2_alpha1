package space.siy.citrus.view.panel.project

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import javafx.scene.input.ClipboardContent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import kotlinx.coroutines.experimental.launch
import org.bytedeco.javacpp.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameGrabber
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.File
import java.nio.ByteBuffer

/**
 * エクスプローラーのアイテム
 */
class FileItem(val file: File) : VBox() {

    val stackPane = StackPane()

    /**
     * サムネイル非表示時のアイコン
     */
    var icon = MaterialDesignIconFactory.get().createIcon(
            if (file.isDirectory)
                MaterialDesignIcon.FOLDER_OUTLINE
            else
                MaterialDesignIcon.FILE, "50").apply {
        VBox.setVgrow(this, Priority.ALWAYS)
    }
    /**
     * サムネイル
     */
    val thumb = ImageView().apply {
        isVisible = false
        isPreserveRatio = true
        fitWidth = 100.0
        fitHeight = 100.0
    }
    /**
     * ファイル名表示用
     */
    val label = Label(file.name).apply {
        font = Font.font(16.0)
        isWrapText = true
    }

    init {
        isFocusTraversable = true//フォーカス可能にする
        alignment = Pos.CENTER
        minWidth = 120.0
        maxWidth = 120.0
        minHeight = 120.0
        children.add(stackPane)
        children.add(label)

        launch{
            analyzeFile()

            Platform.runLater {
                stackPane.children.add(icon)
                stackPane.children.add(thumb)
            }
        }

        addEventHandler(MouseEvent.MOUSE_CLICKED){
            requestFocus()
        }

        setOnDragDetected {
            println("drag detected")
            val db = startDragAndDrop(TransferMode.COPY)
            db.setContent(ClipboardContent().apply {
                putFiles(arrayListOf(file))
            })
            it.consume()
        }

    }

    /**
     * 担当ファイルを解析し、表示形態を決定する
     */
    private fun analyzeFile() {
        try {
            val grabber = FFmpegFrameGrabber(file)
            grabber.pixelFormat = avutil.AV_PIX_FMT_RGB24
            grabber.start()

            //画像データが含まれていたらサムネイル表示
            if (grabber.hasVideo()) {
                val frame = grabber.grabKeyFrame()

                val originalMat = Mat(frame.imageHeight, frame.imageWidth, CvType.CV_8UC3, frame.image[0] as ByteBuffer)
                val smallMat = Mat(Size(100.0, (100.0 / frame.imageWidth) * frame.imageHeight), CvType.CV_8UC3)

                Imgproc.resize(originalMat, smallMat, smallMat.size())

                val data = ByteArray(smallMat.width() * smallMat.height() * 3)
                smallMat.get(0, 0, data)

                val img = WritableImage(smallMat.width(), smallMat.height())
                img.pixelWriter.setPixels(0, 0, smallMat.width(), smallMat.height(), PixelFormat.getByteRgbInstance(), data, 0, smallMat.width() * 3)

                Platform.runLater {
                    thumb.image = img
                    icon.isVisible = false
                    thumb.isVisible = true
                }

            } else if (grabber.hasAudio()) {//オーディオのみの場合、音声アイコンに変更
                icon = MaterialDesignIconFactory.get().createIcon(
                        MaterialDesignIcon.FILE_MUSIC,"50").apply {
                    VBox.setVgrow(this, Priority.ALWAYS)
                }
            }

            grabber.stop()


        } catch (ex: FrameGrabber.Exception) {
            //not media file
        }
    }
}