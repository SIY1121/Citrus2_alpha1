package space.siy.citrus.view.panel.project

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
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
    var icon = MaterialDesignIconFactory.get().createIcon(
            if (file.isDirectory)
                MaterialDesignIcon.FOLDER_OUTLINE
            else
                MaterialDesignIcon.FILE, "50").apply {
        VBox.setVgrow(this, Priority.ALWAYS)
    }
    val thumb = ImageView().apply {
        isVisible = false
        isPreserveRatio = true
        fitWidth = 100.0
        fitHeight = 100.0
    }
    val label = Label(file.name).apply {
        font = Font.font(16.0)
        isWrapText = true
    }

    init {
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


    }


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
                println("audio")
            }

            grabber.stop()


        } catch (ex: FrameGrabber.Exception) {

        }
    }

    private fun genThumb() = launch {
        try {
            val grabber = FFmpegFrameGrabber(file)
            grabber.pixelFormat = avutil.AV_PIX_FMT_RGB24
            grabber.imageWidth = grabber.imageWidth / 100
            grabber.imageHeight = grabber.imageHeight / 100
            grabber.start()
            val frame = grabber.grabKeyFrame() ?: return@launch

            val mat = Mat(frame.imageHeight, frame.imageWidth, CvType.CV_8UC3, frame.image[0] as ByteBuffer)
            val small = Mat(100, ((100.0 / frame.imageHeight) * frame.imageWidth).toInt(), CvType.CV_8UC3)
            Imgproc.resize(mat, small, Size(small.width().toDouble(), small.height().toDouble()))


            val img = WritableImage(small.width(), small.height())
            println(small.width())
            val buf = ByteArray(small.width() * small.height() * 3)
            small.get(0, 0, buf)
            img.pixelWriter.setPixels(0, 0, small.width(), small.height(), PixelFormat.getByteRgbInstance(), buf, 0, small.width() * 3)

            Platform.runLater {
                thumb.image = img
                icon.isVisible = false
                thumb.isVisible = true
            }

            grabber.stop()


        } catch (ex: FrameGrabber.Exception) {
            println("not media file")
        }
    }
}