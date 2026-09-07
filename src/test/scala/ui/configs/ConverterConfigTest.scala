package ui.configs

import asciiArtApp.ui.configs.{ConverterConfig, TableConfig}
import asciiArtApp.converters.tables.predefined.{BourkeLinearTransformationTable, HighContrastNonlinearTransformationTable, StandardLinearTransformationTable}
import asciiArtApp.models.images.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixels.{GrayscalePixel, RGBPixel}
import org.scalatest.funsuite.AnyFunSuite

class ConverterConfigTest extends AnyFunSuite {
  test("createRGBToGrayscaleConverter creates working converter") {
    val converter = ConverterConfig.createRGBToGrayscaleConverter()

    val pixels = Vector(
      Vector(RGBPixel(255, 0, 0), RGBPixel(0, 255, 0))
    )
    val image = RGBImage(pixels)
    val result = converter.convert(image)

    assert(result.width == 2)
    assert(result.height == 1)
  }

  test("createGrayscaleToAsciiConverter creates working converter") {
    val converter = ConverterConfig.createGrayscaleToAsciiConverter(BourkeLinearTransformationTable)

    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(255))
    )
    val image = GrayscaleImage(pixels)
    val result = converter.convert(image)

    assert(result.width == 2)
    assert(result.height == 1)
    assert(result.getPixel(0, 0) == '@')
    assert(result.getPixel(1, 0) == ' ')
  }

  test("getTable returns Bourke table when tableName is 'bourke'") {
    val table = ConverterConfig.getTable(Some("bourke"), None)

    assert(table == BourkeLinearTransformationTable)
  }

  test("getTable returns Standard table when tableName is 'standard'") {
    val table = ConverterConfig.getTable(Some("standard"), None)

    assert(table == StandardLinearTransformationTable)
  }

  test("getTable returns High Contrast table when tableName is 'high-contrast'") {
    val table = ConverterConfig.getTable(Some("high-contrast"), None)
    assert(table == HighContrastNonlinearTransformationTable)
  }

  test("getTable creates custom table from valid characters") {
    val table = ConverterConfig.getTable(None, Some(".#@"))

    assert(table.transform(0) == '.')
    assert(table.transform(255) == '@')
  }

  test("getTable returns default when both options are None") {
    val table = ConverterConfig.getTable(None, None)

    assert(table == TableConfig.default)
  }
}
