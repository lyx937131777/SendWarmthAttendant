package com.example.sendwarmthattendant.db;

public class SystemMessage
{
    private String time;
    private String date;
    private String content;

    public SystemMessage(String time, String date, String content)
    {
        this.time = time;
        this.date = date;
        this.content = content;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
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
