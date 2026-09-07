package asciiArtApp.filters

import asciiArtApp.models.images.Image

/**
 * A filter that transforms images.
 * Implementations should be immutable and return a new transformed image.
 *
 * @tparam T the type of image this filter operates on
 */
trait Filter[T <: Image[?]] {
  /**
   * Applies the filter to an image.
   *
   * @param image the input image
   * @return a new transformed image
   */
  def apply(image: T): T
}
