package com.diegolima.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.diegolima.lojavirtual.TelaPrincipal
import com.diegolima.lojavirtual.databinding.ActivityFormLoginBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        VerificarUsuarioLogado()

        supportActionBar!!.hide()

        binding.btEntrar.setOnClickListener {
            AutenticarUsuario()
        }

        binding.textTelaCadastro.setOnClickListener {
            intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun AutenticarUsuario() {
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()

        if (email.isEmpty() || senha.isEmpty()) {
            Snackbar.make(
                binding.layoutLogin,
                "Coloque um email e uma senha",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBackgroundTint(Color.parseColor("#00bcd4"))
                .setAction("OK") {
                    Toast.makeText(this, "Fico feliz em te ajudar :)", Toast.LENGTH_SHORT)
                        .show()
                    //VoltarParaFormLogin()
                }
                .show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Bem-Vindo",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.frameLBottom.visibility = View.VISIBLE
                        Handler().postDelayed({ AbrirTelaPrincipal() }, 3000)

                    }
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.layoutLogin,
                        "email ou senha incorretos",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(Color.parseColor("#00bcd4"))
                        .setAction("OK") {
                            Toast.makeText(
                                this,
                                "Fico feliz em poder ti ajudar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .show()
                }
        }
    }

    private fun VerificarUsuarioLogado(){
        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if(usuarioAtual != null){
            AbrirTelaPrincipal()
        }
    }

    private fun AbrirTelaPrincipal() {
        intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}