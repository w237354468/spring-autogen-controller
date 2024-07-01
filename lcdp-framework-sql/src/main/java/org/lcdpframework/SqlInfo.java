package org.lcdpframework;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 构造sql查询信息的拼接和参数对象.
 */
public class SqlInfo {

    /** 拼接sql的StringBuilder对象. */
 @Getter
    private StringBuilder buildingSql;

    /** sql语句对应的有序参数. */
    private List<Object> params;

    /** 最终生成的可用sql. */
    private String finalSql;

    /**
     * 全构造方法.
     * @param buildingSql 拼接sql的StringBuilder对象
     * @param params 有序的参数集合
     */
    private SqlInfo(StringBuilder buildingSql, List<Object> params) {
        super();
        this.buildingSql = buildingSql;
        this.params = params;
    }

    /**
     * 获取一个新的SqlInfo实例.
     * @return 返回SqlInfo实例
     */
    public static SqlInfo newInstance() {
    return new SqlInfo(new StringBuilder(), new ArrayList<>());
    }

    /**
     * 如果存在某子SQL字符串，则移除该子SQL字符串，常用于来消除'WHERE 1=1'或其他不需要的SQL字符串的场景.
     * 注意该方法不会移除其对应的参数，所以，这里只应该移除静态SQL字符串，不应该移除包含占位符的SQL.
     *
     * @param subSql 静态子SQL片段
     * @return SqlInfo实例
     */
    public SqlInfo removeIfExist(String subSql) {
        this.finalSql = subSql != null && finalSql.contains(subSql) ? finalSql.replaceAll(subSql, "") : finalSql;
        return this;
    }

    public SqlInfo replaceAll(String str, String  to) {
        this.finalSql = this.finalSql.replaceAll(str,to);
        return this;
    }

    public SqlInfo removeIfEndWith(String subSql) {
        if (this.finalSql.endsWith(subSql)) {
            this.finalSql = this.finalSql.substring(0, this.finalSql.length()-subSql.length());
        }
        return this;
    }

    public SqlInfo removePattern(Pattern pattern) {
        Matcher matcher = pattern.matcher(this.finalSql);
        this.finalSql =      matcher.replaceAll("").replaceAll("\\s{2,}", " ").trim();
        return this;
    }

    /* -------------- 以下是 getter 和 setter 方法 ------------- */

    SqlInfo setBuildingSql(StringBuilder buildingSql) {
        this.buildingSql = buildingSql;
        return this;
    }

    public SqlInfo setParams(List<Object> params) {
        this.params = params;
        return this;
    }

    public List<Object> getParams(){
        return this.params;
    }

    /**
     * 得到参数的对象数组.
     * @return 返回参数的对象数组
     */
    public Object[] getParamsArr() {
        return params == null ? new Object[]{} : this.params.toArray();
    }

    public SqlInfo generateFinalSQL(){
        this.finalSql = buildingSql.toString();
        return this;
    }
    public String getFinalSQL(){
        return this.finalSql;
    }
}