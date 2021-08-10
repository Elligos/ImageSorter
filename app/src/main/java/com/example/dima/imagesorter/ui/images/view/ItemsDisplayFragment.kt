package com.example.dima.imagesorter.ui.images.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.ui.base.view.BaseFragment
import com.example.dima.imagesorter.ui.images.presenter.ItemsDisplayMVPPresenter
import com.example.dima.imagesorter.util.log
import kotlinx.android.synthetic.main.items_recycler_view.*
import javax.inject.Inject


//TODO: resolve memory leakage (critical)!
class ItemsDisplayFragment : BaseFragment() , ItemsDisplayMVPView, ItemsRecyclerViewAdapter.Callback{

    companion object {
        @JvmStatic
        fun newInstance() : ItemsDisplayFragment {
            "ImageScrollFragment new instance created!".log()
            return ItemsDisplayFragment()
        }
    }


    @Inject
    internal lateinit var presenter: ItemsDisplayMVPPresenter<ItemsDisplayMVPView>


    override fun getContext(): Context? {
        return parentContext
    }

    private var items = ArrayList<RowItem>()
    private lateinit var parentContext: Context
    private lateinit var adapter : ItemsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items = presenter.getInitItems()
        "ImageScrollFragment created!".log()
        return
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.items_recycler_view, container, false)
        val itemsRecyclerView = root.findViewById<RecyclerView>(R.id.items_recycler_view)
        val manager = GridLayoutManager(parentContext, 3)
        itemsRecyclerView?.layoutManager = manager
        adapter = ItemsRecyclerViewAdapter(items)
        itemsRecyclerView?.adapter = adapter
        adapter.setCallback(this)
        //fabReturnToPreviousList.setOnClickListener { view ->  }
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.isGroupTitleItem(position)) manager.spanCount else 1
            }
        }

        "ImageScrollFragment view created!".log()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        fabReturnToPreviousList.hide()
        fabReturnToPreviousList.setOnClickListener {
//            items = presenter.getInitItems()
//            displayItems(items)
            presenter.returnToUpperDirectory()
//            fabReturnToPreviousList.hide()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context != null) parentContext = context
        "ImageScrollFragment attached!".log()
    }

    override fun onDetach() {
        super.onDetach()
        "ImageScrollFragment detached!".log()
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun setUp() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun setUp() = navBackBtn.setOnClickListener { getBaseActivity()?.onFragmentDetached(TAG) }
    //--------------------------ItemsRecyclerViewAdapter.Callback---------------------------------//
    override fun onItemClick(item: RowItem){
        presenter.onItemClick(item)
    }
    //--------------------------------------------------------------------------------------------//

    //-------------------------ItemsDisplayMVPView implementation---------------------------------//
    override fun displayItems(items: ArrayList<RowItem>) {
        adapter.setData(items)
        fabReturnToPreviousList.show()
    }

    override fun hideReturnButton() {
        fabReturnToPreviousList.hide()
    }

    override fun showReturnButton() {
        fabReturnToPreviousList.show()
    }

    //--------------------------------------------------------------------------------------------//
}
