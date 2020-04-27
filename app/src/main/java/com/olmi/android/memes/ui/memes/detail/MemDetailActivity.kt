package com.olmi.android.memes.ui.memes.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.olmi.android.memes.R
import com.olmi.android.memes.data.MEM_INFORMATION
import com.olmi.android.memes.data.models.Mem
import com.olmi.android.memes.utils.DateUtils
import com.olmi.android.memes.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.acivity_mem_detail.*
import java.util.*

class MemDetailActivity : AppCompatActivity(), MemDetailView {

    private lateinit var presenter: MemDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_mem_detail)

        presenter = ViewModelProvider(this).get(MemDetailPresenter::class.java)
        presenter.onViewCreated(this, SharedPreferencesUtils.getPrefs(this))

        val mem = intent.getSerializableExtra(MEM_INFORMATION) as Mem
        Log.i("", mem.toString())
        initView(mem)
    }

    private fun initView(mem: Mem) {
        detail_mem_title.text = mem.title
        Glide.with(this)
            .load(mem.photoUrl)
            .into(detail_mem_image)
        detail_mem_date.text = DateUtils.convertLongToTime(
            mem.createdDate,
            getLocale()
        )
        detail_mem_like.isSelected = mem.isFavorite
        detail_mem_like.setOnClickListener { view: View ->
            view.isSelected = !view.isSelected
        }
        detail_mem_description.text = mem.description
    }

    private fun getLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.resources.configuration.locales.get(0)
        } else {
            //noinspection deprecation
            this.resources.configuration.locale
        }
    }
}
