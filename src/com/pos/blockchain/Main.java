package com.pos.blockchain;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();

        // Create validators with stake
        List<Validator> validators = Arrays.asList(
                new Validator(1, 100),
                new Validator(2, 500),
                new Validator(3, 300)
        );

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {

            Validator selected = POSSelector.selectValidator(validators);

            Block newBlock = new Block(
                    blockchain.getLastHash(),
                    Arrays.asList(new Transaction("Alice", "Bob", 10)),
                    selected
            );

            blockchain.addBlock(newBlock);
        }

        executor.shutdown();

        // Wait for all threads to finish
        while (!executor.isTerminated()) {
        }

        System.out.println("\n=========== BLOCKCHAIN ===========\n");
        blockchain.printBlockchain();

        System.out.println("\nBlockchain valid? " + blockchain.isChainValid());
    }
}