package com.app.office.objectpool;

import java.util.Vector;

public class TokenManage {
    private static final int TOKEN_SIZE = 10;
    public static TokenManage kit = new TokenManage();
    public Vector<ParaToken> paraTokens = new Vector<>(10);

    public static TokenManage instance() {
        return kit;
    }

    public synchronized ParaToken allocToken(IMemObj iMemObj) {
        ParaToken paraToken;
        paraToken = null;
        if (this.paraTokens.size() >= 10) {
            int i = 0;
            while (true) {
                if (i >= 10) {
                    break;
                } else if (this.paraTokens.get(i).isFree()) {
                    paraToken = this.paraTokens.remove(i);
                    break;
                } else {
                    i++;
                }
            }
            this.paraTokens.add(paraToken);
        } else {
            paraToken = new ParaToken(iMemObj);
            this.paraTokens.add(paraToken);
        }
        return paraToken;
    }
}
