package com.capstone.hewankita.ui.bottom.ui.tips

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ListTipsAdapter
import com.capstone.hewankita.data.remote.response.LoginResult
import com.capstone.hewankita.data.session.UserSession
import com.capstone.hewankita.databinding.FragmentTipsBinding
import com.capstone.hewankita.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TipsFragment : Fragment() {
    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Tips>()
    private lateinit var pref: UserSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        ViewModelProvider(this)[TipsViewModel::class.java]

        activity?.setTitle(R.string.title_tips)

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

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.localization -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                logOut()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireActivity(), resources.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                activity?.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    private fun logOut(){
        Firebase.auth.signOut()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}