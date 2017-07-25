package com.visionin.shop.Beans;

import java.util.List;

/**
 * Created by wangzhiyuan on 2017/7/25.
 */

public class GoodsBean {

    /**
     * code : 200
     * model : [{"create_time":1500281495,"goods_a_price":32100,"goods_b_price":31200,"goods_c_price":31200,"goods_manufacturer":"ewrqwe","goods_name":"hahah","goods_qr_code":"i68tpooileerslt18up1hddpo7zy9zy5.png","goods_remark":"rewqrewq","id":65,"modify_time":1500281495},{"create_time":1500348346,"goods_a_price":100000,"goods_b_price":120000,"goods_c_price":150000,"goods_manufacturer":"LV鍘�","goods_name":"LV鍖�","goods_qr_code":"f5pepj1zkkcfaddnaruxsbz3d1cjhvb9.png","goods_remark":"LV鍘�","id":66,"modify_time":1500348346},{"create_time":1500365050,"goods_a_price":10000,"goods_b_price":12000,"goods_c_price":15000,"goods_manufacturer":"鍝堝搱鍝�","goods_name":"闉嬪瓙","goods_qr_code":"2utxp6xm068fqzv3mxfmfq5yrgbrmxru.png","goods_remark":"鍝堝搱鍝�","id":67,"modify_time":1500365050},{"create_time":1500465049,"goods_a_price":100000,"goods_b_price":100000,"goods_c_price":20000,"goods_manufacturer":"鍠靛憸","goods_name":"闉嬪瓙","goods_qr_code":"psjwwjduoy1zcef3spdi3uzg20yshizg.png","goods_remark":"鍠靛憸鍠靛憸","id":68,"modify_time":1500465049}]
     * offset : 0
     * total : 4
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
         * create_time : 1500281495
         * goods_a_price : 32100
         * goods_b_price : 31200
         * goods_c_price : 31200
         * goods_manufacturer : ewrqwe
         * goods_name : hahah
         * goods_qr_code : i68tpooileerslt18up1hddpo7zy9zy5.png
         * goods_remark : rewqrewq
         * id : 65
         * modify_time : 1500281495
         */

        private int create_time;
        private int goods_a_price;
        private int goods_b_price;
        private int goods_c_price;
        private String goods_manufacturer;
        private String goods_name;
        private String goods_qr_code;
        private String goods_remark;
        private int id;
        private int modify_time;

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getGoods_a_price() {
            return goods_a_price;
        }

        public void setGoods_a_price(int goods_a_price) {
            this.goods_a_price = goods_a_price;
        }

        public int getGoods_b_price() {
            return goods_b_price;
        }

        public void setGoods_b_price(int goods_b_price) {
            this.goods_b_price = goods_b_price;
        }

        public int getGoods_c_price() {
            return goods_c_price;
        }

        public void setGoods_c_price(int goods_c_price) {
            this.goods_c_price = goods_c_price;
        }

        public String getGoods_manufacturer() {
            return goods_manufacturer;
        }

        public void setGoods_manufacturer(String goods_manufacturer) {
            this.goods_manufacturer = goods_manufacturer;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_qr_code() {
            return goods_qr_code;
        }

        public void setGoods_qr_code(String goods_qr_code) {
            this.goods_qr_code = goods_qr_code;
        }

        public String getGoods_remark() {
            return goods_remark;
        }

        public void setGoods_remark(String goods_remark) {
            this.goods_remark = goods_remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getModify_time() {
            return modify_time;
        }

        public void setModify_time(int modify_time) {
            this.modify_time = modify_time;
        }
    }
}
