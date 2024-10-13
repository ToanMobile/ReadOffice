package com.app.office.pg.model;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.SmartArt;
import com.app.office.common.shape.TableCell;
import com.app.office.common.shape.TableShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.ShapeAnimation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PGSlide {
    public static final byte Slide_Layout = 1;
    public static final byte Slide_Master = 0;
    public static final byte Slide_Normal = 2;
    private BackgroundAndFill bgFill;
    private int geometryType;
    private Map<Integer, List<Integer>> grpShapeLst;
    private boolean hasTable;
    private boolean hasTransition;
    private int[] masterIndexs;
    private PGNotes notes;
    private List<ShapeAnimation> shapeAnimLst;
    private int shapeCountForFind;
    private List<IShape> shapes;
    private List<IShape> shapesForFind;
    private boolean showMasterHeadersFooters;
    private int slideNo;
    private int slideType;
    private Map<String, SmartArt> smartArtList;

    public PGSlide() {
        this.shapeCountForFind = -1;
        this.masterIndexs = new int[]{-1, -1};
        this.shapes = new ArrayList();
        this.showMasterHeadersFooters = true;
        this.geometryType = -1;
    }

    public PGSlide(int i, PGNotes pGNotes) {
        this.shapeCountForFind = -1;
        this.masterIndexs = new int[]{-1, -1};
        this.slideNo = i;
        this.notes = pGNotes;
        this.shapes = new ArrayList();
        this.showMasterHeadersFooters = true;
        this.geometryType = -1;
    }

    public void setSlideNo(int i) {
        this.slideNo = i;
    }

    public int getSlideNo() {
        return this.slideNo;
    }

    public int getSlideType() {
        return this.slideType;
    }

    public void setSlideType(int i) {
        this.slideType = i;
    }

    public void appendShapes(IShape iShape) {
        if (iShape != null) {
            if (!this.hasTable) {
                this.hasTable = iShape.getType() == 6;
            }
            this.shapes.add(iShape);
        }
    }

    public IShape[] getShapes() {
        List<IShape> list = this.shapes;
        return (IShape[]) list.toArray(new IShape[list.size()]);
    }

    public int getShapeCount() {
        return this.shapes.size();
    }

    public int getShapeCountForFind() {
        if (!this.hasTable) {
            return getShapeCount();
        }
        int i = this.shapeCountForFind;
        if (i > 0) {
            return i;
        }
        this.shapesForFind = new ArrayList();
        int i2 = 0;
        for (IShape next : this.shapes) {
            if (next.getType() == 6) {
                int i3 = 0;
                while (true) {
                    TableShape tableShape = (TableShape) next;
                    if (i3 >= tableShape.getCellCount()) {
                        break;
                    }
                    TableCell cell = tableShape.getCell(i3);
                    if (!(cell == null || cell.getText() == null)) {
                        this.shapesForFind.add(cell.getText());
                        i2++;
                    }
                    i3++;
                }
            } else {
                this.shapesForFind.add(next);
                i2++;
            }
        }
        this.shapeCountForFind = i2;
        return i2;
    }

    public IShape getShape(int i) {
        if (i < 0 || i >= this.shapes.size()) {
            return null;
        }
        return this.shapes.get(i);
    }

    public IShape getShapeForFind(int i) {
        if (!this.hasTable) {
            return getShape(i);
        }
        if (i < 0 || i >= this.shapesForFind.size()) {
            return null;
        }
        return this.shapesForFind.get(i);
    }

    public IShape getShape(float f, float f2) {
        for (IShape next : this.shapes) {
            Rectangle bounds = next.getBounds();
            if (next.getType() == 6) {
                TableShape tableShape = (TableShape) next;
                int cellCount = tableShape.getCellCount();
                for (int i = 0; i < cellCount; i++) {
                    TableCell cell = tableShape.getCell(i);
                    if (cell != null && cell.getBounds().contains(f, f2)) {
                        return cell.getText();
                    }
                }
                continue;
            } else if (bounds.contains((double) f, (double) f2)) {
                return next;
            }
        }
        return null;
    }

    public void setNotes(PGNotes pGNotes) {
        this.notes = pGNotes;
    }

    public PGNotes getNotes() {
        return this.notes;
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public void setMasterSlideIndex(int i) {
        this.masterIndexs[0] = i;
    }

    public void setLayoutSlideIndex(int i) {
        this.masterIndexs[1] = i;
    }

    public int[] getMasterIndexs() {
        return this.masterIndexs;
    }

    public IShape getShape(int i, int i2) {
        for (IShape next : this.shapes) {
            Rectangle bounds = next.getBounds();
            if (next.getType() == 6) {
                TableShape tableShape = (TableShape) next;
                int cellCount = tableShape.getCellCount();
                for (int i3 = 0; i3 < cellCount; i3++) {
                    TableCell cell = tableShape.getCell(i3);
                    if (cell != null && cell.getBounds().contains((float) i, (float) i2)) {
                        return cell.getText();
                    }
                }
                continue;
            } else if (bounds.contains(i, i2)) {
                return next;
            }
        }
        return null;
    }

    public IShape getTextboxShape(int i, int i2) {
        for (int size = this.shapes.size() - 1; size >= 0; size--) {
            IShape iShape = this.shapes.get(size);
            Rectangle bounds = iShape.getBounds();
            if (iShape.getType() == 6) {
                TableShape tableShape = (TableShape) iShape;
                int cellCount = tableShape.getCellCount();
                for (int i3 = 0; i3 < cellCount; i3++) {
                    TableCell cell = tableShape.getCell(i3);
                    if (cell != null && cell.getBounds().contains((float) i, (float) i2)) {
                        return cell.getText();
                    }
                }
                continue;
            } else if (bounds.contains(i, i2) && iShape.getType() == 1) {
                return iShape;
            }
        }
        return null;
    }

    public void setTransition(boolean z) {
        this.hasTransition = z;
    }

    public boolean hasTransition() {
        return this.hasTransition;
    }

    public void addShapeAnimation(ShapeAnimation shapeAnimation) {
        if (this.shapeAnimLst == null) {
            this.shapeAnimLst = new ArrayList();
        }
        if (shapeAnimation != null) {
            this.shapeAnimLst.add(shapeAnimation);
        }
    }

    public List<ShapeAnimation> getSlideShowAnimation() {
        return this.shapeAnimLst;
    }

    public void addGroupShape(int i, List<Integer> list) {
        if (this.grpShapeLst == null) {
            this.grpShapeLst = new HashMap();
        }
        int size = list.size();
        Integer[] numArr = new Integer[size];
        list.toArray(numArr);
        for (int i2 = 0; i2 < size; i2++) {
            Integer num = numArr[i2];
            if (this.grpShapeLst.containsKey(num)) {
                list.remove(num);
                list.addAll(this.grpShapeLst.remove(num));
            }
        }
        this.grpShapeLst.put(Integer.valueOf(i), list);
    }

    public Map<Integer, List<Integer>> getGroupShape() {
        return this.grpShapeLst;
    }

    public void addSmartArt(String str, SmartArt smartArt) {
        if (this.smartArtList == null) {
            this.smartArtList = new HashMap();
        }
        this.smartArtList.put(str, smartArt);
    }

    public SmartArt getSmartArt(String str) {
        Map<String, SmartArt> map;
        if (str == null || (map = this.smartArtList) == null) {
            return null;
        }
        return map.remove(str);
    }

    public IShape getTextboxByPlaceHolderID(int i) {
        int size = this.shapes.size();
        for (int i2 = 0; i2 < size; i2++) {
            IShape iShape = this.shapes.get(i2);
            if (iShape.getType() == 1 && iShape.getPlaceHolderID() == i) {
                return iShape;
            }
        }
        return null;
    }

    public boolean isShowMasterHeadersFooter() {
        return this.showMasterHeadersFooters;
    }

    public void setShowMasterHeadersFooters(boolean z) {
        this.showMasterHeadersFooters = z;
    }

    public int getGeometryType() {
        return this.geometryType;
    }

    public void setGeometryType(int i) {
        this.geometryType = i;
    }

    public void dispose() {
        PGNotes pGNotes = this.notes;
        if (pGNotes != null) {
            pGNotes.dispose();
            this.notes = null;
        }
        List<IShape> list = this.shapesForFind;
        if (list != null) {
            list.clear();
            this.shapesForFind = null;
        }
        List<IShape> list2 = this.shapes;
        if (list2 != null) {
            for (IShape dispose : list2) {
                dispose.dispose();
            }
            this.shapes.clear();
            this.shapes = null;
        }
        BackgroundAndFill backgroundAndFill = this.bgFill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
            this.bgFill = null;
        }
        List<ShapeAnimation> list3 = this.shapeAnimLst;
        if (list3 != null) {
            list3.clear();
            this.shapeAnimLst = null;
        }
    }
}
