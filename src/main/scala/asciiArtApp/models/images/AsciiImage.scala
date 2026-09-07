package asciiArtApp.models.images

import asciiArtApp.models.pixels.AsciiPixel

case class AsciiImage(data: Vector[Vector[Char]]) extends Image[AsciiPixel] {
  override def height: Int = data.length
  override def width: Int = if (data.isEmpty) 0 else data.head.length
  def getPixel(x: Int, y: Int): Char = data(y)(x)

  def toText: String = {
    data.map(_.mkString).mkString("\n")
  }
}
