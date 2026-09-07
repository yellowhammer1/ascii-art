package asciiArtApp.filters

import asciiArtApp.models.images.GrayscaleImage
import asciiArtApp.models.pixels.GrayscalePixel

class InvertFilter extends Filter[GrayscaleImage] {
  override def apply(image: GrayscaleImage): GrayscaleImage = {
    val invertedPixels = Vector.tabulate(image.height, image.width) { (y, x) =>
      GrayscalePixel(255 - image.getPixel(x, y).value)
    }
    
    GrayscaleImage(invertedPixels)
  }
}
