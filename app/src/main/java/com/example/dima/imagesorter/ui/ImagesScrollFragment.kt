package com.example.dima.imagesorter.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.SorterContract
import com.example.dima.imagesorter.SorterPresenter
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.util.getListOfImagesPath
import com.example.dima.imagesorter.util.log


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
class ImagesScrollFragment : Fragment() , SorterContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun getContext(): Context {
        if(parentContext == null) throw Exception("parent Context of ImagesScrollFragment is not ready yet")
        return parentContext
    }

    private var listener: OnFragmentInteractionListener? = null
    private var items = ArrayList<RowItem>()
    private lateinit var parentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val item1 = GroupTitleItem(
                title = "String 1"
        )
        val item2 = GroupTitleItem(
                title = "String 2"
        )
        items.add(item1)
        items.add(item2)
        val imagesPath = getListOfImagesPath(parentContext)
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
        itemsRecyclerView?.layoutManager = LinearLayoutManager(parentContext)
        itemsRecyclerView?.adapter = ItemsRecyclerViewAdapter(items)

        "ImageScrollFragment view created!".log()
        return root
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentContext = context
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

    override var presenter: SorterContract.Presenter
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImagesScrollFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) : ImagesScrollFragment {
            "ImageScrollFragment new instance created!".log()
            return ImagesScrollFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        }
    }
}
