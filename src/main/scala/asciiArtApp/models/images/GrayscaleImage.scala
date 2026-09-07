package asciiArtApp.models.images

import asciiArtApp.models.pixels.GrayscalePixel

case class GrayscaleImage(data: Vector[Vector[GrayscalePixel]]) extends Image[GrayscalePixel] {
  override def height: Int = data.length
  override def width: Int = if (data.isEmpty) 0 else data.head.length
  def getPixel(x: Int, y: Int): GrayscalePixel = data(y)(x)
}
