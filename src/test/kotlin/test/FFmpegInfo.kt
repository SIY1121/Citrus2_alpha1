package test

import org.bytedeco.javacpp.avcodec
import org.bytedeco.javacpp.avformat

object FFmpegInfo {
    @JvmStatic
    fun main(args: Array<String>) {
//        avcodec.avcodec_register_all()
//        var codec = avcodec.av_codec_next(null)
//        while (codec!=null)
//        {
//            println(codec.long_name().getString("ASCII") + " - " + codec.name().getString("ASCII") + " - " + codec.id() + "/"+codec.type())
//            codec = avcodec.av_codec_next(codec)
//        }

        avformat.av_register_all()
        println("--format--")
        var format = avformat.av_oformat_next(null)
        while (format!=null){
            try{
                println(format.long_name().getString("ASCII") + " " + format.mime_type().getString("ASCII"))
                if(format.mime_type().getString("ASCII").contains("image/")){
                    format.extensions().getString("ASCII").split(",").forEach { print("\"$it\",") }
                }
            }catch (ex : Exception){
                //ex.printStackTrace()
            }

            format = avformat.av_oformat_next(format)
        }
    }
}