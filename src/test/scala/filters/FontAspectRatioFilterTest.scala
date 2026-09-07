package filters

import asciiArtApp.filters.FontAspectRatioFilter
import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel
import org.scalatest.funsuite.AnyFunSuite

class FontAspectRatioFilterTest extends AnyFunSuite {

  val R = RGBPixel(255, 0, 0)
  val G = RGBPixel(0, 255, 0)
  val B = RGBPixel(0, 0, 255)
  val Y = RGBPixel(255, 255, 0)
  val W = RGBPixel(255, 255, 255)
  val K = RGBPixel(0, 0, 0)

  private def createTestImage(): RGBImage = {
    val pixels = Vector(
      Vector(R, G),
      Vector(B, Y),
      Vector(W, K),
      Vector(R, G)
    )
    RGBImage(pixels)
  }

  test("FontAspectRatioFilter with 1:1 ratio returns original image") {
    val image = createTestImage()
    val filter = new FontAspectRatioFilter(1, 1)
    val result = filter.apply(image)

    assert(result == image)
  }

  test("FontAspectRatioFilter with 1:2 reduces height by half") {
    val image = createTestImage()
    val filter = new FontAspectRatioFilter(1, 2)
    val result = filter.apply(image)

    assert(result.width == 2)
    assert(result.height == 2)

    val expectedPixels = Vector(
      Vector(R, G),
      Vector(W, K)
    )
    assert(result == RGBImage(expectedPixels))
  }

  test("FontAspectRatioFilter with 2:1 enlarges height by half") {
    val image = createTestImage()
    val filter = new FontAspectRatioFilter(2, 1)
    val result = filter.apply(image)

    assert(result.width == 2)
    assert(result.height == 8)

    val expectedPixels = Vector(
      Vector(R, G),
      Vector(R, G),
      Vector(B, Y),
      Vector(B, Y),
      Vector(W, K),
      Vector(W, K),
      Vector(R, G),
      Vector(R, G)
    )
    assert(result == RGBImage(expectedPixels))
  }

  test("FontAspectRatioFilter with 2:5 adjusts height") {
    val pixels = Vector.tabulate(10, 4) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new FontAspectRatioFilter(2, 5)
    val result = filter.apply(image)

    assert(result.width == 4)
    assert(result.height == 4)

    val expectedPixels = Vector(
      Vector(R, R, R, R),
      Vector(R, R, R, R),
      Vector(R, R, R, R),
      Vector(R, R, R, R)
    )
    assert(result == RGBImage(expectedPixels))
  }

  test("FontAspectRatioFilter with small height") {
    val pixels = Vector(
      Vector(R, G),
      Vector(B, Y)
    )
    val image = RGBImage(pixels)
    val filter = new FontAspectRatioFilter(1, 2)
    val result = filter.apply(image)

    assert(result.width == 2)
    assert(result.height == 1)

    val expectedPixels = Vector(
      Vector(R, G)
    )
    assert(result == RGBImage(expectedPixels))
  }

  test("FontAspectRatioFilter handles rounding") {
    val pixels = Vector.tabulate(5, 3) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new FontAspectRatioFilter(1, 2)
    val result = filter.apply(image)

    assert(result.width == 3)
    assert(result.height == 2)

    val expectedPixels = Vector(
      Vector(R, R, R),
      Vector(R, R, R)
    )
    assert(result == RGBImage(expectedPixels))
  }

  test("FontAspectRatioFilter with ratio 3:4") {
    val pixels = Vector.tabulate(12, 9) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new FontAspectRatioFilter(3, 4)
    val result = filter.apply(image)

    assert(result.width == 9)
    assert(result.height == 9)
  }

  test("FontAspectRatioFilter with ratio 1:3") {
    val pixels = Vector.tabulate(10, 5) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new FontAspectRatioFilter(1, 3)
    val result = filter.apply(image)

    assert(result.width == 5)
    assert(result.height == 3)

    val expectedPixels = Vector(
      Vector(R, R, R, R, R),
      Vector(R, R, R, R, R),
      Vector(R, R, R, R, R)
    )
    assert(result == RGBImage(expectedPixels))
  }
}
