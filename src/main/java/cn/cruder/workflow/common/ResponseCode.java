package cn.cruder.workflow.common;

/**
 * @Author: Dsx
 * @Date: 2020-10-18 22:10
 * @Description: description
 */
public enum ResponseCode {
    SUCCESS(0, "成功"),
    ERROR(1, "错误");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
