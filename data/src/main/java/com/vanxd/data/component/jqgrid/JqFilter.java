package com.vanxd.data.component.jqgrid;

import com.vanxd.data.annotation.TableAlias;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author wyd on 2016/10/10.
 */
public class JqFilter {
    /**
     * 关系，当前无视该字段
     */
    private String groupOp;
    /**
     * 规则
     */
    private List<JqRule> rules;

    private Class pojoClazz;

    public Class getPojoClazz() {
        return pojoClazz;
    }

    public void setPojoClazz(Class pojoClazz) {
        this.pojoClazz = pojoClazz;
        fillAliasToRules();
    }

    /**
     * 填充每一个字段的别名
     */
    private void fillAliasToRules() {
        try {
            TableAlias clazzAnnotation = (TableAlias) pojoClazz.getAnnotation(TableAlias.class);
            if(null == clazzAnnotation) {
                throw new RuntimeException("POJO未设置表别名，不能使用jqGrid条件查询");
            }
            String clazzTableAlias = clazzAnnotation.alias();
            for(JqRule jqRule : rules) {
                Field field = pojoClazz.getDeclaredField(jqRule.getField());
                TableAlias fieldAnnotation = field.getAnnotation(TableAlias.class);
                if(null != fieldAnnotation) {
                    jqRule.setTableAlias(fieldAnnotation.alias());
                } else {
                    jqRule.setTableAlias(clazzTableAlias);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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
}
