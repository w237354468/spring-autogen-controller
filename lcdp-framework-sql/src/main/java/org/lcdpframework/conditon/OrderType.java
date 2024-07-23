package org.lcdpframework.conditon;


public enum OrderType {

    ASC("ASC"),
    DESC("DESC"),
    ASC_NULL_LAST("ASC NULLS LAST"),
    DESC_NULL_LAS("DESC NULLS LAST");

    public final String content;

    OrderType(String content){
        this.content = content;
    }

    public static OrderType getOrderTypeBy(String order){
        for (OrderType value : values()) {
            if(value.content.equalsIgnoreCase(order)){
                return value;
            }
        }
        throw new RuntimeException("order type error");
    }
}
