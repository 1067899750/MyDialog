package com.example.mydialog.spiner.model;

/**
 * @Describe
 * @Author puyantao
 * @create 2019/5/16 11:29
 */
public class DropBoxBean {

    /**
     * detail : 生产工具支出
     * id : 6690792004686051592
     * value : 2
     */
    private String id;
    private String detail;
    private String value;

    public DropBoxBean() {
    }

    public DropBoxBean(String detail) {
        this.detail = detail;
    }

    public DropBoxBean(String id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    public DropBoxBean(String id, String detail, String value) {
        this.id = id;
        this.detail = detail;
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
