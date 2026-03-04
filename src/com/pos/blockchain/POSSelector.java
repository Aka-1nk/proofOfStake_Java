package com.pos.blockchain;

import java.util.List;
import java.util.Random;

public class POSSelector {

    private static final Random random = new Random();

    public static Validator selectValidator(List<Validator> validators) {

        double totalStake = validators.stream()
                .mapToDouble(Validator::getStake)
                .sum();

        double randomValue = random.nextDouble() * totalStake;

        double cumulative = 0.0;

        for (Validator v : validators) {
            cumulative += v.getStake();
            if (randomValue <= cumulative) {
                return v;
            }
        }

        return validators.get(0);
    }
}