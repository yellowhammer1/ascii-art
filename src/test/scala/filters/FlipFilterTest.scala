package filters

import asciiArtApp.filters.{FlipFilter, Axis}
import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel
import org.scalatest.funsuite.AnyFunSuite

class FlipFilterTest extends AnyFunSuite {

  val R = RGBPixel(255, 0, 0)
  val G = RGBPixel(0, 255, 0)
  val B = RGBPixel(0, 0, 255)
  val Y = RGBPixel(255, 255, 0)

  private def createTestImage(): RGBImage = {
    val pixels = Vector(
      Vector(R, G),
      Vector(B, Y)
    )
    RGBImage(pixels)
  }

  test("FlipFilter flips on X axis") {
    val image = createTestImage()
    val filter = new FlipFilter(Axis.x)
    val flipped = filter.apply(image)

    val expectedPixels = Vector(
      Vector(B, Y),
      Vector(R, G)
    )
    val expectedImage = RGBImage(expectedPixels)

    assert(flipped == expectedImage)
  }

  test("FlipFilter flips on Y axis") {
    val image = createTestImage()
    val filter = new FlipFilter(Axis.y)
    val flipped = filter.apply(image)

    val expectedPixels = Vector(
      Vector(G, R),
      Vector(Y, B)
    )
    val expectedImage = RGBImage(expectedPixels)

    assert(flipped == expectedImage)
  }

  test("FlipFilter flips on YX axis") {
    val image = createTestImage()
    val filterY = new FlipFilter(Axis.y)
    val filterX = new FlipFilter(Axis.x)
    val flipped = filterX.apply(filterY.apply(image))

    val expectedPixels = Vector(
      Vector(Y, B),
      Vector(G, R)
    )
    assert(flipped == RGBImage(expectedPixels))
  }

  test("FlipFilter flips on XY axis") {
    val image = createTestImage()
    val filterX = new FlipFilter(Axis.x)
    val filterY = new FlipFilter(Axis.y)
    val flipped = filterY.apply(filterX.apply(image))

    val expectedPixels = Vector(
      Vector(Y, B),
      Vector(G, R)
    )
    assert(flipped == RGBImage(expectedPixels))
  }

  test("FlipFilter flips on XX axis") {
    val image = createTestImage()
    val filter = new FlipFilter(Axis.x)
    val flipped = filter.apply(filter.apply(image))

    assert(flipped == image)
  }

  test("FlipFilter flips on YY axis") {
    val image = createTestImage()
    val filter = new FlipFilter(Axis.y)
    val flipped = filter.apply(filter.apply(image))

    assert(flipped == image)
  }

  test("FlipFilter flips one pixel image") {
    val pixel = RGBPixel(255, 0, 0)
    val image = RGBImage(Vector(Vector(pixel)))
    val filter = new FlipFilter(Axis.x)
    val flipped = filter.apply(image)

    assert(flipped == image)
  }
}
