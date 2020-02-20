package com.pnit.smartbag.data.calories;

public class CalorieCalculator {

    private int heightInCm;
    private int weight;

    public CalorieCalculator(int heightInCm, int weight){
        this.heightInCm = heightInCm;
        this.weight = weight;
    }

    public int calculateCalories(int stepsToCalculate){
        int stepsPerKm = getStepsPerKm(heightInCm);
        int kcalPerKm = getKcalPerKm(weight, 5);
        return stepsToCalculate * kcalPerKm / stepsPerKm;
    }

    private int getStepsPerKm(int heigth){
        if (heigth < 150)
            return 10000/5;
        else if (heigth < 160)
            return 10000/6;
        else if (heigth < 170)
            return 10000/7;
        else if (heigth < 180)
            return (int)(10000/7.5);
        else
            return 10000/8;
    }

    private int getKcalPerKm(int weight, int kmh){
        if (weight < 60)
            return 200/kmh;
        else if (weight < 80)
            return 240/kmh;
        else if (weight < 100)
            return 300/kmh;
        else
            return 400/kmh;
    }
}
