package filters

import asciiArtApp.filters.ScaleFilter
import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel
import org.scalatest.funsuite.AnyFunSuite

class ScaleFilterTest extends AnyFunSuite {

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

  test("ScaleFilter with factor 1 returns original image") {
    val image = createTestImage()
    val filter = new ScaleFilter(1)
    val scaled = filter.apply(image)
    
    assert(scaled == image)
  }

  test("ScaleFilter 0.25 reduces 2x2 to 1 pixel") {
    val image = createTestImage()
    val filter = new ScaleFilter(0.25)
    val scaled = filter.apply(image)

    assert(scaled.width == 1)
    assert(scaled.height == 1)
    assert(scaled.getPixel(0, 0) == R)
  }

  test("ScaleFilter 0.25 reduces 4x4 to 2x2") {
    val pixels = Vector(
      Vector(R, R, G, G),
      Vector(R, R, G, G),
      Vector(B, B, Y, Y),
      Vector(B, B, Y, Y)
    )
    val image = RGBImage(pixels)

    val filter = new ScaleFilter(0.25)
    val scaled = filter.apply(image)

    assert(scaled.width == 2)
    assert(scaled.height == 2)
    
    val expectedImage = createTestImage()
    assert(scaled == expectedImage)
  }

  test("ScaleFilter 4 creates 4x4 from 2x2") {
    val image = createTestImage()
    val filter = new ScaleFilter(4)
    val scaled = filter.apply(image)

    assert(scaled.width == 4)
    assert(scaled.height == 4)

    val expectedPixels = Vector(
      Vector(R, R, G, G),
      Vector(R, R, G, G),
      Vector(B, B, Y, Y),
      Vector(B, B, Y, Y)
    )
    assert(scaled == RGBImage(expectedPixels))
  }

  test("ScaleFilter 4 creates 2x2 from 1 pixel") {
    val pixels = Vector(Vector(R))
    val image = RGBImage(pixels)

    val filter = new ScaleFilter(4)
    val scaled = filter.apply(image)

    assert(scaled.width == 2)
    assert(scaled.height == 2)

    val expectedPixels = Vector(
      Vector(R, R),
      Vector(R, R)
    )
    assert(scaled == RGBImage(expectedPixels))
  }

  test("ScaleFilter 4 then 0.25 returns to original size") {
    val image = createTestImage()

    val upFilter = new ScaleFilter(4)
    val upscaled = upFilter.apply(image)

    val downFilter = new ScaleFilter(0.25)
    val downscaled = downFilter.apply(upscaled)
    assert(downscaled == image)
  }

  test("ScaleFilter 0.25 then 4 returns to original size") {
    val image = createTestImage()

    val downFilter = new ScaleFilter(0.25)
    val downscaled = downFilter.apply(image)

    val upFilter = new ScaleFilter(4)
    val upscaled = upFilter.apply(downscaled)

    assert(upscaled.width == 2)
    assert(upscaled.height == 2)

    val expectedPixels = Vector(
      Vector(R, R),
      Vector(R, R)
    )
    assert(upscaled == RGBImage(expectedPixels))
  }

  test("ScaleFilter 0.25 on odd dimensions") {
    val pixels = Vector.tabulate(5, 5) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new ScaleFilter(0.25)
    val scaled = filter.apply(image)

    assert(scaled.width == 2)
    assert(scaled.height == 2)
  }

  test("ScaleFilter 4 doubles dimensions") {
    val pixels = Vector.tabulate(5, 5) { (y, x) => R }
    val image = RGBImage(pixels)

    val filter = new ScaleFilter(4)
    val scaled = filter.apply(image)

    assert(scaled.width == 10)
    assert(scaled.height == 10)
  }
}
