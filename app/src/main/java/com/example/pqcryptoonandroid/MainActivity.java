package com.example.pqcryptoonandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.liboqs.Common;
import com.example.liboqs.KEMs;
import com.example.liboqs.KeyEncapsulation;
import com.example.liboqs.Pair;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static class Client {
        KeyEncapsulation kem;
        String sharedSecret;
        String encryptedText;
    }

    Client client1;
    Client client2;

    Client currentClient;

    private String[] algorithmNames;

    EditText keyTextField;
    EditText plainTextField;
    EditText encryptedTextField;
    EditText decryptedTextField;
    TextView selectedFolderPath;
    Button selectFolderButton;
    Button decryptFilesButton;
    TabLayout tabLayout;
    private Boolean isEncrypt = true;
    private Uri selectedFolderUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Serverless Example");

        algorithmNames = KEMs.get_enabled_KEMs().toArray(new String[0]);

        client1 = new Client();
        client2 = new Client();
        currentClient = client1;

        setupView();
        initCipher(0);

        updateScreen(getOtherClient());
    }

    private void initCipher(int position) {
        String cipher = algorithmNames[position];
        client1.kem = new KeyEncapsulation(cipher);
        client2.kem = new KeyEncapsulation(cipher);

        byte[] publicKeyClient1 = client1.kem.generate_keypair();

        Pair<byte[], byte[]> sharedSecretEncryptedAndPlainText = client2.kem.encap_secret(publicKeyClient1);
        client2.sharedSecret = Common.to_hex(sharedSecretEncryptedAndPlainText.getRight()).substring(0, 16);

        byte[] sharedSecretPlain = client1.kem.decap_secret(sharedSecretEncryptedAndPlainText.getLeft());
        client1.sharedSecret = Common.to_hex(sharedSecretPlain).substring(0, 16);
    }

    private void updateScreen(Client otherClient) {
        keyTextField.setText(currentClient.sharedSecret);

        String plainText = plainTextField.getText().toString();
        String encrypted = SymmetricEncryptionHelper.useDefaultIv(currentClient.sharedSecret).encrypt(plainText);
        encryptedTextField.setText(encrypted);

        encrypted = otherClient.encryptedText;
        if (encrypted == null || "".equals(encrypted)) {
            return;
        }
        plainText = SymmetricEncryptionHelper.useDefaultIv(currentClient.sharedSecret).decrypt(encrypted);
        decryptedTextField.setText(plainText);
    }

    private void setupView() {
        Spinner dropdown = findViewById(R.id.supportedAlgorithms);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, algorithmNames);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initCipher(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        keyTextField = findViewById(R.id.keyText);

        plainTextField = findViewById(R.id.inputText);
        plainTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Client otherClient = getOtherClient();
                updateScreen(otherClient);
            }
        });

        encryptedTextField = findViewById(R.id.outputText);
        decryptedTextField = findViewById(R.id.outputText2);

        tabLayout = findViewById(R.id.simpleTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentClient.encryptedText = encryptedTextField.getText().toString();

                keyTextField.setText("");
                plainTextField.setText("");
                encryptedTextField.setText("");
                decryptedTextField.setText("");

                Client otherClient = currentClient;
                currentClient = getOtherClient();
                updateScreen(otherClient);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        selectFolderButton = findViewById(R.id.selectFolderButton);
        selectedFolderPath = findViewById(R.id.selectedFolderPath);

        selectFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEncrypt = true;
                openFolderPicker();
            }
        });

        decryptFilesButton = findViewById(R.id.decryptFilesButton);
        decryptFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEncrypt = false;
                openFolderPicker();

            }
        });
    }

    private void openFolderPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedFolderUri = data.getData();
            selectedFolderPath.setText("Selected Folder Path: " + selectedFolderUri.toString());
            if (isEncrypt) encryptFilesInFolder();
            else decryptFilesInFolder();

        }
    }

    private void encryptFilesInFolder() {
        if (selectedFolderUri == null) return;

        DocumentFile folder = DocumentFile.fromTreeUri(this, selectedFolderUri);
        if (folder != null && folder.isDirectory()) {
            for (DocumentFile file : folder.listFiles()) {
                if (file.isFile()) {
                    encryptFile(file);
                }
            }
        }
    }

    private void encryptFile(DocumentFile file) {
        try (InputStream is = getContentResolver().openInputStream(file.getUri())) {
            if (is == null) {
                throw new IOException("Failed to open input stream for file: " + file.getUri());
            }

            Uri encryptedFileUri = createEncryptedFileUri(file);
            if (encryptedFileUri == null) {
                throw new IOException("Failed to create URI for encrypted file");
            }

            try (OutputStream os = getContentResolver().openOutputStream(encryptedFileUri)) {
                if (os == null) {
                    throw new IOException("Failed to open output stream for file: " + encryptedFileUri);
                }

                SymmetricEncryptionHelper encryptionHelper = SymmetricEncryptionHelper.useDefaultIv(currentClient.sharedSecret);
                encryptionHelper.encryptStream(is, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptFilesInFolder() {
        if (selectedFolderUri == null) return;

        DocumentFile folder = DocumentFile.fromTreeUri(this, selectedFolderUri);
        if (folder != null && folder.isDirectory()) {
            for (DocumentFile file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".enc")) {
                    decryptFile(file);
                }
            }
        }
    }

    private void decryptFile(DocumentFile file) {
        try (InputStream is = getContentResolver().openInputStream(file.getUri())) {
            if (is == null) {
                throw new IOException("Failed to open input stream for file: " + file.getUri());
            }

            Uri decryptedFileUri = createDecryptedFileUri(file);
            if (decryptedFileUri == null) {
                throw new IOException("Failed to create URI for decrypted file");
            }

            try (OutputStream os = getContentResolver().openOutputStream(decryptedFileUri)) {
                if (os == null) {
                    throw new IOException("Failed to open output stream for file: " + decryptedFileUri);
                }

                SymmetricEncryptionHelper encryptionHelper = SymmetricEncryptionHelper.useDefaultIv(currentClient.sharedSecret);
                encryptionHelper.decryptStream(is, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri createEncryptedFileUri(DocumentFile originalFile) {
        DocumentFile folder = DocumentFile.fromTreeUri(this, selectedFolderUri);
        if (folder != null && folder.isDirectory()) {
            return folder.createFile("application/octet-stream", originalFile.getName() + ".enc").getUri();
        }
        return null;
    }

    private Uri createDecryptedFileUri(DocumentFile originalFile) {
        DocumentFile folder = DocumentFile.fromTreeUri(this, selectedFolderUri);
        if (folder != null && folder.isDirectory()) {
            String originalFileName = originalFile.getName();
            if (originalFileName != null && originalFileName.endsWith(".enc")) {
                return folder.createFile("application/octet-stream", originalFileName.substring(0, originalFileName.length() - 4)).getUri();
            }
        }
        return null;
    }

    private Client getOtherClient() {
        return currentClient == client1 ? client2 : client1;
    }
}
