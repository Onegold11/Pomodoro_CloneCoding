package com.clonecoding.pomodorotimer

import android.annotation.SuppressLint
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    /**
     * Minutes text view
     */
    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    /**
     * Seconds text  view
     */
    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    /**
     * SeekBar
     */
    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }

    /**
     * Timer
     */
    private var currentCountDownTimer: CountDownTimer? = null

    /**
     * Sound
     */
    private val soundPool = SoundPool.Builder().build()

    /**
     * Ticking sound id
     */
    private var tickingSoundId: Int? = null

    /**
     * Bell sound id
     */
    private var bellSoundId: Int? = null

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.bindViews()
        this.initSounds()
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()

        this.soundPool.autoResume()
    }

    /**
     * onPause
     */
    override fun onPause() {
        super.onPause()

        this.soundPool.autoPause()
    }

    /**
     * onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()

        this.soundPool.release()
    }

    /**
     * Init view
     */
    private fun bindViews() {

        this.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                if (fromUser) {
                    updateRemainTime(progress * 60 * 1000L)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

                currentCountDownTimer?.cancel()
                currentCountDownTimer = null
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                seekBar ?: return
                startCountDown()
            }
        })
    }

    /**
     * Create count down timer
     */
    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {

                completeCountDown()
            }
        }

    /**
     * Start timer
     */
    fun startCountDown() {

        currentCountDownTimer =
            createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer?.start()

        tickingSoundId?.let { id ->
            soundPool.play(id, 1F, 1F, 0, -1, 1F)
        }
    }

    /**
     * Complete timer
     */
    fun completeCountDown() {

        updateRemainTime(0)
        updateSeekBar(0)

        soundPool.autoPause()
        bellSoundId?.let { id ->
            soundPool.play(id, 1F, 1F, 0, 0, 1F)
        }
    }


    /**
     * Update time text view
     */
    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {

        val remainSeconds = remainMillis / 1000

        this.remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        this.remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    /**
     * Update seek bar
     */
    private fun updateSeekBar(remainMillis: Long) {

        this.seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }

    /**
     * Init sound file
     */
    private fun initSounds() {

        this.tickingSoundId =
            this.soundPool.load(this, R.raw.timer_ticking, 1)
        this.bellSoundId =
            this.soundPool.load(this, R.raw.timer_bell, 1)
    }
}