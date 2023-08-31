package com.winnerwinter.diceroller

import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer = MediaPlayer() //定义了一个成员函数
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMediaPlayer();

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
            bgFl.setBackgroundColor(
                resources.getColor(
                    if (count % 2 == 0) R.color.colorRed else R.color.colorAccent,
                    null
                )
            )
            vibrate()
            playerMusic()
        }
    }

    private fun vibrate() {
        (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(longArrayOf(300, 500), -1) //开始震动
    }

    private fun playerMusic() {
        //播放铃声
        mediaPlayer.start()
    }
    @Suppress("DEPRECATION")
    private fun initMediaPlayer() {
        volumeControlStream = AudioManager.STREAM_MUSIC //调用音乐服务
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnCompletionListener { mp ->
            mp.seekTo(0)
        }
        var file: AssetFileDescriptor? = null
        try {
            file = resources.openRawResourceFd(R.raw.y1981) //准备歌的所在路径
            mediaPlayer.setDataSource(
                file.fileDescriptor,
                file.startOffset, file.length
            )
            mediaPlayer.setVolume(5f, 5f)
            mediaPlayer.prepare()
        } catch (e: Exception) {
        } finally {
            file?.close()
        }
    }
}