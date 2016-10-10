package com.vanxd.data.component.jqgrid;

import java.util.List;

/**
 * @author wyd on 2016/10/10.
 */
public class Filter {
    private String groupOp;
    private List<Rule> rules;

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
