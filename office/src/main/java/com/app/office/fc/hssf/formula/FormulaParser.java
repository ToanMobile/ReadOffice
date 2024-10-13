package com.app.office.fc.hssf.formula;

import com.app.office.constant.fc.ErrorConstant;
import com.app.office.fc.hssf.formula.function.FunctionMetadata;
import com.app.office.fc.hssf.formula.function.FunctionMetadataRegistry;
import com.app.office.fc.hssf.formula.ptg.AbstractFunctionPtg;
import com.app.office.fc.hssf.formula.ptg.AddPtg;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.ArrayPtg;
import com.app.office.fc.hssf.formula.ptg.AttrPtg;
import com.app.office.fc.hssf.formula.ptg.BoolPtg;
import com.app.office.fc.hssf.formula.ptg.ConcatPtg;
import com.app.office.fc.hssf.formula.ptg.DividePtg;
import com.app.office.fc.hssf.formula.ptg.EqualPtg;
import com.app.office.fc.hssf.formula.ptg.ErrPtg;
import com.app.office.fc.hssf.formula.ptg.FuncPtg;
import com.app.office.fc.hssf.formula.ptg.FuncVarPtg;
import com.app.office.fc.hssf.formula.ptg.GreaterEqualPtg;
import com.app.office.fc.hssf.formula.ptg.GreaterThanPtg;
import com.app.office.fc.hssf.formula.ptg.IntPtg;
import com.app.office.fc.hssf.formula.ptg.LessEqualPtg;
import com.app.office.fc.hssf.formula.ptg.LessThanPtg;
import com.app.office.fc.hssf.formula.ptg.MemAreaPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.MultiplyPtg;
import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.NotEqualPtg;
import com.app.office.fc.hssf.formula.ptg.NumberPtg;
import com.app.office.fc.hssf.formula.ptg.OperandPtg;
import com.app.office.fc.hssf.formula.ptg.OperationPtg;
import com.app.office.fc.hssf.formula.ptg.ParenthesisPtg;
import com.app.office.fc.hssf.formula.ptg.PercentPtg;
import com.app.office.fc.hssf.formula.ptg.PowerPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.RangePtg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtg;
import com.app.office.fc.hssf.formula.ptg.StringPtg;
import com.app.office.fc.hssf.formula.ptg.SubtractPtg;
import com.app.office.fc.hssf.formula.ptg.UnaryMinusPtg;
import com.app.office.fc.hssf.formula.ptg.UnaryPlusPtg;
import com.app.office.fc.hssf.formula.ptg.UnionPtg;
import com.app.office.fc.hssf.formula.ptg.ValueOperatorPtg;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.util.AreaReference;
import com.app.office.fc.ss.util.CellReference;
import java.util.ArrayList;
import java.util.regex.Pattern;
import kotlin.text.Typography;

public final class FormulaParser {
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("(\\$?[A-Za-z]+)?(\\$?[0-9]+)?");
    private static char TAB = '\t';
    private FormulaParsingWorkbook _book;
    private final int _formulaLength;
    private final String _formulaString;
    private int _pointer = 0;
    private ParseNode _rootNode;
    private int _sheetIndex;
    private SpreadsheetVersion _ssVersion;
    private char look;

    private static boolean isArgumentDelimiter(char c) {
        return c == ',' || c == ')';
    }

    private static final class Identifier {
        private final boolean _isQuoted;
        private final String _name;

        public Identifier(String str, boolean z) {
            this._name = str;
            this._isQuoted = z;
        }

        public String getName() {
            return this._name;
        }

