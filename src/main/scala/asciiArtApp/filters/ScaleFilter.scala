package asciiArtApp.filters

import asciiArtApp.models.images.RGBImage

/**
 * Scales an image by a specific factor.
 * Supported factors:
 * - 0.25: reduces to 1/2 dimensions (takes one pixel from every 2x2 block)
 * - 1.0: no change
 * - 4.0: doubles dimensions (each pixel becomes a 2x2 block)
 *
 * @param scaleFactor the scaling factor (0.25, 1.0, or 4.0)
 */
class ScaleFilter(scaleFactor: Double) extends Filter[RGBImage] {
  override def apply(image: RGBImage): RGBImage = {
    scaleFactor match {
      case 1 => image
      case 0.25 => scaleDown(image)
      case 4 => scaleUp(image)
    }
  }

  private def scaleDown(image: RGBImage): RGBImage = {
    val newWidth = image.width / 2
    val newHeight = image.height / 2

    val scaledPixels = Vector.tabulate(newHeight, newWidth) { (y, x) =>
      image.getPixel(x * 2, y * 2)
    }

    RGBImage(scaledPixels)
  }

  private def scaleUp(image: RGBImage): RGBImage = {
    val newWidth = image.width * 2
    val newHeight = image.height * 2

    val scaledPixels = Vector.tabulate(newHeight, newWidth) { (y, x) =>
      image.getPixel(x / 2, y / 2)
    }

    RGBImage(scaledPixels)
  }
}
