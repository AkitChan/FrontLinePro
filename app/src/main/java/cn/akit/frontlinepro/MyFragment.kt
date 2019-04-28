package cn.akit.frontlinepro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Created by chenYuXuan on 2019/1/14.
 * email : southxvii@163.com
 */
class MyFragment : Fragment() {

    var rootView: View? = null
    private var data : MainActivity.Info? = null

    companion object {
        val Tag = "MyFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getSerializable("A") as MainActivity.Info
        Log.i(tag(), "onCreate()")
    }

    var text :TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(tag(), "onCreateView()")
        if (rootView == null) {
            Log.i(tag(), "rootView == null")
            rootView = inflater.inflate(R.layout.fragment_my, container, false)
            text = rootView?.findViewById(R.id.tv_okay)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tag(), "onViewCreated()")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(tag(), "setUserVisibleHint() ${isVisibleToUser}")

        text?.text = data?.content
    }

    fun tag(): String {
        return Tag + "," + data
    }
}