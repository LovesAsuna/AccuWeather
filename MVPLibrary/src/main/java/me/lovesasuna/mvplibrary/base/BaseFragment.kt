package me.lovesasuna.mvplibrary.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


/**
 * @author LovesAsuna
 * @date 2020/10/14 15:47
 **/
abstract class BaseFragment : Fragment(), UICallBack {
    var rootView: View? = null

    var context: Activity? = null

    @get:JvmName("getLayoutInflaterKt")
    var layoutInflater: LayoutInflater? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBeforeView(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutInflater = inflater
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null)
        } else {
            val viewGroup = rootView!!.parent as ViewGroup
            viewGroup.removeView(rootView)
        }
        return rootView
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            this.context = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        context = null
    }

    override fun initBeforeView(savedInstanceState: Bundle?) {}
}