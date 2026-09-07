package asciiArtApp.converters

import asciiArtApp.converters.tables.TransformationTable
import asciiArtApp.models.images.{AsciiImage, GrayscaleImage}

/**
 * Converts grayscale images to ASCII art using a transformation table.
 *
 * @param transformationTable the table mapping grayscale values to ASCII characters
 */
class BasicGrayscaleToAsciiConverter(transformationTable: TransformationTable) extends GrayscaleToAsciiConverter {
  override def convert(input: GrayscaleImage): AsciiImage = {
    val asciiPixels = input.data.map { row =>
      row.map { pixel =>
        transformationTable.transform(pixel.value)
      }
    }
    AsciiImage(asciiPixels)
  }
}
