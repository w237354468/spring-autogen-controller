package org.lcdpframework;


import lombok.Data;
import lombok.Getter;
import org.lcdpframework.constant.ConcatConst;

/**
 * 构建动态sql和参数相关的封装实体类.
 * @author blinkfox on 2016/10/30.
 */
@Getter
@Data
public final class FixAppender {

    /** SQL拼接信息.
     * -- GETTER --
     *  获取sqlInfo的getter方法.
     *
     * @return SqlInfo实例
     */
    private SqlInfo sqlInfo;

    /** 拼接SQL片段的前缀，如:and、or等.
     * -- GETTER --
     *  获取前缀prefix的getter方法.
     *
     * @return prefix对象
     */
    private String prefix;

    /** 拼接SQL片段的后缀，如：> = <=等.
     * -- GETTER --
     *  获取后缀suffix的getter方法.
     *
     * @return suffix对象
     */
    private String suffix;

    /**
     * 仅仅有sqlInfo的构造方法.
     * @param sqlInfo SQL拼接和参数对象
     */
    public FixAppender(SqlInfo sqlInfo) {
        super();
        this.sqlInfo = sqlInfo;
        resetPrefix();
        resetSuffix();
    }

    /**
     * 重置前缀为默认值.
     * 为了防止SQL拼接时连在一起，默认前缀为一个空格的字符串，后缀为空字符串.
     */
    public void resetPrefix() {
        this.prefix = ConcatConst.ONE_SPACE;
        this.suffix = ConcatConst.EMPTY;
    }

    /**
     * 重置后缀为默认值.
     * 为了防止SQL拼接时连在一起，默认后缀为一个空格的字符串.
     */
    public void resetSuffix() {
        this.suffix = ConcatConst.ONE_SPACE;
    }

    /* --------------- 以下是 getter 和 setter 方法. ---------------- */

    /**
     * 设置sqlInfo的setter方法.
     * @param sqlInfo SqlInfo实例
     * @return 当前BuildSource的实例
     */
    public FixAppender setSqlInfo(SqlInfo sqlInfo) {
        this.sqlInfo = sqlInfo;
        return this;
    }

    /**
     * 设置prefix的setter方法.
     * @param prefix prefix对象
     * @return 当前BuildSource的实例
     */
    public FixAppender setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * 设置后缀suffix的setter方法.
     * @param suffix suffix对象
     * @return 当前BuildSource的实例
     */
    public FixAppender setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}