package asciiArtApp.converters

import asciiArtApp.models.images.{AsciiImage, GrayscaleImage}

trait GrayscaleToAsciiConverter extends ImageConverter [GrayscaleImage, AsciiImage] {}
