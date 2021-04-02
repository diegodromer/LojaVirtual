package com.diegolima.lojavirtual.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.diegolima.lojavirtual.Model.Dados
import com.diegolima.lojavirtual.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_produtos.*
import kotlinx.android.synthetic.main.lista_produtos.view.*
import kotlinx.android.synthetic.main.pagamento.*

class Produtos : Fragment() {

    private lateinit var Adapter: GroupAdapter<ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_produtos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Adapter = GroupAdapter()
        recycler_produtos.adapter = Adapter

        Adapter.setOnItemClickListener { item, view ->
            val DialogView = LayoutInflater.from(context).inflate(R.layout.pagamento, null)
            val builder = AlertDialog.Builder(context)
                .setView(DialogView)
                .setTitle("Formas de pagamento")

            val mAlertDialog = builder.show()

            mAlertDialog.bt_pagar.setOnClickListener {
                mAlertDialog.dismiss()
                val pagamento = mAlertDialog.fm_pagamento.text.toString()

                if (pagamento == "249,99") {
                    MaterialDialog.Builder(requireContext())
                        .title("Pagamento concluido")
                        .content("Obrigado pela compra. Volte Sempre.")
                        .show()
                } else {

                }
            }

        }

        BuscarProdutos()

    }


    private inner class ProdutosItem(internal val adProdutos: Dados) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.lista_produtos

        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.nomeProduto.text = adProdutos.nome
            viewHolder.itemView.precoProduto.text = adProdutos.preco
            Picasso.get().load(adProdutos.uid).into(viewHolder.itemView.fotoProduto)

        }
    }

    private fun BuscarProdutos() {
        FirebaseFirestore.getInstance().collection("Produtos")
            .addSnapshotListener { snapshop, exception ->
                exception?.let {
                    return@addSnapshotListener
                }
                snapshop?.let {
                    for (doc in snapshop) {
                        val produtos = doc.toObject(Dados::class.java)
                        Adapter.add(ProdutosItem(produtos))
                    }
                }
            }
    }
}

