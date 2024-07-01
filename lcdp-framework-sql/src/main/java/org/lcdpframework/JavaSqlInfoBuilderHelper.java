package org.lcdpframework;

import java.util.Collection;

/**
 * 构建使用Java拼接sql片段的工具类.
 *
 * @author blinkfox on 2017-04-01.
 */
public class JavaSqlInfoBuilderHelper extends SqlInfoBuilderHelper {

  /** 私有构造方法. */
  private JavaSqlInfoBuilderHelper() {
    super();
  }

  /**
   * 获取JavaSqlInfoBuilder的实例，并初始化属性信息.
   *
   * @param source BuildSource实例
   * @return JavaSqlInfoBuilder实例
   */
  public static JavaSqlInfoBuilderHelper newInstance(FixAppender source) {
    JavaSqlInfoBuilderHelper builder = new JavaSqlInfoBuilderHelper();
    builder.init(source);
    return builder;
  }

  /**
   * 构建" IN "范围查询的sql信息.
   *
   * @param fieldText 数据库字段文本
   * @param values 对象集合
   */
  public void buildInSqlByCollection(String fieldText, Collection<Object> values) {
    super.buildInSql(fieldText, values == null ? null : values.toArray());
  }
}
