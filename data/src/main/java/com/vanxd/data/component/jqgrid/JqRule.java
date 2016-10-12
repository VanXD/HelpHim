package com.vanxd.data.component.jqgrid;

/**
 * JqGrid查询用的规则类
 *
 *
 * @author wyd on 2016/10/10.
 */
public class JqRule {
    /** 字段名 */
    private String field;
    /** jqGrid逻辑运算符 */
    private String op;
    /** 值 */
    private String data;
    /** 表别名 */
    private String tableAlias = "";

    /**
     * 将jqGrid逻辑运算符 转为 数据库逻辑运算符
     * @return
     */
    public String getDbOp() {
        return JqGridOpConverter.getDbOper(op);
    }

    /**
     * 1.连接表别名
     * 2.将驼峰式 转为 下划线分割格式
     * @return
     */
    public String getDbFiled() {
        StringBuilder sb = new StringBuilder(this.tableAlias + ".");
        for(int i = 0, j = this.field.length() ; i < j ; i++) {
            if(Character.isUpperCase(this.field.charAt(i))) {
                sb.append("_" + Character.toLowerCase(this.field.charAt(i)));
            } else {
                sb.append(this.field.charAt(i));
            }
        }
        return sb.toString();
    }

    public String getField() {
        return field;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
