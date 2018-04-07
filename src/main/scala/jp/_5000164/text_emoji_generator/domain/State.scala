package jp._5000164.text_emoji_generator.domain

case class State(
                  text: String,
                  color: String,
                  fontFace: FontFace,
                  align: Align
                )

sealed trait FontFace

case object Gothic extends FontFace

case object Mincho extends FontFace

sealed trait Align

case object Left extends Align

case object Center extends Align
