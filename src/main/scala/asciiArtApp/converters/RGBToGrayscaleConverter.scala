package asciiArtApp.converters

import asciiArtApp.models.images.{GrayscaleImage, RGBImage}

/**
 * Converts RGB images to grayscale images.
 */
trait RGBToGrayscaleConverter extends ImageConverter[RGBImage, GrayscaleImage] {}
