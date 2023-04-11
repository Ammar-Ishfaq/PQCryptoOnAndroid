#!/bin/bash
# SPDX-License-Identifier: MIT
# Generate the liboqs.so library for multiple Android ABIs (armeabi-v7a, arm64-v8a, x86 and x86_64) via xcompilation with NDK tooling.
# Default target API 21 (the lower the api the more compatibility with more API versions one gets). Should work with devices installed w/ APIs >= 24. 
# Originally tested with ndk 21.2.6472646
# run as:
# ./android-build.sh <PATH_TO_SDK>/ndk/21.2.6472646 

set -e
show_help() {
    echo ""
    echo " Usage: ./android-build.sh <ndk-dir> [-a <abi>] [-b <build-directory>] [-s <sdk-version>]"

    echo "   ndk-dir: the directory of the Android NDK (required)"
    echo "   abi: the Android ABI to target for the build"
    echo "   build-directory: the directory in which to build the project"
    echo "   sdk-version: the minimum Android SDK version (API level) to target"
    echo ""
    exit 0
}

oqs_xcompile() {

	target_abi=$1
	ndk_dir=$2
	target_dir=$3
	# Check SDK version is supported
	highestSdkVersion=31
	if (( 1 <= MINSDKVERSION && MINSDKVERSION <= highestSdkVersion ))
	then
	    echo "Compiling for SDK $MINSDKVERSION"
	else
	    echo "Invalid SDK level of $MINSDKVERSION"
	    exit 1
	fi

	cd $target_dir

	cmake ../../.. \
	    -DOQS_USE_OPENSSL=OFF \
        -DBUILD_SHARED_LIBS=ON  \
        -DCMAKE_TOOLCHAIN_FILE="$ndk_dir"/build/cmake/android.toolchain.cmake \
        -DANDROID_ABI="$target_abi" \
        -DANDROID_NATIVE_API_LEVEL="$MINSDKVERSION"
         #-DCMAKE_INSTALL_PREFIX=openssl/oqs 
	cmake --build ./ -j$(nproc)

	# Provide rudimentary information following build
	echo "Completed build run for ABI $target_abi, SDK Version $MINSDKVERSION"
}

# If no arguments provided, show help
if [ $# -eq 0 ]
then
    show_help
fi

# If help requested, show help
for arg in "$@"
do
    if [ "$arg" == "--help" ] || [ "$arg" == "-h" ]
    then
        show_help
    fi
done


NDK=$1
# Verify NDK is valid directory
if [ -d "$NDK" ]
then
    echo "Valid directory for NDK at $NDK"
else
    echo "Directory for NDK doesn't exist at $NDK"
    exit 1
fi

export ANDROID_NDK_HOME=$NDK
export PATH=$ANDROID_NDK_HOME:$PATH

# Parse optional parameters
MINSDKVERSION=24
OPTIND=2
while getopts "a:s:b:" flag
do
    case $flag in
        a) ABI=$OPTARG;;
        s) MINSDKVERSION=$OPTARG;;
        b) BUILDDIR=$OPTARG;;
        *) exit 1
    esac
done


# Check ABI is supported
declare -a valid_abis=("armeabi-v7a" "arm64-v8a" "x86" "x86_64")
declare -a ABIS=("armeabi-v7a" "arm64-v8a" "x86" "x86_64")

if [ ! -z $ABI ]; 
then
	abi_match=false
	for i in "${valid_abis[@]}"
	do
	   :
	   if [ "$ABI" == "$i" ]
	   then abi_match=true
	   fi
	done
	if [ "$abi_match" = true ]
	then
	    echo "Compiling for ABI $ABI"
	else
	    echo "Invalid Android ABI of $ABI"
	    echo "Valid ABIs are:"
	    printf "%s\\n" "${valid_abis[@]}"
	    exit 1
	fi
fi

# Target single ABI in case user-specified
if [ ! -z $ABI ]; then
	ABIS=("$ABI")
fi

OQSDIR=$(pwd)/liboqs-xcompile
if [ ! -d "$OQSDIR" ]; 
then
	git clone https://github.com/open-quantum-safe/liboqs.git $OQSDIR
fi

if [ -z $BUILDDIR ]; 
then
	BASEDIR=${OQSDIR}/"build"
else 
	BASEDIR=$BUILDDIR
fi

for target_abi in "${ABIS[@]}"; do
	echo -e "\nBuilding for api level=${MINSDKVERSION}, abi=${target_abi} .."
	# Remove build directory if it exists
	TARGETDIR="$BASEDIR/api_${MINSDKVERSION}/${target_abi}"
	if [ -d $TARGETDIR ]
	then
	    echo "Cleaning up previous build"
	    rm -rf "$TARGETDIR"
	fi

	echo "Building in directory $TARGETDIR"

	mkdir -p "$TARGETDIR"
	oqs_xcompile ${target_abi} ${ANDROID_NDK_HOME} ${TARGETDIR}
	#cd ${BUILDDIR} && sudo make install
done