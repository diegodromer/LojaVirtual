package com.diegolima.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.diegolima.lojavirtual.R
import com.diegolima.lojavirtual.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btCadastrar.setOnClickListener {
            CadastrarUdsuario()
        }
    }

    private fun CadastrarUdsuario() {
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()

        if (email.isEmpty() || senha.isEmpty()) {
            Snackbar.make(
                binding.layoutCadastro,
                "Coloque o seu email e sua senha",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBackgroundTint(Color.parseColor("#00bcd4"))
                .setAction("OK") {
                    Toast.makeText(this, "Foi bom ter ti ajudado", Toast.LENGTH_SHORT).show()
                }
                .show()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(
                            binding.layoutCadastro,
                            "Cadastro realizado com sucesso",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setBackgroundTint(Color.parseColor("#00bcd4"))
                            .setAction("OK") {
                                Toast.makeText(this, "Que bom se cadastrou com sucesso.", Toast.LENGTH_SHORT).show()
                                VoltarParaFormLogin()
                            }
                            .show()
                    }
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.layoutCadastro,
                        "Falha ao cadastrar usuário",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(Color.parseColor("#00bcd4"))
                        .setAction("OK") {
                            Toast.makeText(
                                this,
                                "verifique a senha e/ou email se estão certos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .show()
                }
        }
    }

    private fun VoltarParaFormLogin() {
        intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}