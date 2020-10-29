package com.example.betcalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.example.betcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
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

                val sum1 = (bet-x).toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()
                val sum2 = x.toBigDecimal().setScale(2, RoundingMode.FLOOR).toFloat()

                val builder = SpannableStringBuilder()

//                val resultString =
//                        "Profit: ${profit}% \n" +
//                        "[1] $koef1 x $sum1 = $totalSum \n" +
//                        "[2] $koef2 x $sum2 = $totalSum \n" +
//                        "Neto: $neto"

                val result1 = "Profit: ${profit}% \n[1] $koef1 x "
                val spannableString = SpannableString(result1)
                builder.append(spannableString)

                val colorSpan = ForegroundColorSpan(Color.BLUE)
                val colorSpan2 = ForegroundColorSpan(Color.BLUE)

                val result2 = "$sum1"
                val spannableString2 = SpannableString(result2)
                spannableString2.setSpan(colorSpan, 0, result2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.append(spannableString2)

                val result3 = " = $totalSum \n[2] $koef2 x "
                val spannableString3 = SpannableString(result3)
                builder.append(spannableString3)

                val result4 = "$sum2"
                val spannableString4 = SpannableString(result4)
                spannableString4.setSpan(colorSpan2, 0, result4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.append(spannableString4)

                val result5 = " = $totalSum \nNeto: $neto"
                val spannableString5 = SpannableString(result5)
                builder.append(spannableString5)

                binding.resultTextView.setText(builder, TextView.BufferType.SPANNABLE)

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