package br.edu.ifsp.dmo.conversordetemperatura.model
object CelsiusStrategy: TemperatureConverter {
    override fun converter(temperature: Double) = (temperature - 32) / 1.8
    override fun getScale() = "ºC"
}