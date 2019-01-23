package com.ranjitzade.litehttp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.snackbar.Snackbar
import com.ranjitzade.litehttp.lib.LiteHttp
import com.ranjitzade.litehttp.lib.core.Method
import com.ranjitzade.litehttp.lib.core.response.ErrorResponse
import com.ranjitzade.litehttp.lib.httploader.IHttpListener
import com.ranjitzade.litehttp.models.Pinterest
import com.ranjitzade.litehttp.utils.Constants
import com.ranjitzade.litehttp.utils.SpacesItemDecoration

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, IHttpListener<List<Pinterest>> {

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipe_refresh_layout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.root_view)
    lateinit var rootView: View

    private var unbinder: Unbinder? = null

    private var mAdapter: PinterestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbinder = ButterKnife.bind(this)
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener(this)

        // recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = PinterestAdapter(this)
        recyclerView.adapter = mAdapter
        val decoration = SpacesItemDecoration(16)
        recyclerView.addItemDecoration(decoration)

        fetchData()
    }

    private fun fetchData() {
        LiteHttp.httpLoader(this).url(Constants.URL)
                .method(Method.GET)
                .clazz(Pinterest::class.java)
                .listener(this).execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onSuccess(t: List<Pinterest>) {
        swipeRefreshLayout.isRefreshing = false
        if (t.isNotEmpty()) {
            mAdapter!!.pinterestList = t
        }
    }

    override fun onError(errorResponse: ErrorResponse) {
        swipeRefreshLayout.isRefreshing = false
        Snackbar.make(rootView, errorResponse.message!!, Snackbar.LENGTH_SHORT).show()
    }
}