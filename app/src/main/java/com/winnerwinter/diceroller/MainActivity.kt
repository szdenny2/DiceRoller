package com.winnerwinter.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diceImage: ImageView = findViewById(R.id.imageView)
        val addTv: TextView = findViewById(R.id.addTv)
        addTv.visibility = View.INVISIBLE
        diceImage.setOnClickListener { rollDice(diceImage, addTv) }
        diceImage.performClick()
    }

    private var lastTime: Long = 0
    private fun rollDice(diceImage: ImageView, addTv: TextView) {
        var currentTimeMillis = System.currentTimeMillis();
        if (abs(lastTime - currentTimeMillis) < 1000) {
            return
        }
        lastTime = currentTimeMillis
        val dice = Dice(60)
        val diceRoll = dice.roll()
        //特殊奖励
        addTv.visibility = if (diceRoll >= 55) View.VISIBLE else View.INVISIBLE
        val drawableResource = when (diceRoll / 10) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(drawableResource)
    }
}