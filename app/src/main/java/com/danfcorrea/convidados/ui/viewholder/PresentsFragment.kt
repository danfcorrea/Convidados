package com.danfcorrea.convidados.ui.viewholder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danfcorrea.convidados.constants.DataBaseConstants
import com.danfcorrea.convidados.databinding.FragmentPresentsBinding
import com.danfcorrea.convidados.ui.adapter.GuestsAdapter
import com.danfcorrea.convidados.ui.listener.OnGuestListener
import com.danfcorrea.convidados.ui.viewmodel.GuestsViewModel

class PresentsFragment : Fragment() {

    private var _binding: FragmentPresentsBinding? = null

    private val binding get() = _binding!!
    private lateinit var guestsViewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        guestsViewModel = ViewModelProvider(this)[GuestsViewModel::class.java]
        _binding = FragmentPresentsBinding.inflate(inflater, container, false)
        binding.recyclerPresents.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.recyclerPresents.adapter = adapter

        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }
            override fun onDelete(id: Int) {
                guestsViewModel.delete(id)
                guestsViewModel.get(DataBaseConstants.GUEST.PRESENT)
            }
        }

        adapter.attachListener(listener)
        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        guestsViewModel.get(DataBaseConstants.GUEST.PRESENT)
        super.onResume()
    }

    private fun observe() {
        guestsViewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}