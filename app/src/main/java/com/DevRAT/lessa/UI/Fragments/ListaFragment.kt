package com.DevRAT.lessa.UI.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DevRAT.lessa.Database.Entities.SenaUser
import com.DevRAT.lessa.Database.Entities.Senas
import com.DevRAT.lessa.Database.ViewModel.SenaViewModel
import com.DevRAT.lessa.Database.ViewModel.SenasViewModel
import com.DevRAT.lessa.Database.ViewModel.WordViewModel
import com.DevRAT.lessa.R
import com.DevRAT.lessa.UI.Activities.MainActivity
import com.DevRAT.lessa.UI.Activities.SenaActivity
import com.DevRAT.lessa.UI.Activities.SenaPageViewActivity
import com.DevRAT.lessa.UI.Adapter.SenasAdapter
import com.DevRAT.lessa.UI.Fragments.HomeFragment.Companion.wordViewModel
import com.example.myapplication.Adapter.WordAdapter
import kotlinx.android.synthetic.main.fragment_home_.view.*
import kotlinx.android.synthetic.main.fragment_lista.*
import kotlinx.android.synthetic.main.fragment_lista.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*





class ListaFragment : Fragment(){


    var  conext :Context? =null
    lateinit var categoria: String
    lateinit var palabra: String

    private lateinit var vm: WordViewModel
    private lateinit var rv: RecyclerView

    private lateinit var observer : Observer<List<Senas>>
    private lateinit var myObserver : Observer<List<SenaUser>>
    private var position = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return  inflater.inflate(com.DevRAT.lessa.R.layout.fragment_lista, container, false).apply{
        vm = ViewModelProviders.of(conext as MainActivity).get(WordViewModel::class.java)
        rv = recyclerviewList

        observer = Observer<List<Senas>> {
            updateRecycler(it)
        }

            vm.callCategory(categoria)


            myObserver = Observer <List<SenaUser>>{
                    change()
        }
            MainActivity.viewModelUser!!.senass.observe(conext as LifecycleOwner, myObserver)



        vm.allPalabras.observe(conext as LifecycleOwner, observer)
            val img = findViewById(com.DevRAT.lessa.R.id.imagendinamicalista) as ImageView
            val texto: TextView = findViewById(R.id.textodinamicolista) as TextView
          when(categoria){

               "saludo"->{
                   img.setImageResource(com.DevRAT.lessa.R.drawable.icon_handshake)
                   texto.setText(palabra)
               }
              "calendario"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_calendar)
                  texto.setText(palabra)
              }
              "color"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_rgb)
                  texto.setText(palabra)
              }
              "comida"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_diet)
                  texto.setText(palabra)
              }
              "numero"->{
              img.setImageResource(com.DevRAT.lessa.R.drawable.icon_number)
                  texto.setText(palabra)
          }
              "ropa"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_laundry)
                  texto.setText(palabra)
              }
              "familia"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_family)
                  texto.setText(palabra)
              }
              "verbos"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_rgb)
                  texto.setText(palabra)
              }
              "comunes"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_desk)
                  texto.setText(palabra)
              }
              "trabajos"->{
              img.setImageResource(com.DevRAT.lessa.R.drawable.icon_farmer)
                  texto.setText(palabra)
              }
              "abecedario"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_abc)
                  texto.setText(palabra)
              }
              "departamentos"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_departamentos)
                  texto.setText(palabra)
              }
              "paises"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_america)
                  texto.setText(palabra)
              }
              "ejemplos"->{
                  img.setImageResource(com.DevRAT.lessa.R.drawable.icon_ejemplo)
                  texto.setText(palabra)
              }
          }



        //return view
    }
    }


    companion object {
        @JvmStatic
        fun newInstance(context: Context,categoria :String,palabra:String) =
            ListaFragment().apply {
                this.conext = context
                this.categoria = categoria
                this.palabra = palabra
                arguments = Bundle().apply {
                }
            }
    }



    private fun updateRecycler(list: List<Senas>) {

            rv.apply {
                setHasFixedSize(true)
                adapter = SenasAdapter(list,
                    {
                        position = it
                        handler.postDelayed({
                            MainActivity.viewModelUser!!.load()
                        }, 300)

                    }) {
                    val index = list.indexOf(it)
                    SenaPageViewActivity.index = index
                    SenaPageViewActivity.senaList = list
                    val intent = Intent(context, SenaPageViewActivity::class.java)
                    startActivity(intent)
                }
                layoutManager = LinearLayoutManager(conext)
            }

    }

    fun change(){
        if(rv.adapter!=null){
            rv.adapter!!.notifyItemChanged(position)
        }
    }

}