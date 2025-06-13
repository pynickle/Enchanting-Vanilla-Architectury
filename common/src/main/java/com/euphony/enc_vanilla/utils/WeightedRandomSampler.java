package com.euphony.enc_vanilla.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class WeightedRandomSampler<K> {
    private final Random random = new Random();
    HashMap<K, Double> probabilityMap;

    public WeightedRandomSampler() {
    }

    public void init(List<K> probabilityList) {
        this.probabilityMap = probabilityList.stream()
                .collect(Collectors.toMap(
                        key -> key,
                        value -> 100.0d,
                        (oldVal, newVal) -> newVal,
                        HashMap::new
                ));
    }

    public K sample() {
        if(probabilityMap.isEmpty()) {
            return null;
        }
        double total = this.probabilityMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        double r = random.nextDouble() * total;

        double cumulative = 0;
        for (Map.Entry<K, Double> entry : this.probabilityMap.entrySet()) {
            cumulative += entry.getValue();
            if (r < cumulative) {
                this.probabilityMap.replace(entry.getKey(), entry.getValue() * 0.2);
                return entry.getKey();
            }
        }

        K result = this.probabilityMap.keySet().iterator().next();
        this.probabilityMap.replace(result, probabilityMap.get(result) * 0.2);

        return result;
    }
}

