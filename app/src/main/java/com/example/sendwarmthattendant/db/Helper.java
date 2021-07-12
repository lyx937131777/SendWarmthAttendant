package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Helper extends LitePalSupport implements Serializable
{
    @SerializedName("id")
    private String internetId;
    private String accountId;

    private String userId;
    private String credential;

    private String helperName;
    private String helperIdCard;
    private String helperTel;
    private String idCardFront;
    private String idCardBack;
    private int workClass1;
    private int workClass2;

    private ServiceSubject workerClass1;
    private ServiceSubject workerClass2;

    private int level;
    private String auditStatus;
    private double score;
    private double scoreSum;
    private int scoreNum;
    private String roleType;
    private int points;
    private int pointsSum;
    private int orderSum;
    private int goodScoreSum;
    private int badScoreSum;
    private String workingStatus;
    private String remark;
    private String workerSkill;

    private double longitude;
    private double latitude;


    public String getWorkerSkill() {
        return workerSkill;
    }

    public void setWorkerSkill(String workerSkill) {
        this.workerSkill = workerSkill;
    }

    public double getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public int getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(int scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getInternetId()
    {
        return internetId;
    }

    public void setInternetId(String internetId)
    {
        this.internetId = internetId;
    }

    public String getHelperName()
    {
        return helperName;
    }

    public void setHelperName(String helperName)
    {
        this.helperName = helperName;
    }

    public String getHelperIdCard()
    {
        return helperIdCard;
    }

    public void setHelperIdCard(String helperIdCard)
    {
        this.helperIdCard = helperIdCard;
    }

    public String getHelperTel()
    {
        return helperTel;
    }

    public void setHelperTel(String helperTel)
    {
        this.helperTel = helperTel;
    }

    public String getIdCardFront()
    {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront)
    {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack()
    {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack)
    {
        this.idCardBack = idCardBack;
    }

    public int getWorkClass1()
    {
        return workClass1;
    }

    public void setWorkClass1(int workClass1)
    {
        this.workClass1 = workClass1;
    }

    public int getWorkClass2()
    {
        return workClass2;
    }

    public void setWorkClass2(int workClass2)
    {
        this.workClass2 = workClass2;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ServiceSubject getWorkerClass1() {
        return workerClass1;
    }

    public void setWorkerClass1(ServiceSubject workerClass1) {
        this.workerClass1 = workerClass1;
    }

    public ServiceSubject getWorkerClass2() {
        return workerClass2;
    }

    public void setWorkerClass2(ServiceSubject workerClass2) {
        this.workerClass2 = workerClass2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPointsSum() {
        return pointsSum;
    }

    public void setPointsSum(int pointsSum) {
        this.pointsSum = pointsSum;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
    }

    public int getGoodScoreSum() {
        return goodScoreSum;
    }

    public void setGoodScoreSum(int goodScoreSum) {
        this.goodScoreSum = goodScoreSum;
    }

    public int getBadScoreSum() {
        return badScoreSum;
    }

    public void setBadScoreSum(int badScoreSum) {
        this.badScoreSum = badScoreSum;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
