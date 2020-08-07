package com.example.sendwarmthattendant.db;

public class Task
{
    private String number;
    private String customer;
    private String time;
    private String type;
    private String serviceContent;

    public Task(String number, String customer, String time, String type, String serviceContent)
    {
        this.number = number;
        this.customer = customer;
        this.time = time;
        this.type = type;
        this.serviceContent = serviceContent;
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

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getServiceContent()
    {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }
}
