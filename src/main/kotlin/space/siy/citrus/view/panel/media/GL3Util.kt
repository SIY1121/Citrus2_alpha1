package space.siy.citrus.view.panel.media

import com.jogamp.opengl.GL3
import com.jogamp.opengl.util.texture.TextureData
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.IntBuffer

fun GL3.UGenTexture(): Int {
    val buf = IntBuffer.allocate(1)
    this.glGenTextures(1, buf)
    return buf.get()
}

fun GL3.USetupTexture(texID: Int, w: Int, h: Int, texData: TextureData? = null, wrapMode: Int = GL3.GL_CLAMP_TO_EDGE, filterMode: Int = GL3.GL_NEAREST) {
    this.glBindTexture(GL3.GL_TEXTURE_2D, texID)
    this.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrapMode)
    this.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrapMode)

    this.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, filterMode)
    this.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, filterMode)

    if (texData == null)
        this.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGBA, w, h, 0, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, ByteBuffer.allocate(w * h * 4))
    else
        this.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGB, w, h, 0, GL3.GL_BGR, GL3.GL_UNSIGNED_BYTE, texData.buffer)


    this.glBindTexture(GL3.GL_TEXTURE_2D, 0)
}

fun GL3.UGenBuffer(): Int {
    val buf = IntBuffer.allocate(1)
    this.glGenBuffers(1, buf)
    return buf.get()
}

fun GL3.UGenVertexArray(): Int {
    val buf = IntBuffer.allocate(1)
    this.glGenVertexArrays(1, buf)
    return buf.get()
}

fun GL3.UCreateVertexShader(source: String): Int {
    val id = this.glCreateShader(GL3.GL_VERTEX_SHADER)
    this.glShaderSource(id, 1, kotlin.arrayOf(source), null)
    this.glCompileShader(id)
    return id
}

fun GL3.UCreateFragmentShader(source: String): Int {
    val id = this.glCreateShader(GL3.GL_FRAGMENT_SHADER)
    this.glShaderSource(id, 1, kotlin.arrayOf(source), null)
    this.glCompileShader(id)
    return id
}

fun GL3.UCreateProgram(vtxId: Int, fId: Int, destroyShader: Boolean = true): Int {
    val id = this.glCreateProgram()

    this.glAttachShader(id, vtxId)
    this.glAttachShader(id, fId)
    this.glLinkProgram(id)
    if (destroyShader) {
        this.glDeleteShader(vtxId)
        this.glDeleteShader(fId)
    }


    return id
}

fun GL3.UGenFrameBuffer():Int{
    val buf = IntBuffer.allocate(1)
    this.glGenFramebuffers(1,buf)
    return buf.get()
}

fun GL3.USaveTexture(texId: Int,w : Int,h:Int) {
    this.glBindTexture(GL3.GL_TEXTURE_2D, texId)
    val buf = ByteBuffer.allocate(w * h * 3)
    this.glGetTexImage(GL3.GL_TEXTURE_2D, 0, GL3.GL_BGR, GL3.GL_UNSIGNED_BYTE, buf)
    val out = FileOutputStream("tex.rgb")
    out.write(buf.array())
    out.close()
    gl.glBindTexture(GL3.GL_TEXTURE_2D,0)
}