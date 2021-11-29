package com.example.ladm_u4_tarea1_calendar

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siLecturaCalendario = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Eventos del calendario")

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR),siLecturaCalendario)
        }
        button.setOnClickListener {
            cargarListaCalendar()
        }
    }

    @SuppressLint("Range")
    private fun cargarListaCalendar() {
        var res="Eventos:\n\n"
        var cursorCalendario = contentResolver.query(
            CalendarContract.Events.CONTENT_URI,null,null,null,null
        )
        if(cursorCalendario!=null){
            if(cursorCalendario.moveToFirst()){
                do{
                    var idCalendar = cursorCalendario.getInt(cursorCalendario.getColumnIndex(CalendarContract.Events._ID))
                    var nomAccount = cursorCalendario.getString(cursorCalendario.getColumnIndex(CalendarContract.Events.ACCOUNT_NAME))
                    var titulo = cursorCalendario.getString(cursorCalendario.getColumnIndex(CalendarContract.Events.TITLE))
                    var ownAcconunt = cursorCalendario.getString(cursorCalendario.getColumnIndex(CalendarContract.Events.OWNER_ACCOUNT))
                    res += "ID: "+idCalendar+"\nNombre de la cuenta: \n"+nomAccount+"\n\nTitulo: "+titulo+"\n\nDueño: \n"+ownAcconunt+"\n\n---------------------\n\n"
                }while (cursorCalendario.moveToNext())
                textView.setText(res)
            }
            else{
                res="No hay nada en el calendario"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==siLecturaCalendario){
            setTitle("Permiso Otorgado")
        }
    }
}