package org.nuaa.tomax.easyblog.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 17:36
 */
@Data
public class Response<T> {
    /**
     * no error ret code
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * normal error ret code
     */
    public static final int NORMAL_EROOR_CODE = 400;

    /**
     * input error ret code
     */
    public static final int INPUT_ERROR_CODE = 403;

    /**
     * server error ret code
     */
    public static final int SERVER_ERROR_CODE = 500;

    private int code;
    private String desc;

    private T data;
    private List<T> array;
    private int count;

    public Response() {}

    public Response(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Response(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.count = data != null ? 1 : 0;
    }

    public Response(int code, String desc, List<T> array) {
        this.code = code;
        this.desc = desc;
        this.array = array;
        this.count = array != null ? array.size() : 0;
    }
}
