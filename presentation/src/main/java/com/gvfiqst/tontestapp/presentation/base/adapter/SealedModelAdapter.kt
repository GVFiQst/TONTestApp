package com.gvfiqst.tontestapp.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SealedModelAdapter<Model : Any> private constructor(
    private val registry: List<ItemRegistry<out Model>>,
    private val fallback: ItemRegistry<out Model>?,
    private val list: MutableList<Model>
) : RecyclerView.Adapter<SealedModelAdapter.ModelViewHolder>() {

    private var inflater: LayoutInflater? = null
    private val safeInflater
        get() = inflater ?: throw IllegalStateException("Adapter is not atteched to RecyclerVIew")

    val size get() = list.size
    var forceNotifyDataSetChanged = false

    fun setItems(newItems: List<Model>) {
        val oldSize = list.size
        list.clear()
        if (!forceNotifyDataSetChanged && oldSize > 0) {
            notifyItemRangeRemoved(0, oldSize)
        }

        list += newItems
        if (forceNotifyDataSetChanged || oldSize == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(0, newItems.size)
        }
    }

    fun addItems(at: Int? = null, nextItems: List<Model>) {
        val index = at ?: list.size
        list.addAll(index, nextItems)

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(index, nextItems.size)
        }
    }

    fun clearItems() {
        val oldSize = list.size
        list.clear()

        if (oldSize == 0) {
            return
        }

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, oldSize)
        }
    }

    fun removeItemsAt(range: IntRange) {
        for (index in range) {
            list.removeAt(index)
        }

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(range.first, range.last)
        }
    }

    fun removeItemAt(index: Int) {
        list.removeAt(index)

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(index)
        }
    }

    fun removeItem(item: Model) {
        val itemIndex = list.indexOf(item)

        if (list.remove(item)) {
            if (forceNotifyDataSetChanged || itemIndex < 0) {
                notifyDataSetChanged()
            } else {
                notifyItemRemoved(itemIndex)
            }
        }
    }

    fun updateItemAt(index: Int, item: Model) {
        list[index] = item

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(index)
        }
    }

    fun updateItem(item: Model) {
        val index = list.indexOf(item)
        if (index > -1) {
            updateItemAt(index, item)
        }
    }

    fun notifyUpdate() {
        if (list.isEmpty()) {
            return
        }

        if (forceNotifyDataSetChanged) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, list.size)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        inflater = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val itemRegistry = registry
                .find { it.viewType == viewType }
                ?: fallback
                ?: throw IllegalStateException("Can't find itemRegistry for viewType: $viewType")

        return ModelViewHolder(itemRegistry, itemRegistry.createView(safeInflater, parent))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        val model = list[position]
        val itemRegistry = registry
                .find { it.isAcceptable(model) }
                ?: fallback
                ?: throw IllegalStateException("Can't find itemRegistry for model type: ${model.javaClass.name}")

        return itemRegistry.viewType
    }

    class ModelViewHolder(private val itemRegistry: ItemRegistry<*>, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(model: Any) {
            itemRegistry.bindView(itemView, model)
        }
    }

    @DslMarker
    annotation class SealedModelAdapterDsl

    @SealedModelAdapterDsl
    class Builder<Model : Any>(val clazz: Class<Model>) {
        private var viewTypeCounter = 1
        private val registry = ArrayList<ItemRegistry<out Model>>()
        private var fallback: ItemRegistry<out Model>? = null
        private val list = mutableListOf<Model>()

        fun <Item : Model> addItem(
            clazz: Class<Item>,
            viewCreator: ViewCreator,
            binder: ViewBinder<Item>
        ) {
            val indexOfClazz = registry.indexOfFirst { it.clazz == clazz }
            if (indexOfClazz > -1) {
                registry.removeAt(indexOfClazz)
            }

            registry += ItemRegistry(clazz, viewTypeCounter++, viewCreator, binder, false)
        }

        fun addFallback(
            viewCreator: ViewCreator,
            binder: ViewBinder<Model>
        ) {
            fallback = ItemRegistry(clazz, 0, viewCreator, binder, true)
        }

        fun fallback(@LayoutRes layoutId: Int, block: (view: View, item: Model) -> Unit) {
            addFallback(LayoutIdViewCreator(layoutId), LambdaViewBinder(block))
        }

        inline fun fallback(init: ItemRegistryBuilder<Model>.() -> Unit) {
            val builder = ItemRegistryBuilder(clazz)
                    .apply(init)

            addFallback(builder.creator, builder.binder)
        }

        inline fun <reified Item : Model> on(@LayoutRes layoutId: Int, noinline block: (view: View, item: Item) -> Unit) {
            addItem(Item::class.java, LayoutIdViewCreator(layoutId), LambdaViewBinder(block))
        }

        inline fun <reified Item : Model> on(init: ItemRegistryBuilder<Item>.() -> Unit) {
            val c = Item::class.java
            val builder = ItemRegistryBuilder(c)
                    .apply(init)

            addItem(c, builder.creator, builder.binder)
        }

        fun items(items: List<Model>) {
            list.clear()
            list += items
        }

        fun build() = SealedModelAdapter(registry, fallback, list)
    }

    @SealedModelAdapterDsl
    data class ItemRegistryBuilder<Model : Any>(val clazz: Class<Model>) {
        private var viewCreator: ViewCreator? = null
        private var viewBinder: ViewBinder<Model>? = null

        fun createView(creator: ViewCreator) {
            viewCreator = creator
        }

        fun inflateView(block: (inflater: LayoutInflater, parent: ViewGroup) -> View) {
            viewCreator = CustomViewCreator(block)
        }

        fun layoutId(layoutId: Int) {
            viewCreator = LayoutIdViewCreator(layoutId)
        }

        fun bindView(binder: ViewBinder<Model>) {
            viewBinder = binder
        }

        fun bindView(block: (view: View, item: Model) -> Unit) {
            viewBinder = LambdaViewBinder(block)
        }

        val creator get() = viewCreator ?: throw IllegalStateException("no view creator")
        val binder get() = viewBinder ?: throw IllegalStateException("no binder")
    }

    interface ViewCreator {

        fun createView(inflater: LayoutInflater, parent: ViewGroup): View
    }

    class CustomViewCreator(
            val block: (inflater: LayoutInflater, parent: ViewGroup) -> View
    ) : ViewCreator {

        override fun createView(inflater: LayoutInflater, parent: ViewGroup): View {
            return block(inflater, parent)
        }
    }

    data class LayoutIdViewCreator(val layoutId: Int) : ViewCreator {

        override fun createView(inflater: LayoutInflater, parent: ViewGroup): View {
            return inflater.inflate(layoutId, parent, false)
        }
    }

    interface ViewBinder<T> {

        fun bindView(view: View, model: T)
    }

    class LambdaViewBinder<Item>(val block: (view: View, item: Item) -> Unit) : ViewBinder<Item> {

        override fun bindView(view: View, model: Item) {
            block(view, model)
        }
    }

    data class ItemRegistry<Model : Any>(
        val clazz: Class<Model>,
        val viewType: Int,
        val viewCreator: ViewCreator,
        val viewBinder: ViewBinder<Model>,
        private val isFallback: Boolean
    ) {

        fun createView(inflater: LayoutInflater, parent: ViewGroup): View {
            return viewCreator.createView(inflater, parent)
        }

        fun bindView(view: View, model: Any) {
            if (isAcceptable(model)) {
                @Suppress("UNCHECKED_CAST")
                viewBinder.bindView(view, model as Model)
                return
            }

            throw IllegalArgumentException("model of type ${model.javaClass.simpleName} is not instance of expected class ${clazz.name}")
        }

        fun isAcceptable(model: Any): Boolean = isFallback || clazz.isInstance(model)
    }

    companion object {

        inline fun <reified Model : Any> create(block: Builder<Model>.() -> Unit): SealedModelAdapter<Model> {
            return Builder(Model::class.java).apply(block).build()
        }
    }
}