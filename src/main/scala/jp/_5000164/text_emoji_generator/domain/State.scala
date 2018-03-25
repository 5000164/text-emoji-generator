package jp._5000164.text_emoji_generator.domain

case class State(
                  text: String,
                  color: String,
                  fontFace: FontFace
                )

sealed trait FontFace

case object Gothic extends FontFace

case object Mincho extends FontFace
