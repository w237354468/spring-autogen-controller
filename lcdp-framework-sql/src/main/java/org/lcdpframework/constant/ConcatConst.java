package org.lcdpframework.constant;

/**
 * @author blinkfox on 2016/10/30.
 */
public final class ConcatConst {

    /** . */
    public static final String SP_AT = "@@"; // @@ separate char

    public static final String COMMA = ",";

    public static final String NODETYPE_TEXT = "Text";
    public static final String NODETYPE_ELEMENT = "Element";

    // =
    public static final String EQUAL = "equal";
    public static final String AND_EQUAL = "andEqual";
    public static final String OR_EQUAL = "orEqual";
    // !=
    public static final String NOT_EQUAL = "notEqual";
    public static final String AND_NOT_EQUAL = "andNotEqual";
    public static final String OR_NOT_EQUAL = "orNotEqual";
    // >
    public static final String MORE = "moreThan";
    public static final String AND_MORE = "andMoreThan";
    public static final String OR_MORE = "orMoreThan";
    // <
    public static final String LESS = "lessThan";
    public static final String AND_LESS = "andLessThan";
    public static final String OR_LESS = "orLessThan";
    // >=
    public static final String MORE_EQUAL = "moreEqual";
    public static final String AND_MORE_EQUAL = "andMoreEqual";
    public static final String OR_MORE_EQUAL = "orMoreEqual";
    // <=
    public static final String LESS_EQUAL = "lessEqual";
    public static final String AND_LESS_EQUAL = "andLessEqual";
    public static final String OR_LESS_EQUAL = "orLessEqual";
    // "like"
    public static final String LIKE = "like";
    public static final String AND_LIKE = "andLike";
    public static final String OR_LIKE = "orLike";
    // "not like"
    public static final String NOT_LIKE = "notLike";
    public static final String AND_NOT_LIKE = "andNotLike";
    public static final String OR_NOT_LIKE = "orNotLike";
    // "between"
    public static final String BETWEEN = "between";
    public static final String AND_BETWEEN = "andBetween";
    public static final String OR_BETWEEN = "orBetween";
    // "in"
    public static final String IN = "in";
    public static final String AND_IN = "andIn";
    public static final String OR_IN = "orIn";
    // "not in"
    public static final String NOT_IN = "notIn";
    public static final String AND_NOT_IN = "andNotIn";
    public static final String OR_NOT_IN = "orNotIn";
    // "is null"
    public static final String IS_NULL = "isNull";
    public static final String AND_IS_NULL = "andIsNull";
    public static final String OR_IS_NULL = "orIsNull";
    // "is not null"
    public static final String IS_NOT_NULL = "isNotNull";
    public static final String AND_IS_NOT_NULL = "andIsNotNull";
    public static final String OR_IS_NOT_NULL = "orIsNotNull";
    // "text"
    public static final String TEXT = "text";
    // "import"
    public static final String IMPORT = "import";
    // "choose"
    public static final String CHOOSE = "choose";

    public static final String ZEALOT_TAG = "zealots/zealot";
    public static final String ATTR_NAMESPACE = "attribute::nameSpace";
    public static final String ATTR_CHILD = "child::node()";
    public static final String ATTR_ID = "attribute::id";
    public static final String ATTR_MATCH = "attribute::match";
    public static final String ATTR_FIELD = "attribute::field";
    public static final String ATTR_VALUE = "attribute::value";
    public static final String ATTR_PATTERN = "attribute::pattern";
    public static final String ATTR_START = "attribute::start";
    public static final String ATTR_ENT = "attribute::end";
    public static final String ATTR_NAME_SPACE = "attribute::namespace";
    public static final String ATTR_ZEALOT_ID = "attribute::zealotid";
    public static final String ATTR_WHEN = "attribute::when";
    public static final String ATTR_THEN = "attribute::then";
    public static final String ATTR_ELSE = "attribute::else";

    /* sql prefix */
    public static final String EMPTY = "";
    public static final String ONE_SPACE = " ";
    public static final String AND_PREFIX = " AND ";
    public static final String OR_PREFIX = " OR ";

    /* sql suffix */
    public static final String EQUAL_SUFFIX = " = ? ";
    public static final String GT_SUFFIX = " > ? ";
    public static final String LT_SUFFIX = " < ? ";
    public static final String GTE_SUFFIX = " >= ? ";
    public static final String LTE_SUFFIX = " <= ? ";
    public static final String NOT_EQUAL_SUFFIX = " <> ? ";
    public static final String LIKE_KEY = " LIKE ";
    public static final String NOT_LIKE_KEY = " NOT LIKE ";
    public static final String BT_AND_SUFFIX = " BETWEEN ? AND ? ";
    public static final String IN_SUFFIX = " IN ";
    public static final String NOT_IN_SUFFIX = " NOT IN ";
    public static final String IS_NULL_SUFFIX = " IS NULL ";
    public static final String IS_NOT_NULL_SUFFIX = " IS NOT NULL ";

    /* type of collection, 0 single object，1 common array，2 java collection */
    public static final int OBJTYPE_ARRAY = 1;
    public static final int OBJTYPE_COLLECTION = 2;

    private ConcatConst() {
        super();
    }
}