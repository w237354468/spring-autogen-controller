package org.lcdpframework.conditon;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class OrderCondition {

    private LinkedHashMap<String, OrderType> order;
}
