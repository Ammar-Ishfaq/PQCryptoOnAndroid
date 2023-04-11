package com.example.android;

import android.util.Log;

import com.example.liboqs.KEMs;
import com.example.liboqs.KeyEncapsulation;
import com.example.liboqs.MechanismNotSupportedError;
import com.example.liboqs.Pair;

//import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@RunWith(Parameterized.class)
public class KEMTest {

    @Parameterized.Parameter(value = 0)
    public String kem_name;

    static ArrayList<String> ignoredKems = new ArrayList<>();

    /**
     * Method to convert the list of KEMs to a stream for input to testAllKEMs
     */
    @Parameterized.Parameters(name = "{0}")
    public static List<Object> getEnabledKEMs() {

        System.out.println("Initialize list of enabled KEMs");
        ArrayList<String> enabled_kems = KEMs.get_enabled_KEMs();

        // Do not use java streams as they are only supported on Android Nougat (7.0 = SDK 24) and above.
        List<Object> parameters = new ArrayList<>();
        for (String kemName : enabled_kems) {
            if (ignoredKems.contains(kemName)) {
                continue;
            }
            parameters.add(new Object[]{kemName});
        }
        return parameters;
    }

    /**
     * Test all enabled KEMs
     */
    //@ParameterizedTest(name = "Testing {arguments}")
    //@MethodSource("getEnabledKEMs")
    @Test
    public void testAllKEMs() {
        try {
            byte[] client_public_key;

            // Create client and server
            KeyEncapsulation client = new KeyEncapsulation(kem_name);
            KeyEncapsulation server = new KeyEncapsulation(kem_name);

            // Generate client key pair
            // Use a separate thread with larger stack for McEliece since it can consume 2-4MB of stack.
            if (kem_name.contains("McEliece")) {
                Thread t = new Thread(Thread.currentThread().getThreadGroup(), client, "T" + kem_name, 4 * 1024 * 1024);
                t.start();
                t.join();
                client_public_key = client.export_public_key();
            } else {
                client_public_key = client.generate_keypair();
            }
            //Log.d(getClass().getSimpleName(), "keypair done");

            // Server: encapsulate secret with client's public key
            Pair<byte[], byte[]> server_pair = server.encap_secret(client_public_key);
            byte[] ciphertext = server_pair.getLeft();
            byte[] shared_secret_server = server_pair.getRight();

            // Client: decapsulate
            byte[] shared_secret_client = client.decap_secret(ciphertext);

            // Check if equal
            assertArrayEquals(shared_secret_client, shared_secret_server, kem_name);

            client.dispose_KEM();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception " + kem_name);
        }
    }

    /**
     * Test the MechanismNotSupported Exception
     */
    public void testUnsupportedKEMExpectedException() {
        Assertions.assertThrows(MechanismNotSupportedError.class, () -> new KeyEncapsulation("MechanismNotSupported"));
    }
}