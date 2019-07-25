package com.example.mydialog.spiner.model;

import java.util.List;

/**
 * @Describe
 * @Author puyantao
 * @Email 1067899750@qq.com
 * @create 2019/5/16 11:29
 */
public class BookEarnInputBean {


    /**
     * success : true
     * constantList : [{"code":"INCOME_TYPE","desc":"工资收入","id":"6690792395528075530","order":"","parentCode":"INCOME_TYPE","value":"1"},{"code":"INCOME_TYPE","desc":"养殖收入","id":"6690792580211669259","order":"","parentCode":"INCOME_TYPE","value":"2"},{"code":"INCOME_TYPE","desc":"种植收入","id":"6690792709060688140","order":"","parentCode":"INCOME_TYPE","value":"3"},{"code":"INCOME_TYPE","desc":"销售收入","id":"6690792807844935949","order":"","parentCode":"INCOME_TYPE","value":"4"},{"code":"INCOME_TYPE","desc":"补贴收入","id":"6690792919514085646","order":"","parentCode":"INCOME_TYPE","value":"5"},{"code":"INCOME_TYPE","desc":"其他","id":"6690793125672515855","order":"","parentCode":"INCOME_TYPE","value":"6"}]
     */

    private String success;
    private List<ConstantListBean> constantList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ConstantListBean> getConstantList() {
        return constantList;
    }

    public void setConstantList(List<ConstantListBean> constantList) {
        this.constantList = constantList;
    }

    public static class ConstantListBean {
        /**
         * code : INCOME_TYPE
         * desc : 工资收入
         * id : 6690792395528075530
         * order :
         * parentCode : INCOME_TYPE
         * value : 1
         */

        private String code;
        private String desc;
        private String id;
        private String order;
        private String parentCode;
        private String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
