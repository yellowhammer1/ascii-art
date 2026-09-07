package asciiArtApp.loaders

import asciiArtApp.loaders.imageGenerators.RandomImageGenerator
import asciiArtApp.models.images.RGBImage

import scala.util.{Random, Try}

/**
 * Generates a random image with random dimensions and pixel colors.
 * Image dimensions are randomly chosen between 50 and 200 pixels.
 *
 * @param seed optional seed for deterministic generation (useful for testing)
 */
class RandomImageLoader(seed: Option[Long] = None) extends ImageLoader {
  private val random = new Random(seed.getOrElse(System.currentTimeMillis()))

  private val minDimension = 50
  private val maxDimension = 200

  override def loadImage(): Try[RGBImage] = Try {
    val width = random.between(minDimension, maxDimension + 1)
    val height = random.between(minDimension, maxDimension + 1)

    val imageGenerator = new RandomImageGenerator(random)
    imageGenerator.generate(width, height)
  }
}
