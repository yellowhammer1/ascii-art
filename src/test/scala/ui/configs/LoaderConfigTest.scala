package ui.configs

import asciiArtApp.ui.configs.LoaderConfig
import asciiArtApp.loaders.ImageLoader
import org.scalatest.funsuite.AnyFunSuite
import scala.util.Success

class LoaderConfigTest extends AnyFunSuite {
  test("RandomImageLoader from createLoader can load image") {
    val loader = LoaderConfig.createLoader(None, imageRandom = true)

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width >= 50 && image.width <= 200)
        assert(image.height >= 50 && image.height <= 200)
      case _ =>
        fail("RandomImageLoader should successfully generate image")
    }
  }

  test("createLoader with valid file path creates working loader") {
    val loader = LoaderConfig.createLoader(
      Some("src/test/resources/BlackAndWhite.jpg"),
      imageRandom = false
    )

    loader.loadImage() match {
      case Success(image) =>
        assert(image.width > 0)
        assert(image.height > 0)
      case _ =>
        fail("StandardFileImageLoader should successfully load image")
    }
  }

  test("createLoader throws IllegalStateException when both are None/false") {
    val exception = intercept[IllegalStateException] {
      LoaderConfig.createLoader(None, imageRandom = false)
    }

    assert(exception.getMessage == "Image path not set")
  }

  test("createLoader creates new RandomImageLoader instance each time") {
    val loader1 = LoaderConfig.createLoader(None, imageRandom = true)
    val loader2 = LoaderConfig.createLoader(None, imageRandom = true)

    assert(!(loader1 eq loader2))
  }

  test("createLoader creates new StandardFileImageLoader instance each time") {
    val loader1 = LoaderConfig.createLoader(Some("test.jpg"), imageRandom = false)
    val loader2 = LoaderConfig.createLoader(Some("test.jpg"), imageRandom = false)

    assert(!(loader1 eq loader2))
  }
}
