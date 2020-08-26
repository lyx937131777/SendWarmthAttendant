package com.example.sendwarmthattendant.db;

import java.io.Serializable;

public class Order implements Serializable
{
    private String number;
    private String customer;
    private String attendant;
    private String time;
    private String state;
    private String serviceType;
    private String serviceContent;
    private double price;

    public Order(String number, String customer, String attendant, String time, String state,
                 String serviceType, String serviceContent, double price)
    {
        this.number = number;
        this.customer = customer;
        this.attendant = attendant;
        this.time = time;
        this.state = state;
        this.serviceType = serviceType;
        this.serviceContent = serviceContent;
        this.price = price;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    public String getAttendant()
    {
        return attendant;
    }

    public void setAttendant(String attendant)
    {
        this.attendant = attendant;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(String serviceType)
    {
        this.serviceType = serviceType;
    }

    public String getServiceContent()
    {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
