package com.app.office.java.awt.geom;

final class ChainEnd {
    int etag;
    CurveLink head;
    ChainEnd partner;
    CurveLink tail;

    public ChainEnd(CurveLink curveLink, ChainEnd chainEnd) {
        this.head = curveLink;
        this.tail = curveLink;
        this.partner = chainEnd;
        this.etag = curveLink.getEdgeTag();
    }

    public CurveLink getChain() {
        return this.head;
    }

    public void setOtherEnd(ChainEnd chainEnd) {
        this.partner = chainEnd;
    }

    public ChainEnd getPartner() {
        return this.partner;
    }

    public CurveLink linkTo(ChainEnd chainEnd) {
        int i;
        ChainEnd chainEnd2;
        ChainEnd chainEnd3;
        int i2 = this.etag;
        if (i2 == 0 || (i = chainEnd.etag) == 0) {
            throw new InternalError("ChainEnd linked more than once!");
        } else if (i2 != i) {
            if (i2 == 1) {
                chainEnd3 = this;
                chainEnd2 = chainEnd;
            } else {
                chainEnd2 = this;
                chainEnd3 = chainEnd;
            }
            this.etag = 0;
            chainEnd.etag = 0;
            chainEnd3.tail.setNext(chainEnd2.head);
            chainEnd3.tail = chainEnd2.tail;
            if (this.partner == chainEnd) {
                return chainEnd3.head;
            }
            ChainEnd chainEnd4 = chainEnd2.partner;
            ChainEnd chainEnd5 = chainEnd3.partner;
            chainEnd4.partner = chainEnd5;
            chainEnd5.partner = chainEnd4;
            if (chainEnd3.head.getYTop() < chainEnd4.head.getYTop()) {
                chainEnd3.tail.setNext(chainEnd4.head);
                chainEnd4.head = chainEnd3.head;
                return null;
            }
            chainEnd5.tail.setNext(chainEnd3.head);
            chainEnd5.tail = chainEnd3.tail;
            return null;
        } else {
            throw new InternalError("Linking chains of the same type!");
        }
    }

    public void addLink(CurveLink curveLink) {
        if (this.etag == 1) {
            this.tail.setNext(curveLink);
            this.tail = curveLink;
            return;
        }
        curveLink.setNext(this.head);
        this.head = curveLink;
    }

    public double getX() {
        if (this.etag == 1) {
            return this.tail.getXBot();
        }
        return this.head.getXBot();
    }
}
