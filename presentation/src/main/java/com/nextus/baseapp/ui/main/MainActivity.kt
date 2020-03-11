package com.nextus.baseapp.ui.main

import android.view.MotionEvent
import android.widget.Toast
import androidx.arch.core.util.Function
import androidx.recyclerview.selection.*
import com.nextus.baseapp.BR
import com.nextus.baseapp.R
import com.nextus.baseapp.databinding.ActivityMainBinding
import com.nextus.baseapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mMainViewModel : MainViewModel by viewModel()

    private val MAXIMUM_SELECTION = 5
    private lateinit var selectionTracker: SelectionTracker<Long>

    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = gists_list.findChildViewUnder(e.x, e.y)
            if (view != null) {
                return (gists_list.getChildViewHolder(view) as GistListAdapter.ViewHolder).getItemDetails()
            }
            return null
        }
    }

    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<Long>() {

        override fun canSelectMultiple(): Boolean {
            return true
        }

        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
            return if (selectionTracker.selection.size() >= MAXIMUM_SELECTION && nextState) {
                Toast.makeText(this@MainActivity,
                    "You can only select $MAXIMUM_SELECTION items in the list.", Toast.LENGTH_SHORT).show()
                false
            } else {
                true
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return mMainViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun init() {
        setupGistRecycler()
    }

    private fun setupGistRecycler() {
        selectionTracker = SelectionTracker.Builder(
                "mySelection-demo",
                gists_list,
                StableIdKeyProvider(gists_list),
                itemDetailsLookup,
                StorageStrategy.createLongStorage())
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                title = if (selectionTracker.hasSelection()) {
                    "Selection ${selectionTracker.selection.size()} / $MAXIMUM_SELECTION"
                } else {
                    MainActivity::class.java.simpleName
                }
            }
        })

        GistListAdapter(getViewModel()).apply {
            tracker = selectionTracker
            gists_list.adapter = this
        }
    }
}
