package io.vinicius.androidcommon.screen.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject
import io.vinicius.androidcommon.R
import kotlinx.android.synthetic.main.row_home.view.*

class HomeAdapter(private val items: Array<String>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    val itemClick = PublishSubject.create<String>()

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) = with(view) {
            view.tvName.text = item
            view.setOnClickListener { _ -> itemClick.onNext(item) }
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.row_home, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])
}