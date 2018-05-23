package com.example.carlosu.androidappbundleexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.tasks.OnFailureListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail.setText("a@a.com")
        etPassword.setText("1234")

        bLogin.setOnClickListener { v ->
            val intent  = Intent(v.context,AlumnoActivity::class.java)
            if (etEmail.text.toString().equals("a@a.com")
                && etPassword.text.toString().equals("1234"))  {
                startActivity(intent)
            }else if (etEmail.text.toString().equals("a@a.com")
                    && etPassword.text.toString().equals("12345")) {
                // Creates an instance of SplitInstallManager.
                val splitInstallManager = SplitInstallManagerFactory.create(v.context)
                val request = SplitInstallRequest.newBuilder().addModule("login_administrativos").build()
                splitInstallManager
                        .startInstall(request)
                        .addOnSuccessListener { i: Int? -> "" }
                        .addOnFailureListener(OnFailureListener { exception ->  })
            }
        }
    }
}
