package com.example.dima.imagesorter.ui.groups.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.ui.groups.presenter.GroupPickerMVPPresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayFragment
import com.example.dima.imagesorter.util.log
import dagger.android.DaggerDialogFragment
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

//TODO: resolve memory leackage!
class GroupPickerFragment : /*DaggerDialogFragment(),*/ DaggerAppCompatDialogFragment(), GroupPickerMVPView, AdapterView.OnItemSelectedListener {

    private lateinit var parentContext: Context

    val groupsArray = arrayOf("App", "Date", "Size")

    companion object {
        //@JvmStatic
        fun newInstance() : GroupPickerFragment {
            "GroupPickerFragment new instance created!".log()
            return GroupPickerFragment()
        }
    }

    @Inject
    internal lateinit var presenter: GroupPickerMVPPresenter<GroupPickerMVPView>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val view = LayoutInflater.from(parentContext).inflate(R.layout.dialog_group_picker, null)
        //val view = parentContext.layoutInflater.inflate(R.layout.dialog_group_picker, null)
        val builder =  AlertDialog.Builder(parentContext)
        //setupSpinner(view)
        presenter.onAttach(this)
        presenter.init()
        setupSpinner(view)
        val title = getCustomTitle()


        with(builder){
            setView(view)
            setCustomTitle(title)
            setNegativeButton(
                    android.R.string.cancel,
                    DialogInterface.OnClickListener(function = negativeButtonClick)
            )
            setPositiveButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener(function = positiveButtonClick)
            )
        }
        return builder.create()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "GroupPickerView created!".log()
 //       presenter.onAttach(this)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        "GroupPickerView destroyed!".log()
        super.onDestroyView()
    }

    override fun onDestroy() {
        "GroupPickerFragment destroyed!".log()
        super.onDestroy()
    }

    private fun getCustomTitle() : TextView{
        val title = TextView(parentContext)

        title.text = getResources().getString(R.string.group_picker_title_text)
        title.gravity = Gravity.CENTER
        title.textSize = 18f
        title.setBackgroundColor((Color.LTGRAY))
        title.setTextColor(Color.BLACK)

        return title
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context != null) parentContext = context
        "GroupPickerFragment attached!".log()
    }

    override fun onDetach() {
        super.onDetach()
        "GroupPickerFragment detached!".log()
    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(parentContext,
                android.R.string.ok, Toast.LENGTH_SHORT).show()
    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(parentContext,
                android.R.string.cancel, Toast.LENGTH_SHORT).show()
    }


    //---------------------------------SPINNER---------------------------------------------------//

    private fun setupSpinner(view: View){
        val spinner: Spinner = view.findViewById(R.id.spinner_group_select)


        ArrayAdapter(parentContext,
                android.R.layout.simple_spinner_item,
                groupsArray
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
//        spinner.pointToPosition(presenter.getGrouping(),0)
        spinner.setSelection(presenter.getGrouping())

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(parentContext,
                "Nothing selected",
                Toast.LENGTH_SHORT).show()
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//        Toast.makeText(parentContext,
//                "Position = $position , group = ${groupsArray[position]}",
//                Toast.LENGTH_SHORT).show()
        presenter.selectGrouping(position)

    }

    //---------------------------------SPINNER-END------------------------------------------------//


    //---------------------------------GroupPickerMVPView-----------------------------------------//
    override fun showMessage(message: String) {
        Toast.makeText( parentContext,
                        message,
                        Toast.LENGTH_SHORT).show()
    }
    //---------------------------------GroupPickerMVPView-END-------------------------------------//
}