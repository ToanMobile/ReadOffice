package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.XPath;
import java.util.List;

public class Stylesheet {
    private String modeName;
    private RuleManager ruleManager = new RuleManager();

    public void addRule(Rule rule) {
        this.ruleManager.addRule(rule);
    }

    public void removeRule(Rule rule) {
        this.ruleManager.removeRule(rule);
    }

    public void run(Object obj) throws Exception {
        run(obj, this.modeName);
    }

    public void run(Object obj, String str) throws Exception {
        if (obj instanceof Node) {
            run((Node) obj, str);
        } else if (obj instanceof List) {
            run((List) obj, str);
        }
    }

    public void run(List list) throws Exception {
        run(list, this.modeName);
    }

    public void run(List list, String str) throws Exception {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            if (obj instanceof Node) {
                run((Node) obj, str);
            }
        }
    }

    public void run(Node node) throws Exception {
        run(node, this.modeName);
    }

    public void run(Node node, String str) throws Exception {
        this.ruleManager.getMode(str).fireRule(node);
    }

    public void applyTemplates(Object obj, XPath xPath) throws Exception {
        applyTemplates(obj, xPath, this.modeName);
    }

    public void applyTemplates(Object obj, XPath xPath, String str) throws Exception {
        Mode mode = this.ruleManager.getMode(str);
        for (Node fireRule : xPath.selectNodes(obj)) {
            mode.fireRule(fireRule);
        }
    }

    public void applyTemplates(Object obj) throws Exception {
        applyTemplates(obj, this.modeName);
    }

    public void applyTemplates(Object obj, String str) throws Exception {
        Mode mode = this.ruleManager.getMode(str);
        int i = 0;
        if (obj instanceof Element) {
            Element element = (Element) obj;
            int nodeCount = element.nodeCount();
            while (i < nodeCount) {
                mode.fireRule(element.node(i));
                i++;
            }
        } else if (obj instanceof Document) {
            Document document = (Document) obj;
            int nodeCount2 = document.nodeCount();
            while (i < nodeCount2) {
                mode.fireRule(document.node(i));
                i++;
            }
        } else if (obj instanceof List) {
            List list = (List) obj;
            int size = list.size();
            while (i < size) {
                Object obj2 = list.get(i);
                if (obj2 instanceof Element) {
                    applyTemplates((Object) (Element) obj2, str);
                } else if (obj2 instanceof Document) {
                    applyTemplates((Object) (Document) obj2, str);
                }
                i++;
            }
        }
    }

    public void clear() {
        this.ruleManager.clear();
    }

    public String getModeName() {
        return this.modeName;
    }

    public void setModeName(String str) {
        this.modeName = str;
    }

    public Action getValueOfAction() {
        return this.ruleManager.getValueOfAction();
    }

    public void setValueOfAction(Action action) {
        this.ruleManager.setValueOfAction(action);
    }
}
