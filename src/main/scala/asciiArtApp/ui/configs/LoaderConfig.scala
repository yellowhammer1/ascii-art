package asciiArtApp.ui.configs

import asciiArtApp.loaders.{ImageLoader, RandomImageLoader, StandardFileImageLoader}

object LoaderConfig {
  def createLoader(imagePath: Option[String], imageRandom: Boolean): ImageLoader = {
    if (imageRandom) {
      new RandomImageLoader()
    } else {
      imagePath match {
        case Some(path) => new StandardFileImageLoader(path)
        case None => throw new IllegalStateException("Image path not set")
      }
    }
  }
}
