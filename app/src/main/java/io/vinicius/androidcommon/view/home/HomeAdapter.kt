package io.vinicius.androidcommon.view.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.constant.MenuOptions
import kotlinx.android.synthetic.main.row_home.view.*

class HomeAdapter(private val items: Array<MenuOptions>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    val itemClick = PublishSubject.create<MenuOptions>()!!

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MenuOptions) = with(view) {
            view.tvName.text = item.menu
            view.setOnClickListener { itemClick.onNext(item) }
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.row_home, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])
}
