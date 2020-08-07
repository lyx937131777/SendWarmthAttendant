package com.example.sendwarmthattendant.db;

import java.io.Serializable;

public class FriendsCircle implements Serializable
{
    private String title;
    private int picture;
    private String author;
    private String description;
    private String time;

    public FriendsCircle(String title, int picture, String author, String description, String time)
    {
        this.title = title;
        this.picture = picture;
        this.author = author;
        this.description = description;
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getPicture()
    {
        return picture;
    }

    public void setPicture(int picture)
    {
        this.picture = picture;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
