package asciiArtApp.loaders.imageGenerators

import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel

import scala.util.Random

class RandomImageGenerator(random: Random) extends ImageGenerator {
  override def generate(width: Int, height: Int): RGBImage = {
    val pixels = Vector.tabulate(height, width) { (y, x) =>
      RGBPixel(
        r = random.nextInt(256),
        g = random.nextInt(256),
        b = random.nextInt(256)
      )
    }

    RGBImage(pixels)
  }
}
