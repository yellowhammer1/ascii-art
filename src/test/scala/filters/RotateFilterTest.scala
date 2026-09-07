package filters

import asciiArtApp.filters.RotateFilter
import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel
import org.scalatest.funsuite.AnyFunSuite

class RotateFilterTest extends AnyFunSuite {

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

  test("RotateFilter rotates image by 90 degrees") {
    val image = createTestImage()
    val filter = new RotateFilter(90)
    val rotated = filter.apply(image)

    val expectedPixels = Vector(
      Vector(B, R),
      Vector(Y, G)
    )
    val expectedImage = RGBImage(expectedPixels)
    assert(rotated == expectedImage)
  }

  test("RotateFilter rotates image by 180 degrees") {
    val image = createTestImage()
    val filter = new RotateFilter(180)
    val rotated = filter.apply(image)

    val expectedPixels = Vector(
      Vector(Y, B),
      Vector(G, R)
    )
    val expectedImage = RGBImage(expectedPixels)
    assert(rotated == expectedImage)
  }

  test("RotateFilter rotates image by -90 degrees (equivalent to 270 degrees)") {
    val image = createTestImage()
    val filter = new RotateFilter(-90)
    val rotated = filter.apply(image)

    val expectedPixels = Vector(
      Vector(G, Y),
      Vector(R, B)
    )
    val expectedImage = RGBImage(expectedPixels)

    assert(rotated == expectedImage)
  }

  test("RotateFilter with 0 degrees returns original image") {
    val image = createTestImage()
    val filter = new RotateFilter(0)
    assert(filter.apply(image) == image)
  }

  test("RotateFilter rotates image by 450 degrees (equivalent to 90 degrees)") {
    val image = createTestImage()
    val filter = new RotateFilter(450)
    val rotated = filter.apply(image)

    val expectedPixels = Vector(
      Vector(B, R),
      Vector(Y, G)
    )
    assert(rotated == RGBImage(expectedPixels))
  }

  test("RotateFilter handles non-square image correctly") {
    val pixels = Vector(Vector(R, G, B))
    val image = RGBImage(pixels)

    assert(image.width == 3)
    assert(image.height == 1)

    val filter = new RotateFilter(90)
    val rotated = filter.apply(image)

    val expectedPixels = Vector(Vector(R), Vector(G), Vector(B))
    assert(rotated.width == 1)
    assert(rotated.height == 3)
    assert(rotated == RGBImage(expectedPixels))
  }
}
