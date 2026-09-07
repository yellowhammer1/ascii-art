package filters

import asciiArtApp.filters.{Axis, BrightnessFilter, FlipFilter, IdentityFilter, InvertFilter, MixedFilter, RotateFilter, ScaleFilter}
import asciiArtApp.models.images.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixels.{GrayscalePixel, RGBPixel}
import org.scalatest.funsuite.AnyFunSuite

class MixedFilterTest extends AnyFunSuite {

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

  test("MixedFilter with empty filter list returns original image") {
    val image = createTestImage()
    val mixedFilter = new MixedFilter[RGBImage](Seq.empty)
    val result = mixedFilter.apply(image)

    assert(result == image)
  }

  test("MixedFilter with single filter applies it") {
    val image = createTestImage()
    val filters = Seq(new RotateFilter(90))
    val mixedFilter = new MixedFilter[RGBImage](filters)
    val result = mixedFilter.apply(image)

    val expectedFilter = new RotateFilter(90)
    val expected = expectedFilter.apply(image)

    assert(result == expected)
  }

  test("MixedFilter applies filters in order") {
    val image = createTestImage()

    val rotate = new RotateFilter(90)
    val scale = new ScaleFilter(4.0)

    val step1 = rotate.apply(image)
    val expected = scale.apply(step1)

    val mixedFilter = new MixedFilter[RGBImage](Seq(rotate, scale))
    val result = mixedFilter.apply(image)

    assert(result == expected)
  }

  test("MixedFilter order matters") {
    val pixels = Vector(
      Vector(R, G, B),
      Vector(Y, W, K)
    )
    val image = RGBImage(pixels)

    val filter1 = new MixedFilter[RGBImage](Seq(
      new RotateFilter(90),
      new FlipFilter(Axis.x)
    ))
    val result1 = filter1.apply(image)

    val filter2 = new MixedFilter[RGBImage](Seq(
      new FlipFilter(Axis.x),
      new RotateFilter(90)
    ))
    val result2 = filter2.apply(image)

    assert(result1.width == result2.width)
    assert(result1.height == result2.height)

    assert(result1 != result2, "Different order should produce different results")
  }

  test("MixedFilter with three filters") {
    val image = createTestImage()

    val filters = Seq(
      new RotateFilter(90),
      new FlipFilter(Axis.x),
      new ScaleFilter(4.0)
    )
    val mixedFilter = new MixedFilter[RGBImage](filters)
    val result = mixedFilter.apply(image)

    val step1 = new RotateFilter(90).apply(image)
    val step2 = new FlipFilter(Axis.x).apply(step1)
    val expected = new ScaleFilter(4.0).apply(step2)

    assert(result == expected)
  }

  test("MixedFilter with only identity filters returns original") {
    val image = createTestImage()

    val filters = Seq(
      new IdentityFilter[RGBImage](),
      new IdentityFilter[RGBImage](),
      new IdentityFilter[RGBImage]()
    )
    val mixedFilter = new MixedFilter[RGBImage](filters)
    val result = mixedFilter.apply(image)

    assert(result == image)
  }

  test("MixedFilter works with GrayscaleImage filters") {
    val pixels = Vector(
      Vector(GrayscalePixel(100), GrayscalePixel(150)),
      Vector(GrayscalePixel(50), GrayscalePixel(200))
    )
    val image = GrayscaleImage(pixels)

    val filters = Seq(
      new BrightnessFilter(10),
      new BrightnessFilter(-5),
      new InvertFilter()
    )
    val mixedFilter = new MixedFilter[GrayscaleImage](filters)
    val result = mixedFilter.apply(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(150), GrayscalePixel(100)),
      Vector(GrayscalePixel(200), GrayscalePixel(50))
    )
    assert(result == GrayscaleImage(expectedPixels))
  }

  test("MixedFilter with inverse operations returns to original") {
    val image = createTestImage()

    val filters = Seq(
      new ScaleFilter(4.0),
      new ScaleFilter(0.25)
    )
    val mixedFilter = new MixedFilter[RGBImage](filters)
    val result = mixedFilter.apply(image)

    assert(result == image)
  }

  test("MixedFilter with rotation 4*90 degrees returns original image (equivalent to 360 degrees)") {
    val image = createTestImage()

    val filters = Seq(
      new RotateFilter(90),
      new RotateFilter(90),
      new RotateFilter(90),
      new RotateFilter(90)
    )
    val mixedFilter = new MixedFilter[RGBImage](filters)
    val result = mixedFilter.apply(image)

    assert(result == image)
  }
}
