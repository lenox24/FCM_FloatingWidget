package com.example.shinhantest

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView


class FloatingWidgetService : Service(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mWindowManager: WindowManager
    private lateinit var mFloatingWidgetView: View
    private lateinit var collapsedView: View
    private lateinit var expandedView: View
    private lateinit var remove_image_view: ImageView
    private lateinit var removeFloatingWidgetView: View
    private var szWindow: Point = Point()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        addRemoveView(inflater)
        addFloatingWidgetView(inflater)
    }

    @SuppressLint("InflateParams", "RtlHardcoded")
    private fun addRemoveView(inflater: LayoutInflater): View {
        removeFloatingWidgetView = inflater.inflate(R.layout.remove_floating_widget_layout, null)

        val paramsRemove: WindowManager.LayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        paramsRemove.gravity = Gravity.TOP or Gravity.LEFT

        removeFloatingWidgetView.visibility = View.GONE
        remove_image_view = removeFloatingWidgetView.findViewById(R.id.remove_img) as ImageView

        mWindowManager.addView(removeFloatingWidgetView, paramsRemove)

        return remove_image_view
    }

    @SuppressLint("RtlHardcoded", "InflateParams")
    private fun addFloatingWidgetView(inflater: LayoutInflater) {
        mFloatingWidgetView = inflater.inflate(R.layout.floating_widget_layout, null)

        val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        mWindowManager.addView(mFloatingWidgetView, params)
        collapsedView = mFloatingWidgetView.findViewById(R.id.collapse_view)
        expandedView = mFloatingWidgetView.findViewById(R.id.expanded_container)
    }

    private fun getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            mWindowManager.defaultDisplay.getSize(szWindow)
        else {
            val w = mWindowManager.defaultDisplay.width
            val h = mWindowManager.defaultDisplay.height
            szWindow.set(w, h)
        }
    }



    override fun onDestroy() {
        super.onDestroy()

        if (mFloatingWidgetView != null)
            mWindowManager.removeView(mFloatingWidgetView)
        if (removeFloatingWidgetView != null)
            mWindowManager.removeView(removeFloatingWidgetView)
    }
}