package com.example.sendwarmthattendant.db;

import java.io.Serializable;

public class InterestingActivity implements Serializable
{
    private String title;
    private String time;
    private int picture;
    private String description;
    private String author;

    public InterestingActivity(String title, String time, int picture, String description,
                               String author)
    {
        this.title = title;
        this.time = time;
        this.picture = picture;
        this.description = description;
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public int getPicture()
    {
        return picture;
    }

    public void setPicture(int picture)
    {
        this.picture = picture;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }
}
