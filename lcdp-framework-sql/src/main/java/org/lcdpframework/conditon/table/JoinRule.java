package org.lcdpframework.conditon.table;

import lombok.Data;

import java.util.LinkedList;

@Data
public class JoinRule {

    private LinkedList<TableRule> tables = new LinkedList<>(); // main table added at first
}