package com.app.office.thirdpart.emf.io;

import com.app.office.thirdpart.emf.io.Action;
import java.util.HashMap;
import java.util.Map;

public class ActionSet {
    protected Map actions = new HashMap();
    protected Action defaultAction = new Action.Unknown();

    public void addAction(Action action) {
        this.actions.put(new Integer(action.getCode()), action);
    }

    public Action get(int i) {
        Action action = (Action) this.actions.get(new Integer(i));
        return action == null ? this.defaultAction : action;
    }

    public boolean exists(int i) {
        return this.actions.get(new Integer(i)) != null;
    }
}
