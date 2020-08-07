package com.example.sendwarmthattendant.db;

public class Menu
{
    private String name;
    private int imageId;
    private String menuName;
    private String type;

    public Menu(String name, int imageId, String menuName)
    {
        this.name = name;
        this.imageId = imageId;
        this.menuName = menuName;
        type = "unknown";
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }
}
