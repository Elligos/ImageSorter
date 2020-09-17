package com.example.dima.imagesorter.ui.images.view

import android.content.Context
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
//        if(itemList == null) return 0;
//        return itemList.size
        return itemList?.size ?: 0
    }

    fun setData(newItems: ArrayList<RowItem>?)
    {
        if (itemList == null) return
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
//        if(itemList.get(position).getItemType() == RowItem.RowItemType.IMAGE_ITEM) return 1
//        return 2
        if(itemList==null) return 2
        val type = itemList[position].getItemType()
        return when(type){
            RowItem.RowItemType.IMAGE_ITEM -> 1
            RowItem.RowItemType.GROUP_TITLE_ITEM -> 2
            RowItem.RowItemType.DIRECTORY_ITEM -> 3
        }
    }
}

class ViewHolderImage (view: View) : RecyclerView.ViewHolder(view) {
    private val imageContainer: ImageView? = view.item_image
    private val context: Context? = view.context

    fun bindItems(imageItem: ImageItem) {
        if(imageContainer == null) throw Exception("Image container is null!")
        if(context == null) throw Exception("Context in bindItem for image is null!")

        val file = File(imageItem.path)



       Picasso.get().load(file).resize(480, 480).centerCrop().into(imageContainer)

//        Glide.with(context).load(images.get(position))
//                .placeholder(R.drawable.ic_launcher).centerCrop()
//                .into(picturesView)
//        var tmpImage = context.getResources().getDrawable(R.drawable.temp_image)
//        imageContainer.setImageDrawable(tmpImage)

        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO: show fullsize image
            }
        })
        "ViewHolderImage with height = ${imageContainer.height}, width = ${imageContainer.width} binded!".log()
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


//        Glide.with(context) .asBitmap() .load(R.drawable.ic_folder_black_24dp) .into(imageContainer)
//        var directoryImage = with(context) { resources.getDrawable(R.drawable.ic_folder_black_24dp) }
//        imageContainer.setImageDrawable(directoryImage)




//        Picasso.get()
//                .load(R.drawable.ic_folder_brown_100dp)
//                .placeholder(R.drawable.ic_folder_brown_100dp)
//                //.error(R.drawable.ic_menu_camera)
//                .resize(480, 360)
//                .centerInside()
//                .transform( CropTransformation(240,320,  CropTransformation.GravityHorizontal.CENTER, CropTransformation.GravityVertical.BOTTOM))
//                //.centerCrop()
//                .into(imageContainer)
        /*
        Picasso.get()
                .load(R.drawable.ic_folder_brown_100dp)
                //.placeholder(R.drawable.ic_folder_brown_100dp)
                //.error(R.drawable.ic_menu_camera)
                //.resize(480, 360)
//                .centerInside()
                //.transform( CropTransformation(0.1f, 0.1f,  CropTransformation.GravityHorizontal.CENTER, CropTransformation.GravityVertical.CENTER))
                //.centerCrop()
                //.resize(480, 360)
                .placeholder(R.drawable.ic_icons8_folder_light_brown_96dp)
                .into(imageContainer)

         */
        imageContainer.setImageResource(R.drawable.ic_icons8_folder_light_brown_96dp)
        nameContainer?.let {
            it.text = directoryItem.directoryName
        }
//        itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                //TODO: show fullsize image
//            }
//        })
        "ViewHolderDirectory with height = ${imageContainer.height}, width = ${imageContainer.width} binded!".log()
    }
}