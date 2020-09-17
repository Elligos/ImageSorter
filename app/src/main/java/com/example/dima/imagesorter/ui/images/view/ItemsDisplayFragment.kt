package com.example.dima.imagesorter.ui.images.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.providers.ImagePathfinder
import com.example.dima.imagesorter.ui.SorterContract
import com.example.dima.imagesorter.ui.base.view.BaseFragment
import com.example.dima.imagesorter.ui.images.presenter.ItemsDisplayMVPPresenter
import com.example.dima.imagesorter.util.getListOfImagesPath
import com.example.dima.imagesorter.util.log
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImagesScrollFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImagesScrollFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ImagesScrollFragment : BaseFragment() , ItemsDisplayMVPView, ItemsRecyclerViewAdapter.Callback, SorterContract.View {

    @Inject
    internal lateinit var presenter: ItemsDisplayMVPPresenter<ItemsDisplayMVPView>


    override fun getContext(): Context? {
        if(parentContext == null) throw Exception("parent Context of ImagesScrollFragment is not ready yet")
        return parentContext
    }

    private var listener: OnFragmentInteractionListener? = null
    private var items = ArrayList<RowItem>()
    private lateinit var parentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        items = presenter.getItems()
        return
        val item1 = GroupTitleItem(
                "String 1"
        )
        val item2 = GroupTitleItem(
                "String 2"
        )
        val item3 = DirectoryItem(
                "",
                "Directory"
        )
        items.add(item1)
        items.add(item2)
        items.add(item3)
        items.add(item2)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item2)
        val imagesPath = getListOfImagesPath(parentContext)

//        getListOfImageBuckets(parentContext)
        ImagePathfinder.getImageFoldersFromExternal(parentContext)
        ImagePathfinder.getImageFolderPathsFromExternal(parentContext)
//        for(imagePath in imagesPath){
//            val item = GroupTitleItem(
//                    title = imagePath
//            )
//            items.add(item)
//        }
        for(imagePath in imagesPath){
            val item = ImageItem(
                    info = imagePath,
                    path = imagePath
            )
            items.add(item)
        }
        "ImageScrollFragment created!".log()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.items_recycler_view, container, false)
        val itemsRecyclerView = root.findViewById<RecyclerView>(R.id.items_recycler_view)
//        items_recycler_view?.layoutManager = LinearLayoutManager(parentContext)
//        items_recycler_view?.adapter = ItemsRecyclerViewAdapter(items, parentContext)
        val manager = GridLayoutManager(parentContext, 3)
        itemsRecyclerView?.layoutManager = manager//GridLayoutManager(parentContext, 3)//LinearLayoutManager(parentContext)
        val adapter = ItemsRecyclerViewAdapter(items)
        itemsRecyclerView?.adapter = adapter//ItemsRecyclerViewAdapter(items)
        adapter.setCallback(this)

        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //return if (adapter.isHeader(position)) manager.spanCount else 1
                return if(adapter.getItemViewType(position) == 2)  manager.spanCount else 1
            }
        }

        "ImageScrollFragment view created!".log()
        return root
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context != null) parentContext = context
        "ImageScrollFragment attached!".log()
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        "ImageScrollFragment detached!".log()
//        listener = null
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showImages() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSortedImages(items: List<RowItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() : ImagesScrollFragment {
            "ImageScrollFragment new instance created!".log()
            return ImagesScrollFragment()
        }
    }

    override fun setUp() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(item: RowItem){
        presenter.onItemClick(item)
    }

//    override fun setUp() = navBackBtn.setOnClickListener { getBaseActivity()?.onFragmentDetached(TAG) }
}
