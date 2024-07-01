package org.lcdpframework;

import org.lcdpframework.constant.ConcatConst;
import org.lcdpframework.constant.SqlKeyWordConst;
import org.lcdpframework.util.ArraysHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * SQL拼接的入口，简单的在此直接拼接，复杂通用的使用BuilderHelper，还可以执行各种回调
 */
public class WwdSQLGenerator {

  private static final Pattern BLANK_PATTERN = Pattern.compile("\\|\t|\r|\n");

  /** 封装了SqlInfo、应用中提供的上下文参数、前缀等信息.由于这里是纯Java拼接,所以就没有xml的Node节点信息，初始为为null. */
  private FixAppender source;

  /** 私有构造方法，构造时就初始化BuildSource相应的参数信息. */
  public WwdSQLGenerator() {
    this.source = new FixAppender(SqlInfo.newInstance());
  }

  public static WwdSQLGenerator start() {
    return new WwdSQLGenerator();
  }

  /**
   * 结束SQL拼接流程，并生成最终的sqlInfo信息.
   *
   * @return sqlInfo
   */
  public SqlInfo end() {
    return this.source.getSqlInfo().generateFinalSQL().removePattern(BLANK_PATTERN);
  }

  public void reset() {
      this.source.setSqlInfo(SqlInfo.newInstance());
      this.source.resetPrefix();
      this.source.resetSuffix();
  }

  /**
   * 连接字符串.
   *
   * @param sqlKey sql关键字
   * @param params 其他若干字符串参数
   */
  private WwdSQLGenerator concat(String sqlKey, String... params) {
    this.source
        .getSqlInfo()
        .getBuildingSql()
        .append(SqlKeyWordConst.SPACE)
        .append(sqlKey)
        .append(SqlKeyWordConst.SPACE);
    if (ArraysHelper.isNotEmpty(params)) {
      for (String s : params) {
        this.source.getSqlInfo().getBuildingSql().append(s).append(SqlKeyWordConst.SPACE);
      }
    }
    return this;
  }

