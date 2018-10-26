package com.carlosu.androidappbundleexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val tag = "DynamicFeatures"
    private val packageNames = "com.carlosu.androidappbundleexample.ondemand"
    private val administrativoClassname = "$packageNames.AdministrativosActivity"

    private lateinit var manager: SplitInstallManager
    private lateinit var listener:SplitInstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Logica del Log In
        etEmail.setText("a@a.com")
        etPassword.setText("1234")
        btLogin.setOnClickListener { _ ->
            if (etEmail.text.toString() == "a@a.com"
                    && etPassword.text.toString() == "1234")   {
                val intent = Intent(this,AlumnoActivity::class.java)
                startActivity(intent)
            }else if (etEmail.text.toString() == "a@a.com"
                    && etPassword.text.toString() == "12345") {
                loadAndLaunchModule(moduleAdministrativos)
            }
        }

        // Crear instancia del SplitInstallManager
        manager= SplitInstallManagerFactory.create(this)

        //listener para saber el estado del modulo
        listener = SplitInstallStateUpdatedListener { state ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    displayLoadingState(state, "Descargando el modulo ${state.moduleNames()[0]}")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    launchActivity(administrativoClassname)
                    displayLogin()
                }

                SplitInstallSessionStatus.INSTALLING -> displayLoadingState(state, "Instalando el Modulo ${state.moduleNames()[0]}")
                SplitInstallSessionStatus.FAILED -> {
                    toastAndLog("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }
        }
    }

    override fun onResume() {
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        manager.unregisterListener(listener)
        super.onPause()
    }

    /** Display a loading state to the user. */
    private fun displayLoadingState(state: SplitInstallSessionState, message: String) {
        displayProgress()

        progressBar.max = state.totalBytesToDownload().toInt()
        progressBar.progress = state.bytesDownloaded().toInt()

        updateProgressMessage(message)
    }

    /** Display progress bar and text. */
    private fun displayProgress() {
        progress.visibility = View.VISIBLE
        login.visibility = View.GONE
    }

    private fun updateProgressMessage(message: String) {
        if (progress.visibility != View.VISIBLE) displayProgress()
        progressText.text = message
    }


    private val moduleAdministrativos by lazy { getString(R.string.name_administrativos) }

    /** Launch an activity by its class name. */
    private fun launchActivity(className: String) {
        Intent().setClassName(packageNames, className)
                .also {
                    startActivity(it)
                }
    }

    /** Display buttons to accept user input. */
    private fun displayLogin() {
        progress.visibility = View.GONE
        login.visibility = View.VISIBLE
    }

    fun toastAndLog(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        Log.d(tag, text)
    }

    /**
     * Load a feature by module name.
     * @param name The name of the feature module to load.
     */
    private fun loadAndLaunchModule(name: String) {
        updateProgressMessage("Cargando el Modulo $name")
        // Skip loading if the module already is installed. Perform success action directly.
        if (manager.installedModules.contains(name)) {
            updateProgressMessage("Ya esta instalado")
            launchActivity(administrativoClassname)
            displayLogin()
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
                .addModule(name)
                .build()

        // Load and install the requested feature module.
        manager.startInstall(request)

        updateProgressMessage("Cargando!")
    }

}


