package filters

import asciiArtApp.filters.IdentityFilter
import asciiArtApp.models.images.{AsciiImage, GrayscaleImage, RGBImage}
import asciiArtApp.models.pixels.{GrayscalePixel, RGBPixel}
import org.scalatest.funsuite.AnyFunSuite

class IdentityFilterTest extends AnyFunSuite {

  test("IdentityFilter returns exact same RGBImage instance") {
    val pixels = Vector(
      Vector(RGBPixel(255, 0, 0), RGBPixel(0, 255, 0)),
      Vector(RGBPixel(0, 0, 255), RGBPixel(255, 255, 0))
    )
    val image = RGBImage(pixels)
    val filter = new IdentityFilter[RGBImage]()
    val result = filter.apply(image)

    assert(result eq image, "Should return the same instance, not a copy")
  }

  test("IdentityFilter returns exact same GrayscaleImage instance") {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(100)),
      Vector(GrayscalePixel(200), GrayscalePixel(255))
    )
    val image = GrayscaleImage(pixels)
    val filter = new IdentityFilter[GrayscaleImage]()
    val result = filter.apply(image)

    assert(result eq image)
  }

  test("IdentityFilter returns exact same AsciiImage instance") {
    val pixels = Vector(
      Vector('H', 'e', 'l', 'l', 'o'),
      Vector('W', 'o', 'r', 'l', 'd')
    )
    val image = AsciiImage(pixels)
    val filter = new IdentityFilter[AsciiImage]()
    val result = filter.apply(image)

    assert(result eq image)
  }

  test("IdentityFilter works with empty RGBImage") {
    val image = RGBImage(Vector.empty)
    val filter = new IdentityFilter[RGBImage]()
    val result = filter.apply(image)

    assert(result.data.isEmpty)
    assert(result eq image)
  }

  test("IdentityFilter works with single pixel image") {
    val image = RGBImage(Vector(Vector(RGBPixel(128, 128, 128))))
    val filter = new IdentityFilter[RGBImage]()
    val result = filter.apply(image)

    assert(result eq image)
  }
}
