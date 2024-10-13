package com.app.office.java.awt;

public class Rectanglef {
    private float height;
    private float width;
    private float x;
    private float y;

    public Rectanglef() {
    }

    public Rectanglef(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void setLocation(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void setSize(float f, float f2) {
        this.width = f;
        this.height = f2;
    }

    public void setBounds(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void translate(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public boolean contains(float f, float f2) {
        float f3 = this.width;
        float f4 = this.height;
        if (f3 < 0.0f || f4 < 0.0f) {
            return false;
        }
        float f5 = this.x;
        if (f < f5) {
            return false;
        }
        float f6 = this.y;
        if (f2 < f6) {
            return false;
        }
        float f7 = f3 + f5;
        float f8 = f4 + f6;
        if (f7 >= f5 && f7 <= f) {
            return false;
        }
        if (f8 < f6 || f8 > f2) {
            return true;
        }
        return false;
    }

    public boolean contains(float f, float f2, float f3, float f4) {
        float f5 = this.width;
        float f6 = this.height;
        if (f5 >= 0.0f && f3 >= 0.0f && f6 >= 0.0f && f4 >= 0.0f) {
            float f7 = this.x;
            if (f >= f7) {
                float f8 = this.y;
                if (f2 >= f8) {
                    float f9 = f5 + f7;
                    float f10 = f3 + f;
                    if (f10 <= f) {
                        if (f9 >= f7 || f10 > f9) {
                            return false;
                        }
                    } else if (f9 >= f7 && f10 > f9) {
                        return false;
                    }
                    float f11 = f6 + f8;
                    float f12 = f4 + f2;
                    if (f12 <= f2) {
                        if (f11 >= f8 || f12 > f11) {
                            return false;
                        }
                        return true;
                    } else if (f11 < f8 || f12 <= f11) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void add(float f, float f2) {
        float min = Math.min(this.x, f);
        float max = Math.max(this.x + this.width, f);
        float min2 = Math.min(this.y, f2);
        float max2 = Math.max(this.y + this.height, f2);
        this.x = min;
        this.y = min2;
        this.width = max - min;
        this.height = max2 - min2;
    }

    public void grow(float f, float f2) {
        this.x -= f;
        this.y -= f2;
        this.width += f * 2.0f;
        this.height += f2 * 2.0f;
    }

    public boolean isEmpty() {
        return this.width <= 0.0f || this.height <= 0.0f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Rectanglef)) {
            return super.equals(obj);
        }
        Rectanglef rectanglef = (Rectanglef) obj;
        return this.x == rectanglef.x && this.y == rectanglef.y && this.width == rectanglef.width && this.height == rectanglef.height;
    }

    public String toString() {
        return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + "]";
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }
}
