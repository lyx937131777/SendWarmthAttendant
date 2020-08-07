package com.example.sendwarmthattendant.db;

import java.io.Serializable;

public class PensionInstitution implements Serializable
{
    private String name;
    private String address;
    private String tel;
    private String contact;
    private String description;
    private int picture;

    public PensionInstitution(String name, String address, String tel, String contact,
                              String description, int picture)
    {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.contact = contact;
        this.description = description;
        this.picture = picture;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getPicture()
    {
        return picture;
    }

    public void setPicture(int picture)
    {
        this.picture = picture;
    }
}
