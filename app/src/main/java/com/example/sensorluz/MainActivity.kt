package com.example.sensorluz

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvValores : TextView
    private lateinit var tvEstado : TextView
    private lateinit var tvTitulo : TextView

    // Crear un atributo que permita el manejo de sensores
    private lateinit var sensorManager: SensorManager

    // Atributo que permite usar el sensor
    private var sensorLuz: Sensor? = null
    private lateinit var layout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvValores = findViewById(R.id.tvValores)
        tvEstado = findViewById(R.id.tvEstado)
        tvTitulo = findViewById(R.id.tvTitulo)

        layout = findViewById(R.id.main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Hacer uso del sensor de luz
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        // Verificar si el sensor de luz esta disponible
        if (sensorLuz == null){
            tvEstado.text = "No hay sensor de luz"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // METODOS OBLIGATORIOS DE LA CLASE
    // Metodo para ver los cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {
        // Validar que el sensor sea el correcto
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){
            // Variable para guardar valores
            val luz = event.values[0]
            tvValores.text = "Luz: $luz lx"

            if (luz < 50){
                layout.setBackgroundColor(Color.BLACK)
                tvEstado.setTextColor(Color.WHITE)
                tvEstado.text = "Modo oscuro 7w7"
                tvValores.setTextColor(Color.WHITE)
                tvTitulo.setTextColor(Color.WHITE)



            }else if(luz > 500){
                layout.setBackgroundColor(Color.WHITE)
                tvEstado.setTextColor(Color.BLACK)
                tvEstado.text = "Modo claro UwU"
                tvValores.setTextColor(Color.BLACK)
                tvTitulo.setTextColor(Color.BLACK)
            }else{
                tvEstado.text = "Punto nemo"
            }
        }
    }

    // Metodo para hacer cambios en la precisión
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // METODO PARA ENCENDER EL SENSOR
    override fun onResume() {
        super.onResume()
        sensorLuz?.let{
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

}