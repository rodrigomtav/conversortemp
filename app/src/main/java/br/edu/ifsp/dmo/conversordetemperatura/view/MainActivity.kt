package br.edu.ifsp.dmo.conversordetemperatura.view

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.conversordetemperatura.R
import br.edu.ifsp.dmo.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.conversordetemperatura.model.CelsiusStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.FahrenheitStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.KelvinStrategy
import kotlin.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setClickListener()
    }

    private fun setupSpinners() {
        val scales = resources.getStringArray(R.array.scales)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, scales)
        binding.spinnerInitialScale.adapter = adapter
        binding.spinnerTargetScale.adapter = adapter
    }

    private fun setClickListener() {
        binding.btnConvert.setOnClickListener {
            handleConversion()
        }
    }

    private fun readTemperature(): Double {
        return try {
            binding.edittextTemperature.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            throw NumberFormatException("Input Error")
        }
    }

    private fun handleConversion() {
        try {
            val inputValue = readTemperature()
            val initialScale = binding.spinnerInitialScale.selectedItem.toString()
            val targetScale = binding.spinnerTargetScale.selectedItem.toString()

            val result = when (initialScale to targetScale) {
                "Celsius" to "Fahrenheit" -> FahrenheitStrategy.converter(inputValue)
                "Celsius" to "Kelvin" -> KelvinStrategy.converter(FahrenheitStrategy.converter(inputValue))
                "Fahrenheit" to "Celsius" -> CelsiusStrategy.converter(inputValue)
                "Fahrenheit" to "Kelvin" -> KelvinStrategy.converter(inputValue)
                "Kelvin" to "Celsius" -> CelsiusStrategy.converter(FahrenheitStrategy.converter(inputValue - 273.15))
                "Kelvin" to "Fahrenheit" -> FahrenheitStrategy.converter(inputValue - 273.15)
                else -> inputValue // Mesma escala
            }

            binding.textviewResultNumber.text = String.format(
                "%.2f %s",
                result,
                targetScale.first().toString()
            )
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_popup_notify),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("APP_DMO", e.stackTraceToString())
        }
    }
}