  /**
   * 拼接并带上'INSERT_INTO'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator insertInto(String text) {
    return concat(SqlKeyWordConst.INSERT_INTO, text);
  }

  /**
   * 拼接并带上'VALUES'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator values(String text) {
    return concat(SqlKeyWordConst.VALUES, text);
  }

  /**
   * 拼接并带上'DELETE FROM'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator deleteFrom(String text) {
    return concat(SqlKeyWordConst.DELETE_FROM, text);
  }

  /**
   * 拼接并带上'UPDATE'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator update(String text) {
    return concat(SqlKeyWordConst.UPDATE, text);
  }

  /**
   * 拼接并带上'SELECT'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator select(String text) {
    return concat(SqlKeyWordConst.SELECT, text);
  }

  /**
   * 拼接并带上'FROM'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator from(String text) {
    return concat(SqlKeyWordConst.FROM, text);
  }

  /**
   * 拼接并带上'WHERE'关键字的字符串和动态参数.
   *
   * @param text 文本
   * @param value 参数值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator where(String text, Object... value) {
    concat(SqlKeyWordConst.WHERE, text);
    return this.param(value);
  }

  /**
   * 拼接并带上'AND'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator and(String text) {
    return concat(SqlKeyWordConst.AND, text);
  }

  /**
   * 拼接并带上'OR'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator or(String text) {
    return concat(SqlKeyWordConst.OR, text);
  }

  /**
   * 拼接并带上'AS'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator as(String text) {
    return concat(SqlKeyWordConst.AS, text);
  }

  /**
   * 拼接并带上'AS'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator set(String text) {
    return concat(SqlKeyWordConst.SET, text);
  }

  /**
   * 拼接并带上'INNER JOIN'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator innerJoin(String text) {
    return concat(SqlKeyWordConst.INNER_JOIN, text);
  }

  /**
   * 拼接并带上'LEFT JOIN'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator leftJoin(String text) {
    return concat(SqlKeyWordConst.LEFT_JOIN, text);
  }

  /**
   * 拼接并带上'RIGHT JOIN'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator rightJoin(String text) {
    return concat(SqlKeyWordConst.RIGHT_JOIN, text);
  }

  /**
   * 拼接并带上'FULL JOIN'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator fullJoin(String text) {
    return concat(SqlKeyWordConst.FULL_JOIN, text);
  }

  /**
   * 拼接并带上'ON'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator on(String text) {
    return concat(SqlKeyWordConst.ON, text);
  }

  /**
   * 拼接并带上'ORDER BY'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orderBy(String text) {
    return concat(SqlKeyWordConst.ORDER_BY, text);
  }

  /**
   * 拼接并带上'GROUP BY'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator groupBy(String text) {
    return concat(SqlKeyWordConst.GROUP_BY, text);
  }

  /**
   * 拼接并带上'HAVING'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator having(String text) {
    return concat(SqlKeyWordConst.HAVING, text);
  }

  /**
   * 拼接并带上'LIMIT'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator limit(String text) {
    return concat(SqlKeyWordConst.LIMIT, text);
  }

  /**
   * 拼接并带上'OFFSET'关键字的字符串.
   *
   * @param text 文本
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator offset(String text) {
    return concat(SqlKeyWordConst.OFFSET, text);
  }

  /**
   * 拼接并带上'ASC'关键字的字符串.
   *
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator asc() {
    return concat(SqlKeyWordConst.ASC);
  }

  /**
   * 拼接并带上'DESC'关键字的字符串.
   *
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator desc() {
    return concat(SqlKeyWordConst.DESC);
  }

  /**
   * 拼接并带上'UNION'关键字的字符串.
   *
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator union() {
    return concat(SqlKeyWordConst.UNION);
  }

  /**
   * 拼接并带上'UNION ALL'关键字的字符串.
   *
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator unionAll() {
    return concat(SqlKeyWordConst.UNION_ALL);
  }

  /**
   * 在sql后追加任何文本字符串，后可追加自定义可变参数.
   *
   * @param text 文本
   * @param values 可变参数数组
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator text(String text, Object... values) {
    this.source.getSqlInfo().getBuildingSql().append(text);
    this.appendParams(values, ConcatConst.OBJTYPE_ARRAY);
    return this;
  }

  /**
   * 在sql后追加任何文本字符串，后可追加自定义可变参数，如果match为true时，才生成此SQL文本和参数.
   *
   * @param match 匹配条件
   * @param text 文本
   * @param values 可变参数数组
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator text(boolean match, String text, Object... values) {
    return match ? text(text, values) : this;
  }

  /**
   * 在sql的参数集合后追加任何的数组.
   *
   * @param value 值
   * @param objType 对象类型那
   * @return WwdSQLGenerator实例
   */
  private WwdSQLGenerator appendParams(Object value, int objType) {
    Object[] values = ArraysHelper.toArray(value, objType);
    if (ArraysHelper.isNotEmpty(values)) {
      Collections.addAll(this.source.getSqlInfo().getParams(), values);
    }
    return this;
  }

