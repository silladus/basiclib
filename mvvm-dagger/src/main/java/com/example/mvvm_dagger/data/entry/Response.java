package com.example.mvvm_dagger.data.entry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author silladus
 * Created on 2018/7/21/0021.
 * GitHub: https://github.com/silladus
 * Description:结果包装类
 */
public class Response<T> {

    public int code;
    public String msg;
    public long time;

    //    @Expose
//    @SerializedName(value = "result", alternate = {"obj"})
    public T result;

    @NonNull
    @Override
    public String toString() {
        return "code:" + code
                + "\n"
                + "msg:" + msg
                + "\n"
                + "time:" + time
                + "\n"
                + "result:" + result;
    }

    public T body() throws Exception {
        return isOk().result;
    }

    public Response<T> isOk() throws Exception {
        if (code == 0) {
            return this;
        } else {
            throw new Exception("code:" + code
                    + ", "
                    + "msg:" + msg
                    + ", "
                    + "time:" + time);
        }
    }
}

