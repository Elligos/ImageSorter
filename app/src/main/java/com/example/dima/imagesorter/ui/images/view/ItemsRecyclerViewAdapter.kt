package com.example.dima.imagesorter.ui.images.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import kotlinx.android.synthetic.main.items_group_text.view.*
import android.view.LayoutInflater
import com.example.dima.imagesorter.R.layout.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import android.widget.ImageView
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.util.log
import java.io.File


class ItemsRecyclerViewAdapter(private val itemList: ArrayList<RowItem>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            else -> throw ExceptionInInitializerError("Unsupported item type!")
        }
    }

    override fun getItemCount(): Int {
//        if(itemList == null) return 0;
//        return itemList.size
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if(holder.itemViewType == 1){
//            val imageHolder : ViewHolderImage = holder
//        }
//        else{
//
//        }
        if(itemList==null) return
        val item = itemList[position]
//        if(holder is ViewHolderImage){
//            if(item is ImageItem) holder.bindItems(item)
//        }
//        if(holder is ViewHolderGroupTitle){
//            if(item is GroupTitleItem) holder.bindItems(item)
//        }
        val params = holder.itemView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        holder.itemView.layoutParams = params
        when(holder){
            is ViewHolderImage -> if(item is ImageItem) holder.bindItems(item)
            is ViewHolderGroupTitle -> if(item is GroupTitleItem) holder.bindItems(item)
            else -> "No holder binded!".log()
        }
    }

    override fun getItemViewType(position: Int): Int {
//        if(itemList.get(position).getItemType() == RowItem.RowItemType.IMAGE_ITEM) return 1
//        return 2
        if(itemList==null) return 2
        val type = itemList[position].getItemType()
        return when(type){
            RowItem.RowItemType.IMAGE_ITEM -> 1
            RowItem.RowItemType.GROUP_TITLE_ITEM -> 2
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


//        Picasso.with(context).load(file).into(imageContainer)
        Picasso.with(context).load(file).resize(480, 360).centerCrop().into(imageContainer)
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