package org.example.spider.common.result.template;

/**
 * @ClassName ContentResultForm
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/27 17:10
 * @Version 1.0
 **/
public class ContentResultForm<T> extends ResultForm {
    private T content;

    public ContentResultForm(boolean success) {
        super(success);
    }

    public ContentResultForm(boolean success, T content) {
        super(success);
        this.content = content;
    }

    public ContentResultForm(boolean success, T content, String message) {
        super(success, message);
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
