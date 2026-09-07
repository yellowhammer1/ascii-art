package asciiArtApp.models.images

import asciiArtApp.models.pixels.Pixel

trait Image[T <: Pixel[?]] {
  def width: Int
  def height: Int
}
