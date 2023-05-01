![Project workflow](https://github.com/geovandro/PQCryptoOnAndroid/actions/workflows/android.yml/badge.svg)

# PQCryptoOnAndroid
  
PQCryptoOnAndroid provides a sample Android project with [instrumentation tests](https://developer.android.com/reference/android/app/Instrumentation) for the NIST PQC KEM and Signature algorithms.

The PQC implementations come from the [libOQS](https://github.com/open-quantum-safe/liboqs) library.
The project illustrates how to deploy PQCrypto instrumented testing on multiple Android CPU architectures and OS versions (APIs). It also allows to run the tests via Github CI actions.

PQCryptoOnAndroid is a fork of [LibOQSTestApp](https://github.com/Hatzen/LibOQSTestApp) and addresses issues like:
* Building and testing (via Github CI on remote emulators) libOQS on 32-bit ARMv7 and x86 (in addition to ARMv8 and x86_64) architectures.
* Stack overflow from Classic McEliece. McEliece requires 2-4MB stack space while the Android JVM (ART) thread stack size is 1MB by default. PQCryptoOnAndroid increases the thread stack size in those cases to allow Classic McEliece tests to run. 
* Slow testing on emulators. Allows to locally run PQC tests on emulator devices for major Android ABIs (armeabi-v7a, arm64-v8a, x86 and x86_64) without long boot timings (in certain [hardware](https://developer.android.com/studio/run/emulator-acceleration#vm-windows)).   
&ensp; This project gives tailored emulator configurations for faster local tests.

## Project structure

The project is split into 
  
1. The module which wraps the JNI Interface to use liboqs on android (`liboqs-android` folder).

2. An example app (app folder) showing the usage with a fictional example:

<img src="https://user-images.githubusercontent.com/21283655/114078514-53566d00-98a9-11eb-919e-b587c62e41bd.png" height="300">  

## Description of sources and modifications

A Prebuilt version of libOQS (commit [b1d42d6](https://github.com/open-quantum-safe/liboqs/commit/b1d42d61f63aa61ce007ada7939e326e0d6e896c)) is provided for all Android ABIs (see `liboqs-android/jni/jniLibs/*`).
You can also generate updated prebuilt `liboqs.so` files for the latest libOQS versions by running the `gen_prebuilt_liboqs.sh` script (require NDK installed). 

The jni files `app/jni/jni/*` are slightly modified (from the [libOQS java wrapper](https://github.com/open-quantum-safe/liboqs-java)) (such as the McEliece thread fix) to compile and run successfully.  
  
## Building and testing the project (tested on Windows but should work on Ubuntu and MacOS w/ small changes)
- Make sure you have a Java JDK installed.
- Download and install [Android Studio](https://developer.android.com/studio).
- Import the cloned PQCryptoOnAndroid project into Android Studio: `File` -> `New` -> `Import Project`.
- Sync the Gradle configurations: `File` -> `Sync Project with Gradle Files`.
- Create an AVD device (emulator):
	- `Tools` -> `Device Manager`. `Create Device`.
		- `Tablet`: `Nexus 10` -> `Next`. Now select an Android image:
			* For x86 ABI you could pick under `recommended` an image with `API 24 : ABI x86`.
			* For x86_64 ABI you could pick under `x86 images` an image with `API 24 : ABI x86_64`.
			* For armeabi-v7a or arm64-v8a ABIs: 
              * On an ARM-based host, pick under `other images` an image with ABI `armeabi-v7a` or `arm64-v8a`. Recommended an API level between 24 and 31.
              * On a x86_64 host: pick under `x86 images` an image with `API 30 : ABI x86_64` (see also [Run ARM binaries on Android emulator](https://android-developers.googleblog.com/2020/03/run-arm-apps-on-android-emulator.html)).
		- `Next` -> `Finish`.
- Build the project for instrumentation tests locally on the emulator:
  - Open Android studio terminal (`View` -> `Tool Windows` -> `Terminal`).
  - Run: `.\gradlew :liboqs-android:assembleDebugAndroidTest [-Pabi-splits=<ABI>]`  
  - Run the instrumented tests on the emulator:
    - Start the emulator: `"play button"`. If booting time is slow check out [hardware accelation for emulators](https://developer.android.com/studio/run/emulator-acceleration#vm-windows). 
    - Open Android studio terminal (`View` -> `Tool Windows` -> `Terminal`).
    - Run: `.\gradle :liboqs-android:connectedDebugAndroidTest -i [-Pabi-splits=<ABI>]`.  
&emsp;&emsp;&ensp;The optional parameter `-Pabi-splits=<ABI>` allows you to install a specific ABI into the APK. This is needed for testing ARM ABIs on x86_64 emulators. If a specific ABI is not set, multiple versions are shipped and the emulator will pick an x86-based one as its primary ABI. See [Android platform ABI support](https://developer.android.com/ndk/guides/abis#android-platform-abi-support).
&emsp;&emsp;&ensp;The local instrumented tests should generate a testing report that can be viewed by opening the xml file under `.\liboqs-android\build\outputs\androidTest-results\connected\`.

## TODOs
 - [ ] Add tests for an Android device running a post-quantum OpenSSL client to connect to a remote TLS server.
 - [ ] Provide build and testing instructions for Ubuntu and macOS

## Remark on Sphincs+
Although Sphincs+ tests pass and run fast with x86-based ABIs on emulators, they get very slow on ARM-based ABI emulation, especially the "s-robust" variants. For example, if the "s-robust" variants are included it can double the workflow time for the arm64-v8a ABI (from 25+ minutes to 45+ minutes). Therefore we omit the "s-robust" tests on the CI workflow.  