package com.visionin.shop.http;

import java.util.Map;

public abstract class CallbackForRequest<T> {

    Class<T> mClass;

    public CallbackForRequest(Class<T> _class) {
        mClass = _class;
    }

    public CallbackForRequest() {
    }

    public abstract void doSuccess(T bean);// throws JSONException;

    /**
     * 不重写时,返回不做任何处理
     * @param object
     */
    public abstract void doError(Object object);

    /**
     * 请求的基本参数,不重写时使用基本参数
     *
     * @return
     */
    public abstract Map<String, String> getParams();/* {
        return null;
    }*/

    /**
     * 请求的接口
     *
     * @return
     */
    public abstract API_ENUM getApiEnum();

    public <T> Class<T> _getClass() {
        return mClass == null ? getApiEnum()._getClass() : (Class<T>) mClass;
    }
}
