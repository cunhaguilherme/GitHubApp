package br.com.guilhermecunha.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSearch.setOnClickListener {
            buscar()
        }

        carregaImagem("https://miro.medium.com/max/1200/1*CfXGrg1zh3xPwllRcHNf-Q.png")
    }

    private fun carregaImagem(url: String){
        Picasso.get()
            .load(url)
            .into(ivUser)
    }

    private fun getOkhttp(): OkHttpClient{
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build();
    }


    private fun buscar() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkhttp())
            .build()

        val service = retrofit.create(GitHubService::class.java)

        service.buscarUsuario(etUsername.text.toString())
            .enqueue(object: Callback<Usuario>{
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){
                        val usuario = response.body()
                        tvUser.text = usuario?.nome
                    }else{
                        tvUser.text = "Erro ao buscar o usuário"
                    }
                }
            })
    }
}
