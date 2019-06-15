package com.prim.gkapp.ui.common

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc common list adapter
 * @author prim
 * @time 2019-06-15 - 07:34
 * @param layoutRes layout xml id
 * @version 1.0.0
 */
abstract class CommonListAdapter<T>(@LayoutRes layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //companion object 类的半生对象 其实就相当于Java中的静态方法/变量
    /**
     * 相当于Java中的 public static final int ANIMATION_DURATION = 300;
     *
     */
    companion object {
        //定义静态常量
        const val ANIMATION_DURATION = 300
    }

    //相当于Java 中的默认构造方法
    /**
     * public CommonListAdapter() {
     *  this.setHasStableIds(true);
     * }
     */
    init {
        this.setHasStableIds(true)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(, parent, false)
//        return CommonViewHolder(view)
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class CommonViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView);
}