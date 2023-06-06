package com.edwnmrtnz.trendingrepo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwnmrtnz.trendingrepo.R
import com.edwnmrtnz.trendingrepo.databinding.ActivityMainBinding
import com.github.amaterasu.scopey.scopey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainScreenView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TrendingRepoAdapter

    @Inject
    lateinit var provider: Provider<MainPresenter>

    private val presenter by scopey {
        provider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        adapter = TrendingRepoAdapter()
        binding.rvGithubRepos.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvGithubRepos.addItemDecoration(
            RecyclerViewItemDecoration(this, R.drawable.shape_divider)
        )
        binding.rvGithubRepos.adapter = adapter
        binding.btnRetry.setOnClickListener { presenter.onRetryAction() }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun render(state: MainUiState) {
        if (state.isLoading) {
            binding.llEmptyStateContainer.visibility = View.GONE
            binding.sflMainShimmer.sflMainShimmer.visibility = View.VISIBLE
            binding.rvGithubRepos.visibility = View.GONE
            return
        }

        if (!state.loadError.isNullOrBlank()) {
            binding.rvGithubRepos.visibility = View.GONE
            binding.sflMainShimmer.sflMainShimmer.visibility = View.GONE
            binding.llEmptyStateContainer.visibility = View.VISIBLE

            binding.tvEmptyStateTitle.text = getString(R.string.title_detault_error)
            binding.tvEmptyStateSubtitle.text = getString(R.string.subtitle_default_error)
            return
        }

        if (state.repos.isNotEmpty()) {
            adapter.submitList(state.repos)
            binding.rvGithubRepos.visibility = View.VISIBLE
            binding.sflMainShimmer.sflMainShimmer.visibility = View.GONE
            binding.llEmptyStateContainer.visibility = View.GONE
        } else {
            adapter.submitList(mutableListOf())
            binding.rvGithubRepos.visibility = View.GONE
            binding.sflMainShimmer.sflMainShimmer.visibility = View.GONE
            binding.llEmptyStateContainer.visibility = View.VISIBLE

            binding.tvEmptyStateTitle.text = getString(R.string.title_detault_error)
            binding.tvEmptyStateSubtitle.text = getString(R.string.subtitle_error_no_trending)
        }
    }
}
