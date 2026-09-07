package asciiArtApp.models.images

import asciiArtApp.models.pixels.RGBPixel

case class RGBImage(data: Vector[Vector[RGBPixel]]) extends Image[RGBPixel] {
  override def height: Int = data.length
  override def width: Int = if (data.isEmpty) 0 else data.head.length
  def getPixel(x: Int, y: Int): RGBPixel = data(y)(x)
}
