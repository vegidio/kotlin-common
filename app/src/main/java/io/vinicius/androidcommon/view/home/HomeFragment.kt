package io.vinicius.androidcommon.view.home

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.BaseFragment
import io.vinicius.androidcommon.view.country.CountryFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment()
{
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val adapter = HomeAdapter(arrayOf("REST Countries", "Firebase Authentication"))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater!!.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        // Setting up the RecyclerView
        rvHome.setHasFixedSize(true)
        rvHome.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))
        rvHome.layoutManager = LinearLayoutManager(this.activity)
        rvHome.adapter = adapter
    }

    override fun bindViewModel()
    {
        disposables.add(
                adapter.itemClick.subscribe {
                    FragmentUtil.push(activity, CountryFragment.newInstance(), R.animator.slide_in_right,
                        R.animator.slide_out_right)
                }
        )
    }
}
