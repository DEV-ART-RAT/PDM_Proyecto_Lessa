package com.DevRAT.lessa.UI.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DevRAT.lessa.Database.Entities.SenaUser
import com.DevRAT.lessa.Database.Entities.Senas
import com.DevRAT.lessa.Database.ViewModel.WordViewModel
import com.DevRAT.lessa.R
import com.DevRAT.lessa.UI.Activities.MainActivity
import com.DevRAT.lessa.UI.Activities.SenaActivity
import com.DevRAT.lessa.UI.Activities.SenaPageViewActivity
import com.DevRAT.lessa.UI.Adapter.SenasAdapter
import kotlinx.android.synthetic.main.fragment_test.view.*


class TestFragment : Fragment() {

    var  conext : Context? =null
    lateinit var categoria: String

    private lateinit var vm: WordViewModel
    private lateinit var rv: RecyclerView
    private lateinit var senasAdapter: SenasAdapter
    private lateinit var observer : Observer<List<Senas>>
    private var searchView: SearchView? = null

    private lateinit var myObserver : Observer<List<SenaUser>>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return  inflater.inflate(R.layout.fragment_test, container, false).apply{
            vm = ViewModelProviders.of(conext as MainActivity).get(WordViewModel::class.java)
            rv = rv_busqueda
            observer = Observer{
                updateRecycler(vm.busca.value!!)
            }

            myObserver = Observer {
                change()
            }
            MainActivity.viewModelUser!!.senass.observe(conext as LifecycleOwner, myObserver)
            val h=""
            vm.getSenaByNombre("%$h%")
            vm.busca.observe(conext as LifecycleOwner, observer)
            //return view
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(context: Context) =
            TestFragment().apply {
                this.conext = context
                arguments = Bundle().apply {

                }
            }
    }



    private fun updateRecycler(list: List<Senas>) {

        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(conext)
            adapter = SenasAdapter(list, {
                position = it
                handler.postDelayed({
                MainActivity.viewModelUser!!.load()
            }, 300)

        }) {
                SenaPageViewActivity.senaList = list
                var index = list.indexOf(it)
                SenaPageViewActivity.index = index
                val intent = Intent(context, SenaPageViewActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun change(){
        if(rv.adapter!=null){
            rv.adapter!!.notifyItemChanged(position)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView!!.isSubmitButtonEnabled = true

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Hace que cuando presiones el botón de sumbit se ejecute lo que pongas aquí
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Hace que cambie dinamicamente mientras escribis, porque ejecuta lo que pongas aquí cada vez que escribis.
                //Log.e("com.DevRAT,lessa",newText?:"no hay ma")
               //queryToDatabase(newText?: "N/A")

                vm.getSenaByNombre("%$newText%")
                //vm.busca.observe(conext as LifecycleOwner, observer)
                return true
            }

        })

    }

}