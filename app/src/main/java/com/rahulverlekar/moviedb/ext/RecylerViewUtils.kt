package com.rahulverlekar.moviedb.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahulverlekar.moviedb.BR

class DataBindingVH(view: View) : RecyclerView.ViewHolder(view) {
    lateinit var binding: ViewDataBinding

    companion object {
        fun <E> createVH(parent: ViewGroup, @LayoutRes layoutId: Int): DataBindingVH {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            val viewHolder = DataBindingVH(binding.root)
            viewHolder.binding = binding
            return viewHolder
        }
    }
}

interface RecyclerViewCallback {
    // This will be the base interface for any callback that will be passed to the view holder
    fun onClick(item: ListItem) {
    }
}

interface ListItem {
    fun getType(): Int {
        return 0
    }
}

class LinearLayoutAdapter<E : ListItem>(private val layoutMap: Map<Int, Int>) :
    RecyclerView.Adapter<DataBindingVH>(),
    RecyclerViewCallback {
    var dataSource: List<E> = arrayListOf()
    var callback: RecyclerViewCallback? = null

    constructor(layoutId: Int) : this(mutableMapOf<Int, Int>(Pair(0, layoutId)))

    override fun onBindViewHolder(holder: DataBindingVH, position: Int) {
        if (callback != null) {
            holder.binding.setVariable(BR.callback, callback)
        }
        holder.binding.setVariable(BR.item, dataSource[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return dataSource[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingVH {
        return DataBindingVH.createVH<E>(parent, layoutMap[viewType]!!)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}

// These is an extension functions that will allow us to bind datasource to the recylerview
fun <E : ListItem> RecyclerView.addDataSource(
    dataSource: List<E>,
    @LayoutRes layoutId: Int,
    callback: RecyclerViewCallback?,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, orientation, false)
) {
    val adapter = LinearLayoutAdapter<E>(layoutId)
    adapter.callback = callback
    adapter.dataSource = dataSource
    this.layoutManager = layoutManager
    adapter.notifyDataSetChanged()
    setAdapter(adapter)


}

fun <E : ListItem> RecyclerView.addDataSource(
    dataSource: List<E>,
    layoutMap: Map<Int, Int>,
    callback: RecyclerViewCallback?,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, orientation, false)
) {
    val adapter = LinearLayoutAdapter<E>(layoutMap)
    adapter.callback = callback
    adapter.dataSource = dataSource
    this.layoutManager = layoutManager
    adapter.notifyDataSetChanged()
    setAdapter(adapter)
}

fun <E : ListItem> RecyclerView.addDataSource(
    dataSource: List<E>,
    @LayoutRes layoutId: Int,
    callback: RecyclerViewCallback?,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
) {
    val adapter = LinearLayoutAdapter<E>(layoutId)
    adapter.callback = callback
    adapter.dataSource = dataSource
    layoutManager = LinearLayoutManager(context, orientation, false)
    adapter.notifyDataSetChanged()
    setAdapter(adapter)
}

fun <E : ListItem> RecyclerView.addDataSource(
    dataSource: List<E>,
    layoutMap: Map<Int, Int>,
    callback: RecyclerViewCallback?,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
) {
    val adapter = LinearLayoutAdapter<E>(layoutMap)
    adapter.callback = callback
    adapter.dataSource = dataSource
    layoutManager = LinearLayoutManager(context, orientation, false)
    adapter.notifyDataSetChanged()
    setAdapter(adapter)
}

fun <E : ListItem> RecyclerView.addGridDataSource(
    dataSource: List<E>,
    layoutMap: Map<Int, Int>,
    callback: RecyclerViewCallback?,
    spanCount: Int
) {
    val adapter = LinearLayoutAdapter<E>(layoutMap)
    adapter.callback = callback
    adapter.dataSource = dataSource
    layoutManager = GridLayoutManager(context, spanCount)
    adapter.notifyDataSetChanged()
    setAdapter(adapter)
}

fun <E : ListItem> RecyclerView.addGridDataSource(
    dataSource: List<E>,
    @LayoutRes layoutId: Int,
    callback: RecyclerViewCallback?,
    spanCount: Int
) {
    addGridDataSource(dataSource, mutableMapOf(Pair(0, layoutId)), callback, spanCount)
}

fun <E : ListItem> RecyclerView.changeDataSet(source: List<E>) {
    @Suppress("UNCHECKED_CAST")
    (adapter as? LinearLayoutAdapter<E>)?.dataSource = source
    adapter?.notifyDataSetChanged()
}

data class NoItemWithAction(
    @DrawableRes val image: Int,
    val message: String,
    val actionOneTitle: String?,
    val actionTwoTitle: String?,
    val actionOneColor: Int? = null,
    val actionTwoColor: Int? = null
) : ListItem {
    override fun getType() = NO_ITEM_TYPE

    companion object {
        const val NO_ITEM_TYPE = 999
    }
}

interface NoItemCallback : RecyclerViewCallback {

    fun onPrimaryClicked(item: NoItemWithAction) {
    }

    fun onSecondaryClicked(item: NoItemWithAction)
}
