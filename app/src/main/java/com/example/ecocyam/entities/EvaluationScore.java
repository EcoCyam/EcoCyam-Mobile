package com.example.ecocyam.entities;

public final class EvaluationScore {
    private double ecoScore;
    private double userScore;
    private double durablityScore;

    public EvaluationScore(double ecoScore, double userScore, double durablityScore) {
        this.ecoScore = ecoScore;
        this.userScore = userScore;
        this.durablityScore = durablityScore;
    }

    public double getEcoScore() {
        return ecoScore;
    }

    public void setEcoScore(double ecoScore) {
        this.ecoScore = ecoScore;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public double getDurablityScore() {
        return durablityScore;
    }

    public void setDurablityScore(double durablityScore) {
        this.durablityScore = durablityScore;
    }
}
