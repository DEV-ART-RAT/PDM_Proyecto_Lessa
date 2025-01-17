package com.DevRAT.lessa.UI.Fragments


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.DevRAT.lessa.Database.ViewModel.WordViewModel
import com.DevRAT.lessa.R
import com.example.myapplication.Adapter.WordAdapter
import kotlinx.android.synthetic.main.fragment_home_.view.*




class HomeFragment : Fragment(){

    private var  conext :Context? =null

    private var listener: OnFragmentInteractionListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view =  inflater.inflate(R.layout.fragment_home_, container, false)
        initAll(view)
        return view
    }



    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(context: Context) =
            HomeFragment().apply {
                this.conext = context
                arguments = Bundle().apply {
                }
            }
        var wordViewModel: WordViewModel? = null
    }

    fun initAll(view: View) {


        val recyclerView = view.recyclerview
        val adapter = object : WordAdapter(view.context) {
            override fun addListener(
                holder: WordViewHolder,
                palabra: String,
                Categoria: String,
                seña: Int
            ) {
                holder.word_container.setOnClickListener {
                    //wordViewModel!!.callCategory(Categoria)
                    val fragment = ListaFragment.newInstance(conext!!,Categoria,palabra)
                    fragmentManager!!.beginTransaction().replace(R.id.main_container,fragment).addToBackStack("").commit()
                }
            }

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context,2)


        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordViewModel!!.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

    }

}

