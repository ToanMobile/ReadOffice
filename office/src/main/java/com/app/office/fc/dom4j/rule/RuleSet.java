package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Node;
import java.util.ArrayList;
import java.util.Collections;

public class RuleSet {
    private Rule[] ruleArray;
    private ArrayList rules = new ArrayList();

    public String toString() {
        return super.toString() + " [RuleSet: " + this.rules + " ]";
    }

    public Rule getMatchingRule(Node node) {
        Rule[] ruleArray2 = getRuleArray();
        for (int length = ruleArray2.length - 1; length >= 0; length--) {
            Rule rule = ruleArray2[length];
            if (rule.matches(node)) {
                return rule;
            }
        }
        return null;
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
        this.ruleArray = null;
    }

    public void removeRule(Rule rule) {
        this.rules.remove(rule);
        this.ruleArray = null;
    }

    public void addAll(RuleSet ruleSet) {
        this.rules.addAll(ruleSet.rules);
        this.ruleArray = null;
    }

    /* access modifiers changed from: protected */
    public Rule[] getRuleArray() {
        if (this.ruleArray == null) {
            Collections.sort(this.rules);
            Rule[] ruleArr = new Rule[this.rules.size()];
            this.ruleArray = ruleArr;
            this.rules.toArray(ruleArr);
        }
        return this.ruleArray;
    }
}
