package asciiArtApp.converters

import asciiArtApp.models.images.Image

trait ImageConverter[Input <: Image[?], Output <: Image[?]] {
  def convert(input: Input): Output
}
