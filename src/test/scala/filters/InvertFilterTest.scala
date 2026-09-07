package filters

import asciiArtApp.filters.InvertFilter
import asciiArtApp.models.images.GrayscaleImage
import asciiArtApp.models.pixels.GrayscalePixel
import org.scalatest.funsuite.AnyFunSuite

class InvertFilterTest extends AnyFunSuite{

  private def createTestImage(): GrayscaleImage = {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(255)),
      Vector(GrayscalePixel(100), GrayscalePixel(50))
    )
    GrayscaleImage(pixels)
  }

  test("InvertFilter inverts image"){
    val image = createTestImage()
    val filter = new InvertFilter()
    val inverted = filter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(255), GrayscalePixel(0)),
      Vector(GrayscalePixel(155), GrayscalePixel(205))
    )
    assert(inverted == GrayscaleImage(expectedPixels))
  }

  test("InvertFilter applied twice returns original image") {
    val original = createTestImage()
    val filter = new InvertFilter()

    val onceInverted = filter.apply(original)
    val twiceInverted = filter.apply(onceInverted)

    assert(twiceInverted == original)
  }
}
