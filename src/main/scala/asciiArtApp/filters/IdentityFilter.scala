package asciiArtApp.filters

import asciiArtApp.models.images.Image

/**
 * A filter that returns the input image unchanged.
 * Used as a no-op filter or default value.
 */
class IdentityFilter[T <: Image[?]] extends Filter[T]{
  override def apply(image: T): T = image
}
