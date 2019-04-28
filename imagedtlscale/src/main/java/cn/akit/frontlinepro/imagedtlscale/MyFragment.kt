package cn.akit.frontlinepro.imagedtlscale

import android.graphics.BitmapFactory
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/**
 * Created by chenYuXuan on 2019/4/13.
 * email : southxvii@163.com
 */
class MyFragment : Fragment() {


    var location: ArrayList<Int>? = null
    var textureView: TextureView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        location = arguments?.getIntegerArrayList("location")
        val view = inflater.inflate(R.layout.fg_my, container, false)
        textureView = TextureView(activity)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        textureView?.layoutParams = params
        view.findViewById<FrameLayout>(R.id.fl_content).addView(textureView)
        return view
    }

    val player = MediaPlayer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!userVisibleHint) {
            return
        }

        player.setDataSource(activity, Uri.parse("android.resource://${activity!!.packageName}/${R.raw.test}"))
        player.isLooping = true
        player.prepareAsync()
        player.setOnPreparedListener {
            //            player.start()
        }

        textureView?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                return false
            }

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
//                val canvas = textureView!!.lockCanvas()
                val bm = BitmapFactory.decodeResource(getResources(), R.drawable.background);
//                canvas.drawBitmap(bm, 0f, 0f, Paint())
//                textureView!!.unlockCanvasAndPost(canvas)
//                textureView.background.
//                player.setSurface(Surface(surface))
            }
        }

        textureView?.postDelayed({
//            textureView?.
            player.setSurface(Surface(textureView!!.surfaceTexture))
            player.start()
        }, 3000)
    }
}