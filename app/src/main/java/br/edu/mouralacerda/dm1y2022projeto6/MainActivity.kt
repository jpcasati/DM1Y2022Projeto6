package br.edu.mouralacerda.dm1y2022projeto6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botaoBuscar = findViewById<Button>(R.id.btnBuscar)

        botaoBuscar.setOnClickListener {

            buscarDadosWS()

        }

    }

    fun buscarDadosWS() {

        val url = "http://10.0.2.2/livros/wslivro.php"

        CoroutineScope(Dispatchers.IO).launch {

            val resp = URL(url).readText()

            withContext(Dispatchers.Main) {
                preencherLivro(resp)
            }
        }
    }

    fun preencherLivro(resp: String) {

        val livroJSON = JSONObject(resp)

        val livro = Livro(
            livroJSON.getString("isbn"),
            livroJSON.getString("titulo"),
            livroJSON.getString("autor"),
            livroJSON.getString("ano")
        )

        preencherTelaLivro(livro)

    }

    fun preencherTelaLivro(livro: Livro) {

        findViewById<TextView>(R.id.txtTitulo).text = livro.titulo
        findViewById<TextView>(R.id.txtAutor).text = livro.autor
        findViewById<TextView>(R.id.txtAno).text = livro.ano

    }

}