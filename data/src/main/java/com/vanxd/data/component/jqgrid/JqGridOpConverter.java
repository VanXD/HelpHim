package com.vanxd.data.component.jqgrid;

import java.util.HashMap;
import java.util.Map;

/**
 * JqGrid的条件搜索符与数据库条件转换器
 *
 * @author wyd on 2016/9/8.
 */
public class JqGridOpConverter {
    private static final Map<String, String> jqGridOpMap = new HashMap<String, String>();

    static {
        jqGridOpMap.put("eq", " = ");
        jqGridOpMap.put("ne", " <> ");
        jqGridOpMap.put("lt", " < ");
        jqGridOpMap.put("le", " <= ");
        jqGridOpMap.put("gt", " > ");
        jqGridOpMap.put("ge", " >= ");
        jqGridOpMap.put("bw", " LIKE ");
        jqGridOpMap.put("bn", " NOT LIKE ");
        jqGridOpMap.put("ew", " LIKE ");
        jqGridOpMap.put("en", " NOT LIKE ");
        jqGridOpMap.put("cn", " LIKE ");
        jqGridOpMap.put("nc", " NOT LIKE ");
        jqGridOpMap.put("in", " IN ");
        jqGridOpMap.put("ni", " NOT IN ");
    }

    public static String getDbOper(String op) {
        return jqGridOpMap.get(op);
    }
}
