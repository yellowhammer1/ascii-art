package ui.configs

import asciiArtApp.ui.configs.FilterConfig
import asciiArtApp.filters.{RotateFilter, ScaleFilter, BrightnessFilter, IdentityFilter}
import asciiArtApp.models.images.{RGBImage, GrayscaleImage}
import asciiArtApp.models.pixels.{RGBPixel, GrayscalePixel}
import org.scalatest.funsuite.AnyFunSuite

class FilterConfigTest extends AnyFunSuite {

  val R = RGBPixel(255, 0, 0)
  val G = RGBPixel(0, 255, 0)
  val B = RGBPixel(0, 0, 255)
  val Y = RGBPixel(255, 255, 0)

  private def createRGBImage(): RGBImage = {
    val pixels = Vector(
      Vector(R, G),
      Vector(B, Y)
    )
    RGBImage(pixels)
  }

  private def createGrayscaleImage(): GrayscaleImage = {
    val pixels = Vector(
      Vector(GrayscalePixel(0), GrayscalePixel(255)),
      Vector(GrayscalePixel(128), GrayscalePixel(64))
    )
    GrayscaleImage(pixels)
  }

  test("FilterConfig with empty filters returns original RGB image") {
    val config = FilterConfig()
    val image = createRGBImage()
    val result = config.applyRgbFilters(image)

    assert(result == image)
  }

  test("FilterConfig with empty filters returns original Grayscale image") {
    val config = FilterConfig()
    val image = createGrayscaleImage()
    val result = config.applyGrayscaleFilters(image)

    assert(result == image)
  }

  test("FilterConfig applies single RGB filter") {
    val config = FilterConfig(
      rgbFilters = Seq(new RotateFilter(90))
    )
    val image = createRGBImage()
    val result = config.applyRgbFilters(image)

    val expected = new RotateFilter(90).apply(image)
    assert(result == expected)
  }

  test("FilterConfig applies multiple RGB filters in order") {
    val config = FilterConfig(
      rgbFilters = Seq(
        new RotateFilter(90),
        new ScaleFilter(4.0)
      )
    )
    val image = createRGBImage()
    val result = config.applyRgbFilters(image)

    val step1 = new RotateFilter(90).apply(image)
    val expected = new ScaleFilter(4.0).apply(step1)

    assert(result == expected)
  }

  test("FilterConfig applies single Grayscale filter") {
    val config = FilterConfig(
      grayscaleFilters = Seq(new BrightnessFilter(10))
    )
    val image = createGrayscaleImage()
    val result = config.applyGrayscaleFilters(image)

    val expected = new BrightnessFilter(10).apply(image)
    assert(result == expected)
  }

  test("FilterConfig applies multiple Grayscale filters in order") {
    val config = FilterConfig(
      grayscaleFilters = Seq(
        new BrightnessFilter(50),
        new BrightnessFilter(-20)
      )
    )
    val image = createGrayscaleImage()
    val result = config.applyGrayscaleFilters(image)

    val expectedPixels = Vector(
      Vector(GrayscalePixel(30), GrayscalePixel(235)),
      Vector(GrayscalePixel(158), GrayscalePixel(94))
    )
    assert(result == GrayscaleImage(expectedPixels))
  }

  test("FilterConfig with identity filters returns original") {
    val config = FilterConfig(
      rgbFilters = Seq(new IdentityFilter[RGBImage]())
    )
    val image = createRGBImage()
    val result = config.applyRgbFilters(image)

    assert(result eq image)
  }
}
