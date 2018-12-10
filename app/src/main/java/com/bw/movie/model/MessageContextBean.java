package com.bw.movie.model;

/**
 * 作者：owner on 2018/12/4 19:01
 */
public class MessageContextBean {

    /**
     * count : 1
     * message : 查询成功
     * status : 0000
     */

    private int count;
    private String message;
    private String status;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
