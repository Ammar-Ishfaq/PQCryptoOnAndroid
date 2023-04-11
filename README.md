# PQCryptoOnAndroid
  
PQCryptoOnAndroid provides a sample Android project with a batch of NIST PQC KEM and Signature tests.

The PQC algorithm implementations come from the [libOQS](https://github.com/open-quantum-safe/liboqs) library.
The project illustrates how to deploy PQCrypto test batches on multiple Android cpu architectures and OS versions (APIs).

PQCryptoOnAndroid is a fork of [LibOQSTestApp](https://github.com/Hatzen/LibOQSTestApp) and addresses multiple issues, including:
* Build libOQS tests on 32-bit ARMv7 and x86 (in addition to ARMv8 and x86_64) architectures.
* Fix to a stack overflow issue of Classic McEliece. McEliece needs a 2-4MB stack but the Android JVM (ART) threads are defaulted to 1MB stacks.
* Allows to locally run PQCrypto tests on emulator devices for all major Android ABIs (armeabi-v7a, arm64-v8a, x86 and x86_64) without long boot timings.

## Project structure

The project is split into 
  
1. The module which wraps the JNI Interface to use liboqs on android (`liboqs-android` folder).

2. An example app (app folder) showing the usage with a fictional example:

<img src="https://user-images.githubusercontent.com/21283655/114078514-53566d00-98a9-11eb-919e-b587c62e41bd.png" height="300">  

## Description of sources and modifications

A Prebuilt version of libOQS (commit [b1d42d6](https://github.com/open-quantum-safe/liboqs/commit/b1d42d61f63aa61ce007ada7939e326e0d6e896c)) is provided for all Android ABIs (see `liboqs-android/jni/jniLibs/*`).
You can also generate updated prebuilt `liboqs.so` files for the latest libOQS versions by running the `gen_prebuilt_liboqs.sh` script (require NDK installed). 

The jni files `app/jni/jni/*` are slightly modified (from the [libOQS java wrapper](https://github.com/open-quantum-safe/liboqs-java)) (package name changes and a minor fix) to compile successfully.  
  
## Building and testing the project on Windows (should work on Ubuntu w/ small changes)
- Make sure you have a Java JDK installed.
- Download and install [Android Studio](https://developer.android.com/studio).
- Import the cloned PQCryptoOnAndroid project into Android Studio: `File` -> `New` -> `Import Project`.
- Build: `Build` -> `Make Project`.
- Create an AVD device (emulator):
	- `Tools` -> `Device Manager`. `Create Device`.
		- `Tablet`: `Nexus 10` -> `Next`. Now select an Android image:
			* For x86 you could pick under `recommended` an image with `api 24 : ABI x86`.
			* For x86_64 you could pick under `x86 images` an image with `api 24 : ABI x86_64`.
			* For armeabi-v7a and arm64-v8a you should pick under `x86 images` an image with `api 30 : ABI x86_64`. see [Run ARM binaries on Android emulator](https://android-developers.googleblog.com/2020/03/run-arm-apps-on-android-emulator.html).
		- `Next` -> `Finish`.
- Run the instrumented tests on the emulator. 
	- Start the emulator: `"play button"`. If booting time is slow check out [hardware accelation for emulators](https://developer.android.com/studio/run/emulator-acceleration#vm-windows).
	- Open Android studio terminal (`View` -> `Tool Windows` -> `Terminal`).
	- Type: `./gradlew connectedDebugAndroidTest -i`.

The instrumented tests should run and the result can be viewed by opening the xml report file under `.\liboqs-android\build\outputs\androidTest-results\connected\` .
See a successful sample [report](https://github.com/geovandro/PQCryptoOnAndroid/tree/master/liboqs-android/sample-test-report/report.xml).


## TODOs
 - [ ] Add tests for an Android device running a post-quantum OpenSSL client to connect to a remote TLS server.
 - [ ] Provide build and testing instructions for Ubuntu and macOS
