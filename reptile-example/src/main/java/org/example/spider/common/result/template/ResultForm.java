package org.example.spider.common.result.template;

/**
 * @ClassName ResultForm
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 17:09
 * @Version 1.0
 **/
public class ResultForm {
    protected boolean success;
    protected String message;

    public ResultForm(boolean success) {
        this.success = success;
        this.message = "";
    }

    public ResultForm(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
