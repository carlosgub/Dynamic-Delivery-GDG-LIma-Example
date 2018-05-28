package com.example.carlosu.androidappbundleexample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    var mySessionId:Int? = 0
    lateinit var splitInstallManager:SplitInstallManager
    lateinit var listener:SplitInstallStateUpdatedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creates an instance of SplitInstallManager.
        splitInstallManager= SplitInstallManagerFactory.create(this)

        //listener para saber el estado del modulo
        listener = SplitInstallStateUpdatedListener { state ->
            Log.d(":)",state.sessionId().toString())
            when(state.sessionId()){
                3->Toast.makeText(this,"Esta Descargado",Toast.LENGTH_LONG).show()
                2->Toast.makeText(this,"Se esta Descargando",Toast.LENGTH_LONG).show()
                5->Toast.makeText(this,"Esta Instalado",Toast.LENGTH_LONG).show()
                4->Toast.makeText(this,"Esta Instalado",Toast.LENGTH_LONG).show()
                else->{Toast.makeText(this,state.sessionId().toString(),Toast.LENGTH_LONG).show()}
            }
        }

        splitInstallManager.registerListener(listener)

        etEmail.setText("a@a.com")
        etPassword.setText("1234")
        bLogin.setOnClickListener { _ ->
            if (etEmail.text.toString().equals("a@a.com")
                    && etPassword.text.toString().equals("1234"))   {
                val intent = Intent(this,AlumnoActivity::class.java)
                startActivity(intent)
            }else if (etEmail.text.toString().equals("a@a.com")
                    && etPassword.text.toString().equals("12345")) {
                val request = SplitInstallRequest.newBuilder().addModule("login_administrativos").build()
                splitInstallManager
                        .startInstall(request)
                        .addOnSuccessListener { i: Int? ->
                            mySessionId = i
                            val intent = Intent(this,
                                    Class.forName("com.example.carlosu.login_administrativos.AdministrativosActivity"))
                            splitInstallManager.unregisterListener(listener)
                            startActivity(intent) }
                        .addOnFailureListener{ exception: Exception? ->
                        }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(listener)
    }

}
