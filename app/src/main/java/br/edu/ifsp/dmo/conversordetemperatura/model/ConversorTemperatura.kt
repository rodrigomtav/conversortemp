package br.edu.ifsp.dmo.conversordetemperatura.model
interface TemperatureConverter {
    fun converter(temperature: Double): Double
    fun getScale(): String
}
