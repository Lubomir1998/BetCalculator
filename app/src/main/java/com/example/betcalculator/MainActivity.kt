package com.example.betcalculator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.betcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.calculateBtn.setOnClickListener {
            if(binding.betEditText.text.isNotEmpty() && binding.koef1EditText.text.isNotEmpty() && binding.koef2EditText.text.isNotEmpty()){

                val koef1 = binding.koef1EditText.text.toString().toFloat()
                val koef2 = binding.koef2EditText.text.toString().toFloat()
                val bet = binding.betEditText.text.toString().toFloat()


                val x = (koef1 * bet) / (koef1 + koef2)
                val totalSum = (koef2 * x).toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()
                val neto = (totalSum - bet).toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()
                val profit = ((neto / bet) * 100).toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()

                binding.resultTextView.text =
                        "Profit: ${profit}% \n" +
                        "[1] ${koef1} x ${(bet-x).toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()} = $totalSum \n" +
                        "[2] ${koef2} x ${x.toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()} = $totalSum \n" +
                        "Neto: $neto"
            }
            else{
                Snackbar.make(binding.constraintLayout, "All fileds must be filled!", Snackbar.LENGTH_SHORT).show()
            }

            hideKeyBoard()

        }

        binding.resetImg.setOnClickListener {
            binding.betEditText.text.clear()
            binding.koef1EditText.text.clear()
            binding.koef2EditText.text.clear()
            binding.resultTextView.text = ""
        }


    }

    private fun hideKeyBoard(){
        val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}