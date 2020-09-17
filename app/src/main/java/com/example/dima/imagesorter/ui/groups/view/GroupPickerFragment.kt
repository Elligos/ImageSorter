package com.example.dima.imagesorter.ui.groups.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.util.log


class GroupPickerFragment : DialogFragment() {

    private lateinit var parentContext: Context

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val view = LayoutInflater.from(parentContext).inflate(R.layout.dialog_group_picker, null)
        val builder =  AlertDialog.Builder(parentContext)
        with(builder){
            setView(view)
            setTitle(R.string.group_picker_title_text)
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

        //return super.onCreateDialog(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context != null) parentContext = context
        "GroupPickerFragment attached!".log()
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        "GroupPickerFragment detached!".log()
//        listener = null
    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(parentContext,
                android.R.string.ok, Toast.LENGTH_SHORT).show()
    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(parentContext,
                android.R.string.cancel, Toast.LENGTH_SHORT).show()
    }
}