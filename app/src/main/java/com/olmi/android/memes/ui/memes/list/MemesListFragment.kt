package com.olmi.android.memes.ui.memes.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.olmi.android.memes.R
import com.olmi.android.memes.data.MEM_INFORMATION
import com.olmi.android.memes.data.exceptions.HttpCallFailureException
import com.olmi.android.memes.data.exceptions.NoNetworkException
import com.olmi.android.memes.data.exceptions.ServerUnreachableException
import com.olmi.android.memes.data.models.Mem
import com.olmi.android.memes.ui.memes.detail.MemDetailActivity
import com.olmi.android.memes.ui.memes.list.adapter.MemesListAdapter
import com.olmi.android.memes.ui.memes.list.adapter.OnItemClickListener
import com.olmi.android.memes.ui.utils.GridSpaceItemDecoration
import com.olmi.android.memes.utils.SharedPreferencesUtils

const val COLUMN_COUNT = 2
const val GRID_SPACE = 24

class MemesListFragment : Fragment(), MemesListView, OnItemClickListener {

    private val TAG = this.javaClass.name
    private lateinit var presenter: MemesListPresenter
    private val adapter = MemesListAdapter(this)

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView: TextView
    private lateinit var swipeContainer: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_memes_list, container, false)
        initViewElements()
        initPresenter()
        initRefreshLayout()

        if (savedInstanceState == null) {
            //TODO when revert screen we again go to server and show progressBar
            showProgressBar(true)
        }

        return rootView
    }

    override fun showProgressBar(show: Boolean) {
        progressBar.isVisible = show
    }

    override fun showLoadMemesError(error: Throwable?) {
        when (error) {
            is NoNetworkException -> showSnackbar(resources.getString(R.string.no_internet_connection_error))
            is ServerUnreachableException -> showSnackbar(resources.getString(R.string.server_unreachable_error))
            is HttpCallFailureException -> showSnackbar(resources.getString(R.string.http_call_error))
            else -> showSnackbar(resources.getString(R.string.login_error))
        }
    }

    override fun isRefreshingShow(show: Boolean) {
        swipeContainer.isRefreshing = show
    }

    private fun initViewElements() {
        recyclerView = rootView.findViewById(R.id.memes_list)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(COLUMN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(
            GridSpaceItemDecoration(GRID_SPACE, COLUMN_COUNT)
        )
        recyclerView.adapter = adapter

        progressBar = rootView.findViewById(R.id.memes_list_progress_bar)
        emptyView = rootView.findViewById(R.id.empty_view)
    }

    private fun initPresenter() {
        presenter = ViewModelProvider(this).get(MemesListPresenter::class.java)
        presenter.onViewCreated(
            this,
            SharedPreferencesUtils.getPrefs(activity!!.applicationContext!!),
            viewLifecycleOwner
        )
        //Sign the adapter for list changes
        presenter.getMemes().observe(viewLifecycleOwner, Observer { result ->
            when {
                result.isFailure -> {
                    showLoadMemesError(result.exceptionOrNull())
                    showProgressBar(false)
                    emptyView.text =
                        context!!.resources.getString(R.string.memes_user_friendly_error)
                }
                result.isSuccess -> {
                    adapter.setData(result.getOrThrow())
                    showProgressBar(false)
                    emptyView.text = ""
                }
            }
        })
    }

    private fun initRefreshLayout() {
        swipeContainer = rootView.findViewById(R.id.swipeMemes)
        swipeContainer.setOnRefreshListener {
            presenter.updateMemes()
        }
    }


    private fun showSnackbar(msg: String) {
        Snackbar.make(
            view!!.findViewById(R.id.memes_list_view),
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onItemClicked(mem: Mem) {
        Log.d(TAG, "Mem was chosen: $mem")
        val intent = Intent(activity, MemDetailActivity::class.java)
        intent.putExtra(MEM_INFORMATION, mem)
        activity?.startActivity(intent)
    }

}
