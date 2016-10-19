package com.vanxd.data.component.jqgrid;

import com.vanxd.data.annotation.TableAlias;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author wyd on 2016/10/10.
 */
public class JqFilter {
    /** 关系，当前无视该字段 */
    private String groupOp;
    /** 条件集合 */
    private List<JqRule> rules;
    /** 基本表的实体类 */
    private Class pojoClazz;
    /** 基本表的别名 */
    private String pojoTableAlias;


    public Class getPojoClazz() {
        return pojoClazz;
    }

    /**
     * 设置基本表的类
     * 设置基本表的别
     * 填充别名到条件集合
     * @param pojoClazz
     */
    public void setPojoClazz(Class pojoClazz) {
        this.pojoClazz = pojoClazz;
        setPojoTableAlias();
        fillAliasToRules();
    }


    /**
     * 填充每一个字段的别名
     */
    private void fillAliasToRules() {
        try {
            for(JqRule jqRule : rules) {
                Field field = pojoClazz.getDeclaredField(jqRule.getField());
                TableAlias fieldAnnotation = field.getAnnotation(TableAlias.class);
                if(null != fieldAnnotation && fieldAnnotation.isRequire()) {
                    jqRule.setTableAlias(fieldAnnotation.alias());
                } else {
                    jqRule.setTableAlias(this.pojoTableAlias);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置基本表的别名
     */
    private void setPojoTableAlias() {
        TableAlias clazzAnnotation = (TableAlias) this.pojoClazz.getAnnotation(TableAlias.class);
        if(null == clazzAnnotation) {
            throw new RuntimeException("POJO未设置表别名，不能使用jqGrid条件查询");
        }
        this.pojoTableAlias = clazzAnnotation.alias();
    }

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<JqRule> getRules() {
        return rules;
    }

    public void setRules(List<JqRule> rules) {
        this.rules = rules;
    }

    public String getPojoTableAlias() {
        return pojoTableAlias;
    }
}
