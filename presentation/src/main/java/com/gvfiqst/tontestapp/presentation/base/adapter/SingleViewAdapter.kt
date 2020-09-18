package com.gvfiqst.tontestapp.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


typealias CreateView<V> = (parent: ViewGroup) -> V
typealias BindView<V, Data> = (view: V, data: Data, position: Int) -> Unit

class SingleViewAdapter<V : View, Data> private constructor(
    private val createView: CreateView<V>,
    private val bindView: BindView<V, Data>
) : RecyclerView.Adapter<SingleViewAdapter.SingleViewHolder<V>>() {

    private val items: MutableList<Data> = mutableListOf()

    val size: Int get() = items.size

    fun setNewData(newData: List<Data>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged() // TODO: Vlad Greschuk 18/09/2020 Use DiffUtil
    }

    fun add(list: List<Data>) {
        val size = items.size
        items += list

        notifyItemRangeInserted(size, list.size)
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder<V> {
        val view = createView(parent)
        return SingleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: SingleViewHolder<V>, position: Int) {
        val view = holder.view
        val data = items.getOrNull(position) ?: return
        bindView(view, data, position)
    }

    class SingleViewHolder<V : View>(val view: V) : RecyclerView.ViewHolder(view)

    interface Builder<V : View, Data> {
        fun withData(list: List<Data>): Builder<V, Data>
        fun layoutResId(@LayoutRes layoutResId: Int): Builder<V, Data>
        fun createView(block: CreateView<V>): Builder<V, Data>
        fun bindView(block: BindView<V, Data>): Builder<V, Data>
        fun build(): SingleViewAdapter<V, Data>
    }

    companion object {

        fun <V : View, Data> create() = object : Builder<V, Data> {
            private var createView: CreateView<V>? = null
            private var bindView: BindView<V, Data>? = null
            private var initialData: List<Data>? = null

            override fun withData(list: List<Data>): Builder<V, Data> {
                initialData = list
                return this
            }

            override fun layoutResId(layoutResId: Int): Builder<V, Data> {
                return createView { parent ->
                    LayoutInflater.from(parent.context)
                        .inflate(layoutResId, parent, false) as V
                }
            }

            override fun createView(block: CreateView<V>): Builder<V, Data> {
                createView = block
                return this
            }

            override fun bindView(block: BindView<V, Data>): Builder<V, Data> {
                bindView = block
                return this
            }

            override fun build(): SingleViewAdapter<V, Data> {
                val createViewBlock = createView
                    ?: throw IllegalArgumentException("createView() or layoutResId() should be called")
                createView = null

                val bindViewBlock = bindView
                    ?: throw IllegalArgumentException("bindView() should be called")
                bindView = null

                val adapter = SingleViewAdapter<V, Data>(createViewBlock, bindViewBlock)
                if (initialData != null) {
                    adapter.setNewData(initialData!!)
                    initialData = null
                }

                return adapter
            }

        }

    }

}
