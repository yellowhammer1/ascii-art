package loaders

import asciiArtApp.loaders.RandomImageLoader
import org.scalatest.funsuite.AnyFunSuite
import scala.util.Success

class RandomImageLoaderTest extends AnyFunSuite {
  test("RandomImageLoader generates image with valid dimensions") {
    val loader = new RandomImageLoader()

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width >= 50 && image.width <= 200)
        assert(image.height >= 50 && image.height <= 200)
      case _ =>
        fail("Failed to generate random image")
    }
  }

  test("RandomImageLoader with same seed generates identical images") {
    val seed = 42L
    val loader1 = new RandomImageLoader(Some(seed))
    val loader2 = new RandomImageLoader(Some(seed))

    (loader1.loadImage(), loader2.loadImage()) match {
      case (Success(image1), Success(image2)) =>
        assert(image1.width == image2.width)
        assert(image1.height == image2.height)

        for (i <- 0 until math.min(5, image1.width); j <- 0 until math.min(5, image1.height)) {
          assert(image1.getPixel(i, j) == image2.getPixel(i, j))
        }
      case _ =>
        fail("Failed to load images")
    }
  }

  test("RandomImageLoader with different seeds generates different images") {
    val loader1 = new RandomImageLoader(Some(42L))
    val loader2 = new RandomImageLoader(Some(999L))

    (loader1.loadImage(), loader2.loadImage()) match {
      case (Success(image1), Success(image2)) =>
        val hasDifference =
          image1.width != image2.width ||
            image1.height != image2.height ||
            image1.getPixel(0, 0) != image2.getPixel(0, 0) ||
            image1.getPixel(1, 1) != image2.getPixel(1, 1)

        assert(hasDifference)
      case _ =>
        fail("Failed to load images")
    }
  }
}
