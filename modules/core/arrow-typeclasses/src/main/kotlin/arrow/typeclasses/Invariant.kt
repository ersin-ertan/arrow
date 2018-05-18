package arrow.typeclasses

import arrow.Kind

interface Invariant<F> {
  fun <A, B> Kind<F, A>.imap(f: (A) -> B, fi: (B) -> A): Kind<F, B>

//  companion object /*required if extending InvariantOf<F>*/
}
