package com.diegolima.lojavirtual.Fragments

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.diegolima.lojavirtual.Model.Dados
import com.diegolima.lojavirtual.R
import com.diegolima.lojavirtual.databinding.ActivityCadastroProdutosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class CadastroProdutos : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroProdutosBinding

    private var SelecionarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSelecionarFoto.setOnClickListener {
            SelecionarFotoDaGaleria()
        }

        binding.btCadastrarProduto.setOnClickListener {
            SalvarDadosNoFirebase()
        }
    }

    private fun SalvarDadosNoFirebase() {
        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeArquivo}"
        )

        SelecionarUri?.let {
            referencia.putFile(it).addOnSuccessListener {
                referencia.downloadUrl.addOnSuccessListener {

                    val url = it.toString()
                    val nome = binding.editNome.text.toString()
                    val preco = binding.editPreco.text.toString()
                    val uid = FirebaseAuth.getInstance().uid

                    val Produtos = Dados(url, nome, preco)
                    FirebaseFirestore.getInstance().collection("Produtos")
                        .add(Produtos)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Produto cadastrado com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener {

                        }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            SelecionarUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelecionarUri)
            binding.fotoProduto.setImageBitmap(bitmap)
            binding.btSelecionarFoto.alpha = 0f
        }
    }

    private fun SelecionarFotoDaGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }
}