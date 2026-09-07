package filters

import asciiArtApp.filters.BrightnessFilter
import asciiArtApp.models.images.GrayscaleImage
import asciiArtApp.models.pixels.GrayscalePixel
import org.scalatest.funsuite.AnyFunSuite

class BrightnessFilterTest extends AnyFunSuite{

  private def createTestImage(): GrayscaleImage = {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(255)),
      Vector(GrayscalePixel(100), GrayscalePixel(50))
    )
    GrayscaleImage(pixels)
  }

  test("BrightnessFilter with value +10 brightens images"){
    val image = createTestImage()
    val filter = new BrightnessFilter(+10)
    val brightened = filter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(10), GrayscalePixel(255)),
      Vector(GrayscalePixel(110), GrayscalePixel(60))
    )
    val expectedImage = GrayscaleImage(expectedPixels)
    assert(brightened == expectedImage)
  }

  test("BrightnessFilter with value -10 darkens images") {
    val image = createTestImage()
    val filter = new BrightnessFilter(-10)
    val brightened = filter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(245)),
      Vector(GrayscalePixel(90), GrayscalePixel(40))
    )
    val expectedImage = GrayscaleImage(expectedPixels)
    assert(brightened == expectedImage)
  }

  test("BrightnessFilter with value 0 returns original image") {
    val image = createTestImage()
    val filter = new BrightnessFilter(0)
    val brightened = filter.apply(image)

    assert(brightened == image)
  }

  test("BrightnessFilter large positive value") {
    val image = createTestImage()
    val filter = new BrightnessFilter(+1000)
    val brightened = filter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(255), GrayscalePixel(255)),
      Vector(GrayscalePixel(255), GrayscalePixel(255))
    )
    val expectedImage = GrayscaleImage(expectedPixels)
    assert(brightened == expectedImage)
  }

  test("BrightnessFilter with large negative value") {
    val image = createTestImage()
    val filter = new BrightnessFilter(-1000)
    val brightened = filter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(0)),
      Vector(GrayscalePixel(0), GrayscalePixel(0))
    )
    val expectedImage = GrayscaleImage(expectedPixels)
    assert(brightened == expectedImage)
  }
}
