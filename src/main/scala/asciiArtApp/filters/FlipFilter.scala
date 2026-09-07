package asciiArtApp.filters

import asciiArtApp.models.images.RGBImage

/**
 * Flips an image horizontally or vertically.
 *
 * @param axis the flip axis (Axis.x for horizontal, Axis.y for vertical)
 */
class FlipFilter(axis: Axis) extends Filter[RGBImage] {
  override def apply(image: RGBImage): RGBImage = {
    axis match {
      case Axis.x => flipX(image)
      case Axis.y => flipY(image)
    }
  }
  
  private def flipX(image: RGBImage): RGBImage = {
    val flippedData = image.data.reverse
    RGBImage(flippedData)
  }
  
  private def flipY(image: RGBImage): RGBImage = {
    val flippedData = image.data.map(_.reverse)
    RGBImage(flippedData)
  }
}

enum Axis {
  case x, y
}
