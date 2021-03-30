package com.dsoft.liverpooldirectory.ui.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentSendCommentDialogBinding
import com.dsoft.liverpooldirectory.ui.social.adapter.SocialCommentsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogSendCommentFragment : DialogFragment() {

    private val viewModel by viewModels<SocialViewModel>()
    private val binding by viewBinding(FragmentSendCommentDialogBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_send_comment_dialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = viewModel.appPreferences.getPostId()
        viewModel.getComments(postId)
        setupRecyclerView()

        binding.closeMessageButton.setOnClickListener {
            dismiss()
        }

        binding.sendButton.setOnClickListener {
            val comment = binding.customEditText.text.toString()
            viewModel.sendMessage(comment, requireContext())
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        val adapter = SocialCommentsRecyclerAdapter()
        val recyclerView = binding.rvRecyclerViewComments
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.listOfComments.observe(viewLifecycleOwner) {
            if (it == null) {
                Log.d("TestComm1", "Test: $it")
                return@observe
            }
            Log.d("TestComm2", "Test: ${it.first().text}")
            adapter.commentsList = it
        }
    }
}