        public boolean isQuoted() {
            return this._isQuoted;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            if (this._isQuoted) {
                stringBuffer.append("'");
                stringBuffer.append(this._name);
                stringBuffer.append("'");
            } else {
                stringBuffer.append(this._name);
            }
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class SheetIdentifier {
        private final String _bookName;
        private final Identifier _sheetIdentifier;

        public SheetIdentifier(String str, Identifier identifier) {
            this._bookName = str;
            this._sheetIdentifier = identifier;
        }

        public String getBookName() {
            return this._bookName;
        }

        public Identifier getSheetIdentifier() {
            return this._sheetIdentifier;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            if (this._bookName != null) {
                stringBuffer.append(" [");
                stringBuffer.append(this._sheetIdentifier.getName());
                stringBuffer.append("]");
            }
            if (this._sheetIdentifier.isQuoted()) {
                stringBuffer.append("'");
                stringBuffer.append(this._sheetIdentifier.getName());
                stringBuffer.append("'");
            } else {
                stringBuffer.append(this._sheetIdentifier.getName());
            }
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private FormulaParser(String str, FormulaParsingWorkbook formulaParsingWorkbook, int i) {
        this._formulaString = str;
        this._book = formulaParsingWorkbook;
        this._ssVersion = formulaParsingWorkbook == null ? SpreadsheetVersion.EXCEL97 : formulaParsingWorkbook.getSpreadsheetVersion();
        this._formulaLength = str.length();
        this._sheetIndex = i;
    }

    public static Ptg[] parse(String str, FormulaParsingWorkbook formulaParsingWorkbook, int i, int i2) {
        FormulaParser formulaParser = new FormulaParser(str, formulaParsingWorkbook, i2);
        formulaParser.parse();
        return formulaParser.getRPNPtg(i);
    }

    private void GetChar() {
        int i = this._pointer;
        int i2 = this._formulaLength;
        if (i <= i2) {
            if (i < i2) {
                this.look = this._formulaString.charAt(i);
            } else {
                this.look = 0;
            }
            this._pointer++;
            return;
        }
        throw new RuntimeException("too far");
    }

    private void resetPointer(int i) {
        this._pointer = i;
        if (i <= this._formulaLength) {
            this.look = this._formulaString.charAt(i - 1);
        } else {
            this.look = 0;
        }
    }

    private RuntimeException expected(String str) {
        String str2;
        if (this.look != '=' || this._formulaString.substring(0, this._pointer - 1).trim().length() >= 1) {
            str2 = "Parse error near char " + (this._pointer - 1) + " '" + this.look + "' in specified formula '" + this._formulaString + "'. Expected " + str;
        } else {
            str2 = "The specified formula '" + this._formulaString + "' starts with an equals sign which is not allowed.";
        }
        return new FormulaParseException(str2);
    }

    private static boolean IsAlpha(char c) {
        return Character.isLetter(c) || c == '$' || c == '_';
    }

    private static boolean IsDigit(char c) {
        return Character.isDigit(c);
    }

    private static boolean IsWhite(char c) {
        return c == ' ' || c == TAB;
    }

    private void SkipWhite() {
        while (IsWhite(this.look)) {
            GetChar();
        }
    }

    private void Match(char c) {
        if (this.look == c) {
            GetChar();
            return;
        }
        throw expected("'" + c + "'");
    }

    private String GetNum() {
        StringBuffer stringBuffer = new StringBuffer();
        while (IsDigit(this.look)) {
            stringBuffer.append(this.look);
            GetChar();
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        return stringBuffer.toString();
    }

    private ParseNode parseRangeExpression() {
        ParseNode parseRangeable = parseRangeable();
        boolean z = false;
        while (this.look == ':') {
            int i = this._pointer;
            GetChar();
            ParseNode parseRangeable2 = parseRangeable();
            checkValidRangeOperand("LHS", i, parseRangeable);
            checkValidRangeOperand("RHS", i, parseRangeable2);
            parseRangeable = new ParseNode((Ptg) RangePtg.instance, new ParseNode[]{parseRangeable, parseRangeable2});
            z = true;
        }
        return z ? augmentWithMemPtg(parseRangeable) : parseRangeable;
    }

    private static ParseNode augmentWithMemPtg(ParseNode parseNode) {
        Ptg ptg;
        if (needsMemFunc(parseNode)) {
            ptg = new MemFuncPtg(parseNode.getEncodedSize());
        } else {
            ptg = new MemAreaPtg(parseNode.getEncodedSize());
        }
        return new ParseNode(ptg, parseNode);
    }

    private static boolean needsMemFunc(ParseNode parseNode) {
        Ptg token = parseNode.getToken();
        if ((token instanceof AbstractFunctionPtg) || (token instanceof ExternSheetReferenceToken) || (token instanceof NamePtg) || (token instanceof NameXPtg)) {
            return true;
        }
        boolean z = token instanceof OperationPtg;
        if (z || (token instanceof ParenthesisPtg)) {
            for (ParseNode needsMemFunc : parseNode.getChildren()) {
                if (needsMemFunc(needsMemFunc)) {
                    return true;
                }
            }
            return false;
        } else if (!(token instanceof OperandPtg) && z) {
            return true;
        } else {
            return false;
        }
    }

    private static void checkValidRangeOperand(String str, int i, ParseNode parseNode) {
        if (!isValidRangeOperand(parseNode)) {
            throw new FormulaParseException("The " + str + " of the range operator ':' at position " + i + " is not a proper reference.");
        }
    }

    private static boolean isValidRangeOperand(ParseNode parseNode) {
        Ptg token = parseNode.getToken();
        if (token instanceof OperandPtg) {
            return true;
        }
        if (token instanceof AbstractFunctionPtg) {
            if (((AbstractFunctionPtg) token).getDefaultOperandClass() == 0) {
                return true;
            }
            return false;
        } else if (token instanceof ValueOperatorPtg) {
            return false;
        } else {
            if (token instanceof OperationPtg) {
                return true;
            }
            if (token instanceof ParenthesisPtg) {
                return isValidRangeOperand(parseNode.getChildren()[0]);
            }
            if (token == ErrPtg.REF_INVALID) {
                return true;
            }
            return false;
        }
    }

    private ParseNode parseRangeable() {
        char c;
        String str;
        SkipWhite();
        int i = this._pointer;
        SheetIdentifier parseSheetName = parseSheetName();
        if (parseSheetName == null) {
            resetPointer(i);
        } else {
            SkipWhite();
            i = this._pointer;
        }
        SimpleRangePart parseSimpleRangePart = parseSimpleRangePart();
        if (parseSimpleRangePart != null) {
            boolean IsWhite = IsWhite(this.look);
            if (IsWhite) {
                SkipWhite();
            }
            char c2 = this.look;
            SimpleRangePart simpleRangePart = null;
            if (c2 == ':') {
                int i2 = this._pointer;
                GetChar();
                SkipWhite();
                SimpleRangePart parseSimpleRangePart2 = parseSimpleRangePart();
                if (parseSimpleRangePart2 == null || parseSimpleRangePart.isCompatibleForArea(parseSimpleRangePart2)) {
                    simpleRangePart = parseSimpleRangePart2;
                }
                if (simpleRangePart != null) {
                    return createAreaRefParseNode(parseSheetName, parseSimpleRangePart, simpleRangePart);
                }
                resetPointer(i2);
                if (parseSimpleRangePart.isCell()) {
                    return createAreaRefParseNode(parseSheetName, parseSimpleRangePart, simpleRangePart);
                }
                if (parseSheetName == null) {
                    str = "";
                } else {
                    str = "'" + parseSheetName.getSheetIdentifier().getName() + '!';
                }
                throw new FormulaParseException(str + parseSimpleRangePart.getRep() + "' is not a proper reference.");
            } else if (c2 == '.') {
                GetChar();
                int i3 = 1;
                while (true) {
                    c = this.look;
                    if (c != '.') {
                        break;
                    }
                    i3++;
                    GetChar();
                }
                boolean IsWhite2 = IsWhite(c);
                SkipWhite();
                SimpleRangePart parseSimpleRangePart3 = parseSimpleRangePart();
                String substring = this._formulaString.substring(i - 1, this._pointer - 1);
                if (parseSimpleRangePart3 == null) {
                    if (parseSheetName == null) {
                        return parseNonRange(i);
                    }
                    throw new FormulaParseException("Complete area reference expected after sheet name at index " + this._pointer + ".");
                } else if (IsWhite || IsWhite2) {
                    if (!parseSimpleRangePart.isRowOrColumn() && !parseSimpleRangePart3.isRowOrColumn()) {
                        return createAreaRefParseNode(parseSheetName, parseSimpleRangePart, parseSimpleRangePart3);
                    }
                    throw new FormulaParseException("Dotted range (full row or column) expression '" + substring + "' must not contain whitespace.");
                } else if (i3 == 1 && parseSimpleRangePart.isRow() && parseSimpleRangePart3.isRow()) {
                    return parseNonRange(i);
                } else {
                    if ((!parseSimpleRangePart.isRowOrColumn() && !parseSimpleRangePart3.isRowOrColumn()) || i3 == 2) {
                        return createAreaRefParseNode(parseSheetName, parseSimpleRangePart, parseSimpleRangePart3);
                    }
                    throw new FormulaParseException("Dotted range (full row or column) expression '" + substring + "' must have exactly 2 dots.");
                }
            } else if (parseSimpleRangePart.isCell() && isValidCellReference(parseSimpleRangePart.getRep())) {
                return createAreaRefParseNode(parseSheetName, parseSimpleRangePart, (SimpleRangePart) null);
            } else {
                if (parseSheetName == null) {
                    return parseNonRange(i);
                }
                throw new FormulaParseException("Second part of cell reference expected after sheet name at index " + this._pointer + ".");
            }
        } else if (parseSheetName == null) {
            return parseNonRange(i);
        } else {
            if (this.look == '#') {
                return new ParseNode(ErrPtg.valueOf(parseErrorLiteral()));
            }
            throw new FormulaParseException("Cell reference expected after sheet name at index " + this._pointer + ".");
        }
    }

    private ParseNode parseNonRange(int i) {
        resetPointer(i);
        if (Character.isDigit(this.look)) {
            return new ParseNode(parseNumber());
        }
        if (this.look == '\"') {
            return new ParseNode(new StringPtg(parseStringLiteral()));
        }
        StringBuilder sb = new StringBuilder();
        if (Character.isLetter(this.look) || this.look == '_') {
            while (isValidDefinedNameChar(this.look)) {
                sb.append(this.look);
                GetChar();
            }
            SkipWhite();
            String sb2 = sb.toString();
            if (this.look == '(') {
                return function(sb2);
            }
            if (sb2.equalsIgnoreCase("TRUE") || sb2.equalsIgnoreCase("FALSE")) {
                return new ParseNode(BoolPtg.valueOf(sb2.equalsIgnoreCase("TRUE")));
            }
            FormulaParsingWorkbook formulaParsingWorkbook = this._book;
            if (formulaParsingWorkbook != null) {
                EvaluationName name = formulaParsingWorkbook.getName(sb2, this._sheetIndex);
                if (name == null) {
                    throw new FormulaParseException("Specified named range '" + sb2 + "' does not exist in the current workbook.");
                } else if (name.isRange()) {
                    return new ParseNode(name.createPtg());
                } else {
                    throw new FormulaParseException("Specified name '" + sb2 + "' is not a range as expected.");
                }
            } else {
                throw new IllegalStateException("Need book to evaluate name '" + sb2 + "'");
            }
        } else {
            throw expected("number, string, or defined name");
        }
    }

    private static boolean isValidDefinedNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '?' || c == '\\' || c == '_';
    }

    private ParseNode createAreaRefParseNode(SheetIdentifier sheetIdentifier, SimpleRangePart simpleRangePart, SimpleRangePart simpleRangePart2) throws FormulaParseException {
        int i;
        Ptg ptg;
        if (sheetIdentifier == null) {
            i = Integer.MIN_VALUE;
        } else {
            String name = sheetIdentifier.getSheetIdentifier().getName();
            if (sheetIdentifier.getBookName() == null) {
                i = this._book.getExternalSheetIndex(name);
            } else {
                i = this._book.getExternalSheetIndex(sheetIdentifier.getBookName(), name);
            }
        }
        if (simpleRangePart2 == null) {
            CellReference cellReference = simpleRangePart.getCellReference();
            if (sheetIdentifier == null) {
                ptg = new RefPtg(cellReference);
            } else {
                ptg = new Ref3DPtg(cellReference, i);
            }
        } else {
            AreaReference createAreaRef = createAreaRef(simpleRangePart, simpleRangePart2);
            if (sheetIdentifier == null) {
                ptg = new AreaPtg(createAreaRef);
            } else {
                ptg = new Area3DPtg(createAreaRef, i);
            }
        }
        return new ParseNode(ptg);
    }

    private static AreaReference createAreaRef(SimpleRangePart simpleRangePart, SimpleRangePart simpleRangePart2) {
        if (!simpleRangePart.isCompatibleForArea(simpleRangePart2)) {
            throw new FormulaParseException("has incompatible parts: '" + simpleRangePart.getRep() + "' and '" + simpleRangePart2.getRep() + "'.");
        } else if (simpleRangePart.isRow()) {
            return AreaReference.getWholeRow(simpleRangePart.getRep(), simpleRangePart2.getRep());
        } else {
            if (simpleRangePart.isColumn()) {
                return AreaReference.getWholeColumn(simpleRangePart.getRep(), simpleRangePart2.getRep());
            }
            return new AreaReference(simpleRangePart.getCellReference(), simpleRangePart2.getCellReference());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0073, code lost:
        if (r5 <= 65536) goto L_0x0076;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.FormulaParser.SimpleRangePart parseSimpleRangePart() {
        /*
            r8 = this;
            int r0 = r8._pointer
            r1 = 1
            int r0 = r0 - r1
            r2 = 0
            r3 = 0
        L_0x0006:
            int r4 = r8._formulaLength
            if (r0 >= r4) goto L_0x002b
            java.lang.String r4 = r8._formulaString
            char r4 = r4.charAt(r0)
            boolean r5 = java.lang.Character.isDigit(r4)
            if (r5 == 0) goto L_0x0018
            r2 = 1
            goto L_0x0028
        L_0x0018:
            boolean r5 = java.lang.Character.isLetter(r4)
            if (r5 == 0) goto L_0x0020
            r3 = 1
            goto L_0x0028
        L_0x0020:
            r5 = 36
            if (r4 == r5) goto L_0x0028
            r5 = 95
            if (r4 != r5) goto L_0x002b
        L_0x0028:
            int r0 = r0 + 1
            goto L_0x0006
        L_0x002b:
            int r4 = r8._pointer
            int r5 = r4 + -1
            r6 = 0
            if (r0 > r5) goto L_0x0033
            return r6
        L_0x0033:
            java.lang.String r5 = r8._formulaString
            int r4 = r4 - r1
            java.lang.String r4 = r5.substring(r4, r0)
            java.util.regex.Pattern r5 = CELL_REF_PATTERN
            java.util.regex.Matcher r5 = r5.matcher(r4)
            boolean r5 = r5.matches()
            if (r5 != 0) goto L_0x0047
            return r6
        L_0x0047:
            if (r3 == 0) goto L_0x0052
            if (r2 == 0) goto L_0x0052
            boolean r5 = r8.isValidCellReference(r4)
            if (r5 != 0) goto L_0x0076
            return r6
        L_0x0052:
            java.lang.String r5 = ""
            java.lang.String r7 = "$"
            if (r3 == 0) goto L_0x0065
            java.lang.String r5 = r4.replace(r7, r5)
            com.app.office.fc.ss.SpreadsheetVersion r7 = r8._ssVersion
            boolean r5 = com.app.office.fc.ss.util.CellReference.isColumnWithnRange(r5, r7)
            if (r5 != 0) goto L_0x0076
            return r6
        L_0x0065:
            if (r2 == 0) goto L_0x0080
            java.lang.String r5 = r4.replace(r7, r5)     // Catch:{ NumberFormatException -> 0x0080 }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x0080 }
            if (r5 < r1) goto L_0x0080
            r7 = 65536(0x10000, float:9.18355E-41)
            if (r5 <= r7) goto L_0x0076
            goto L_0x0080
        L_0x0076:
            int r0 = r0 + r1
            r8.resetPointer(r0)
            com.app.office.fc.hssf.formula.FormulaParser$SimpleRangePart r0 = new com.app.office.fc.hssf.formula.FormulaParser$SimpleRangePart
            r0.<init>(r4, r3, r2)
            return r0
        L_0x0080:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.FormulaParser.parseSimpleRangePart():com.app.office.fc.hssf.formula.FormulaParser$SimpleRangePart");
    }

    private static final class SimpleRangePart {
        private final String _rep;
        private final Type _type;

        private enum Type {
            CELL,
            ROW,
            COLUMN;

            public static Type get(boolean z, boolean z2) {
                if (z) {
                    return z2 ? CELL : COLUMN;
                }
                if (z2) {
                    return ROW;
                }
                throw new IllegalArgumentException("must have either letters or numbers");
            }
        }

        public SimpleRangePart(String str, boolean z, boolean z2) {
            this._rep = str;
            this._type = Type.get(z, z2);
        }

        public boolean isCell() {
            return this._type == Type.CELL;
        }

        public boolean isRowOrColumn() {
            return this._type != Type.CELL;
        }

        public CellReference getCellReference() {
            if (this._type == Type.CELL) {
                return new CellReference(this._rep);
            }
            throw new IllegalStateException("Not applicable to this type");
        }

        public boolean isColumn() {
            return this._type == Type.COLUMN;
        }

        public boolean isRow() {
            return this._type == Type.ROW;
        }

        public String getRep() {
            return this._rep;
        }

        public boolean isCompatibleForArea(SimpleRangePart simpleRangePart) {
            return this._type == simpleRangePart._type;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append(getClass().getName());
            sb.append(" [");
            sb.append(this._rep);
            sb.append("]");
            return sb.toString();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0039, code lost:
        if (r9.look == '\'') goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003b, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003d, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003e, code lost:
        if (r6 != false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        r2.append(r9.look);
        GetChar();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
        if (r9.look != '\'') goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004c, code lost:
        Match('\'');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        if (r9.look == '\'') goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0054, code lost:
        r4 = new com.app.office.fc.hssf.formula.FormulaParser.Identifier(r2.toString(), true);
        SkipWhite();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0062, code lost:
        if (r9.look != '!') goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0064, code lost:
        GetChar();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return new com.app.office.fc.hssf.formula.FormulaParser.SheetIdentifier(r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.FormulaParser.SheetIdentifier parseSheetName() {
        /*
            r9 = this;
            char r0 = r9.look
            r1 = 0
            r2 = 91
            if (r0 != r2) goto L_0x0024
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r9.GetChar()
        L_0x000f:
            char r2 = r9.look
            r3 = 93
            if (r2 == r3) goto L_0x001c
            r0.append(r2)
            r9.GetChar()
            goto L_0x000f
        L_0x001c:
            r9.GetChar()
            java.lang.String r0 = r0.toString()
            goto L_0x0025
        L_0x0024:
            r0 = r1
        L_0x0025:
            char r2 = r9.look
            r3 = 33
            r4 = 0
            r5 = 39
            if (r2 != r5) goto L_0x006d
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r9.Match(r5)
            char r6 = r9.look
            r7 = 1
            if (r6 != r5) goto L_0x003d
        L_0x003b:
            r6 = 1
            goto L_0x003e
        L_0x003d:
            r6 = 0
        L_0x003e:
            if (r6 != 0) goto L_0x0054
            char r8 = r9.look
            r2.append(r8)
            r9.GetChar()
            char r8 = r9.look
            if (r8 != r5) goto L_0x003e
            r9.Match(r5)
            char r6 = r9.look
            if (r6 == r5) goto L_0x003d
            goto L_0x003b
        L_0x0054:
            com.app.office.fc.hssf.formula.FormulaParser$Identifier r4 = new com.app.office.fc.hssf.formula.FormulaParser$Identifier
            java.lang.String r2 = r2.toString()
            r4.<init>(r2, r7)
            r9.SkipWhite()
            char r2 = r9.look
            if (r2 != r3) goto L_0x006c
            r9.GetChar()
            com.app.office.fc.hssf.formula.FormulaParser$SheetIdentifier r1 = new com.app.office.fc.hssf.formula.FormulaParser$SheetIdentifier
            r1.<init>(r0, r4)
        L_0x006c:
            return r1
        L_0x006d:
            r5 = 95
            if (r2 == r5) goto L_0x0079
            boolean r2 = java.lang.Character.isLetter(r2)
            if (r2 == 0) goto L_0x0078
            goto L_0x0079
        L_0x0078:
            return r1
        L_0x0079:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x007e:
            char r5 = r9.look
            boolean r5 = isUnquotedSheetNameChar(r5)
            if (r5 == 0) goto L_0x008f
            char r5 = r9.look
            r2.append(r5)
            r9.GetChar()
            goto L_0x007e
        L_0x008f:
            r9.SkipWhite()
            char r5 = r9.look
            if (r5 != r3) goto L_0x00a7
            r9.GetChar()
            com.app.office.fc.hssf.formula.FormulaParser$SheetIdentifier r1 = new com.app.office.fc.hssf.formula.FormulaParser$SheetIdentifier
            com.app.office.fc.hssf.formula.FormulaParser$Identifier r3 = new com.app.office.fc.hssf.formula.FormulaParser$Identifier
            java.lang.String r2 = r2.toString()
            r3.<init>(r2, r4)
            r1.<init>(r0, r3)
        L_0x00a7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.FormulaParser.parseSheetName():com.app.office.fc.hssf.formula.FormulaParser$SheetIdentifier");
    }

    private static boolean isUnquotedSheetNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '_';
    }

    private boolean isValidCellReference(String str) {
        boolean z = true;
        boolean z2 = CellReference.classifyCellReference(str, this._ssVersion) == CellReference.NameType.CELL;
        if (!z2) {
            return z2;
        }
        if (!(FunctionMetadataRegistry.getFunctionByName(str.toUpperCase()) != null)) {
            return z2;
        }
        int i = this._pointer;
        resetPointer(str.length() + i);
        SkipWhite();
        if (this.look == '(') {
            z = false;
        }
        resetPointer(i);
        return z;
    }

    private ParseNode function(String str) {
        Ptg ptg;
        if (!AbstractFunctionPtg.isBuiltInFunctionName(str)) {
            FormulaParsingWorkbook formulaParsingWorkbook = this._book;
            if (formulaParsingWorkbook != null) {
                EvaluationName name = formulaParsingWorkbook.getName(str, this._sheetIndex);
                if (name == null) {
                    ptg = this._book.getNameXPtg(str);
                    if (ptg == null) {
                        throw new FormulaParseException("Name '" + str + "' is completely unknown in the current workbook");
                    }
                } else if (name.isFunctionName()) {
                    ptg = name.createPtg();
                } else {
                    throw new FormulaParseException("Attempt to use name '" + str + "' as a function, but defined name in workbook does not refer to a function");
                }
            } else {
                throw new IllegalStateException("Need book to evaluate name '" + str + "'");
            }
        } else {
            ptg = null;
        }
        Match('(');
        ParseNode[] Arguments = Arguments();
        Match(')');
        return getFunction(str, ptg, Arguments);
    }

    private ParseNode getFunction(String str, Ptg ptg, ParseNode[] parseNodeArr) {
        Ptg ptg2;
        FunctionMetadata functionByName = FunctionMetadataRegistry.getFunctionByName(str.toUpperCase());
        int length = parseNodeArr.length;
        if (functionByName == null) {
            if (ptg != null) {
                int i = length + 1;
                ParseNode[] parseNodeArr2 = new ParseNode[i];
                parseNodeArr2[0] = new ParseNode(ptg);
                System.arraycopy(parseNodeArr, 0, parseNodeArr2, 1, length);
                return new ParseNode((Ptg) FuncVarPtg.create(str, i), parseNodeArr2);
            }
            throw new IllegalStateException("NamePtg must be supplied for external functions");
        } else if (ptg == null) {
            boolean z = !functionByName.hasFixedArgsLength();
            int index = functionByName.getIndex();
            if (index == 4 && parseNodeArr.length == 1) {
                return new ParseNode((Ptg) AttrPtg.getSumSingle(), parseNodeArr);
            }
            validateNumArgs(parseNodeArr.length, functionByName);
            if (z) {
                ptg2 = FuncVarPtg.create(str, length);
            } else {
                ptg2 = FuncPtg.create(index);
            }
            return new ParseNode(ptg2, parseNodeArr);
        } else {
            throw new IllegalStateException("NamePtg no applicable to internal functions");
        }
    }

    private void validateNumArgs(int i, FunctionMetadata functionMetadata) {
        int i2;
        String str;
        String str2;
        if (i < functionMetadata.getMinParams()) {
            String str3 = "Too few arguments to function '" + functionMetadata.getName() + "'. ";
            if (functionMetadata.hasFixedArgsLength()) {
                str2 = str3 + "Expected " + functionMetadata.getMinParams();
            } else {
                str2 = str3 + "At least " + functionMetadata.getMinParams() + " were expected";
            }
            throw new FormulaParseException(str2 + " but got " + i + ".");
        }
        if (functionMetadata.hasUnlimitedVarags()) {
            FormulaParsingWorkbook formulaParsingWorkbook = this._book;
            if (formulaParsingWorkbook != null) {
                i2 = formulaParsingWorkbook.getSpreadsheetVersion().getMaxFunctionArgs();
            } else {
                i2 = functionMetadata.getMaxParams();
            }
        } else {
            i2 = functionMetadata.getMaxParams();
        }
        if (i > i2) {
            String str4 = "Too many arguments to function '" + functionMetadata.getName() + "'. ";
            if (functionMetadata.hasFixedArgsLength()) {
                str = str4 + "Expected " + i2;
            } else {
                str = str4 + "At most " + i2 + " were expected";
            }
            throw new FormulaParseException(str + " but got " + i + ".");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
        if (r5.look != ')') goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
        r1 = new com.app.office.fc.hssf.formula.ParseNode[r0.size()];
        r0.toArray(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001f, code lost:
        if (r3 == false) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
        r0.add(new com.app.office.fc.hssf.formula.ParseNode(com.app.office.fc.hssf.formula.ptg.MissingArgPtg.instance));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.ParseNode[] Arguments() {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 2
            r0.<init>(r1)
            r5.SkipWhite()
            char r1 = r5.look
            r2 = 41
            if (r1 != r2) goto L_0x0012
            com.app.office.fc.hssf.formula.ParseNode[] r0 = com.app.office.fc.hssf.formula.ParseNode.EMPTY_ARRAY
            return r0
        L_0x0012:
            r1 = 1
        L_0x0013:
            r3 = 1
        L_0x0014:
            r5.SkipWhite()
            char r4 = r5.look
            boolean r4 = isArgumentDelimiter(r4)
            if (r4 == 0) goto L_0x003f
            if (r3 == 0) goto L_0x002b
            com.app.office.fc.hssf.formula.ParseNode r3 = new com.app.office.fc.hssf.formula.ParseNode
            com.app.office.fc.hssf.formula.ptg.Ptg r4 = com.app.office.fc.hssf.formula.ptg.MissingArgPtg.instance
            r3.<init>(r4)
            r0.add(r3)
        L_0x002b:
            char r3 = r5.look
            if (r3 != r2) goto L_0x0039
            int r1 = r0.size()
            com.app.office.fc.hssf.formula.ParseNode[] r1 = new com.app.office.fc.hssf.formula.ParseNode[r1]
            r0.toArray(r1)
            return r1
        L_0x0039:
            r3 = 44
            r5.Match(r3)
            goto L_0x0013
        L_0x003f:
            com.app.office.fc.hssf.formula.ParseNode r3 = r5.comparisonExpression()
            r0.add(r3)
            r3 = 0
            r5.SkipWhite()
            char r4 = r5.look
            boolean r4 = isArgumentDelimiter(r4)
            if (r4 == 0) goto L_0x0053
            goto L_0x0014
        L_0x0053:
            java.lang.String r0 = "',' or ')'"
            java.lang.RuntimeException r0 = r5.expected(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.FormulaParser.Arguments():com.app.office.fc.hssf.formula.ParseNode[]");
    }

    private ParseNode powerFactor() {
        ParseNode percentFactor = percentFactor();
        while (true) {
            SkipWhite();
            if (this.look != '^') {
                return percentFactor;
            }
            Match('^');
            percentFactor = new ParseNode(PowerPtg.instance, percentFactor, percentFactor());
        }
    }

    private ParseNode percentFactor() {
        ParseNode parseSimpleFactor = parseSimpleFactor();
        while (true) {
            SkipWhite();
            if (this.look != '%') {
                return parseSimpleFactor;
            }
            Match('%');
            parseSimpleFactor = new ParseNode((Ptg) PercentPtg.instance, parseSimpleFactor);
        }
    }

    private ParseNode parseSimpleFactor() {
        char c;
        SkipWhite();
        char c2 = this.look;
        if (c2 == '\"') {
            return new ParseNode(new StringPtg(parseStringLiteral()));
        }
        if (c2 == '#') {
            return new ParseNode(ErrPtg.valueOf(parseErrorLiteral()));
        }
        if (c2 == '(') {
            Match('(');
            ParseNode comparisonExpression = comparisonExpression();
            Match(')');
            return new ParseNode((Ptg) ParenthesisPtg.instance, comparisonExpression);
        } else if (c2 == '+') {
            Match('+');
            return parseUnary(true);
        } else if (c2 == '-') {
            Match('-');
            return parseUnary(false);
        } else if (c2 == '{') {
            Match('{');
            ParseNode parseArray = parseArray();
            Match('}');
            return parseArray;
        } else if (IsAlpha(c2) || Character.isDigit(this.look) || (c = this.look) == '\'' || c == '[') {
            return parseRangeExpression();
        } else {
            if (c == '.') {
                return new ParseNode(parseNumber());
            }
            throw expected("cell ref or constant literal");
        }
    }

    private ParseNode parseUnary(boolean z) {
        boolean z2 = IsDigit(this.look) || this.look == '.';
        ParseNode powerFactor = powerFactor();
        if (z2) {
            Ptg token = powerFactor.getToken();
            if (token instanceof NumberPtg) {
                if (z) {
                    return powerFactor;
                }
                return new ParseNode(new NumberPtg(-((NumberPtg) token).getValue()));
            } else if (token instanceof IntPtg) {
                if (z) {
                    return powerFactor;
                }
                return new ParseNode(new NumberPtg((double) (-((IntPtg) token).getValue())));
            }
        }
        return new ParseNode((Ptg) z ? UnaryPlusPtg.instance : UnaryMinusPtg.instance, powerFactor);
    }

    private ParseNode parseArray() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(parseArrayRow());
            char c = this.look;
            if (c == '}') {
                Object[][] objArr = new Object[arrayList.size()][];
                arrayList.toArray(objArr);
                checkRowLengths(objArr, objArr[0].length);
                return new ParseNode(new ArrayPtg(objArr));
            } else if (c == ';') {
                Match(';');
            } else {
                throw expected("'}' or ';'");
            }
        }
    }

    private void checkRowLengths(Object[][] objArr, int i) {
        int i2 = 0;
        while (i2 < objArr.length) {
            int length = objArr[i2].length;
            if (length == i) {
                i2++;
            } else {
                throw new FormulaParseException("Array row " + i2 + " has length " + length + " but row 0 has length " + i);
            }
        }
    }

    private Object[] parseArrayRow() {
        char c;
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(parseArrayItem());
            SkipWhite();
            c = this.look;
            if (c != ',') {
                break;
            }
            Match(',');
        }
        if (c == ';' || c == '}') {
            Object[] objArr = new Object[arrayList.size()];
            arrayList.toArray(objArr);
            return objArr;
        }
        throw expected("'}' or ','");
    }

    private Object parseArrayItem() {
        SkipWhite();
        char c = this.look;
        if (c == '\"') {
            return parseStringLiteral();
        }
        if (c == '#') {
            return ErrorConstant.valueOf(parseErrorLiteral());
        }
        if (c != '-') {
            return (c == 'F' || c == 'T' || c == 'f' || c == 't') ? parseBooleanLiteral() : convertArrayNumber(parseNumber(), true);
        }
        Match('-');
        SkipWhite();
        return convertArrayNumber(parseNumber(), false);
    }

    private Boolean parseBooleanLiteral() {
        String parseUnquotedIdentifier = parseUnquotedIdentifier();
        if ("TRUE".equalsIgnoreCase(parseUnquotedIdentifier)) {
            return Boolean.TRUE;
        }
        if ("FALSE".equalsIgnoreCase(parseUnquotedIdentifier)) {
            return Boolean.FALSE;
        }
        throw expected("'TRUE' or 'FALSE'");
    }

    private static Double convertArrayNumber(Ptg ptg, boolean z) {
        double d;
        if (ptg instanceof IntPtg) {
            d = (double) ((IntPtg) ptg).getValue();
        } else if (ptg instanceof NumberPtg) {
            d = ((NumberPtg) ptg).getValue();
        } else {
            throw new RuntimeException("Unexpected ptg (" + ptg.getClass().getName() + ")");
        }
        if (!z) {
            d = -d;
        }
        return new Double(d);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.ptg.Ptg parseNumber() {
        /*
            r6 = this;
            java.lang.String r0 = r6.GetNum()
            char r1 = r6.look
            r2 = 0
            r3 = 46
            if (r1 != r3) goto L_0x0013
            r6.GetChar()
            java.lang.String r1 = r6.GetNum()
            goto L_0x0014
        L_0x0013:
            r1 = r2
        L_0x0014:
            char r3 = r6.look
            r4 = 69
            java.lang.String r5 = "Integer"
            if (r3 != r4) goto L_0x0050
            r6.GetChar()
            char r2 = r6.look
            r3 = 43
            if (r2 != r3) goto L_0x0029
            r6.GetChar()
            goto L_0x0033
        L_0x0029:
            r3 = 45
            if (r2 != r3) goto L_0x0033
            r6.GetChar()
            java.lang.String r2 = "-"
            goto L_0x0035
        L_0x0033:
            java.lang.String r2 = ""
        L_0x0035:
            java.lang.String r3 = r6.GetNum()
            if (r3 == 0) goto L_0x004b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r3)
            java.lang.String r2 = r4.toString()
            goto L_0x0050
        L_0x004b:
            java.lang.RuntimeException r0 = r6.expected(r5)
            throw r0
        L_0x0050:
            if (r0 != 0) goto L_0x005a
            if (r1 == 0) goto L_0x0055
            goto L_0x005a
        L_0x0055:
            java.lang.RuntimeException r0 = r6.expected(r5)
            throw r0
        L_0x005a:
            com.app.office.fc.hssf.formula.ptg.Ptg r0 = getNumberPtgFromString(r0, r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.FormulaParser.parseNumber():com.app.office.fc.hssf.formula.ptg.Ptg");
    }

    private int parseErrorLiteral() {
        Match('#');
        String upperCase = parseUnquotedIdentifier().toUpperCase();
        if (upperCase != null) {
            char charAt = upperCase.charAt(0);
            if (charAt != 'D') {
                if (charAt != 'N') {
                    if (charAt != 'R') {
                        if (charAt != 'V') {
                            throw expected("#VALUE!, #REF!, #DIV/0!, #NAME?, #NUM!, #NULL! or #N/A");
                        } else if (upperCase.equals("VALUE")) {
                            Match('!');
                            return 15;
                        } else {
                            throw expected("#VALUE!");
                        }
                    } else if (upperCase.equals("REF")) {
                        Match('!');
                        return 23;
                    } else {
                        throw expected("#REF!");
                    }
                } else if (upperCase.equals("NAME")) {
                    Match('?');
                    return 29;
                } else if (upperCase.equals("NUM")) {
                    Match('!');
                    return 36;
                } else if (upperCase.equals("NULL")) {
                    Match('!');
                    return 0;
                } else if (upperCase.equals("N")) {
                    Match(PackagingURIHelper.FORWARD_SLASH_CHAR);
                    char c = this.look;
                    if (c == 'A' || c == 'a') {
                        Match(c);
                        return 42;
                    }
                    throw expected("#N/A");
                } else {
                    throw expected("#NAME?, #NUM!, #NULL! or #N/A");
                }
            } else if (upperCase.equals("DIV")) {
                Match(PackagingURIHelper.FORWARD_SLASH_CHAR);
                Match('0');
                Match('!');
                return 7;
            } else {
                throw expected("#DIV/0!");
            }
        } else {
            throw expected("remainder of error constant literal");
        }
    }

    private String parseUnquotedIdentifier() {
        if (this.look != '\'') {
            StringBuilder sb = new StringBuilder();
            while (true) {
                if (!Character.isLetterOrDigit(this.look) && this.look != '.') {
                    break;
                }
                sb.append(this.look);
                GetChar();
            }
            if (sb.length() < 1) {
                return null;
            }
            return sb.toString();
        }
        throw expected("unquoted identifier");
    }

    private static Ptg getNumberPtgFromString(String str, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str2 == null) {
            stringBuffer.append(str);
            if (str3 != null) {
                stringBuffer.append('E');
                stringBuffer.append(str3);
            }
            String stringBuffer2 = stringBuffer.toString();
            try {
                int parseInt = Integer.parseInt(stringBuffer2);
                if (IntPtg.isInRange(parseInt)) {
                    return new IntPtg(parseInt);
                }
                return new NumberPtg(stringBuffer2);
            } catch (NumberFormatException unused) {
                return new NumberPtg(stringBuffer2);
            }
        } else {
            if (str != null) {
                stringBuffer.append(str);
            }
            stringBuffer.append('.');
            stringBuffer.append(str2);
            if (str3 != null) {
                stringBuffer.append('E');
                stringBuffer.append(str3);
            }
            return new NumberPtg(stringBuffer.toString());
        }
    }

    private String parseStringLiteral() {
        Match(Typography.quote);
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (this.look == '\"') {
                GetChar();
                if (this.look != '\"') {
                    return stringBuffer.toString();
                }
            }
            stringBuffer.append(this.look);
            GetChar();
        }
    }

    private ParseNode Term() {
        ValueOperatorPtg valueOperatorPtg;
        ParseNode powerFactor = powerFactor();
        while (true) {
            SkipWhite();
            char c = this.look;
            if (c == '*') {
                Match('*');
                valueOperatorPtg = MultiplyPtg.instance;
            } else if (c != '/') {
                return powerFactor;
            } else {
                Match(PackagingURIHelper.FORWARD_SLASH_CHAR);
                valueOperatorPtg = DividePtg.instance;
            }
            powerFactor = new ParseNode(valueOperatorPtg, powerFactor, powerFactor());
        }
    }

    private ParseNode unionExpression() {
        ParseNode comparisonExpression = comparisonExpression();
        boolean z = false;
        while (true) {
            SkipWhite();
            if (this.look != ',') {
                break;
            }
            GetChar();
            z = true;
            comparisonExpression = new ParseNode(UnionPtg.instance, comparisonExpression, comparisonExpression());
        }
        if (z) {
            return augmentWithMemPtg(comparisonExpression);
        }
        return comparisonExpression;
    }

    private ParseNode comparisonExpression() {
        ParseNode concatExpression = concatExpression();
        while (true) {
            SkipWhite();
            switch (this.look) {
                case '<':
                case '=':
                case '>':
                    concatExpression = new ParseNode(getComparisonToken(), concatExpression, concatExpression());
                default:
                    return concatExpression;
            }
        }
    }

    private Ptg getComparisonToken() {
        char c = this.look;
        if (c == '=') {
            Match(c);
            return EqualPtg.instance;
        }
        boolean z = c == '>';
        Match(c);
        if (!z) {
            char c2 = this.look;
            if (c2 == '=') {
                Match('=');
                return LessEqualPtg.instance;
            } else if (c2 != '>') {
                return LessThanPtg.instance;
            } else {
                Match(Typography.greater);
                return NotEqualPtg.instance;
            }
        } else if (this.look != '=') {
            return GreaterThanPtg.instance;
        } else {
            Match('=');
            return GreaterEqualPtg.instance;
        }
    }

    private ParseNode concatExpression() {
        ParseNode additiveExpression = additiveExpression();
        while (true) {
            SkipWhite();
            if (this.look != '&') {
                return additiveExpression;
            }
            Match(Typography.amp);
            additiveExpression = new ParseNode(ConcatPtg.instance, additiveExpression, additiveExpression());
        }
    }

    private ParseNode additiveExpression() {
        ValueOperatorPtg valueOperatorPtg;
        ParseNode Term = Term();
        while (true) {
            SkipWhite();
            char c = this.look;
            if (c == '+') {
                Match('+');
                valueOperatorPtg = AddPtg.instance;
            } else if (c != '-') {
                return Term;
            } else {
                Match('-');
                valueOperatorPtg = SubtractPtg.instance;
            }
            Term = new ParseNode(valueOperatorPtg, Term, Term());
        }
    }

    private void parse() {
        this._pointer = 0;
        GetChar();
        this._rootNode = unionExpression();
        if (this._pointer <= this._formulaLength) {
            throw new FormulaParseException("Unused input [" + this._formulaString.substring(this._pointer - 1) + "] after attempting to parse the formula [" + this._formulaString + "]");
        }
    }

    private Ptg[] getRPNPtg(int i) {
        new OperandClassTransformer(i).transformFormula(this._rootNode);
        return ParseNode.toTokenArray(this._rootNode);
    }
}
