package asciiArtApp.loaders.imageGenerators

import asciiArtApp.models.images.RGBImage

trait ImageGenerator {
  def generate(width: Int, height: Int): RGBImage
}
