package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable
{
    @SerializedName("id")
    private String internetId;
    private String orderNo;
    private String customerId;
    private Customer customerInfo;
    private String deliveryDetail;
    private String deliveryPhone;
    private String houseNum;
    private double longitude;
    private double latitude;
    private String appointedPerson;
    private String message;
    private double tip;
    @SerializedName("orderStatusWorker")
    private String state;//not_accepted, not_start, on_going, completed, canceled, un_evaluated

    private String serviceClassId;
    private ServiceClass serviceClassInfo;
    private String serviceSubjectId;
    private ServiceSubject serviceSubjectInfo;
    @SerializedName("services")
    private String serviceContent;

    private String orderType;//expedited_order, book_order
    private String oderWorkType;//help worker all

    private String orderCreateTime;
    private String expectStartTime;
    private String expectEndTime;
    private String startTime;
    private String endTime;
    private double duration;
    private double salaryHourly;
    private double salarySum;

    private int score;//0-5
    private String customerDes;
    private String workerDes;

    private String workerId;
    private Worker workerInfo;
    private String helperId;
    private Helper helperInfo;

    private String regionId;
    private String storeId;


//    public Order(String orderNo, String customer, String attendant, String time, String state,
//                 String serviceType, String serviceContent, double salarySum)
//    {
//        this.orderNo = orderNo;
//        this.customer = customer;
//        this.attendant = attendant;
//        this.time = time;
//        this.state = state;
//        this.serviceType = serviceType;
//        this.serviceContent = serviceContent;
//        this.salarySum = salarySum;
//    }

    public String getRole(){
        if(helperId != null){
            return "helper";
        }
        if(workerId != null){
            return "worker";
        }
        return "unknown";
    }

    public String getAttendantId(){
        if(helperId != null){
            return helperId;
        }
        if(workerId != null){
            return workerId;
        }
        return "unknown";
    }

    public String getAttendantName(){
        if(workerInfo != null){
            return workerInfo.getWorkerName();
        }
        if(helperInfo != null){
            return helperInfo.getHelperName();
        }
        return "无";
    }

    public String getAttendantTel(){
        if(workerInfo != null){
            return workerInfo.getWorkerTel();
        }
        if(helperInfo != null){
            return helperInfo.getHelperTel();
        }
        return "无";
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }


    public String getServiceContent()
    {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }

    public double getSalarySum()
    {
        return salarySum;
    }

    public void setSalarySum(double salarySum)
    {
        this.salarySum = salarySum;
    }

    public String getInternetId()
    {
        return internetId;
    }

    public void setInternetId(String internetId)
    {
        this.internetId = internetId;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public Customer getCustomerInfo()
    {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo)
    {
        this.customerInfo = customerInfo;
    }

    public String getDeliveryDetail()
    {
        return deliveryDetail;
    }

    public void setDeliveryDetail(String deliveryDetail)
    {
        this.deliveryDetail = deliveryDetail;
    }

    public String getDeliveryPhone()
    {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone)
    {
        this.deliveryPhone = deliveryPhone;
    }

    public String getHouseNum()
    {
        return houseNum;
    }

    public void setHouseNum(String houseNum)
    {
        this.houseNum = houseNum;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getAppointedPerson()
    {
        return appointedPerson;
    }

    public void setAppointedPerson(String appointedPerson)
    {
        this.appointedPerson = appointedPerson;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public double getTip()
    {
        return tip;
    }

    public void setTip(double tip)
    {
        this.tip = tip;
    }

    public String getServiceClassId()
    {
        return serviceClassId;
    }

    public void setServiceClassId(String serviceClassId)
    {
        this.serviceClassId = serviceClassId;
    }

    public ServiceClass getServiceClassInfo()
    {
        return serviceClassInfo;
    }

    public void setServiceClassInfo(ServiceClass serviceClassInfo)
    {
        this.serviceClassInfo = serviceClassInfo;
    }

    public String getServiceSubjectId()
    {
        return serviceSubjectId;
    }

    public void setServiceSubjectId(String serviceSubjectId)
    {
        this.serviceSubjectId = serviceSubjectId;
    }

    public ServiceSubject getServiceSubjectInfo()
    {
        return serviceSubjectInfo;
    }

    public void setServiceSubjectInfo(ServiceSubject serviceSubjectInfo)
    {
        this.serviceSubjectInfo = serviceSubjectInfo;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOderWorkType()
    {
        return oderWorkType;
    }

    public void setOderWorkType(String oderWorkType)
    {
        this.oderWorkType = oderWorkType;
    }

    public String getOrderCreateTime()
    {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime)
    {
        this.orderCreateTime = orderCreateTime;
    }

    public String getExpectStartTime()
    {
        return expectStartTime;
    }

    public void setExpectStartTime(String expectStartTime)
    {
        this.expectStartTime = expectStartTime;
    }

    public String getExpectEndTime()
    {
        return expectEndTime;
    }

    public void setExpectEndTime(String expectEndTime)
    {
        this.expectEndTime = expectEndTime;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    public double getSalaryHourly()
    {
        return salaryHourly;
    }

    public void setSalaryHourly(double salaryHourly)
    {
        this.salaryHourly = salaryHourly;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getCustomerDes()
    {
        return customerDes;
    }

    public void setCustomerDes(String customerDes)
    {
        this.customerDes = customerDes;
    }

    public String getWorkerDes()
    {
        return workerDes;
    }

    public void setWorkerDes(String workerDes)
    {
        this.workerDes = workerDes;
    }

    public String getWorkerId()
    {
        return workerId;
    }

    public void setWorkerId(String workerId)
    {
        this.workerId = workerId;
    }

    public Worker getWorkerInfo()
    {
        return workerInfo;
    }

    public void setWorkerInfo(Worker workerInfo)
    {
        this.workerInfo = workerInfo;
    }

    public String getHelperId()
    {
        return helperId;
    }

    public void setHelperId(String helperId)
    {
        this.helperId = helperId;
    }

    public Helper getHelperInfo()
    {
        return helperInfo;
    }

    public void setHelperInfo(Helper helperInfo)
    {
        this.helperInfo = helperInfo;
    }

    public String getRegionId()
    {
        return regionId;
    }

    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }

    public String getStoreId()
    {
        return storeId;
    }

    public void setStoreId(String storeId)
    {
        this.storeId = storeId;
    }
}
