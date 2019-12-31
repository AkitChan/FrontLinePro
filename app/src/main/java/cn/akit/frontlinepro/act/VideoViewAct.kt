package cn.akit.frontlinepro.act

import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.SurfaceHolder
import android.widget.VideoView
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import kotlinx.android.synthetic.main.act_video.*

/**
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class VideoViewAct : BaseAct() {

    @Volatile
    private lateinit var path: String

    override fun init() {
        path = "android.resource://" + getPackageName() + "/" + R.raw.video
        video_view.setVideoPath(path)
//        video_view.start()


//        val params  = layout.layoutParams
//        params.height = 900
//        params.width = 900
//        layout.layoutParams = params
//
//        layout.scaleX  = 0.5f
//        layout.scaleY  = 0.5f
//        layout.translationX = 400f
        loadBitmap(0)
        video_view.setOnClickListener {
            if (video_view.isPlaying) {
                video_view.pause()
            } else {
                video_view.start()
            }
        }
        btn_play.setOnClickListener {
            if (video_view.isPlaying) {
                video_view.pause()
            } else {
                video_view.start()
            }
        }

        Thread(UpdateRunnable(video_view))
                .start()


        video_view.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

            }
        })


    }

    class UpdateRunnable(val view: VideoView) : Runnable {
        override fun run() {
            while (true) {
                Thread.sleep(100)
                if (view.isPlaying) {
                    view.postDelayed(Runnable {
                        if (view.isPlaying) {
                            view.background = null
                        }
                    }, 100)
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        video_view.seekTo(seekPosition)
////        video_view.start()
//    }
//
//    var seekPosition = 0
//
//
//    override fun onPause() {
//        seekPosition = video_view.currentPosition
//        Log.e("Test", "Position ${seekPosition}")
//        loadBitmap(seekPosition.toLong())
//        super.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//    }
//
    private fun loadBitmap(time: Long) {
        Thread(Runnable {
            var retriever = MediaMetadataRetriever()
            retriever.setDataSource(this, Uri.parse(path))
            val bitmap = retriever.getFrameAtTime(time * 1000 )
            runOnUiThread {
                video_view.background = BitmapDrawable(resources, bitmap)
            }
            retriever.release()

        }).start()
    }
//
    override fun provideViewRes(): Int {
        return R.layout.act_video
    }


}