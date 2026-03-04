package com.pos.blockchain;

import java.security.MessageDigest;
import java.util.List;

public class Block {

    private final String previousHash;
    private final List<Transaction> transactions;
    private final long timestamp;

    private String hash;
    private final Validator validator;
    private byte[] signature;

    public Block(String previousHash,
                 List<Transaction> transactions,
                 Validator validator) {

        this.previousHash = previousHash;
        this.transactions = transactions;
        this.validator = validator;
        this.timestamp = System.currentTimeMillis();

        this.hash = calculateHash();

        // Genesis block case (validator = null)
        if (validator != null) {
            this.signature = validator.signData(this.hash);
        }
    }

    public String calculateHash() {
        try {
            String data = previousHash +
                    transactions.toString() +
                    timestamp +
                    (validator != null ? validator.getId() : 0);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());

            StringBuilder builder = new StringBuilder();
            for (byte b : hashBytes) {
                builder.append(String.format("%02x", b));
            }

            return builder.toString();

        } catch (Exception e) {
            throw new RuntimeException("Hash calculation failed");
        }
    }

    public boolean verifySignature() {
        if (validator == null) return true; // Genesis block

        return validator.verifySignature(hash, signature);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public Validator getValidator() {
        return validator;
    }

    public byte[] getSignature() {
        return signature;
    }
}