package asciiArtApp.ui.configs

import asciiArtApp.filters.{Filter, MixedFilter}
import asciiArtApp.models.images.{AsciiImage, GrayscaleImage, RGBImage}

/**
 * Configuration for image filters organized by image type.
 * Filters are applied in the order they were added.
 *
 * @param rgbFilters filters to apply to RGB images
 * @param grayscaleFilters filters to apply to grayscale images
 */
case class FilterConfig(rgbFilters: Seq[Filter[RGBImage]] = Seq.empty,
                        grayscaleFilters: Seq[Filter[GrayscaleImage]] = Seq.empty) {

  def applyRgbFilters(image: RGBImage): RGBImage = {
    val pipeline = new MixedFilter[RGBImage](rgbFilters)
    pipeline.apply(image)
  }

  def applyGrayscaleFilters(image: GrayscaleImage): GrayscaleImage = {
    val pipeline = new MixedFilter[GrayscaleImage](grayscaleFilters)
    pipeline.apply(image)
  }
}
