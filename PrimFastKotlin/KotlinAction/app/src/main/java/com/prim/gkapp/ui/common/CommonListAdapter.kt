package com.prim.gkapp.ui.common

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.prim.gkapp.R
import com.prim.gkapp.config.Config
import com.prim.lib_base.utils.otherwise
import com.prim.lib_base.utils.textRes
import com.prim.lib_base.utils.yes
import kotlinx.android.synthetic.main.item_common_list.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

/**
 * @desc common list adapter
 * @author prim
 * @time 2019-06-15 - 07:34
 * @param layoutRes layout xml id
 * @param layoutRes footerRes layout xml id,
 * @param isOpenLoadMore impl load more adapter,default is false.if need load more data,please
 * isOpenLoadMore set true
 * @version 1.0.0
 */
abstract class CommonListAdapter<T>(
    @LayoutRes val layoutRes: Int,
    private val isOpenLoadMore: Boolean = false,
    @LayoutRes val footerRes: Int = R.layout.item_footer_layout
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = AdapterList<T>(this)

    private var oldPosition = -1

    private var onLoadMoreListener: OnLoadMoreListener? = null

    private var isAutoLoadMoreEnd: Boolean = false

    lateinit var footerViewHolder: FooterViewHolder

    //companion object 类的半生对象 其实就相当于Java中的静态方法/变量
    /**
     * 相当于Java中的 public static final int ANIMATION_DURATION = 300;
     *
     */
    companion object {
        //定义静态常量
        const val ANIMATION_DURATION = 300L
        const val ANIMATION_CARD_DURATION = 100L
        const val TYPE_FOOTER_VIEW = 10
        const val TYPE_COMMON_VIEW = 11
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_common_list, parent, false)
        when (viewType) {
            TYPE_FOOTER_VIEW -> {
                itemView = LayoutInflater.from(parent.context).inflate(footerRes, parent, false)
                return FooterViewHolder(itemView)
            }
            TYPE_COMMON_VIEW -> {
                LayoutInflater.from(itemView.context).inflate(layoutRes, itemView.item_container)
            }
        }
        return CommonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = holder.itemViewType
        isCommonView(itemViewType).yes {
            onBindItem(holder, data[position])
            holder.itemView.setOnTouchListener { v, event ->
                if (Config.itemClickAnimator) {
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
                }
                false
            }
            holder.itemView.onClick {
                onItemClick(holder.itemView, data[position])
            }
            holder.itemView.onLongClick {
                onItemLongClick(holder.itemView, data[position])
            }
        }.otherwise {
            isFooterTypeView(itemViewType).yes {
                if (holder is FooterViewHolder) {
                    footerViewHolder = holder
                    holder.onBindItem()
                    holder.itemView.onClick {
                        onLoadErrorClick(holder.itemView)
                    }
                }
            }
        }

    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is CommonViewHolder && holder.layoutPosition > oldPosition && Config.listScrollAnimator) {
            ObjectAnimator.ofFloat(holder.itemView, "translationY", 500f, 0f).setDuration(ANIMATION_DURATION).start()
            oldPosition = holder.layoutPosition
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        startListenerLoadMore(recyclerView)
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    private fun startListenerLoadMore(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (isOpenLoadMore && onLoadMoreListener != null) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val findLastVisibleItemPosition = findLastVisibleItemPosition(layoutManager)
                    if (isAutoLoadMoreEnd && findLastVisibleItemPosition + 1 == itemCount) {
                        scrollLoadMore()
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (findLastVisibleItemPosition(layoutManager) + 1 == itemCount) {
                        if (isAutoLoadMore && !isAutoLoadMoreEnd) {
                            scrollLoadMore()
                        } else if (!isAutoLoadMoreEnd) {
                            isAutoLoadMoreEnd = true
                            //数据加载完成
                        }
                    } else {
                        isAutoLoadMoreEnd = true
                    }
                }
            })
        }

    }

    private var isAutoLoadMore: Boolean = false

    /**
     * 开启初次数据不满一屏自动加载更多
     */
    fun openAutoLoadMore() {
        this.isAutoLoadMore = true
    }

    /**
     * 执行加载更多数据
     */
    private fun scrollLoadMore() {
        isNotMore.yes {
            isAutoLoadMoreEnd = false
            onLoadMoreListener?.onLoadMore()
        }
    }

    fun setOnLoadMoreListeners(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    private fun findLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
            return findMax(lastVisibleItemPositions)
        }
        return -1
    }

    fun findMax(lastVisiblePositions: IntArray): Int {
        var max = lastVisiblePositions[0]
        for (value in lastVisiblePositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        if (data.isEmpty() && isOpenLoadMore) {
            return 1
        }

        return data.size + getFooterViewCount()
    }

    private fun getFooterViewCount(): Int {
        return if (isOpenLoadMore && !data.isEmpty()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW
        }
        return TYPE_COMMON_VIEW
    }

    fun getItem(position: Int): T? {
        if (data.isEmpty()) {
            return null
        }
        return data[position]
    }

    private fun isFooterView(position: Int): Boolean {
        return isOpenLoadMore && position >= itemCount - 1
    }

    private fun isFooterTypeView(viewType: Int): Boolean {
        return viewType == TYPE_FOOTER_VIEW
    }


    private fun isCommonView(viewType: Int): Boolean {
        return viewType == TYPE_COMMON_VIEW
    }


    abstract fun onBindItem(holder: RecyclerView.ViewHolder, item: T)

    abstract fun onItemClick(view: View, item: T)

    abstract fun onItemLongClick(view: View, item: T)

    open fun onLoadErrorClick(view: View) {

    }

    open class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 底部加载更多viewHolder 实现
     */
    class FooterViewHolder(itemView: View) : CommonViewHolder(itemView) {
        lateinit var title: TextView
        lateinit var pd: ProgressBar
        fun onBindItem() {
            title = itemView.find(R.id.item_tv_loading)
            pd = itemView.find(R.id.item_pb_loading)
        }

        private fun visibleFooter() {
            (itemView.visibility != VISIBLE).yes {
                itemView.visibility = VISIBLE
            }
        }

        fun onNext() {
            visibleFooter()
            pd.visibility = VISIBLE
            title.textRes(R.string.str_loading)
        }

        fun onNotMore() {
            visibleFooter()
            pd.visibility = GONE
            title.textRes(R.string.str_loaded)
        }

        fun onError(@StringRes res: Int) {
            visibleFooter()
            pd.visibility = GONE
            title.textRes(res)
        }
    }

    fun onNextMore() {
        isNotMore = true
        if (footerViewHolder != null) {
            footerViewHolder.onNext()
        }
    }

    var isNotMore = true //标记是否还有更多数据

    fun onNotMore() {
        isNotMore = false
        if (footerViewHolder != null) {
            footerViewHolder.onNotMore()
        }
    }

    fun onErrorMore(@StringRes res: Int = R.string.str_load_error) {
        if (footerViewHolder != null) {
            footerViewHolder.onError(res)
        }
    }
}