package io.vinicius.androidcommon.view

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.vinicius.androidcommon.R
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater!!.inflate(R.layout.fragment_country, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel()
    {
        tvCountryName.text = "Filho"
        tvCountryCapital.text = "Da Puta"
    }
}