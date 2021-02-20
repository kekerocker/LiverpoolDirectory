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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogSendCommentFragment: DialogFragment() {

    private lateinit var binding: FragmentSendCommentDialogBinding
    private var component = DaggerComponent.create()
    private var appPreferences: AppPreferences? = null

    private lateinit var viewModel: SocialViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_send_comment_dialog, container, false)
        appPreferences = AppPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(SocialViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSendCommentDialogBinding.bind(view)
        setupRecyclerView()
        binding.closeMessageButton.setOnClickListener {
            dismiss()
        }

        binding.sendButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val postId = appPreferences?.getPosition().toString()
                    val token = appPreferences?.getToken()!!
                    val message = binding.customEditText.text.toString()

                    component.getVkInfo().api.postComment(postId, token, message)

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

    override fun onDestroy() {
        super.onDestroy()
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
}