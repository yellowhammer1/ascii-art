package asciiArtApp.models.pixels

case class RGBPixel(r: Int, g: Int, b: Int) extends Pixel[(Int, Int, Int)] {
  override def value: (Int, Int, Int) = (r, g, b)
}
