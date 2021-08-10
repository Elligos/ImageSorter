package com.example.dima.imagesorter.ui.images.view

import android.R.attr.path
import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.R.layout.*
import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.util.log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_directory.view.*
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.items_group_text.view.*
import java.io.File


class ItemsRecyclerViewAdapter(private val itemList: ArrayList<RowItem>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onClickCallback : Callback

    interface Callback {
        fun onItemClick(item : RowItem)
    }

    fun setCallback(callback: Callback) {
        onClickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType){
            1 -> {
                "ViewHolderImage created!".log()
                ViewHolderImage(inflater.inflate(item_image, parent, false))
            }
            2 -> {
                "ViewHolderGroupTitle created!".log()
                ViewHolderGroupTitle(inflater.inflate(items_group_text, parent, false))
            }
            3 -> {
                "ViewHolderDirectory created!".log()
                ViewHolderDirectory(inflater.inflate(item_directory, parent, false))
            }
            else -> throw ExceptionInInitializerError("Unsupported item type!")
        }


    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    fun setData(newItems: ArrayList<RowItem>?)
    {
        "setData() call".log()
        if (itemList == null) return
        "New data set used!".log()
        itemList.clear()
        newItems?.let { itemList.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(itemList==null) return
        val item = itemList[position]

        val params = holder.itemView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        holder.itemView.layoutParams = params
        when(holder){
            is ViewHolderImage -> if(item is ImageItem) holder.bindItems(item)
            is ViewHolderGroupTitle -> if(item is GroupTitleItem) holder.bindItems(item)
            is ViewHolderDirectory -> if(item is DirectoryItem) holder.bindItems(item)
            else -> "No holder binded!".log()
        }
        holder.itemView.setOnClickListener { onClickCallback.onItemClick(item) }
    }

    override fun getItemViewType(position: Int): Int {
        if(itemList==null) return 2
        val type = itemList[position].getItemType()
        return when(type){
            RowItem.RowItemType.IMAGE_ITEM -> 1
            RowItem.RowItemType.GROUP_TITLE_ITEM -> 2
            RowItem.RowItemType.DIRECTORY_ITEM -> 3
        }
    }

    fun isGroupTitleItem(position: Int): Boolean {
        if(itemList==null) return false
        val type = itemList[position].getItemType()
        return type == RowItem.RowItemType.GROUP_TITLE_ITEM
    }
}

class ViewHolderImage (view: View) : RecyclerView.ViewHolder(view) {
    private val imageContainer: ImageView? = view.item_image
    private val context: Context? = view.context

    //TODO: try to use COIL instead of Picasso to solve OOM (OutOfMemory error) problem
    fun bindItems(imageItem: ImageItem) {
        if(imageContainer == null) throw Exception("Image container is null!")
        if(context == null) throw Exception("Context in bindItem for image is null!")
        if(imageItem.path == null) throw Exception("Image path is null!")


//        if (isImageCorrupted(imageItem.path)) {
//            imageContainer.setImageResource(R.drawable.temp_image)
//            "ViewHolderImage bound with temp_image!".log()
//            return
//        }

        val file = File(imageItem.path)
        Picasso.get().
            load(file).
            error(R.drawable.temp_image).
            resize(480, 480).
            centerCrop().
            into(imageContainer)

        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO: show fullsize image
            }
        })
        ("ViewHolderImage with height = ${imageContainer.height}, " +
                "width = ${imageContainer.width} bound!").log()
    }

    private fun isImageCorrupted(path : String) : Boolean{
        //If inJustDecodeBounds set to true, the decoder will return null (no bitmap), but the out...
        // fields will still be set, allowing the caller to query the bitmap without having to
        // allocate the memory for its pixels
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        if (options.outWidth == -1 || options.outHeight == -1) {
            return true
        }
        return false
    }
}

class ViewHolderGroupTitle (view: View) : RecyclerView.ViewHolder(view) {
    private val row = view.items_group_text
    fun bindItems(titleItem : GroupTitleItem) {
        row.text = titleItem.title
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO: hide and show group of elements
            }
        })
        "ViewHolderGroupTitle binded!".log()
    }
}

class ViewHolderDirectory (view: View) : RecyclerView.ViewHolder(view) {
    private val imageContainer: ImageView? = view.item_directory
    private val nameContainer: TextView? = view.item_directory_name
    private val context: Context? = view.context

    fun bindItems(directoryItem: DirectoryItem) {
        if (imageContainer == null) throw Exception("Image container is null!")
        if (context == null) throw Exception("Context in bindItem for image is null!")


        imageContainer.setImageResource(R.drawable.ic_icons8_folder_light_brown_96dp)
        nameContainer?.let {
            it.text = directoryItem.directoryName
        }
//        itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                //TODO: show fullsize image
//            }
//        })
        ("ViewHolderDirectory with height = ${imageContainer.height}, " +
                "width = ${imageContainer.width} binded!").log()
    }
}