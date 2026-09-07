package converters

import asciiArtApp.converters.BasicRGBToGrayscaleConverter
import asciiArtApp.models.images.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixels.{GrayscalePixel, RGBPixel}
import org.scalatest.funsuite.AnyFunSuite

class BasicRGBToGrayscaleConverterTest extends AnyFunSuite{

  val R = RGBPixel(255, 0, 0)
  val G = RGBPixel(0, 255, 0)
  val B = RGBPixel(0, 0, 255)
  val Y = RGBPixel(255, 255, 0)
  val W = RGBPixel(255, 255, 255)
  val K = RGBPixel(0, 0, 0)

  private def createTestImage(): RGBImage = {
    val pixels = Vector(
      Vector(R, G),
      Vector(B, Y)
    )
    RGBImage(pixels)
  }

  private def calculateGrayscaleValue(RGBPixel: RGBPixel): Int = {
    (0.3 * RGBPixel.r + 0.59 * RGBPixel.g + 0.11 * RGBPixel.b).toInt
  }

  test("BasicRGBToGrayscaleConverter converts image from RGB to grayscale"){
    val RGBImage = createTestImage()
    val converter = new BasicRGBToGrayscaleConverter
    val grayscaleImage = converter.convert(RGBImage)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(calculateGrayscaleValue(R)), GrayscalePixel(calculateGrayscaleValue(G))),
      Vector(GrayscalePixel(calculateGrayscaleValue(B)), GrayscalePixel(calculateGrayscaleValue(Y)))
    )
    assert(grayscaleImage == GrayscaleImage(expectedPixels))
  }

  test("BasicRGBToGrayscaleConverter converts red correctly") {
    val pixels = Vector(Vector(R))
    val image = RGBImage(pixels)
    val converter = new BasicRGBToGrayscaleConverter
    val result = converter.convert(image)

    assert(result.getPixel(0, 0).value == 76)
  }

  test("BasicRGBToGrayscaleConverter converts green correctly") {
    val pixels = Vector(Vector(G))
    val image = RGBImage(pixels)
    val converter = new BasicRGBToGrayscaleConverter
    val result = converter.convert(image)

    assert(result.getPixel(0, 0).value == 150)
  }

  test("BasicRGBToGrayscaleConverter converts blue correctly") {
    val pixels = Vector(Vector(B))
    val image = RGBImage(pixels)
    val converter = new BasicRGBToGrayscaleConverter
    val result = converter.convert(image)

    assert(result.getPixel(0, 0).value == 28)
  }

  test("BasicRGBToGrayscaleConverter converts white correctly") {
    val pixels = Vector(Vector(W))
    val image = RGBImage(pixels)
    val converter = new BasicRGBToGrayscaleConverter
    val result = converter.convert(image)

    assert(result.getPixel(0, 0).value == 255)
  }

  test("BasicRGBToGrayscaleConverter converts black correctly") {
    val pixels = Vector(Vector(K))
    val image = RGBImage(pixels)
    val converter = new BasicRGBToGrayscaleConverter
    val result = converter.convert(image)

    assert(result.getPixel(0, 0).value == 0)
  }
}
