
package br.edu.ifsp.dmo.conversordetemperatura.model

object KelvinStrategy : TemperatureConverter {
    override fun converter(temperature: Double): Double {
        return (temperature - 32) / 1.8 + 273.15
    }

    override fun getScale(): String {
        return "°K"
    }
}
