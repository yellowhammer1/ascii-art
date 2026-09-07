package asciiArtApp.converters

import asciiArtApp.models.images.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixels.GrayscalePixel

/**
 * Converts RGB to grayscale using the standard luminosity formula.
 * Formula: 0.3*R + 0.59*G + 0.11*B
 */
class BasicRGBToGrayscaleConverter extends RGBToGrayscaleConverter {
  override def convert(input: RGBImage): GrayscaleImage = {
    val grayscalePixels = input.data.map { row =>
      row.map { pixel =>
        // Standard luminosity formula for RGB to grayscale conversion
        val grayValue = (0.3 * pixel.r + 0.59 * pixel.g + 0.11 * pixel.b).toInt
        GrayscalePixel(grayValue)
      }
    }
    GrayscaleImage(grayscalePixels)
  }
}
