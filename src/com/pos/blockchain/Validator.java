package com.pos.blockchain;

import java.security.*;
import java.util.Base64;

public class Validator {

    private final int id;
    private final int stake;
    private final KeyPair keyPair;

    public Validator(int id, int stake) {
        this.id = id;
        this.stake = stake;
        this.keyPair = generateKeyPair();
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Key generation failed");
        }
    }

    public byte[] signData(String data) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(keyPair.getPrivate());
            signature.update(data.getBytes());
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Signing failed");
        }
    }

    public boolean verifySignature(String data, byte[] signatureBytes) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(keyPair.getPublic());
            signature.update(data.getBytes());
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Verification failed");
        }
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public int getId() {
        return id;
    }

    public int getStake() {
        return stake;
    }

    public String getPublicKeyAsString() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }
}