package com.widget.cardview

class Card(val cardName :String, val possibleLengths :ArrayList<Int>, val drawable :Int) {
    val maxLength :Int
        get() = possibleLengths[possibleLengths.size - 1]
}