  /**
   * 在sql的参数集合后追加不定对象个数的数组.
   *
   * @param values 不定个数的值，也是数组
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator param(Object... values) {
    return this.appendParams(values, ConcatConst.OBJTYPE_ARRAY);
  }

  /**
   * 在sql的参数集合后追加任何的一个集合.
   *
   * @param values 不定个数的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator param(Collection<?> values) {
    return this.appendParams(values, ConcatConst.OBJTYPE_COLLECTION);
  }

  /**
   * 执行自定义的任意操作.
   *
   * @param action 执行when条件中的方法
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator doAnything(ICustomAction action) {
    SqlInfo sqlInfo = this.source.getSqlInfo();
    action.execute(sqlInfo.getBuildingSql(), sqlInfo.getParams());
    return this;
  }

  /**
   * 当匹配match条件为true时，才执行自定义的任意操作.
   *
   * @param match 匹配条件
   * @param action 执行when条件中的方法
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator doAnything(boolean match, ICustomAction action) {
    return match ? this.doAnything(action) : this;
  }

  /**
   * 执行生成等值查询SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param value 值
   * @param suffix 后缀
   * @param match 是否匹配
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doNormal(
      String prefix, String field, Object value, String suffix, boolean match) {
    if (match) {
      SqlInfoBuilderHelper.newInstance(this.source.setPrefix(prefix)).buildNormalSql(field, value, suffix);
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 执行生成like模糊查询SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @param positive true则表示是like，否则是not like
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doLike(
      String prefix, String field, Object value, boolean match, boolean positive) {
    if (match) {
      String suffix = positive ? ConcatConst.LIKE_KEY : ConcatConst.NOT_LIKE_KEY;
      SqlInfoBuilderHelper.newInstance(this.source.setPrefix(prefix).setSuffix(suffix))
          .buildLikeSql(field, value);
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 执行根据传入模式来生成like匹配SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param pattern 值
   * @param match 是否匹配
   * @param positive true则表示是like，否则是not like
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doLikePattern(
      String prefix, String field, String pattern, boolean match, boolean positive) {
    if (match) {
      String suffix = positive ? ConcatConst.LIKE_KEY : ConcatConst.NOT_LIKE_KEY;
      SqlInfoBuilderHelper.newInstance(this.source.setPrefix(prefix).setSuffix(suffix))
          .buildLikePatternSql(field, pattern);
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 执行生成like模糊查询SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param startValue 值
   * @param endValue 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doBetween(
      String prefix, String field, Object startValue, Object endValue, boolean match) {
    if (match) {
      SqlInfoBuilderHelper.newInstance(this.source.setPrefix(prefix))
          .buildBetweenSql(field, startValue, endValue);
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 执行生成in范围查询SQL片段的方法,如果是集合或数组，则执行生成，否则抛出异常.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param value 数组的值
   * @param match 是否匹配
   * @param objType 对象类型，取自ZealotConst.java中以OBJTYPE开头的类型
   * @param positive true则表示是in，否则是not in
   * @return WwdSQLGenerator实例的当前实例
   */
  @SuppressWarnings("unchecked")
  private WwdSQLGenerator doInByType(
      String prefix, String field, Object value, boolean match, int objType, boolean positive) {
    if (match) {
      // 赋予source对象in SQL片段的前缀和后缀操作符.
      this.source
          .setPrefix(prefix)
          .setSuffix(positive ? ConcatConst.IN_SUFFIX : ConcatConst.NOT_IN_SUFFIX);
      // 根据对象类型调用对应的生成in查询的sql片段方法,否则抛出类型不符合的异常
      switch (objType) {
          // 如果类型是数组.
        case ConcatConst.OBJTYPE_ARRAY:
          SqlInfoBuilderHelper.newInstance(source).buildInSql(field, (Object[]) value);
          break;
          // 如果类型是Java集合.
        case ConcatConst.OBJTYPE_COLLECTION:
          JavaSqlInfoBuilderHelper.newInstance(source)
              .buildInSqlByCollection(field, (Collection<Object>) value);
          break;
        default:
          throw new RuntimeException("in查询的值不是有效的集合或数组!");
      }
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 执行生成in范围查询SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @param positive true则表示是in，否则是not in
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doIn(
      String prefix, String field, Object[] values, boolean match, boolean positive) {
    return this.doInByType(prefix, field, values, match, ConcatConst.OBJTYPE_ARRAY, positive);
  }

  /**
   * 执行生成in范围查询SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @param positive true则表示是in，否则是not in
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doIn(
      String prefix, String field, Collection<?> values, boolean match, boolean positive) {
    return this.doInByType(prefix, field, values, match, ConcatConst.OBJTYPE_COLLECTION, positive);
  }

  /**
   * 执行生成" IS NULL "SQL片段的方法.
   *
   * @param prefix 前缀
   * @param field 数据库字段
   * @param match 是否匹配
   * @param positive true则表示是 IS NULL，否则是IS NOT NULL
   * @return WwdSQLGenerator实例的当前实例
   */
  private WwdSQLGenerator doIsNull(String prefix, String field, boolean match, boolean positive) {
    if (match) {
      // 判断是"IS NULL"还是"IS NOT NULL"来设置source实例.
      this.source =
          this.source
              .setPrefix(prefix)
              .setSuffix(positive ? ConcatConst.IS_NULL_SUFFIX : ConcatConst.IS_NOT_NULL_SUFFIX);
      SqlInfoBuilderHelper.newInstance(this.source).buildIsNullSql(field);
      this.source.resetPrefix();
    }
    return this;
  }

  /**
   * 生成等值查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator equal_(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.EQUAL_SUFFIX, true);
  }

  /**
   * 生成等值查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator equal_(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.EQUAL_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀等值查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andEqual(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.EQUAL_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀等值查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.EQUAL_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀等值查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orEqual(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.EQUAL_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀等值查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.EQUAL_SUFFIX, match);
  }

  /**
   * 生成不等查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notEqual(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.NOT_EQUAL_SUFFIX, true);
  }

  /**
   * 生成不等查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.NOT_EQUAL_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀不等查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotEqual(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.NOT_EQUAL_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀不等查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.NOT_EQUAL_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀不等查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotEqual(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.NOT_EQUAL_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀不等查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.NOT_EQUAL_SUFFIX, match);
  }

  /**
   * 生成大于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator moreThan(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.GT_SUFFIX, true);
  }

  /**
   * 生成大于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator moreThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.GT_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀大于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andMoreThan(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.GT_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀大于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andMoreThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.GT_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀大于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orMoreThan(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.GT_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀大于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orMoreThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.GT_SUFFIX, match);
  }

  /**
   * 生成小于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator lessThan(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.LT_SUFFIX, true);
  }

  /**
   * 生成小于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator lessThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.LT_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀小于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLessThan(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.LT_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀小于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLessThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.LT_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀小于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLessThan(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.LT_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀小于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLessThan(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.LT_SUFFIX, match);
  }

  /**
   * 生成大于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator moreEqual(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.GTE_SUFFIX, true);
  }

  /**
   * 生成大于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator moreEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.GTE_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀大于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andMoreEqual(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.GTE_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀大于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andMoreEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.GTE_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀大于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orMoreEqual(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.GTE_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀大于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orMoreEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.GTE_SUFFIX, match);
  }

  /**
   * 生成小于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator lessEqual(String field, Object value) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.LTE_SUFFIX, true);
  }

  /**
   * 生成小于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator lessEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.ONE_SPACE, field, value, ConcatConst.LTE_SUFFIX, match);
  }

  /**
   * 生成带" AND "前缀小于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLessEqual(String field, Object value) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.LTE_SUFFIX, true);
  }

  /**
   * 生成带" AND "前缀小于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLessEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.AND_PREFIX, field, value, ConcatConst.LTE_SUFFIX, match);
  }

  /**
   * 生成带" OR "前缀小于等于查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLessEqual(String field, Object value) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.LTE_SUFFIX, true);
  }

  /**
   * 生成带" OR "前缀小于等于查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLessEqual(String field, Object value, boolean match) {
    return this.doNormal(ConcatConst.OR_PREFIX, field, value, ConcatConst.LTE_SUFFIX, match);
  }

  /**
   * 生成like模糊查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator like(String field, Object value) {
    return this.doLike(ConcatConst.ONE_SPACE, field, value, true, true);
  }

  /**
   * 生成like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator like(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.ONE_SPACE, field, value, match, true);
  }

  /**
   * 生成带" AND "前缀的like模糊查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLike(String field, Object value) {
    return this.doLike(ConcatConst.AND_PREFIX, field, value, true, true);
  }

  /**
   * 生成带" AND "前缀的like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLike(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.AND_PREFIX, field, value, match, true);
  }

  /**
   * 生成带" OR "前缀的like模糊查询的SQL片段.
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLike(String field, Object value) {
    return this.doLike(ConcatConst.OR_PREFIX, field, value, true, true);
  }

  /**
   * 生成带" OR "前缀的like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLike(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.OR_PREFIX, field, value, match, true);
  }

  /**
   * 生成" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Spring"} 两个参数，生成的SQL片段为：" b.title NOT LIKE ? ", SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notLike(String field, Object value) {
    return this.doLike(ConcatConst.ONE_SPACE, field, value, true, false);
  }

  /**
   * 生成" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Spring", true} 三个参数，生成的SQL片段为：" b.title NOT LIKE ? ", SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notLike(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.ONE_SPACE, field, value, match, false);
  }

  /**
   * 生成带" AND "前缀的" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Spring"} 两个参数，生成的SQL片段为：" AND b.title NOT LIKE ? ", SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotLike(String field, Object value) {
    return this.doLike(ConcatConst.AND_PREFIX, field, value, true, false);
  }

  /**
   * 生成带" AND "前缀的" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Spring", true} 三个参数，生成的SQL片段为：" AND b.title NOT LIKE ? ",
   * SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotLike(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.AND_PREFIX, field, value, match, false);
  }

  /**
   * 生成带" OR "前缀的" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Spring"} 两个参数，生成的SQL片段为：" OR b.title NOT LIKE ? ", SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotLike(String field, Object value) {
    return this.doLike(ConcatConst.OR_PREFIX, field, value, true, false);
  }

  /**
   * 生成带" OR "前缀的" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Spring", true} 三个参数，生成的SQL片段为：" OR b.title NOT LIKE ? ",
   * SQL参数为:{"%Spring%"}
   *
   * @param field 数据库字段
   * @param value 值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotLike(String field, Object value, boolean match) {
    return this.doLike(ConcatConst.OR_PREFIX, field, value, match, false);
  }

  /**
   * 根据指定的模式字符串生成like模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" b.title LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator likePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.ONE_SPACE, field, pattern, true, true);
  }

  /**
   * 根据指定的模式字符串生成like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" b.title LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator likePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.ONE_SPACE, field, pattern, match, true);
  }

  /**
   * 根据指定的模式字符串生成带" AND "前缀的like模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" AND b.title LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLikePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.AND_PREFIX, field, pattern, true, true);
  }

  /**
   * 根据指定的模式字符串生成带" AND "前缀的like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" AND b.title LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andLikePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.AND_PREFIX, field, pattern, match, true);
  }

  /**
   * 根据指定的模式字符串生成带" OR "前缀的like模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" OR b.title LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLikePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.OR_PREFIX, field, pattern, true, true);
  }

  /**
   * 根据指定的模式字符串生成带" OR "前缀的like模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" OR b.title LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orLikePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.OR_PREFIX, field, pattern, match, true);
  }

  /**
   * 根据指定的模式字符串生成" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" b.title NOT LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notLikePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.ONE_SPACE, field, pattern, true, false);
  }

  /**
   * 根据指定的模式字符串生成" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" b.title NOT LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notLikePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.ONE_SPACE, field, pattern, match, false);
  }

  /**
   * 根据指定的模式字符串生成带" AND "前缀的" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" AND b.title NOT LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotLikePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.AND_PREFIX, field, pattern, true, false);
  }

  /**
   * 根据指定的模式字符串生成带" AND "前缀的" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" AND b.title NOT LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotLikePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.AND_PREFIX, field, pattern, match, false);
  }

  /**
   * 根据指定的模式字符串生成带" OR "前缀的" NOT LIKE "模糊查询的SQL片段.
   *
   * <p>示例：传入 {"b.title", "Java%"} 两个参数，生成的SQL片段为：" OR b.title NOT LIKE 'Java%' "
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotLikePattern(String field, String pattern) {
    return this.doLikePattern(ConcatConst.OR_PREFIX, field, pattern, true, false);
  }

  /**
   * 根据指定的模式字符串生成带" OR "前缀的" NOT LIKE "模糊查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"b.title", "Java%", true} 三个参数，生成的SQL片段为：" OR b.title NOT LIKE 'Java%' "
   *
   * <p>示例：传入 {"b.title", "Java%", false} 三个参数，生成的SQL片段为空字符串.
   *
   * @param field 数据库字段
   * @param pattern 模式字符串
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotLikePattern(String field, String pattern, boolean match) {
    return this.doLikePattern(ConcatConst.OR_PREFIX, field, pattern, match, false);
  }

  /**
   * 生成between区间查询的SQL片段(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator between(String field, Object startValue, Object endValue) {
    return this.doBetween(ConcatConst.ONE_SPACE, field, startValue, endValue, true);
  }

  /**
   * 生成between区间查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator between(String field, Object startValue, Object endValue, boolean match) {
    return this.doBetween(ConcatConst.ONE_SPACE, field, startValue, endValue, match);
  }

  /**
   * 生成带" AND "前缀的between区间查询的SQL片段(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andBetween(String field, Object startValue, Object endValue) {
    return this.doBetween(ConcatConst.AND_PREFIX, field, startValue, endValue, true);
  }

  /**
   * 生成带" AND "前缀的between区间查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andBetween(
      String field, Object startValue, Object endValue, boolean match) {
    return this.doBetween(ConcatConst.AND_PREFIX, field, startValue, endValue, match);
  }

  /**
   * 生成带" OR "前缀的between区间查询的SQL片段(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orBetween(String field, Object startValue, Object endValue) {
    return this.doBetween(ConcatConst.OR_PREFIX, field, startValue, endValue, true);
  }

  /**
   * 生成带" OR "前缀的between区间查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成(当某一个值为null时，会是大于等于或小于等于的情形).
   *
   * @param field 数据库字段
   * @param startValue 开始值
   * @param endValue 结束值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orBetween(
      String field, Object startValue, Object endValue, boolean match) {
    return this.doBetween(ConcatConst.OR_PREFIX, field, startValue, endValue, match);
  }

  /**
   * 生成in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator in(String field, Object[] values) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, true, true);
  }

  /**
   * 生成in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator in(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, match, true);
  }

  /**
   * 生成in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator in(String field, Collection<?> values) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, true, true);
  }

  /**
   * 生成in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator in(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, match, true);
  }

  /**
   * 生成带" AND "前缀的in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIn(String field, Object[] values) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, true, true);
  }

  /**
   * 生成带" AND "前缀的in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIn(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, match, true);
  }

  /**
   * 生成带" AND "前缀的in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIn(String field, Collection<?> values) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, true, true);
  }

  /**
   * 生成带" AND "前缀的in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIn(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, match, true);
  }

  /**
   * 生成带" OR "前缀的in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIn(String field, Object[] values) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, true, true);
  }

  /**
   * 生成带" OR "前缀的in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIn(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, match, true);
  }

  /**
   * 生成带" OR "前缀的in范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIn(String field, Collection<?> values) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, true, true);
  }

  /**
   * 生成带" OR "前缀的in范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIn(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, match, true);
  }

  /**
   * 生成" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notIn(String field, Object[] values) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, true, false);
  }

  /**
   * 生成" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notIn(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, match, false);
  }

  /**
   * 生成" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notIn(String field, Collection<?> values) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, true, false);
  }

  /**
   * 生成" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator notIn(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.ONE_SPACE, field, values, match, false);
  }

  /**
   * 生成带" AND "前缀的" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotIn(String field, Object[] values) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, true, false);
  }

  /**
   * 生成带" AND "前缀的" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotIn(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, match, false);
  }

  /**
   * 生成带" AND "前缀的" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotIn(String field, Collection<?> values) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, true, false);
  }

  /**
   * 生成带" AND "前缀的" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andNotIn(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.AND_PREFIX, field, values, match, false);
  }

  /**
   * 生成带" OR "前缀的" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotIn(String field, Object[] values) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, true, false);
  }

  /**
   * 生成带" OR "前缀的" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 数组的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotIn(String field, Object[] values, boolean match) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, match, false);
  }

  /**
   * 生成带" OR "前缀的" NOT IN "范围查询的SQL片段.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotIn(String field, Collection<?> values) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, true, false);
  }

  /**
   * 生成带" OR "前缀的" NOT IN "范围查询的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * @param field 数据库字段
   * @param values 集合的值
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orNotIn(String field, Collection<?> values, boolean match) {
    return this.doIn(ConcatConst.OR_PREFIX, field, values, match, false);
  }

  /**
   * 生成" IS NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" a.name IS NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator isNull(String field) {
    return this.doIsNull(ConcatConst.ONE_SPACE, field, true, true);
  }

  /**
   * 生成" IS NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" a.name IS NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator isNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.ONE_SPACE, field, match, true);
  }

  /**
   * 生成带" AND "前缀" IS NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" AND a.name IS NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIsNull(String field) {
    return this.doIsNull(ConcatConst.AND_PREFIX, field, true, true);
  }

  /**
   * 生成带" AND "前缀" IS NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" AND a.name IS NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIsNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.AND_PREFIX, field, match, true);
  }

  /**
   * 生成带" OR "前缀" IS NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" OR a.name IS NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIsNull(String field) {
    return this.doIsNull(ConcatConst.OR_PREFIX, field, true, true);
  }

  /**
   * 生成带" OR "前缀" IS NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" OR a.name IS NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIsNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.OR_PREFIX, field, match, true);
  }

  /**
   * 生成" IS NOT NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator isNotNull(String field) {
    return this.doIsNull(ConcatConst.ONE_SPACE, field, true, false);
  }

  /**
   * 生成" IS NOT NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator isNotNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.ONE_SPACE, field, match, false);
  }

  /**
   * 生成带" AND "前缀" IS NOT NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" AND a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIsNotNull(String field) {
    return this.doIsNull(ConcatConst.AND_PREFIX, field, true, false);
  }

  /**
   * 生成带" AND "前缀" IS NOT NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" AND a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator andIsNotNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.AND_PREFIX, field, match, false);
  }

  /**
   * 生成带" OR "前缀" IS NOT NULL "的SQL片段.
   *
   * <p>示例：传入 {"a.name"} 参数，生成的SQL片段为：" OR a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIsNotNull(String field) {
    return this.doIsNull(ConcatConst.OR_PREFIX, field, true, false);
  }

  /**
   * 生成带" OR "前缀" IS NOT NULL "的SQL片段,如果match为true时则生成该条SQL片段，否则不生成.
   *
   * <p>示例：传入 {"a.name", true} 两个参数，生成的SQL片段为：" OR a.name IS NOT NULL "
   *
   * @param field 数据库字段
   * @param match 是否匹配
   * @return WwdSQLGenerator实例
   */
  public WwdSQLGenerator orIsNotNull(String field, boolean match) {
    return this.doIsNull(ConcatConst.OR_PREFIX, field, match, false);
  }
}
