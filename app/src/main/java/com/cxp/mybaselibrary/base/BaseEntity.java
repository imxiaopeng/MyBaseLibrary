package com.cxp.mybaselibrary.base;

public class BaseEntity {
    /**
     * "Code": "0",
     * "Data": 269716,
     * "Msg": "发送成功",
     * "Time": "2018-08-29 11:03:50"
     */
    public String Code;
    public String Msg = "";
    public String Time = "";

    public int getCode() {
        return Integer.parseInt(Code);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "Code='" + Code + '\'' +
                ", Msg='" + Msg + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }

    public boolean isSuccess() {
        if (Integer.parseInt(Code) == 0) {
            return true;
        }
        return false;//1error 0 success
    }
}
