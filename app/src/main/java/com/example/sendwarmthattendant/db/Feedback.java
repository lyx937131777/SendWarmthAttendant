package com.example.sendwarmthattendant.db;

public class Feedback
{
    private String customer;
    private String date;
    private double star;
    private String content;

    public Feedback(String customer, String date, double star, String content)
    {
        this.customer = customer;
        this.date = date;
        this.star = star;
        this.content = content;
    }

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer(String customer)
    {
        this.customer = customer;
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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
