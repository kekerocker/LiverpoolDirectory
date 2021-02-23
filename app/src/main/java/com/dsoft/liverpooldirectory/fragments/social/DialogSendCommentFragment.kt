package com.dsoft.liverpooldirectory.fragments.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.FragmentSendCommentDialogBinding
import com.dsoft.liverpooldirectory.di.DaggerComponent
import com.dsoft.liverpooldirectory.fragments.social.adapter.SocialCommentsRecyclerAdapter
import com.dsoft.liverpooldirectory.model.Comments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DialogSendCommentFragment: DialogFragment() {

    private lateinit var binding: FragmentSendCommentDialogBinding
    private var component = DaggerComponent.create()
    private var appPreferences: AppPreferences? = null

    private var textList = mutableListOf<String>()

    private lateinit var viewModel: SocialViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_send_comment_dialog, container, false)
        appPreferences = AppPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(SocialViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSendCommentDialogBinding.bind(view)
        setupRecyclerView()
        val position = appPreferences?.getPosition().toString()
        val token = appPreferences?.getToken().toString()

        viewModel.deleteAllComments()
        Log.d("FetchComments", position)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = component.getRetrofit().api.getComments(position, token)

                withContext(Dispatchers.Main) {
                    //Fetch text
                    for (item in response.response.items) {
                        addInfo(item.text)
                    }
                    addInfoToDatabase(textList)
                    textList.clear()
                }
            } catch (e: HttpException) {
                Log.e("Social95", e.toString())
            }  catch(e: Exception){
                Log.e("ErrorComments", e.toString())
            }
        }

        binding.closeMessageButton.setOnClickListener {
            dismiss()
        }

        binding.sendButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val postId = appPreferences?.getPosition().toString()
                    val message = binding.customEditText.text.toString()

                    component.getRetrofit().api.postComment(postId, token, message)

                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), "Комментарий отправлен!", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                } catch (e:Exception){
                    Toast.makeText(requireContext(), "Ошибка!", Toast.LENGTH_SHORT).show()
                    Log.e("CommentSend", e.toString())
                }

            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = SocialCommentsRecyclerAdapter()
        val recyclerView = binding.rvRecyclerViewComments
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readComments.observe(viewLifecycleOwner, { comments ->
            adapter.setData(comments)
        })
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