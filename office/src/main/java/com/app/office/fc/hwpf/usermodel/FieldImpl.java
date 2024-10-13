package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.PlexOfField;
import com.app.office.fc.util.Internal;

@Internal
class FieldImpl implements Field {
    private PlexOfField endPlex;
    private PlexOfField separatorPlex;
    private PlexOfField startPlex;

    public FieldImpl(PlexOfField plexOfField, PlexOfField plexOfField2, PlexOfField plexOfField3) {
        if (plexOfField == null) {
            throw new IllegalArgumentException("startPlex == null");
        } else if (plexOfField3 == null) {
            throw new IllegalArgumentException("endPlex == null");
        } else if (plexOfField.getFld().getBoundaryType() != 19) {
            throw new IllegalArgumentException("startPlex (" + plexOfField + ") is not type of FIELD_BEGIN");
        } else if (plexOfField2 != null && plexOfField2.getFld().getBoundaryType() != 20) {
            throw new IllegalArgumentException("separatorPlex" + plexOfField2 + ") is not type of FIELD_SEPARATOR");
        } else if (plexOfField3.getFld().getBoundaryType() == 21) {
            this.startPlex = plexOfField;
            this.separatorPlex = plexOfField2;
            this.endPlex = plexOfField3;
        } else {
            throw new IllegalArgumentException("endPlex (" + plexOfField3 + ") is not type of FIELD_END");
        }
    }

    public Range firstSubrange(Range range) {
        if (hasSeparator()) {
            if (getMarkStartOffset() + 1 == getMarkSeparatorOffset()) {
                return null;
            }
            return new Range(getMarkStartOffset() + 1, getMarkSeparatorOffset(), range) {
                public String toString() {
                    return "FieldSubrange1 (" + super.toString() + ")";
                }
            };
        } else if (getMarkStartOffset() + 1 == getMarkEndOffset()) {
            return null;
        } else {
            return new Range(getMarkStartOffset() + 1, getMarkEndOffset(), range) {
                public String toString() {
                    return "FieldSubrange1 (" + super.toString() + ")";
                }
            };
        }
    }

    public int getFieldEndOffset() {
        return this.endPlex.getFcStart() + 1;
    }

    public int getFieldStartOffset() {
        return this.startPlex.getFcStart();
    }

    public CharacterRun getMarkEndCharacterRun(Range range) {
        return new Range(getMarkEndOffset(), getMarkEndOffset() + 1, range).getCharacterRun(0);
    }

    public int getMarkEndOffset() {
        return this.endPlex.getFcStart();
    }

    public CharacterRun getMarkSeparatorCharacterRun(Range range) {
        if (!hasSeparator()) {
            return null;
        }
        return new Range(getMarkSeparatorOffset(), getMarkSeparatorOffset() + 1, range).getCharacterRun(0);
    }

    public int getMarkSeparatorOffset() {
        return this.separatorPlex.getFcStart();
    }

    public CharacterRun getMarkStartCharacterRun(Range range) {
        return new Range(getMarkStartOffset(), getMarkStartOffset() + 1, range).getCharacterRun(0);
    }

    public int getMarkStartOffset() {
        return this.startPlex.getFcStart();
    }

    public int getType() {
        return this.startPlex.getFld().getFieldType();
    }

    public boolean hasSeparator() {
        return this.separatorPlex != null;
    }

    public boolean isHasSep() {
        return this.endPlex.getFld().isFHasSep();
    }

    public boolean isLocked() {
        return this.endPlex.getFld().isFLocked();
    }

    public boolean isNested() {
        return this.endPlex.getFld().isFNested();
    }

    public boolean isPrivateResult() {
        return this.endPlex.getFld().isFPrivateResult();
    }

    public boolean isResultDirty() {
        return this.endPlex.getFld().isFResultDirty();
    }

    public boolean isResultEdited() {
        return this.endPlex.getFld().isFResultEdited();
    }

    public boolean isZombieEmbed() {
        return this.endPlex.getFld().isFZombieEmbed();
    }

    public Range secondSubrange(Range range) {
        if (!hasSeparator() || getMarkSeparatorOffset() + 1 == getMarkEndOffset()) {
            return null;
        }
        return new Range(getMarkSeparatorOffset() + 1, getMarkEndOffset(), range) {
            public String toString() {
                return "FieldSubrange2 (" + super.toString() + ")";
            }
        };
    }

    public String toString() {
        return "Field [" + getFieldStartOffset() + "; " + getFieldEndOffset() + "] (type: 0x" + Integer.toHexString(getType()) + " = " + getType() + " )";
    }
}
