package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Worker extends LitePalSupport implements Serializable
{
    @SerializedName("id")
    private String internetId;

    private String userId;
    private String credential;

    private String workerName;
    private String workerTel;
    private String employeeId;
    private int level;
    private double score;
    private int scoreSum;
    private double scoreNum;

    private String roleType;
    private int points;
    private int pointsSum;
    private int orderSum;
    private int goodScoreSum;
    private int badScoreSum;
    private String workingStatus;
    private String remark;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getCredential()
    {
        return credential;
    }

    public void setCredential(String credential)
    {
        this.credential = credential;
    }

    public String getInternetId()
    {
        return internetId;
    }

    public void setInternetId(String internetId)
    {
        this.internetId = internetId;
    }

    public String getWorkerName()
    {
        return workerName;
    }

    public void setWorkerName(String workerName)
    {
        this.workerName = workerName;
    }

    public String getWorkerTel()
    {
        return workerTel;
    }

    public void setWorkerTel(String workerTel)
    {
        this.workerTel = workerTel;
    }

    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public int getScoreSum()
    {
        return scoreSum;
    }

    public void setScoreSum(int scoreSum)
    {
        this.scoreSum = scoreSum;
    }

    public double getScoreNum()
    {
        return scoreNum;
    }

    public void setScoreNum(double scoreNum)
    {
        this.scoreNum = scoreNum;
    }

    public String getRoleType()
    {
        return roleType;
    }

    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getPointsSum()
    {
        return pointsSum;
    }

    public void setPointsSum(int pointsSum)
    {
        this.pointsSum = pointsSum;
    }

    public int getOrderSum()
    {
        return orderSum;
    }

    public void setOrderSum(int orderSum)
    {
        this.orderSum = orderSum;
    }

    public int getGoodScoreSum()
    {
        return goodScoreSum;
    }

    public void setGoodScoreSum(int goodScoreSum)
    {
        this.goodScoreSum = goodScoreSum;
    }

    public int getBadScoreSum()
    {
        return badScoreSum;
    }

    public void setBadScoreSum(int badScoreSum)
    {
        this.badScoreSum = badScoreSum;
    }

    public String getWorkingStatus()
    {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus)
    {
        this.workingStatus = workingStatus;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
