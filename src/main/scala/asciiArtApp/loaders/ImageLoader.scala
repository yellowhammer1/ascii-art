package asciiArtApp.loaders

import scala.util.Try
import asciiArtApp.models.images.RGBImage

/**
 * Loads an image from a source.
 */
trait ImageLoader {
  /**
   * Loads an image.
   *
   * @return Success with the loaded image, or Failure with an error
   */
  def loadImage(): Try[RGBImage]
}
