package com.gvfiqst.tontestapp.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class SimpleModelAdapter<T>(
        @LayoutRes var layoutResId: Int,
        items: List<T> = emptyList()
) : RecyclerView.Adapter<SimpleModelAdapter.ModelViewHolder<T>>() {

    constructor(
            @LayoutRes layoutResId: Int,
            items: List<T> = emptyList(),
            block: (T, view: View) -> Unit
    ) : this(layoutResId, items) {
        binder = block
    }

    constructor(
            @LayoutRes layoutResId: Int,
            items: List<T> = emptyList(),
            block: (T, view: View, position: Int) -> Unit
    ) : this(layoutResId, items) {
        indexedBinder = block
    }

    private val items: MutableList<T> = ArrayList(items)

    val size: Int get() = items.size

    var binder: ((T, view: View) -> Unit)? = null
        set(value) { field = value; if (value != null) indexedBinder = null }

    var indexedBinder: ((T, view: View, position: Int) -> Unit)? = null
        set(value) { field = value; if (value != null) binder = null }

    @LayoutRes
    var footerResId: Int = 0
    var showFooter = false
        set(value) {
            if (field == value)
                return

            field = value

            if (value) {
                notifyItemInserted(items.size)
            } else {
                notifyItemRemoved(items.size)
            }
        }

    fun add(list: List<T>) {
        val size = items.size
        items += list

        notifyItemRangeInserted(size, list.size)
    }

    fun findIndexBy(predicate: (T) -> Boolean): Int {
        return items.indexOfFirst(predicate)
    }

    fun update(newModel: T, findBy: (T) -> Boolean) {
        val index = findIndexBy(findBy)

        if (index > -1) {
            items[index] = newModel
            notifyItemChanged(index)
        }
    }

    operator fun plusAssign(list: List<T>) = items.let {
        it.clear()
        it += list
        notifyDataSetChanged()
    }

    operator fun get(pos: Int): T = if (pos == items.size) items[pos - 1] else items[pos]

    operator fun contains(elements: List<T>): Boolean = items.containsAll(elements)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<T> {
        return if (viewType == 0)
            ModelViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false), binder, indexedBinder)
        else
            ModelViewHolder(LayoutInflater.from(parent.context).inflate(footerResId, parent, false), { _, _ -> }, { _, _, _ -> })
    }

    override fun getItemViewType(position: Int): Int {
        return if (footerResId == 0 || !showFooter || position < items.size) 0 else 1
    }

    override fun getItemCount() = items.size + if (footerResId == 0 || !showFooter) 0 else 1

    override fun onBindViewHolder(holder: ModelViewHolder<T>, pos: Int) = if (pos < items.size && pos >= 0) holder.bind(pos, items[pos]) else Unit

    class ModelViewHolder<T>(
            itemView: View,
            val binder: ((T, view: View) -> Unit)?,
            val indexedBinder: ((T, view: View, position: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(pos: Int, model: T) {
            indexedBinder?.invoke(model, itemView, pos)
                ?: binder?.invoke(model, itemView)
        }
    }
}