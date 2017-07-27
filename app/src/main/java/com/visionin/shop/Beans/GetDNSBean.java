package com.visionin.shop.Beans;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/7/27.
 */

public class GetDNSBean {

    /**
     * code : 200
     * model : [{"ip":"192.168.1.101","name":"大屏1"},{"ip":"192.168.1.102","name":"大屏2"}]
     * offset : 0
     * total : 0
     */

    private int code;
    private int offset;
    private int total;
    private List<ModelBean> model;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ModelBean> getModel() {
        return model;
    }

    public void setModel(List<ModelBean> model) {
        this.model = model;
    }

    public static class ModelBean {
        /**
         * ip : 192.168.1.101
         * name : 大屏1
         */

        private String ip;
        private String name;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
