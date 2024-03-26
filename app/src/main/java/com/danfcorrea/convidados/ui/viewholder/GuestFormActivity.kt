package com.danfcorrea.convidados.ui.viewholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.danfcorrea.convidados.R
import com.danfcorrea.convidados.constants.DataBaseConstants
import com.danfcorrea.convidados.databinding.ActivityGuestFormBinding
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.ui.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]
        binding.radioPresent.isChecked = true
        binding.buttonSave.setOnClickListener(this)

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            viewModel.save(GuestModel(guestid, name, presence))
        }
    }

    private fun observe() {
        viewModel.guest.observe(this) {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        }
        viewModel.saveGuest.observe(this) {
            if (it.success) {
                finish()
            }
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestid = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestid)
        }
    }
}