package com.prim.gkapp.ui.common

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.R
import kotlinx.android.synthetic.main.item_common_list.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @desc common list adapter
 * @author prim
 * @time 2019-06-15 - 07:34
 * @param layoutRes layout xml id
 * @version 1.0.0
 */
abstract class CommonListAdapter<T>(@LayoutRes val layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = AdapterList<T>(this)

    private var oldPosition = -1

    //companion object 类的半生对象 其实就相当于Java中的静态方法/变量
    /**
     * 相当于Java中的 public static final int ANIMATION_DURATION = 300;
     *
     */
    companion object {
        //定义静态常量
        const val ANIMATION_DURATION = 300L
        const val ANIMATION_CARD_DURATION = 100L
    }

    //相当于Java 中的默认构造方法
    /**
     * public CommonListAdapter() {
     *  this.setHasStableIds(true);
     * }
     */
    init {
        Log.e("CommonListAdapter", "init:" + data.javaClass)
        this.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
//        Log.e("CommonListAdapter", "getItemCount:" + data.size)
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_common_list, parent, false)
        LayoutInflater.from(itemView.context).inflate(layoutRes, itemView.item_container)
        return CommonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindItem(holder, data[position])
        holder.itemView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    ViewCompat.animate(holder.itemView).scaleX(1.03f)
                        .scaleY(1.03f).translationZ(v.dip(10).toFloat()).duration = ANIMATION_CARD_DURATION
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    ViewCompat.animate(holder.itemView).scaleX(1f)
                        .scaleY(1f).translationZ(v.dip(0).toFloat()).duration = ANIMATION_CARD_DURATION
                }
            }
            false
        }
        holder.itemView.onClick {
            onItemClick(holder.itemView, data[position])
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is CommonViewHolder && holder.layoutPosition > oldPosition) {
            ObjectAnimator.ofFloat(holder.itemView, "translationY", 500f, 0f).setDuration(ANIMATION_DURATION).start()
            oldPosition = holder.layoutPosition
        }
    }

    abstract fun onBindItem(holder: RecyclerView.ViewHolder, item: T)

    abstract fun onItemClick(view: View, item: T)

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}