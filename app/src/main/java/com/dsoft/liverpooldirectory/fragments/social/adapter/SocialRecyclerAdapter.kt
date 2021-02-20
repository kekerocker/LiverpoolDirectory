package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.di.DaggerComponent
import com.dsoft.liverpooldirectory.fragments.social.DialogSendCommentFragment
import com.dsoft.liverpooldirectory.fragments.social.SocialViewModel
import com.dsoft.liverpooldirectory.model.Comments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SocialRecyclerAdapter(
    private var text: List<String>,
    private var likes: List<String>,
    private var comments: List<String>,
    private var views: List<String>,
    private var id: List<String>,
    //private var date: List<Int>,
    private val context: Context,
    private val storeOwner: ViewModelStoreOwner
) : RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    private var textList = mutableListOf<String>()

    private var component = DaggerComponent.create()
    private var appPreferences: AppPreferences? = null
    private lateinit var viewModel: SocialViewModel

    inner class ViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews

        init {
            itemView.setOnClickListener {
                val token = appPreferences?.getToken()!!
                val position: Int = adapterPosition
                appPreferences?.savePosition(id[position])
                Log.d("FetchComments", id[position])
                viewModel.deleteAllComments()

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = component.getVkInfo().api.getComments(id[position], token)

                        withContext(Dispatchers.Main) {
                            //Fetch text
                            for (item in response.response.items) {
                                addInfo(item.text)
                            }
                            addInfoToDatabase(textList)
                            textList.clear()
                        }
                    } catch(e: Exception){
                        Log.e("ErrorComments", e.toString())
                    }
                }

                val manager = (context as AppCompatActivity).supportFragmentManager
                val dialog = DialogSendCommentFragment()
                dialog.show(manager, "test")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        appPreferences = AppPreferences(context)
        viewModel = ViewModelProvider(storeOwner).get(SocialViewModel::class.java)
        return ViewHolder(ItemSocialNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = text[position]
        holder.itemLikes.text = likes[position]
        holder.itemComments.text = comments[position]
        holder.itemViews.text = views[position]
    }

    override fun getItemCount(): Int {
        return text.size
    }

    private fun addInfoToDatabase(listText: List<String>) {
        var x = 0
        do {
            val comments = Comments(null, listText[x])
            viewModel.addComments(comments)
            x++
        } while (x < listText.size)
    }

    private fun addInfo(text: String) {
        textList.add(text)
    }
}