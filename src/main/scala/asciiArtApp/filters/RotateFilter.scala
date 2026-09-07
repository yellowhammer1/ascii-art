package asciiArtApp.filters

import asciiArtApp.models.images.RGBImage

/**
 * Rotates an image by a multiple of 90 degrees.
 * Only rotations divisible by 90 are supported (90, 180, 270, -90, etc.).
 *
 * @param degrees the rotation angle (must be divisible by 90)
 */
class RotateFilter(degrees: Int) extends Filter[RGBImage] {
  override def apply(image: RGBImage): RGBImage = {
    val normalizedDegrees = ((degrees % 360) + 360) % 360

    normalizedDegrees match {
      case 0 => image
      case 90 => rotate90(image)
      case 180 => rotate180(image)
      case 270 => rotate270(image)
    }
  }

  private def rotate90(image: RGBImage): RGBImage = {
    val newWidth = image.height
    val newHeight = image.width

    val rotatedPixels = Vector.tabulate(newHeight, newWidth) { (y, x) =>
      image.getPixel(y, image.height - 1 - x)
    }

    RGBImage(rotatedPixels)
  }
  
  private def rotate180(image: RGBImage): RGBImage = {
    val rotatedPixels = Vector.tabulate(image.height, image.width) { (y, x) =>
      image.getPixel(image.width - 1 - x, image.height - 1 - y)
    }

    RGBImage(rotatedPixels)
  }
  
  private def rotate270(image: RGBImage): RGBImage = {
    val newWidth = image.height
    val newHeight = image.width

    val rotatedPixels = Vector.tabulate(newHeight, newWidth) { (y, x) =>
      image.getPixel(image.width - 1 - y, x)
    }

    RGBImage(rotatedPixels)
  }
}
