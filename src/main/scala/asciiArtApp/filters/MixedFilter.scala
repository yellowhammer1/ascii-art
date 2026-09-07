package asciiArtApp.filters

import asciiArtApp.models.images.Image

/**
 * A filter that applies multiple filters in sequence.
 * Filters are applied in the order they appear in the sequence.
 * If the sequence is empty, behaves as an identity filter.
 *
 * @param filters the sequence of filters to apply
 */
class MixedFilter[T <: Image[?]](filters: Seq[Filter[T]]) extends Filter[T]{
  override def apply(image: T): T = {
    if (filters.isEmpty)
      IdentityFilter[T]().apply(image)
    else filters.foldLeft(image)((img, filter) => filter.apply(img))
  }
}
