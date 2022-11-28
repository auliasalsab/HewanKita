package com.capstone.hewankita.ui.bottom.ui.tips

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ListTipsAdapter
import com.capstone.hewankita.databinding.FragmentTipsBinding

class TipsFragment : Fragment() {
    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Tips>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val tipsViewModel =
            ViewModelProvider(this).get(TipsViewModel::class.java)

        getActivity()?.setTitle(R.string.title_tips);

        _binding = FragmentTipsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        list.addAll(listTips)
        dataTips()

        return root
    }

    private val listTips : ArrayList<Tips>
    get() {
        val judul = resources.getStringArray(R.array.judul)
        val deskripsi = resources.getStringArray(R.array.deskripsi)
        val photo = resources.obtainTypedArray(R.array.photo)
        val listTips = ArrayList<Tips>()
        for (i in judul.indices) {
            val tips = Tips(judul[i],deskripsi[i], photo.getResourceId(i,-1))
            listTips.add(tips)
        }
        return listTips
    }
    private fun dataTips() {
        binding.rvTips.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = ListTipsAdapter(list)

        /*adapter.setOnItemClickCallback(object : ListTipsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Tips) {
                Intent(requireContext(), UserDetail::class.java).also {
                    it.putExtra(UserDetail.EXTRA_DETAIL, data.judul)
                    startActivity(it)
                }
            }
        })*/
        binding.rvTips.setHasFixedSize(true)
        binding.rvTips.adapter = adapter

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}