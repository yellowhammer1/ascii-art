package converters

import asciiArtApp.converters.BasicGrayscaleToAsciiConverter
import asciiArtApp.converters.tables.{LinearTransformationTable, NonlinearTransformationTable}
import asciiArtApp.converters.tables.predefined.{BourkeLinearTransformationTable, StandardLinearTransformationTable, HighContrastNonlinearTransformationTable}
import asciiArtApp.models.images.GrayscaleImage
import asciiArtApp.models.pixels.GrayscalePixel
import org.scalatest.funsuite.AnyFunSuite

class BasicGrayscaleToAsciiConverterTest extends AnyFunSuite {

  private def createTestImage(): GrayscaleImage = {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(255)),
      Vector(GrayscalePixel(128), GrayscalePixel(64))
    )
    GrayscaleImage(pixels)
  }

  test("BasicGrayscaleToAsciiConverter with Bourke table converts correctly") {
    val image = createTestImage()
    val converter = new BasicGrayscaleToAsciiConverter(BourkeLinearTransformationTable)
    val result = converter.convert(image)

    assert(result.width == 2)
    assert(result.height == 2)

    assert(result.getPixel(0, 0) == '@')
    assert(result.getPixel(1, 0) == ' ')
    assert(result.getPixel(0, 1) == '=')
    assert(result.getPixel(1, 1) == '#')
  }

  test("BasicGrayscaleToAsciiConverter with Standard table converts correctly") {
    val image = createTestImage()
    val converter = new BasicGrayscaleToAsciiConverter(StandardLinearTransformationTable)
    val result = converter.convert(image)

    assert(result.width == 2)
    assert(result.height == 2)

    assert(result.getPixel(0, 0) == '$')
    assert(result.getPixel(1, 0) == ' ')
    assert(result.getPixel(0, 1) == 'x')
    assert(result.getPixel(1, 1) == 'q')
  }

  test("BasicGrayscaleToAsciiConverter with HighContrast table converts correctly") {
    val image = createTestImage()
    val converter = new BasicGrayscaleToAsciiConverter(HighContrastNonlinearTransformationTable)
    val result = converter.convert(image)

    assert(result.getPixel(0, 0) == '@')
    assert(result.getPixel(1, 0) == ' ')
    assert(result.getPixel(0, 1) == '.')
    assert(result.getPixel(1, 1) == '@')
  }

  test("BasicGrayscaleToAsciiConverter with HighContrast table - boundary values") {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(99), GrayscalePixel(100)),
      Vector(GrayscalePixel(127), GrayscalePixel(128), GrayscalePixel(155)),
      Vector(GrayscalePixel(156), GrayscalePixel(255), GrayscalePixel(128))
    )
    val image = GrayscaleImage(pixels)
    val converter = new BasicGrayscaleToAsciiConverter(HighContrastNonlinearTransformationTable)
    val result = converter.convert(image)

    assert(result.getPixel(0, 0) == '@')
    assert(result.getPixel(1, 0) == '@')
    assert(result.getPixel(2, 0) == '*')
    assert(result.getPixel(0, 1) == '*')
    assert(result.getPixel(1, 1) == '.')
    assert(result.getPixel(2, 1) == '.')
    assert(result.getPixel(0, 2) == ' ')
    assert(result.getPixel(1, 2) == ' ')
  }

  test("BasicGrayscaleToAsciiConverter with simple custom table") {
    LinearTransformationTable(Seq('.', '#', '@')) match {
      case Some(customTable) =>
        val image = createTestImage()
        val converter = new BasicGrayscaleToAsciiConverter(customTable)
        val result = converter.convert(image)

        assert(result.getPixel(0, 0) == '.')
        assert(result.getPixel(1, 0) == '@')
      case None =>
        fail("Failed to create custom table")
    }
  }

  test("BasicGrayscaleToAsciiConverter with custom nonlinear table") {
    NonlinearTransformationTable(
      Seq('@', '#', '*', '+', ' '),
      Seq(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 255)
    ) match {
      case Some(customTable) =>
        val image = createTestImage()
        val converter = new BasicGrayscaleToAsciiConverter(customTable)
        val result = converter.convert(image)

        assert(result.getPixel(0, 0) == '@')
        assert(result.getPixel(1, 0) == ' ')
        assert(result.getPixel(0, 1) == '*')
        assert(result.getPixel(1, 1) == '#')
      case None =>
        fail("Failed to create custom nonlinear table")
    }
  }

  test("Different tables produce different ASCII output") {
    val image = createTestImage()

    val bourkeConverter = new BasicGrayscaleToAsciiConverter(BourkeLinearTransformationTable)
    val standardConverter = new BasicGrayscaleToAsciiConverter(StandardLinearTransformationTable)

    val bourkeResult = bourkeConverter.convert(image)
    val standardResult = standardConverter.convert(image)

    assert(bourkeResult != standardResult)
  }
}
