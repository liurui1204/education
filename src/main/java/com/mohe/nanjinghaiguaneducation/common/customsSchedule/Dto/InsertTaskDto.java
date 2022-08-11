package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto;

public class InsertTaskDto {

    private String id;
    private Integer type;
    private String key;
    private String taskTitle;
    private String fromUserGuid;
    private String fromUserName;
    private String toUserGuid;
    private String toUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFromUserGuid() {
        return fromUserGuid;
    }

    public void setFromUserGuid(String fromUserGuid) {
        this.fromUserGuid = fromUserGuid;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserGuid() {
        return toUserGuid;
    }

    public void setToUserGuid(String toUserGuid) {
        this.toUserGuid = toUserGuid;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
