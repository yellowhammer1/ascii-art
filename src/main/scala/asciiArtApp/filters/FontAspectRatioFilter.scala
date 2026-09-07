package asciiArtApp.filters

import asciiArtApp.models.images.RGBImage

/**
 * Adjusts image height to compensate for font aspect ratio.
 * Fonts typically have a height-to-width ratio greater than 1:1,
 * causing ASCII art to appear vertically stretched. This filter
 * resamples the image height to correct for this distortion.
 *
 * @param fontAspectX the font width component
 * @param fontAspectY the font height component
 */
class FontAspectRatioFilter(fontAspectX: Double, fontAspectY: Double) extends Filter[RGBImage] {
  override def apply(image: RGBImage): RGBImage = {
    val width = image.width
    val height = image.height

    // Calculate new height based on font aspect ratio
    // If font is taller than wide (e.g., 1:2), reduce image height
    val fontRatio = fontAspectY / fontAspectX
    val newHeight = (height / fontRatio).toInt

    if (newHeight == height) {
      return image
    }

    // Resample image rows proportionally
    val adjustedPixels = Vector.tabulate(newHeight, width) { (y, x) =>
      val srcY = (y.toDouble / newHeight * height).toInt
      image.getPixel(x, srcY.min(height - 1))
    }

    RGBImage(adjustedPixels)
  }
}
