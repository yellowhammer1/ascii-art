package asciiArtApp.models.pixels

case class GrayscalePixel(intensity: Int) extends Pixel[Int] {
  override def value: Int = intensity
}
