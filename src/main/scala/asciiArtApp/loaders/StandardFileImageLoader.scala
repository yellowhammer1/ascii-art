package asciiArtApp.loaders

import asciiArtApp.models.images.RGBImage
import asciiArtApp.models.pixels.RGBPixel

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.util.Try

/**
 * Loads an image from a file using ImageIO.
 * Supports common formats: JPG, JPEG, PNG, GIF, BMP.
 *
 * @param path the file path to load from
 */
class StandardFileImageLoader(val path: String) extends ImageLoader {

  private val supportedFormats = Set("jpg", "jpeg", "png", "gif", "bmp")

  private def isSupportedFormat: Boolean = {
    val format = path.split('.').lastOption.getOrElse("").toLowerCase
    supportedFormats.contains(format)
  }

  override def loadImage(): Try[RGBImage] = Try {
    if (!isSupportedFormat) {
      throw new IllegalArgumentException(s"Unsupported file format: $path")
    }

    val file = new File(path)
    if (!file.exists()) {
      throw new java.io.FileNotFoundException(s"File not found: $path")
    }

    val bufferedImage: BufferedImage = ImageIO.read(file)

    if (bufferedImage == null) {
      throw new IllegalArgumentException("ImageIO could not decode the file.")
    }

    val width = bufferedImage.getWidth
    val height = bufferedImage.getHeight

    if (width == 0 || height == 0) {
      throw new IllegalArgumentException("Image has empty dimensions.")
    }

    val pixels = Vector.tabulate(height, width) { (y, x) =>
      val rgb = bufferedImage.getRGB(x, y)

      RGBPixel(
        r = (rgb >> 16) & 0xff,
        g = (rgb >> 8) & 0xff,
        b = rgb & 0xff
      )
    }

    RGBImage(pixels)
  }
}
