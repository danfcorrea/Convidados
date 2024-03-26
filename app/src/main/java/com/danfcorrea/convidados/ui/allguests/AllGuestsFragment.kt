package com.danfcorrea.convidados.ui.allguests

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danfcorrea.convidados.constants.DataBaseConstants
import com.danfcorrea.convidados.databinding.FragmentAllGuestsBinding
import com.danfcorrea.convidados.ui.guestform.GuestFormActivity
import com.danfcorrea.convidados.ui.listener.OnGuestListener

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var allGuestsViewModel: AllGuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allGuestsViewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.recyclerAllGuests.adapter = adapter

        val listener = object : OnGuestListener{
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }
            override fun onDelete(id: Int) {
                allGuestsViewModel.delete(id)
                allGuestsViewModel.getAll()
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
        allGuestsViewModel.getAll()
        super.onResume()
    }

    private fun observe() {
        allGuestsViewModel.allGuests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}
