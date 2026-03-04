package com.pos.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block("0", new ArrayList<>(), null);
    }

    public synchronized void addBlock(Block newBlock) {

        Block lastBlock = getLastBlock();

        if (!newBlock.getPreviousHash().equals(lastBlock.getHash())) {
            System.out.println("Invalid previous hash!");
            return;
        }

        if (!newBlock.getHash().equals(newBlock.calculateHash())) {
            System.out.println("Invalid block hash!");
            return;
        }

        chain.add(newBlock);

        if (newBlock.getValidator() != null) {
            System.out.println("Block added by Validator " +
                    newBlock.getValidator().getId());
        }

        System.out.println("Hash: " + newBlock.getHash());
        System.out.println("----------------------------------");
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public String getLastHash() {
        return getLastBlock().getHash();
    }

    public boolean isChainValid() {

        for (int i = 1; i < chain.size(); i++) {

            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }

            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }

        return true;
    }

    public void printBlockchain() {

        for (int i = 0; i < chain.size(); i++) {

            Block block = chain.get(i);

            System.out.println("Block #" + i);

            if (block.getValidator() != null) {
                System.out.println("Validator: " +
                        block.getValidator().getId());
            } else {
                System.out.println("Validator: Genesis");
            }

            System.out.println("Hash: " + block.getHash());
            System.out.println("Previous: " + block.getPreviousHash());
            System.out.println("=================================");
        }
    }
}