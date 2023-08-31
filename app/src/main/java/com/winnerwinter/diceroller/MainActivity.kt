package com.winnerwinter.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bgFl: View = findViewById(R.id.bgFl)
        val diceImage: ImageView = findViewById(R.id.imageView)
        val addTv: TextView = findViewById(R.id.addTv)
        addTv.visibility = View.INVISIBLE
        diceImage.setOnClickListener { rollDice(bgFl, diceImage, addTv) }
        diceImage.performClick()
    }

    private var count: Int = 0
    private var lastTime: Long = 0
    private fun rollDice(bgFl: View, diceImage: ImageView, addTv: TextView) {
        var currentTimeMillis = System.currentTimeMillis();
        if (abs(lastTime - currentTimeMillis) < 1000) {
            return
        }
        try {
            lastTime = currentTimeMillis
            val dice = Dice(60)
            val diceRoll = dice.roll()
            //特殊奖励，规则可以自定义，被5整除
            addTv.visibility = if (diceRoll % 5 == 0) View.VISIBLE else View.INVISIBLE
            val point = diceRoll / 10
            Log.d("point=", point.toString())
            val drawableResource = when (point) {
                0 -> R.drawable.dice_1
                1 -> R.drawable.dice_2
                2 -> R.drawable.dice_3
                3 -> R.drawable.dice_4
                4 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }
            diceImage.setImageResource(drawableResource)
        } finally {
            count++
            bgFl.setBackgroundColor(resources.getColor(if(count%2==0) R.color.colorRed else R.color.colorAccent, null))
        }
    }
}