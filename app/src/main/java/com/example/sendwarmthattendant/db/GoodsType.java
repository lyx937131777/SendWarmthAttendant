package com.example.sendwarmthattendant.db;

public class GoodsType
{
    private String name;
    private String typeName;

    public GoodsType(String name, String typeName)
    {
        this.name = name;
        this.typeName = typeName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
}
