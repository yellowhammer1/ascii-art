package asciiArtApp.models.pixels

case class AsciiPixel (char: Char) extends Pixel[Char] {
  override def value: Char = char
}
