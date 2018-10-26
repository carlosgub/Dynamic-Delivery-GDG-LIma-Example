# AndroidAppBundleExample <a href="https://play.google.com/store/apps/details?id=com.carlosu.androidappbundleexample.ondemand"><img src="https://png2.kisspng.com/sh/8d69c6e7892e9eb0b93d553b5b455df0/L0KzQYm3VMA3N5VAj5H0aYP2gLBuTfdwd5hxfZ95bHH8PbL1hQJwcZUyeeJ5LYP3f8PsTfdwd5hxfZ95bHH8PYbog8diOmNnTasAYke8PomAVMU2QWE6Sac7M0C2Qoe4WcM3Pl91htk=/kisspng-google-play-android-app-store-google-play-5ac7a22b595b79.874559051523032619366.png" height="40" /></a>

<img src="https://developer.android.com/static/images/app-bundle/app-bundle-logo.svg" height="100" />

Este es un ejemplo del uso de Dynamic Delivery de Android App Bundle, presentado en Google I/O 18.

Este ejemplo es para descargar de manera dinamica features de una aplicacion.

Que es Android app bundle?

Android App Bundle es el nuevo formato de publicación de aplicaciones, que te ayudará a crear y lanzar de manera más eficiente tu aplicación. Android App Bundle te permitirá ofrecer una gran experiencia más fácilmente en un tamaño de aplicación más pequeño, lo que te permitirá que más clientes puedan descargar tu aplicación en la actualidad. Es fácil de cambiar. No necesita refactorizar su código para comenzar a beneficiarse de una aplicación más pequeña.

<img src="/images/appBundle.gif" height="300" />

Que es Dynamic Delivery?

Dynamic Delivery es una característica de Android App Bundle que se encuentra en fase beta que ayuda a las empresas o desarrolladores a entregar una aplicación más personalizada a los usuarios mediante la entrega de las funcionalidades de manera dinámica. ¿Como? Se sube un Base APK donde estan todas las funcionalidades que la mayoría de los usuarios usan. Y los demás funcionalidades serán descargados cuando el usuario quiera usar esta característica.

<img src="https://i.imgur.com/ztrm7ko.png" height="500" />

Puntos positivos?

* Reducción del tamaño de la aplicación
* Mayor cantidad de dispositivos que pueden usar nuestra aplicación base
* Creación de una aplicación más personalizada para cada usuario.

Puntos negativos?

* La única manera de probar la app es subiendo el .abb al play store (Google ya esta trabajando la manera de probarlo de manera offline)
* Se debe usar la herramienta bundletool para verificar que el .abb no contenga errores antes de subirlo al playstore

# Pasos para poder compilar la repo
<img src="/images/app.gif" height="700" />

# Pasos para poder compilar la repo
* Compilar.

# Pasos para probar la app con la funcion Dynamic Delivery con cuenta propia
* Cambiar el aplicationId.
* Subir la aplicacion a su cuenta de Google Play Console.

# Pasos para probar la app
* Descargar la aplicacion del play store 

<a href="https://play.google.com/store/apps/details?id=com.carlosu.androidappbundleexample.ondemand"><img src="https://png2.kisspng.com/sh/8d69c6e7892e9eb0b93d553b5b455df0/L0KzQYm3VMA3N5VAj5H0aYP2gLBuTfdwd5hxfZ95bHH8PbL1hQJwcZUyeeJ5LYP3f8PsTfdwd5hxfZ95bHH8PYbog8diOmNnTasAYke8PomAVMU2QWE6Sac7M0C2Qoe4WcM3Pl91htk=/kisspng-google-play-android-app-store-google-play-5ac7a22b595b79.874559051523032619366.png" height="100" /></a>

# Cuentas del Log In
* Correo : a@a.com -- contraseña : 1234 -> Log in como alumno
* Correo : a@a.com -- contraseña : 12345 -> Log in como administrativo (Dynamic Feature)

# Arquitectura del Proyecto
 ![](https://i.imgur.com/CF7YKcw.jpg)

## Kotlin
---
 * Kotlin [1.2.71] - http://kotlinlang.org
 
 ## Librerias
---
 * Ninguna.
 
