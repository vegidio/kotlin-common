package io.vinicius.androidcommon.view.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.view.BaseFragment
import io.vinicius.androidcommon.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryFragment : BaseFragment()
{
    companion object {
        fun newInstance() = CountryFragment()
    }

    @Inject
    lateinit var viewModel: CountryViewModel

    init { App.component.inject(this) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater!!.inflate(R.layout.fragment_country, container, false)

    override fun bindViewModel()
    {
        disposables.addAll(
                RxView.clicks(btRun)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe { loadData() },

                viewModel.country.subscribe {
                    tvCountryName.text = it.name
                    tvCountryCapital.text = it.capital
                }
        )
    }

    private fun loadData()
    {
        disposables.add(
                viewModel.getCountryByCode("SE").subscribe({}, {})
        )
    }
}
