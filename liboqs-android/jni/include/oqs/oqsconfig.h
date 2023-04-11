// SPDX-License-Identifier: MIT

#define OQS_VERSION_TEXT "0.8.0-dev"
#define OQS_COMPILE_BUILD_TARGET "armv7-a-Linux-4.15.0-206-generic"
#define OQS_DIST_BUILD 1
/* #undef OQS_DIST_X86_64_BUILD */
/* #undef OQS_DIST_X86_BUILD */
/* #undef OQS_DIST_ARM64_V8_BUILD */
#define OQS_DIST_ARM32_V7_BUILD 1
/* #undef OQS_DIST_PPC64LE_BUILD */
/* #undef OQS_DEBUG_BUILD */
/* #undef ARCH_X86_64 */
/* #undef ARCH_ARM64v8 */
#define ARCH_ARM32v7 1
#define BUILD_SHARED_LIBS 1
/* #undef OQS_BUILD_ONLY_LIB */
#define OQS_OPT_TARGET "generic"
/* #undef USE_SANITIZER */
/* #undef CMAKE_BUILD_TYPE */

/* #undef OQS_USE_OPENSSL */
/* #undef OQS_USE_AES_OPENSSL */
/* #undef OQS_USE_SHA2_OPENSSL */
/* #undef OQS_USE_SHA3_OPENSSL */

#define OQS_USE_PTHREADS_IN_TESTS 1

/* #undef OQS_USE_ADX_INSTRUCTIONS */
/* #undef OQS_USE_AES_INSTRUCTIONS */
/* #undef OQS_USE_AVX_INSTRUCTIONS */
/* #undef OQS_USE_AVX2_INSTRUCTIONS */
/* #undef OQS_USE_AVX512_INSTRUCTIONS */
/* #undef OQS_USE_BMI1_INSTRUCTIONS */
/* #undef OQS_USE_BMI2_INSTRUCTIONS */
/* #undef OQS_USE_PCLMULQDQ_INSTRUCTIONS */
/* #undef OQS_USE_VPCLMULQDQ_INSTRUCTIONS */
/* #undef OQS_USE_POPCNT_INSTRUCTIONS */
/* #undef OQS_USE_SSE_INSTRUCTIONS */
/* #undef OQS_USE_SSE2_INSTRUCTIONS */
/* #undef OQS_USE_SSE3_INSTRUCTIONS */

/* #undef OQS_USE_ARM_AES_INSTRUCTIONS */
/* #undef OQS_USE_ARM_SHA2_INSTRUCTIONS */
/* #undef OQS_USE_ARM_SHA3_INSTRUCTIONS */
/* #undef OQS_USE_ARM_NEON_INSTRUCTIONS */

/* #undef OQS_SPEED_USE_ARM_PMU */

/* #undef OQS_ENABLE_TEST_CONSTANT_TIME */

/* #undef OQS_ENABLE_SHA3_xkcp_low_avx2 */

/* #undef OQS_ENABLE_KEM_BIKE */
/* #undef OQS_ENABLE_KEM_bike_l1 */
/* #undef OQS_ENABLE_KEM_bike_l3 */
/* #undef OQS_ENABLE_KEM_bike_l5 */

#define OQS_ENABLE_KEM_FRODOKEM 1
#define OQS_ENABLE_KEM_frodokem_640_aes 1
#define OQS_ENABLE_KEM_frodokem_640_shake 1
#define OQS_ENABLE_KEM_frodokem_976_aes 1
#define OQS_ENABLE_KEM_frodokem_976_shake 1
#define OQS_ENABLE_KEM_frodokem_1344_aes 1
#define OQS_ENABLE_KEM_frodokem_1344_shake 1

#define OQS_ENABLE_KEM_NTRUPRIME 1
#define OQS_ENABLE_KEM_ntruprime_sntrup761 1
/* #undef OQS_ENABLE_KEM_ntruprime_sntrup761_avx2 */

///// OQS_COPY_FROM_UPSTREAM_FRAGMENT_ADD_ALG_ENABLE_DEFINES_START

#define OQS_ENABLE_KEM_CLASSIC_MCELIECE 1
#define OQS_ENABLE_KEM_classic_mceliece_348864 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_348864_avx */
#define OQS_ENABLE_KEM_classic_mceliece_348864f 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_348864f_avx */
#define OQS_ENABLE_KEM_classic_mceliece_460896 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_460896_avx */
#define OQS_ENABLE_KEM_classic_mceliece_460896f 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_460896f_avx */
#define OQS_ENABLE_KEM_classic_mceliece_6688128 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_6688128_avx */
#define OQS_ENABLE_KEM_classic_mceliece_6688128f 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_6688128f_avx */
#define OQS_ENABLE_KEM_classic_mceliece_6960119 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_6960119_avx */
#define OQS_ENABLE_KEM_classic_mceliece_6960119f 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_6960119f_avx */
#define OQS_ENABLE_KEM_classic_mceliece_8192128 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_8192128_avx */
#define OQS_ENABLE_KEM_classic_mceliece_8192128f 1
/* #undef OQS_ENABLE_KEM_classic_mceliece_8192128f_avx */

