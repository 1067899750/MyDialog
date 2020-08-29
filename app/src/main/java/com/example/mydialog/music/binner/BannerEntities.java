package com.example.mydialog.music.binner;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author puyantao
 * @description
 * @date 2020/8/29 10:51
 */
public class BannerEntities implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id = "";
    private String url = "";
    private String content = "";
    private String type = "";
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
