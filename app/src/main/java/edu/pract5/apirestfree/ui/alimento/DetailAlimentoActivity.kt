package edu.pract5.apirestfree.ui.alimento

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.pract5.apirestfree.databinding.ActivityDetailAlimentoBinding
import edu.pract5.apirestfree.model.Alimento

class DetailAlimentoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAlimentoBinding
    private var alimento: Alimento? = null

    companion object {
        const val ALIMENTO_KEY = "ALIMENTO"
        fun navigate(activity: AppCompatActivity, alimento: Alimento) {
            val intent = Intent(activity, DetailAlimentoActivity::class.java).apply {
                putExtra(ALIMENTO_KEY, alimento)
            }
            activity.startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailAlimentoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        alimento = intent.getParcelableExtra<Alimento>(ALIMENTO_KEY)

        Log.d("DetailAlimento", "Alimento recibido: $alimento")

        if (alimento == null) {
            Log.e("DetailAlimento", "El objeto Alimento es null")
            finish() // Cierra la actividad si no hay datos
            return
        }

        alimento?.let {
            binding.tvName.text = it.name
            binding.tvCarbohydrates.text = "${it.carbohydratesTotalG}g"
            binding.tvCholesterol.text = "${it.cholesterolMg}mg"
            binding.tvFatSaturated.text = "${it.fatSaturatedG}g"
            binding.tvFatTotal.text = "${it.fatTotalG}g"
            binding.tvFiber.text = "${it.fiberG}g"
            binding.tvPotassium.text = "${it.potassiumMg}mg"
            binding.tvSodium.text = "${it.sodiumMg}mg"
            binding.tvSugar.text = "${it.sugarG}g"
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

}