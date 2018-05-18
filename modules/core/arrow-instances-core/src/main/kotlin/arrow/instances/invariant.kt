package arrow.instances

import arrow.Kind
import arrow.instance
import arrow.typeclasses.ForMonoid
import arrow.typeclasses.Invariant
import arrow.typeclasses.Monoid
import arrow.typeclasses.Semigroup

// just by annotating Monoid with @higherkind

val invariantMonoid: Invariant<ForMonoid> = object : Invariant<ForMonoid> {
  override fun <A, B> Kind<ForMonoid, A>.imap(f: (A) -> B, fi: (B) -> A): Kind<ForMonoid, B> = object : Kind<ForMonoid, B>, Monoid<B> {
    override fun empty(): B = TODO("not implemented")
    override fun B.combine(b: B): B = TODO("not implemented")
  }
}

// -----------

@instance(Invariant::class)
interface InvariantSemigroupInstance<A> : Semigroup<Invariant<A>> {
  fun SG(): Semigroup<A>
  override fun Invariant<A>.combine(b: Invariant<A>): Invariant<A> = TODO("not implemented")
}

@instance(Invariant::class)
interface InvariantMonoidInstance<A> : InvariantSemigroupInstance<A>, Monoid<Invariant<A>> {
  override fun empty(): Invariant<A> = TODO("not implemented")
}

// Created invariant monoid instance by extending invariant semigroup instance
// Note: existing monoid instances type params are @higherkind annotated ex. Monoid<Type that is @higherkind annotated<A>>
// and extend the ...Of version of itself Option<A> : OptionOf<A> which generates the OptionOf.fix extension function

// Currently Invariant is not annotated with @higherkind, nor extends InvariantOf which would then require the
// companion object within Invariant

val invariantMonoidInstance: Invariant<ForMonoid> = object : Invariant<ForMonoid> {
  override fun <A, B> Kind<ForMonoid, A>.imap(f: (A) -> B, fi: (B) -> A): Kind<ForMonoid, B> = object : InvariantMonoidInstance<B> {
    override fun SG(): Semigroup<B> = TODO("not implemented")
  }.run { imap(f, fi) }
}