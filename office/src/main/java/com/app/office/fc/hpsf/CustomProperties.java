package com.app.office.fc.hpsf;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CustomProperties extends HashMap<Object, CustomProperty> {
    private Map<Long, String> dictionaryIDToName = new HashMap();
    private Map<String, Long> dictionaryNameToID = new HashMap();
    private boolean isPure = true;

    public CustomProperty put(String str, CustomProperty customProperty) {
        if (str == null) {
            this.isPure = false;
            return null;
        } else if (str.equals(customProperty.getName())) {
            Long valueOf = Long.valueOf(customProperty.getID());
            Long l = this.dictionaryNameToID.get(str);
            this.dictionaryIDToName.remove(l);
            this.dictionaryNameToID.put(str, valueOf);
            this.dictionaryIDToName.put(valueOf, str);
            CustomProperty customProperty2 = (CustomProperty) super.remove(l);
            super.put(valueOf, customProperty);
            return customProperty2;
        } else {
            throw new IllegalArgumentException("Parameter \"name\" (" + str + ") and custom property's name (" + customProperty.getName() + ") do not match.");
        }
    }

    private Object put(CustomProperty customProperty) throws ClassCastException {
        String name = customProperty.getName();
        Long l = this.dictionaryNameToID.get(name);
        if (l != null) {
            customProperty.setID(l.longValue());
        } else {
            long j = 1;
            for (Long longValue : this.dictionaryIDToName.keySet()) {
                long longValue2 = longValue.longValue();
                if (longValue2 > j) {
                    j = longValue2;
                }
            }
            customProperty.setID(j + 1);
        }
        return put(name, customProperty);
    }

    public Object remove(String str) {
        Long l = this.dictionaryNameToID.get(str);
        if (l == null) {
            return null;
        }
        this.dictionaryIDToName.remove(l);
        this.dictionaryNameToID.remove(str);
        return super.remove(l);
    }

    public Object put(String str, String str2) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(31);
        mutableProperty.setValue(str2);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Object put(String str, Long l) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(20);
        mutableProperty.setValue(l);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Object put(String str, Double d) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(5);
        mutableProperty.setValue(d);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Object put(String str, Integer num) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(3);
        mutableProperty.setValue(num);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Object put(String str, Boolean bool) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(11);
        mutableProperty.setValue(bool);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Object get(String str) {
        CustomProperty customProperty = (CustomProperty) super.get(this.dictionaryNameToID.get(str));
        if (customProperty != null) {
            return customProperty.getValue();
        }
        return null;
    }

    public Object put(String str, Date date) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(-1);
        mutableProperty.setType(64);
        mutableProperty.setValue(date);
        return put(new CustomProperty(mutableProperty, str));
    }

    public Set keySet() {
        return this.dictionaryNameToID.keySet();
    }

    public Set<String> nameSet() {
        return this.dictionaryNameToID.keySet();
    }

    public Set<String> idSet() {
        return this.dictionaryNameToID.keySet();
    }

    public void setCodepage(int i) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID(1);
        mutableProperty.setType(2);
        mutableProperty.setValue(Integer.valueOf(i));
        put(new CustomProperty(mutableProperty));
    }

    /* access modifiers changed from: package-private */
    public Map<Long, String> getDictionary() {
        return this.dictionaryIDToName;
    }

    public boolean containsKey(Object obj) {
        if (obj instanceof Long) {
            return super.containsKey((Long) obj);
        }
        if (obj instanceof String) {
            return super.containsKey(this.dictionaryNameToID.get(obj));
        }
        return false;
    }

    public boolean containsValue(Object obj) {
        if (obj instanceof CustomProperty) {
            return super.containsValue((CustomProperty) obj);
        }
        for (CustomProperty value : super.values()) {
            if (value.getValue() == obj) {
                return true;
            }
        }
        return false;
    }

    public int getCodepage() {
        Iterator it = values().iterator();
        int i = -1;
        while (i == -1 && it.hasNext()) {
            CustomProperty customProperty = (CustomProperty) it.next();
            if (customProperty.getID() == 1) {
                i = ((Integer) customProperty.getValue()).intValue();
            }
        }
        return i;
    }

    public boolean isPure() {
        return this.isPure;
    }

    public void setPure(boolean z) {
        this.isPure = z;
    }
}
