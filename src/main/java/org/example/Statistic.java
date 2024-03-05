package org.example;

import java.util.List;

public class Statistic {
    public double getAverageScore(List<Double> studentScores) {
        if (studentScores.isEmpty()) return 0;
        double sum = 0.0;
        for (Double score : studentScores) {
            if (score < 1) throw new IllegalArgumentException("Invalid mark (Below zero).");
            if (score > 100) throw new IllegalArgumentException("Invalid mark (Above hundred).");
            sum += score;
        }

        return sum / studentScores.size();
    }


}
