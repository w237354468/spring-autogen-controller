package org.lcdpframework.conditon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageCondition {

    private Integer pageSize;

    private Integer pageNum;

}
