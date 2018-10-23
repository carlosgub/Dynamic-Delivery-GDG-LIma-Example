package com.example.carlosu.androidappbundleexample

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

    private val TAG = "DynamicFeatures"
    private val packageNames = "com.example.carlosu.androidappbundleexample.ondemand"
    private val AdministrativoClassname = "$packageNames.AdministrativosActivity"

    private lateinit var manager: SplitInstallManager
    lateinit var listener:SplitInstallStateUpdatedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creates an instance of SplitInstallManager.
        manager= SplitInstallManagerFactory.create(this)

        //listener para saber el estado del modulo
        listener = SplitInstallStateUpdatedListener { state ->
            // Handle changes in state.
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    //  In order to see this, the application has to be uploaded to the Play Store.
                    displayLoadingState(state, "Downloading Profesor")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    /*
                      This may occur when attempting to download a sufficiently large module.

                      In order to see this, the application has to be uploaded to the Play Store.
                      Then features can be requested until the confirmation path is triggered.
                     */
                    startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    launchActivity(AdministrativoClassname)
                    displayLogin()
                }

                SplitInstallSessionStatus.INSTALLING -> displayLoadingState(state, "Instalando Modulo Administrativo")
                SplitInstallSessionStatus.FAILED -> {
                    toastAndLog("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }

        }

        etEmail.setText("a@a.com")
        etPassword.setText("1234")
        btLogin.setOnClickListener { _ ->
            if (etEmail.text.toString().equals("a@a.com")
                    && etPassword.text.toString().equals("1234"))   {
                val intent = Intent(this,AlumnoActivity::class.java)
                startActivity(intent)
            }else if (etEmail.text.toString().equals("a@a.com")
                    && etPassword.text.toString().equals("12345")) {
                loadAndLaunchModule(moduleAdministrativos)
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


    private val moduleAdministrativos by lazy { getString(R.string.title_login_administrativos) }

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
        Log.d(TAG, text)
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
            launchActivity(AdministrativoClassname)
            displayLogin()
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
                .addModule(name)
                .build()

        // Load and install the requested feature module.
        manager.startInstall(request)

        updateProgressMessage("Instalando el modulo $name")
    }

}


