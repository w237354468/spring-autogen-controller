package org.lcdpframework;

import java.util.List;

public interface ICustomAction {

  /**
   * 执行的执行语句.
   *
   * @param join 拼接SQL字符串的StringBuilder对象
   * @param params 有序参数
   */
  void execute(final StringBuilder join, final List<Object> params);
}
