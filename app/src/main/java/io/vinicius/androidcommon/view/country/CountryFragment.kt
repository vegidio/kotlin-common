package io.vinicius.androidcommon.view.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.RxView
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.custom.BaseFragment
import io.vinicius.androidcommon.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CountryFragment : BaseFragment()
{
    companion object {
        fun newInstance() = CountryFragment()
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(CountryViewModel::class.java) }
    private var ignoreValue = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_country, container, false)

    override fun bindViewModel()
    {
        super.bindViewModel()

        disposables.addAll(
            RxView.clicks(btRun)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { loadData() },

            viewModel.country.subscribe {
                ignoreValue = it.alpha2Code ?: ""
                tvCountryName.text = it.name
                tvCountryCapital.text = it.capital
            }
        )
    }

    /*
     * Private Methods
     */

    private fun loadData()
    {
        val codes = arrayOf("AS", "BY", "BR", "ES", "GR", "HR", "IR", "LT", "MZ", "PS", "RS", "RU", "SE", "UA")
        val filtered = codes.filter { it != ignoreValue }
        val code = filtered.shuffled().first()

        disposables.add(
            viewModel.getCountryByCode(code).subscribe({}, {
                Timber.e(it, "Error getting the country by code")
            })
        )
    }
}
