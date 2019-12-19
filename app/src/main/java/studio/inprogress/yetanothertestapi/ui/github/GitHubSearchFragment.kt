package studio.inprogress.yetanothertestapi.ui.github

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.fragment_github_search.*
import kotlinx.coroutines.*
import studio.inprogress.yetanothertestapi.R
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.ui.BaseLoader
import studio.inprogress.yetanothertestapi.ui.items.UserItem
import studio.inprogress.yetanothertestapi.utils.EndlessRecyclerViewScrollListener
import studio.inprogress.yetanothertestapi.utils.Resulted
import studio.inprogress.yetanothertestapi.utils.logDebug
import studio.inprogress.yetanothertestapi.utils.showToast

class GitHubSearchFragment : Fragment(), BaseLoader<UserEntity> {

    companion object {
        fun newInstance() =
            GitHubSearchFragment()
    }

    private lateinit var viewModel: GitHubSearchViewModel

    private val adapter = FastItemAdapter<UserItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_github_search, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val searchView =
            menu.findItem(R.id.appSearchBar).actionView as androidx.appcompat.widget.SearchView

        // TODO: отрефакторь меня плес
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            private var queryTextChangedJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                query?.let { viewModel.searchNew(query) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = coroutineScope.launch() {
                    newText?.let {
                        delay(600)
                        if (it.isEmpty()) {
                            viewModel.searchNew(" ")
                        } else {
                            onQueryTextSubmit(newText)
                        }
                    }
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GitHubSearchViewModel::class.java)

        viewModel.loadUsers().observe(this, Observer(::handleResult))
        viewModel.loadNextUsers().observe(this, Observer(::handleResult))

        userList.adapter = adapter

        refresher.setOnRefreshListener { refresher.isRefreshing = false }
        userList.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(userList.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                logDebug { "onLoadMore page $page of total $totalItemsCount" }
                viewModel.next()
            }
        })
    }

    private fun handleResult(resulted: Resulted<List<UserEntity>>) {
        when (resulted) {
            is Resulted.Loading -> showLoading()
            is Resulted.Success -> showData(resulted.data)
            is Resulted.Error -> showError(resulted.message)
        }
    }

    override fun showError(message: String) {
        hideLoading()
        logDebug { message }
        showToast(message)
    }

    override fun showData(data: List<UserEntity>) {
        hideLoading()
        for (user in data) {
            adapter.add(UserItem(user))
        }
    }

    override fun showLoading() {
        refresher.isRefreshing = true
        logDebug { "Loading show" }
    }

    override fun hideLoading() {
        refresher.isRefreshing = false
        logDebug { "Loading hide" }
    }
}
