package loaders

import asciiArtApp.loaders.StandardFileImageLoader
import org.scalatest.funsuite.AnyFunSuite
import scala.util.{Success, Failure}

class StandardFileImageLoaderTest extends AnyFunSuite {
  test("StandardFileImageLoader loads valid JPG image") {
    val loader = new StandardFileImageLoader("src/test/resources/BlackAndWhite.jpg")

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width > 0)
        assert(image.height > 0)
      case Failure(e) =>
        fail(s"Failed to load JPG: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader loads valid PNG image") {
    val loader = new StandardFileImageLoader("src/test/resources/BlackAndWhite.png")

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width > 0)
        assert(image.height > 0)
      case Failure(e) =>
        fail(s"Failed to load PNG: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader loads valid GIF image") {
    val loader = new StandardFileImageLoader("src/test/resources/BlackAndWhite.gif")

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width > 0)
        assert(image.height > 0)
      case Failure(e) =>
        fail(s"Failed to load GIF: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader loads valid BMP image") {
    val loader = new StandardFileImageLoader("src/test/resources/BlackAndWhite.bmp")

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width > 0)
        assert(image.height > 0)
      case Failure(e) =>
        fail(s"Failed to load BMP: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader loads 1x1 pixel image") {
    val loader = new StandardFileImageLoader("src/test/resources/OnePixel.jpg")

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width == 1)
        assert(image.height == 1)
        val pixel = image.getPixel(0, 0)
      case Failure(e) =>
        fail(s"Failed to load 1x1 image: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader correctly loads black and white image pixels") {
    val loader = new StandardFileImageLoader("src/test/resources/BlackAndWhite.png")

    loader.loadImage() match {
      case Success(image) =>
        val firstPixel = image.getPixel(0, 0)

        assert(firstPixel.r >= 0 && firstPixel.r <= 255)
        assert(firstPixel.g >= 0 && firstPixel.g <= 255)
        assert(firstPixel.b >= 0 && firstPixel.b <= 255)
      case Failure(e) =>
        fail(s"Failed to load black and white image: ${e.getMessage}")
    }
  }

  test("StandardFileImageLoader fails on non-existent file") {
    val loader = new StandardFileImageLoader("src/test/resources/non-existent-file.jpg")

    loader.loadImage() match {
      case Success(_) =>
        fail("Should have failed on non-existent file")
      case Failure(e) =>
        assert(e.getMessage.contains("not found") || e.getMessage.contains("File not found"))
    }
  }

  test("StandardFileImageLoader fails on unsupported format") {
    val loader = new StandardFileImageLoader("src/test/resources/TextFile.txt")

    loader.loadImage() match {
      case Success(_) =>
        fail("Should have failed on unsupported format")
      case Failure(e) =>
        assert(e.getMessage.contains("Unsupported") || e.getMessage.contains("unsupported"))
    }
  }

  test("StandardFileImageLoader fails on empty/corrupted JPG file") {
    val loader = new StandardFileImageLoader("src/test/resources/EmptyImage.jpg")

    loader.loadImage() match {
      case Success(_) =>
        fail("Should have failed on empty/corrupted file")
      case Failure(e) =>
        assert(
          e.getMessage.contains("could not decode") ||
            e.getMessage.contains("null") ||
            e.getMessage.contains("empty")
        )
    }
  }
}
