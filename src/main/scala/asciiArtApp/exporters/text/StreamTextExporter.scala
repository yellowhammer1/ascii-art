package asciiArtApp.exporters.text

import java.io.OutputStream

/**
 * Base class for exporters that write to output streams.
 * Manages stream lifecycle and ensures proper closure.
 *
 * @param outputStream the stream to write to
 */
class StreamTextExporter(outputStream: OutputStream) extends TextExporter
{
  private var closed = false

  protected def exportToStream(text: String): Unit ={

    if (closed)
      throw new Exception("The stream is already closed")

    outputStream.write(text.getBytes("UTF-8"))
    outputStream.flush()
  }

  def close(): Unit = {
    if (closed)
      return

    outputStream.close()
    closed = true
  }

  override def output(item: String): Unit = exportToStream(item)
}
