package se.linerotech.module201.project1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTemperature: EditText
    private lateinit var buttonCelsius: Button
    private lateinit var buttonFahrenheit: Button
    private lateinit var buttonKelvin: Button
    private lateinit var textViewResult1: TextView
    private lateinit var textViewResult2: TextView

    private var selectedUnit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTemperature = findViewById(R.id.editTextTemperature)
        buttonCelsius = findViewById(R.id.buttonCelsius)
        buttonFahrenheit = findViewById(R.id.buttonFahrenheit)
        buttonKelvin = findViewById(R.id.buttonKelvin)
        textViewResult1 = findViewById(R.id.textViewResult1)
        textViewResult2 = findViewById(R.id.textViewResult2)

        editTextTemperature.doAfterTextChanged { updateResults() }

        buttonCelsius.setOnClickListener {
            selectedUnit = UNIT_CELSIUS
            updateResults()
        }
        buttonFahrenheit.setOnClickListener {
            selectedUnit = UNIT_FAHRENHEIT
            updateResults()
        }
        buttonKelvin.setOnClickListener {
            selectedUnit = UNIT_KELVIN
            updateResults()
        }
    }

    private fun updateResults() {
        val inputText = editTextTemperature.text.toString()
        if (inputText.isEmpty()) {
            if (selectedUnit != null) {
                Toast.makeText(this, MESSAGE_ENTER_VALUE_FIRST, Toast.LENGTH_SHORT).show()
            }
            textViewResult1.text = EMPTY
            textViewResult2.text = EMPTY
            return
        }

        val value = inputText.toDoubleOrNull() ?: return

        when (selectedUnit) {
            UNIT_CELSIUS -> {
                val f = value * NINE / FIVE + THIRTY_TWO
                val k = value + KELVIN_OFFSET
                textViewResult1.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_FAHRENHEIT, f)
                textViewResult2.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_KELVIN, k)
            }
            UNIT_FAHRENHEIT -> {
                val c = (value - THIRTY_TWO) * FIVE / NINE
                val k = c + KELVIN_OFFSET
                textViewResult1.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_CELSIUS, c)
                textViewResult2.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_KELVIN, k)
            }
            UNIT_KELVIN -> {
                val c = value - KELVIN_OFFSET
                val f = c * NINE / FIVE + THIRTY_TWO
                textViewResult1.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_CELSIUS, c)
                textViewResult2.text = String.format(Locale.US, ONE_DECIMAL_WITH_SPACE_AND_FAHRENHEIT, f)
            }
            else -> {
                textViewResult1.text = EMPTY
                textViewResult2.text = EMPTY
            }
        }
    }

    companion object {
        // Conversion constants
        private const val NINE = 9.0
        private const val FIVE = 5.0
        private const val THIRTY_TWO = 32.0
        private const val KELVIN_OFFSET = 273.15

        // Unit markers (helps avoid magic strings)
        private const val UNIT_CELSIUS = "C"
        private const val UNIT_FAHRENHEIT = "F"
        private const val UNIT_KELVIN = "K"

        // Strings / formats (avoid magic strings & keep detekt happy)
        private const val ONE_DECIMAL_WITH_SPACE_AND_CELSIUS = "%.1f °C"
        private const val ONE_DECIMAL_WITH_SPACE_AND_FAHRENHEIT = "%.1f °F"
        private const val ONE_DECIMAL_WITH_SPACE_AND_KELVIN = "%.1f Kelvin"
        private const val MESSAGE_ENTER_VALUE_FIRST = "Please enter a value first"
        private const val EMPTY = ""
    }
}
