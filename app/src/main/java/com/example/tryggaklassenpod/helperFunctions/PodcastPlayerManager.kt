package com.example.tryggaklassenpod.helperFunctions

import android.media.MediaPlayer
import java.io.IOException

class PodcastPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition = 0
    private var currentEpisodeUrl: String? = null
    private var epDuration = 0


    fun preloadEpisode(url: String) {

        if (currentEpisodeUrl == null) {
            currentEpisodeUrl = url
            mediaPlayer?.release()
            try {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(url)
                    setOnPreparedListener { mp ->
                        epDuration = (mp.duration) / 1000
                    }
                    prepareAsync()
                }
            }
            catch (e: IOException) {
                releasePlayer()
            }
        }
    }

    fun getFullDuration():Int {
        return epDuration
    }

    fun playEpisode(url: String) {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    currentPosition = it.currentPosition / 1000
                } else {
                    it.start()
                    it.seekTo(currentPosition * 1000)
                }
            }
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun pauseEpisode() {
        try {
            mediaPlayer?.let {
                currentPosition = it.currentPosition / 1000
                it.pause()
            }
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun stopEpisode() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            currentPosition = 0
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun releasePlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0
        currentEpisodeUrl = null
        epDuration = 0
    }

    fun seekTo(position: Int) {
        try {
            mediaPlayer?.seekTo(position*1000)
            currentPosition = position
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun replay10Seconds() {
        try {
            mediaPlayer?.let {

                val newPosition = it.currentPosition - 10000
                currentPosition = if (newPosition >= 0) (
                        newPosition / 1000
                ) else {
                    0
                }
                it.seekTo(currentPosition*1000)
            }
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun forward30Seconds() {
        try {
            mediaPlayer?.let {
                val newPosition = it.currentPosition + 30000
                val duration = it.duration

                if (newPosition < duration) {
                    currentPosition = newPosition / 1000
                    it.seekTo(newPosition)
                } else {
                    currentPosition = duration / 1000
                    it.seekTo(duration)
                }
            }
        }
        catch (e: IOException) {
            releasePlayer()
        }
    }

    fun getCurrentInSeconds(): Int {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                     currentPosition = it.currentPosition / 1000
                }
            }
            return currentPosition
        }
        catch (e: IOException) {
            releasePlayer()
        }
        return currentPosition
    }

    fun canPlayerStart():Boolean {
        return mediaPlayer != null
    }

    fun playAgain() {
        currentPosition = 0
    }
}
