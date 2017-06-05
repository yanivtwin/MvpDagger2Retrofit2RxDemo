package com.yantwin.ulike.model;

import com.google.gson.annotations.Expose;

import java.util.List;


public class ButtonResponse {

    @Expose
    private String type;
    @Expose
    private Boolean enabled;
    @Expose
    private int priority;
    @Expose
    private List<Integer> valid_days;
    @Expose
    private long cool_down;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Integer> getValid_days() {
        return valid_days;
    }

    public void setValid_days(List<Integer> valid_days) {
        this.valid_days = valid_days;
    }

    public long getCool_down() {
        return cool_down;
    }

    public void setCool_down(long cool_down) {
        this.cool_down = cool_down;
    }
}
