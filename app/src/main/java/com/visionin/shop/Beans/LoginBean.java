package com.visionin.shop.Beans;

/**
 * Created by wangzhiyuan on 2017/7/17.
 */

public class LoginBean {

    /**
     * code : 200
     * model : {"token":"5lc2211t82j2nfoxu8z01bwffdngsufb"}
     * offset : 0
     */

    private int code;
    private ModelBean model;
    private int offset;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static class ModelBean {
        /**
         * token : 5lc2211t82j2nfoxu8z01bwffdngsufb
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
