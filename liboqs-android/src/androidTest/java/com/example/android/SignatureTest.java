package com.example.android;

import static org.junit.jupiter.api.Assertions.assertTrue;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.liboqs.MechanismNotSupportedError;
import com.example.liboqs.Signature;
import com.example.liboqs.Sigs;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parallelized.class)
public class SignatureTest {

    private String sig_name;

    private byte[] message = "This is the message to sign".getBytes();

    /**
     *  Print test result when run via "am instrument"
     */
    private void out(String str) {
        Bundle b = new Bundle();
        b.putString(Instrumentation.REPORT_KEY_STREAMRESULT, "\n" + str);
        InstrumentationRegistry.getInstrumentation().sendStatus(0, b);
    }

    /**
     * Method to convert the list of Sigs to a list for input to testAllSigs.
     */
    @Parameterized.Parameters(name = "{0}")
    public static List<Object> getEnabledSigs() {
        ArrayList<String> enabled_sigs = Sigs.get_enabled_sigs();

        // Do not use java streams as they are only supported on Android Nougat (7.0 = SDK 24) and above.
        List<Object> parameters = new ArrayList<>();
        for (String sigName: enabled_sigs) {
            if (!sigName.contains("s-robust")) // The s-robust variants of Sphincs+ are too slow to run via github actions w/ ARM emulation
                parameters.add(new Object[] { sigName });
        }
        return parameters;
    }

    public SignatureTest(String signame) {
        this.sig_name = signame;
    }

    /**
     * Test all enabled Sigs
     */
    @ParameterizedTest(name = "Testing {arguments}")
    @MethodSource("getEnabledSigs")
    @Test
    public void testAllSigs() {
        Log.d(getClass().getSimpleName(), "Test " + sig_name);
        StringBuilder sb = new StringBuilder();
        sb.append(sig_name);
        sb.append(String.format("%1$" + (40 - sig_name.length()) + "s", ""));

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
        sb.append("[").append("PASSED").append("]");
        System.out.println(sb);
        out(sb.toString()); // print to adb instrument output
        signer.dispose_sig();
        verifier.dispose_sig();
    }


    /**
     * Test the MechanismNotSupported Exception
     */
    public void testUnsupportedSigExpectedException() {
        Assertions.assertThrows(MechanismNotSupportedError.class, () -> new Signature("MechanismNotSupported"));
    }
}

