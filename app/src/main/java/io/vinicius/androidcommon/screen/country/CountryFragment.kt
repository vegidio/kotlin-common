package io.vinicius.androidcommon.screen.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_country.*
import java.util.concurrent.TimeUnit

class CountryFragment : BaseFragment()
{
    private val viewModel = CountryViewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater!!.inflate(R.layout.fragment_country, container, false)

    override fun bindViewModel()
    {
        disposables.add(
                RxView.clicks(btRun)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe { loadData() }
        )

        disposables.addAll(
                viewModel.name.subscribe { value -> tvCountryName.text = value },
                viewModel.capital.subscribe { value -> tvCountryCapital.text = value }
        )
    }

    private fun loadData()
    {
        disposables.add(
                viewModel.getCountryByCode("SE").subscribe({}, {})
        )
    }
}