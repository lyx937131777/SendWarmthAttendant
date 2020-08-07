package com.example.sendwarmthattendant.db;

public class User
{
    private String userId;
    private String password;
    private String nickname;
    private String realName;
    private String profileUri;
    private String profilePath;
    private String address;
    private String personalSignature;

    private String userType;
    private String title;
    private String star;


    public String getPersonalSignature()
    {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature)
    {
        this.personalSignature = personalSignature;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getProfileUri()
    {
        return profileUri;
    }

    public void setProfileUri(String profileUri)
    {
        this.profileUri = profileUri;
    }

    public String getProfilePath()
    {
        return profilePath;
    }

    public void setProfilePath(String profilePath)
    {
        this.profilePath = profilePath;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStar()
    {
        return star;
    }

    public void setStar(String star)
    {
        this.star = star;
    }
}
