package asciiArtApp.filters

import asciiArtApp.models.images.GrayscaleImage
import asciiArtApp.models.pixels.GrayscalePixel

/**
 * Adjusts image brightness by adding/subtracting from pixel values.
 * Values are clamped to the range [0, 255].
 *
 * @param value the brightness adjustment (-255 to +255)
 */
class BrightnessFilter(value: Int) extends Filter[GrayscaleImage] {
  override def apply(image: GrayscaleImage): GrayscaleImage = {
    val brightenedPixels = Vector.tabulate(image.height, image.width) { (y, x) =>
      var newValue = image.getPixel(x, y).value + value
      if (newValue < 0)
        newValue = 0
      else if (newValue > 255)
        newValue = 255
        
      GrayscalePixel(newValue)
    }

    GrayscaleImage(brightenedPixels)
  }
}
