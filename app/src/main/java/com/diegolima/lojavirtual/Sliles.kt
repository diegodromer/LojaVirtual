package com.diegolima.lojavirtual

import android.content.Intent
import android.os.Bundle
import com.diegolima.lojavirtual.Form.FormLogin
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class Sliles : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sliles)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.Roxo)
                .image(R.drawable.drawer)
                .backgroundDark(R.color.Branco)
                .title("Loja de Calçados")
                .description("Entre e confira as promoções que preparamos para voçê!")
                .build()
        )
        addSlide(
            SimpleSlide.Builder()
                .background(R.color.AV)
                .title("Crie uma conta grátis")
                .canGoBackward(false)
                .description("Cadastre-se agora mesmo! E venha conhecer os nossos produtos.")
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}