package com.example.sendwarmthattendant.db;

public class Evaluate
{
    private String number;
    private String date;
    private double star;
    private String evaluate;

    public Evaluate(String number, String date, double star, String evaluate)
    {
        this.number = number;
        this.date = date;
        this.star = star;
        this.evaluate = evaluate;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getStar()
    {
        return star;
    }

    public void setStar(double star)
    {
        this.star = star;
    }

    public String getEvaluate()
    {
        return evaluate;
    }

    public void setEvaluate(String evaluate)
    {
        this.evaluate = evaluate;
    }
}
