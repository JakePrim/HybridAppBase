package com.prim.gkapp.ui.common

import androidx.recyclerview.widget.RecyclerView

/**
 * @desc adapter list data handle
 * @author prim
 * apply 在当前函数范围内，可以调用对象的开放的任意方法 并返回该对象
 * @time 2019-06-17 - 10:06
 * @version 1.0.0
 */
class AdapterList<T>(val adapter: RecyclerView.Adapter<*>) : ArrayList<T>() {

    override fun add(index: Int, element: T) {
        super.add(index, element).apply {
            adapter.notifyItemInserted(index)
        }
    }

    override fun add(element: T): Boolean {
        return super.add(element).apply {
            adapter.notifyItemInserted(size - 1)
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return super.addAll(elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return super.addAll(index, elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun removeAt(index: Int): T {
        return super.removeAt(index).apply {
            adapter.notifyItemRemoved(index)
        }
    }

    override fun remove(element: T): Boolean {
        val index = indexOf(element)
        return super.remove(element).apply { adapter.notifyItemRemoved(index) }
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex).apply {
            adapter.notifyItemRangeRemoved(fromIndex, toIndex)
        }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return super.removeAll(elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun set(index: Int, element: T): T {
        return super.set(index, element).also {
            adapter.notifyDataSetChanged()
        }
    }

    override fun clear() {
        super.clear().apply {
            adapter.notifyDataSetChanged()
        }
    }

    fun update(elements: Collection<T>) {
        super.clear()
        super.addAll(elements)
        adapter.notifyDataSetChanged()
    }

    fun addMore(elements: Collection<T>) {
        val length = size
        super.addAll(elements)
        if (length > 0) {
            adapter.notifyItemRangeInserted(length, size - length)
        } else {
            adapter.notifyDataSetChanged()
        }
    }

}