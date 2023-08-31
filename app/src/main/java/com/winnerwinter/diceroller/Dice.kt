package com.winnerwinter.diceroller

class Dice (private val numSide: Int){

    fun roll(): Int {
        return (0..numSide).random()
    }
}