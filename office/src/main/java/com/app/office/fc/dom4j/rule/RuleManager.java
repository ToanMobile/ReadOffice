package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.rule.pattern.NodeTypePattern;
import java.io.PrintStream;
import java.util.HashMap;

public class RuleManager {
    private int appearenceCount;
    private HashMap modes = new HashMap();
    private Action valueOfAction;

    public Mode getMode(String str) {
        Mode mode = (Mode) this.modes.get(str);
        if (mode != null) {
            return mode;
        }
        Mode createMode = createMode();
        this.modes.put(str, createMode);
        return createMode;
    }

    public void addRule(Rule rule) {
        int i = this.appearenceCount + 1;
        this.appearenceCount = i;
        rule.setAppearenceCount(i);
        Mode mode = getMode(rule.getMode());
        Rule[] unionRules = rule.getUnionRules();
        if (unionRules != null) {
            for (Rule addRule : unionRules) {
                mode.addRule(addRule);
            }
            return;
        }
        mode.addRule(rule);
    }

    public void removeRule(Rule rule) {
        Mode mode = getMode(rule.getMode());
        Rule[] unionRules = rule.getUnionRules();
        if (unionRules != null) {
            for (Rule removeRule : unionRules) {
                mode.removeRule(removeRule);
            }
            return;
        }
        mode.removeRule(rule);
    }

    public Rule getMatchingRule(String str, Node node) {
        Mode mode = (Mode) this.modes.get(str);
        if (mode != null) {
            return mode.getMatchingRule(node);
        }
        PrintStream printStream = System.out;
        printStream.println("Warning: No Mode for mode: " + mode);
        return null;
    }

    public void clear() {
        this.modes.clear();
        this.appearenceCount = 0;
    }

    public Action getValueOfAction() {
        return this.valueOfAction;
    }

    public void setValueOfAction(Action action) {
        this.valueOfAction = action;
    }

    /* access modifiers changed from: protected */
    public Mode createMode() {
        Mode mode = new Mode();
        addDefaultRules(mode);
        return mode;
    }

    /* access modifiers changed from: protected */
    public void addDefaultRules(final Mode mode) {
        AnonymousClass1 r0 = new Action() {
            public void run(Node node) throws Exception {
                if (node instanceof Element) {
                    mode.applyTemplates((Element) node);
                } else if (node instanceof Document) {
                    mode.applyTemplates((Document) node);
                }
            }
        };
        Action valueOfAction2 = getValueOfAction();
        addDefaultRule(mode, NodeTypePattern.ANY_DOCUMENT, r0);
        addDefaultRule(mode, NodeTypePattern.ANY_ELEMENT, r0);
        if (valueOfAction2 != null) {
            addDefaultRule(mode, NodeTypePattern.ANY_ATTRIBUTE, valueOfAction2);
            addDefaultRule(mode, NodeTypePattern.ANY_TEXT, valueOfAction2);
        }
    }

    /* access modifiers changed from: protected */
    public void addDefaultRule(Mode mode, Pattern pattern, Action action) {
        mode.addRule(createDefaultRule(pattern, action));
    }

    /* access modifiers changed from: protected */
    public Rule createDefaultRule(Pattern pattern, Action action) {
        Rule rule = new Rule(pattern, action);
        rule.setImportPrecedence(-1);
        return rule;
    }
}
