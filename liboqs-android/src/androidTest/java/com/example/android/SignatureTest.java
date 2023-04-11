package com.example.android;


import android.util.Log;

import com.example.liboqs.MechanismNotSupportedError;
import com.example.liboqs.Signature;
import com.example.liboqs.Sigs;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Parameterized.class)
public class SignatureTest {

    @Parameterized.Parameter(value = 0)
    public String sig_name;

    static ArrayList<String> ignoredSigs = new ArrayList<>();

    /**
     * Method to convert the list of Sigs to a list for input to testAllSigs.
     */
    @Parameterized.Parameters(name = "{0}")
    public static List<Object> getEnabledSigs() {

        System.out.println("Initialize list of enabled Signatures");
        ArrayList<String> enabled_sigs = Sigs.get_enabled_sigs();

        // Do not use java streams as they are only supported on Android Nougat (7.0 = SDK 24) and above.
        List<Object> parameters = new ArrayList<>();
        for (String kemName: enabled_sigs) {
            parameters.add( new Object[] { kemName });
        }
        return parameters;
    }

    private final byte[] message = "This is the message to sign".getBytes();


    /**
     * Test all enabled Sigs
     */
//    @ParameterizedTest(name = "Testing {arguments}")
//    @MethodSource("getEnabledSigs")
    @Test
    public void testAllSigs() {
        Log.d(getClass().getSimpleName(), "Test " + sig_name);
        StringBuilder sb = new StringBuilder();
        sb.append(sig_name);
        sb.append(String.format("%1$" + (40 - sig_name.length()) + "s", ""));

        // Create signer and verifier
        Signature signer = new Signature(sig_name);
        Signature verifier = new Signature(sig_name);

        // Generate signer key pair
        byte[] signer_public_key = signer.generate_keypair();

        // Sign the message
        byte[] signature = signer.sign(message);

        // Verify the signature
        boolean is_valid = verifier.verify(message, signature, signer_public_key);

        assertTrue(is_valid, sig_name);

        // If successful print Sig name, otherwise an exception will be thrown
        sb.append("\033[0;32m").append("PASSED").append("\033[0m");
        System.out.println(sb.toString());
    }

    /**
     * Test the MechanismNotSupported Exception
     */
    public void testUnsupportedSigExpectedException() {
        Assertions.assertThrows(MechanismNotSupportedError.class, () -> new Signature("MechanismNotSupported"));
    }
}