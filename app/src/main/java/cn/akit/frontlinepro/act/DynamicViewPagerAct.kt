package cn.akit.frontlinepro.act

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import cn.akit.frontlinepro.R
import cn.akit.frontlinepro.common.base.BaseAct
import com.socks.library.KLog
import kotlinx.android.synthetic.main.act_dynamic_view_pager.*
import kotlinx.android.synthetic.main.fragment_dynamic.*

/**
 * Created by chenYuXuan on 2019/5/14.
 * email : southxvii@163.com
 */
class DynamicViewPagerAct : BaseAct() {

    override fun init() {

//        val data = ArrayList<Int>()
////
//        initListData(data)

        val fragments = ArrayList<Fragment>()
        fragments.add(MyFragment.newInstance(5))

        val pagerAdapter = DynamicFragmentAdapter(supportFragmentManager, fragments)

//            override fun getItem(position: Int): Fragment {
//                return MyFragment.newInstance(data[position])
//            }
//
//            override fun getCount(): Int {
//                return data.size
//            }
//
//            override fun getItemPosition(@NonNull `object`: Any): Int {
//                return POSITION_NONE
//            }


        view_pager.adapter = pagerAdapter


        view_pager.postDelayed(Runnable {
            var preCount = 0
            repeat(10) {
                preCount++
//                data.add(0, it)
                val newInstance = MyFragment.newInstance(it)
                newInstance.setOnClickListener(View.OnClickListener {
                    fragments.removeAt(0)
                    pagerAdapter.updateData(fragments)
                })
                fragments.add(0, newInstance)
            }

//            repeat(10) {
//                //                data.add(data.size, it)
//                val newInstance = MyFragment.newInstance(it)
//                newInstance.setOnClickListener(View.OnClickListener {
//                    //删除自己
//                    fragments.remove(newInstance)
//                    pagerAdapter.updateData(fragments)
//                })
//                fragments.add(fragments.size, newInstance)
//            }

            pagerAdapter.updateData(fragments)
//            view_pager.currentItem = preCount
        }, 500)
    }

    private fun initListData(data: ArrayList<Int>) {
        data.add(5)
    }

    override fun provideViewRes(): Int {
        return R.layout.act_dynamic_view_pager
    }

    class MyFragment : Fragment() {

        companion object {
            fun newInstance(position: Int): MyFragment {
                val fragment = MyFragment()
                val bundle = Bundle()
                bundle.putInt("Position", position)
                fragment.arguments = bundle
                return fragment
            }
        }

        private var listener: View.OnClickListener = View.OnClickListener {
            //
            KLog.debug()
        }

        fun setOnClickListener(listener: View.OnClickListener) {
            this.listener = listener
        }

        var position: Int = 0

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            arguments?.getInt("Position")?.let {
                position = it
            }

            return inflater.inflate(R.layout.fragment_dynamic, container, false)
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            tv_content.text = position.toString()
            tv_content.setOnClickListener(listener)
        }
    }

    class DynamicFragmentAdapter(private val mFragmentManager: FragmentManager, list: List<Fragment>?) : FragmentStatePagerAdapter(mFragmentManager) {

        private val mFragments = ArrayList<Fragment>()

        init {
            if (list != null)
                this.mFragments.addAll(list)
        }

        fun updateData(mlist: List<Fragment>?) {
            if (mlist == null) return
            this.mFragments.clear()
            this.mFragments.addAll(mlist)
            notifyDataSetChanged()
        }

        override fun getItem(arg0: Int): Fragment {
            return mFragments[arg0]//
        }

        override fun getCount(): Int {
            return mFragments.size//
        }

        override fun saveState(): Parcelable? {
            return null
        }

        override fun getItemPosition(`object`: Any): Int {
            return if (!(`object` as Fragment).isAdded || !mFragments.contains(`object`)) {
                PagerAdapter.POSITION_NONE
            } else mFragments.indexOf(`object`)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val instantiateItem = super.instantiateItem(container, position) as Fragment
            val item = mFragments[position]
            if (instantiateItem === item) {
                return instantiateItem
            } else {
                //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
                mFragmentManager.beginTransaction().add(container.id, item).commitNowAllowingStateLoss()
                return item
            }
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val fragment = `object` as Fragment
            //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
            if (mFragments.contains(fragment)) {
                super.destroyItem(container, position, fragment)

                return
            }
            //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
            mFragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss()

        }
    }

}