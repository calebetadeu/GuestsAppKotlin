package com.example.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.R
import com.example.guests.service.constants.GuestConstants
import com.example.guests.view.adapter.GuestAdapter
import com.example.guests.view.listener.GuestListener
import com.example.guests.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        // Elemento de interface - RecyclerView
        // Não é possível deixar o Kotlin fazer o mapeamento, pois a fragment ainda não está totalmente criada
        // Buscar o elemento através de findViewById
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_guests)

        // Atribui um layout que diz como a RecyclerView se comporta
        recycler.layoutManager = LinearLayoutManager(context)

        // Defini um adapater - Faz a ligação da RecyclerView com a listagem de itens
        recycler.adapter = mAdapter

        // Create the observ
        observe()

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.EMPTY)
            }
        }

        // Retorn the  Fragment created
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.load(GuestConstants.FILTER.EMPTY)
    }

    /**
     * Create observ
     */
    private fun observe() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }
}