#define OQS_ENABLE_KEM_HQC 1
#define OQS_ENABLE_KEM_hqc_128 1
/* #undef OQS_ENABLE_KEM_hqc_128_avx2 */
#define OQS_ENABLE_KEM_hqc_192 1
/* #undef OQS_ENABLE_KEM_hqc_192_avx2 */
#define OQS_ENABLE_KEM_hqc_256 1
/* #undef OQS_ENABLE_KEM_hqc_256_avx2 */

#define OQS_ENABLE_KEM_KYBER 1
#define OQS_ENABLE_KEM_kyber_512 1
/* #undef OQS_ENABLE_KEM_kyber_512_avx2 */
/* #undef OQS_ENABLE_KEM_kyber_512_aarch64 */
#define OQS_ENABLE_KEM_kyber_768 1
/* #undef OQS_ENABLE_KEM_kyber_768_avx2 */
/* #undef OQS_ENABLE_KEM_kyber_768_aarch64 */
#define OQS_ENABLE_KEM_kyber_1024 1
/* #undef OQS_ENABLE_KEM_kyber_1024_avx2 */
/* #undef OQS_ENABLE_KEM_kyber_1024_aarch64 */
#define OQS_ENABLE_KEM_kyber_512_90s 1
/* #undef OQS_ENABLE_KEM_kyber_512_90s_avx2 */
#define OQS_ENABLE_KEM_kyber_768_90s 1
/* #undef OQS_ENABLE_KEM_kyber_768_90s_avx2 */
#define OQS_ENABLE_KEM_kyber_1024_90s 1
/* #undef OQS_ENABLE_KEM_kyber_1024_90s_avx2 */

#define OQS_ENABLE_SIG_DILITHIUM 1
#define OQS_ENABLE_SIG_dilithium_2 1
/* #undef OQS_ENABLE_SIG_dilithium_2_avx2 */
/* #undef OQS_ENABLE_SIG_dilithium_2_aarch64 */
#define OQS_ENABLE_SIG_dilithium_3 1
/* #undef OQS_ENABLE_SIG_dilithium_3_avx2 */
/* #undef OQS_ENABLE_SIG_dilithium_3_aarch64 */
#define OQS_ENABLE_SIG_dilithium_5 1
/* #undef OQS_ENABLE_SIG_dilithium_5_avx2 */
/* #undef OQS_ENABLE_SIG_dilithium_5_aarch64 */
#define OQS_ENABLE_SIG_dilithium_2_aes 1
/* #undef OQS_ENABLE_SIG_dilithium_2_aes_avx2 */
#define OQS_ENABLE_SIG_dilithium_3_aes 1
/* #undef OQS_ENABLE_SIG_dilithium_3_aes_avx2 */
#define OQS_ENABLE_SIG_dilithium_5_aes 1
/* #undef OQS_ENABLE_SIG_dilithium_5_aes_avx2 */

#define OQS_ENABLE_SIG_FALCON 1
#define OQS_ENABLE_SIG_falcon_512 1
/* #undef OQS_ENABLE_SIG_falcon_512_avx2 */
#define OQS_ENABLE_SIG_falcon_1024 1
/* #undef OQS_ENABLE_SIG_falcon_1024_avx2 */

#define OQS_ENABLE_SIG_SPHINCS 1
#define OQS_ENABLE_SIG_sphincs_haraka_128f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_128f_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_128f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_128f_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_128s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_128s_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_128s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_128s_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_192f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_192f_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_192f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_192f_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_192s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_192s_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_192s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_192s_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_256f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_256f_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_256f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_256f_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_256s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_256s_robust_aesni */
#define OQS_ENABLE_SIG_sphincs_haraka_256s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_haraka_256s_simple_aesni */
#define OQS_ENABLE_SIG_sphincs_sha256_128f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_128f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_128f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_128f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_128s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_128s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_128s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_128s_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_192f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_192f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_192f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_192f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_192s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_192s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_192s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_192s_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_256f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_256f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_256f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_256f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_256s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_256s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_sha256_256s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_sha256_256s_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_128f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_128f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_128f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_128f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_128s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_128s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_128s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_128s_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_192f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_192f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_192f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_192f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_192s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_192s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_192s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_192s_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_256f_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_256f_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_256f_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_256f_simple_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_256s_robust 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_256s_robust_avx2 */
#define OQS_ENABLE_SIG_sphincs_shake256_256s_simple 1
/* #undef OQS_ENABLE_SIG_sphincs_shake256_256s_simple_avx2 */
///// OQS_COPY_FROM_UPSTREAM_FRAGMENT_ADD_ALG_ENABLE_DEFINES_END
