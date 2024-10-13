package com.app.office.fc.hssf.record;

import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class RecordFactory {
    private static final Class<?>[] CONSTRUCTOR_ARGS = {RecordInputStream.class};
    private static final int NUM_RECORDS = 512;
    private static short[] _allKnownRecordSIDs;
    private static final Map<Integer, I_RecordCreator> _recordCreatorsById;
    private static final Class<? extends Record>[] recordClasses;

    private interface I_RecordCreator {
        Record create(RecordInputStream recordInputStream);

        Class<? extends Record> getRecordClass();
    }

    private static final class ReflectionConstructorRecordCreator implements I_RecordCreator {
        private final Constructor<? extends Record> _c;

        public ReflectionConstructorRecordCreator(Constructor<? extends Record> constructor) {
            this._c = constructor;
        }

        public Record create(RecordInputStream recordInputStream) {
            try {
                return (Record) this._c.newInstance(new Object[]{recordInputStream});
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e2) {
                throw new RuntimeException(e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException(e3);
            } catch (InvocationTargetException e4) {
                throw new RecordFormatException("Unable to construct record instance", e4.getTargetException());
            }
        }

        public Class<? extends Record> getRecordClass() {
            return this._c.getDeclaringClass();
        }
    }

    private static final class ReflectionMethodRecordCreator implements I_RecordCreator {
        private final Method _m;

        public ReflectionMethodRecordCreator(Method method) {
            this._m = method;
        }

        public Record create(RecordInputStream recordInputStream) {
            try {
                return (Record) this._m.invoke((Object) null, new Object[]{recordInputStream});
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RecordFormatException("Unable to construct record instance", e3.getTargetException());
            }
        }

        public Class<? extends Record> getRecordClass() {
            return this._m.getDeclaringClass();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Class<?>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Class<? extends com.app.office.fc.hssf.record.Record>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 1
            java.lang.Class[] r1 = new java.lang.Class[r0]
            java.lang.Class<com.app.office.fc.hssf.record.RecordInputStream> r2 = com.app.office.fc.hssf.record.RecordInputStream.class
            r3 = 0
            r1[r3] = r2
            CONSTRUCTOR_ARGS = r1
            r1 = 161(0xa1, float:2.26E-43)
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<com.app.office.fc.hssf.record.ArrayRecord> r2 = com.app.office.fc.hssf.record.ArrayRecord.class
            r1[r3] = r2
            java.lang.Class<com.app.office.fc.hssf.record.AutoFilterInfoRecord> r2 = com.app.office.fc.hssf.record.AutoFilterInfoRecord.class
            r1[r0] = r2
            r0 = 2
            java.lang.Class<com.app.office.fc.hssf.record.BackupRecord> r2 = com.app.office.fc.hssf.record.BackupRecord.class
            r1[r0] = r2
            r0 = 3
            java.lang.Class<com.app.office.fc.hssf.record.BlankRecord> r2 = com.app.office.fc.hssf.record.BlankRecord.class
            r1[r0] = r2
            r0 = 4
            java.lang.Class<com.app.office.fc.hssf.record.BOFRecord> r2 = com.app.office.fc.hssf.record.BOFRecord.class
            r1[r0] = r2
            r0 = 5
            java.lang.Class<com.app.office.fc.hssf.record.BookBoolRecord> r2 = com.app.office.fc.hssf.record.BookBoolRecord.class
            r1[r0] = r2
            r0 = 6
            java.lang.Class<com.app.office.fc.hssf.record.BoolErrRecord> r2 = com.app.office.fc.hssf.record.BoolErrRecord.class
            r1[r0] = r2
            r0 = 7
            java.lang.Class<com.app.office.fc.hssf.record.BottomMarginRecord> r2 = com.app.office.fc.hssf.record.BottomMarginRecord.class
            r1[r0] = r2
            r0 = 8
            java.lang.Class<com.app.office.fc.hssf.record.BoundSheetRecord> r2 = com.app.office.fc.hssf.record.BoundSheetRecord.class
            r1[r0] = r2
            r0 = 9
            java.lang.Class<com.app.office.fc.hssf.record.CalcCountRecord> r2 = com.app.office.fc.hssf.record.CalcCountRecord.class
            r1[r0] = r2
            r0 = 10
            java.lang.Class<com.app.office.fc.hssf.record.CalcModeRecord> r2 = com.app.office.fc.hssf.record.CalcModeRecord.class
            r1[r0] = r2
            r0 = 11
            java.lang.Class<com.app.office.fc.hssf.record.CFHeaderRecord> r2 = com.app.office.fc.hssf.record.CFHeaderRecord.class
            r1[r0] = r2
            r0 = 12
            java.lang.Class<com.app.office.fc.hssf.record.CFRuleRecord> r2 = com.app.office.fc.hssf.record.CFRuleRecord.class
            r1[r0] = r2
            r0 = 13
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartRecord> r2 = com.app.office.fc.hssf.record.chart.ChartRecord.class
            r1[r0] = r2
            r0 = 14
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartTitleFormatRecord> r2 = com.app.office.fc.hssf.record.chart.ChartTitleFormatRecord.class
            r1[r0] = r2
            r0 = 15
            java.lang.Class<com.app.office.fc.hssf.record.CodepageRecord> r2 = com.app.office.fc.hssf.record.CodepageRecord.class
            r1[r0] = r2
            r0 = 16
            java.lang.Class<com.app.office.fc.hssf.record.ColumnInfoRecord> r2 = com.app.office.fc.hssf.record.ColumnInfoRecord.class
            r1[r0] = r2
            r0 = 17
            java.lang.Class<com.app.office.fc.hssf.record.ContinueRecord> r2 = com.app.office.fc.hssf.record.ContinueRecord.class
            r1[r0] = r2
            r0 = 18
            java.lang.Class<com.app.office.fc.hssf.record.CountryRecord> r2 = com.app.office.fc.hssf.record.CountryRecord.class
            r1[r0] = r2
            r0 = 19
            java.lang.Class<com.app.office.fc.hssf.record.CRNCountRecord> r2 = com.app.office.fc.hssf.record.CRNCountRecord.class
            r1[r0] = r2
            r0 = 20
            java.lang.Class<com.app.office.fc.hssf.record.CRNRecord> r2 = com.app.office.fc.hssf.record.CRNRecord.class
            r1[r0] = r2
            r0 = 21
            java.lang.Class<com.app.office.fc.hssf.record.DateWindow1904Record> r2 = com.app.office.fc.hssf.record.DateWindow1904Record.class
            r1[r0] = r2
            r0 = 22
            java.lang.Class<com.app.office.fc.hssf.record.DBCellRecord> r2 = com.app.office.fc.hssf.record.DBCellRecord.class
            r1[r0] = r2
            r0 = 23
            java.lang.Class<com.app.office.fc.hssf.record.DefaultColWidthRecord> r2 = com.app.office.fc.hssf.record.DefaultColWidthRecord.class
            r1[r0] = r2
            r0 = 24
            java.lang.Class<com.app.office.fc.hssf.record.DefaultRowHeightRecord> r2 = com.app.office.fc.hssf.record.DefaultRowHeightRecord.class
            r1[r0] = r2
            r0 = 25
            java.lang.Class<com.app.office.fc.hssf.record.DeltaRecord> r2 = com.app.office.fc.hssf.record.DeltaRecord.class
            r1[r0] = r2
            r0 = 26
            java.lang.Class<com.app.office.fc.hssf.record.DimensionsRecord> r2 = com.app.office.fc.hssf.record.DimensionsRecord.class
            r1[r0] = r2
            r0 = 27
            java.lang.Class<com.app.office.fc.hssf.record.DrawingGroupRecord> r2 = com.app.office.fc.hssf.record.DrawingGroupRecord.class
            r1[r0] = r2
            r0 = 28
            java.lang.Class<com.app.office.fc.hssf.record.DrawingRecord> r2 = com.app.office.fc.hssf.record.DrawingRecord.class
            r1[r0] = r2
            r0 = 29
            java.lang.Class<com.app.office.fc.hssf.record.DrawingSelectionRecord> r2 = com.app.office.fc.hssf.record.DrawingSelectionRecord.class
            r1[r0] = r2
            r0 = 30
            java.lang.Class<com.app.office.fc.hssf.record.DSFRecord> r2 = com.app.office.fc.hssf.record.DSFRecord.class
            r1[r0] = r2
            r0 = 31
            java.lang.Class<com.app.office.fc.hssf.record.DVALRecord> r2 = com.app.office.fc.hssf.record.DVALRecord.class
            r1[r0] = r2
            r0 = 32
            java.lang.Class<com.app.office.fc.hssf.record.DVRecord> r2 = com.app.office.fc.hssf.record.DVRecord.class
            r1[r0] = r2
            r0 = 33
            java.lang.Class<com.app.office.fc.hssf.record.EOFRecord> r2 = com.app.office.fc.hssf.record.EOFRecord.class
            r1[r0] = r2
            r0 = 34
            java.lang.Class<com.app.office.fc.hssf.record.ExtendedFormatRecord> r2 = com.app.office.fc.hssf.record.ExtendedFormatRecord.class
            r1[r0] = r2
            r0 = 35
            java.lang.Class<com.app.office.fc.hssf.record.ExternalNameRecord> r2 = com.app.office.fc.hssf.record.ExternalNameRecord.class
            r1[r0] = r2
            r0 = 36
            java.lang.Class<com.app.office.fc.hssf.record.ExternSheetRecord> r2 = com.app.office.fc.hssf.record.ExternSheetRecord.class
            r1[r0] = r2
            r0 = 37
            java.lang.Class<com.app.office.fc.hssf.record.ExtSSTRecord> r2 = com.app.office.fc.hssf.record.ExtSSTRecord.class
            r1[r0] = r2
            r0 = 38
            java.lang.Class<com.app.office.fc.hssf.record.FeatRecord> r2 = com.app.office.fc.hssf.record.FeatRecord.class
            r1[r0] = r2
            r0 = 39
            java.lang.Class<com.app.office.fc.hssf.record.FeatHdrRecord> r2 = com.app.office.fc.hssf.record.FeatHdrRecord.class
            r1[r0] = r2
            r0 = 40
            java.lang.Class<com.app.office.fc.hssf.record.FilePassRecord> r2 = com.app.office.fc.hssf.record.FilePassRecord.class
            r1[r0] = r2
            r0 = 41
            java.lang.Class<com.app.office.fc.hssf.record.FileSharingRecord> r2 = com.app.office.fc.hssf.record.FileSharingRecord.class
            r1[r0] = r2
            r0 = 42
            java.lang.Class<com.app.office.fc.hssf.record.FnGroupCountRecord> r2 = com.app.office.fc.hssf.record.FnGroupCountRecord.class
            r1[r0] = r2
            r0 = 43
            java.lang.Class<com.app.office.fc.hssf.record.FontRecord> r2 = com.app.office.fc.hssf.record.FontRecord.class
            r1[r0] = r2
            r0 = 44
            java.lang.Class<com.app.office.fc.hssf.record.FooterRecord> r2 = com.app.office.fc.hssf.record.FooterRecord.class
            r1[r0] = r2
            r0 = 45
            java.lang.Class<com.app.office.fc.hssf.record.FormatRecord> r2 = com.app.office.fc.hssf.record.FormatRecord.class
            r1[r0] = r2
            r0 = 46
            java.lang.Class<com.app.office.fc.hssf.record.FormulaRecord> r2 = com.app.office.fc.hssf.record.FormulaRecord.class
            r1[r0] = r2
            r0 = 47
            java.lang.Class<com.app.office.fc.hssf.record.GridsetRecord> r2 = com.app.office.fc.hssf.record.GridsetRecord.class
            r1[r0] = r2
            r0 = 48
            java.lang.Class<com.app.office.fc.hssf.record.GutsRecord> r2 = com.app.office.fc.hssf.record.GutsRecord.class
            r1[r0] = r2
            r0 = 49
            java.lang.Class<com.app.office.fc.hssf.record.HCenterRecord> r2 = com.app.office.fc.hssf.record.HCenterRecord.class
            r1[r0] = r2
            r0 = 50
            java.lang.Class<com.app.office.fc.hssf.record.HeaderRecord> r2 = com.app.office.fc.hssf.record.HeaderRecord.class
            r1[r0] = r2
            r0 = 51
            java.lang.Class<com.app.office.fc.hssf.record.HeaderFooterRecord> r2 = com.app.office.fc.hssf.record.HeaderFooterRecord.class
            r1[r0] = r2
            r0 = 52
            java.lang.Class<com.app.office.fc.hssf.record.HideObjRecord> r2 = com.app.office.fc.hssf.record.HideObjRecord.class
            r1[r0] = r2
            r0 = 53
            java.lang.Class<com.app.office.fc.hssf.record.HorizontalPageBreakRecord> r2 = com.app.office.fc.hssf.record.HorizontalPageBreakRecord.class
            r1[r0] = r2
            r0 = 54
            java.lang.Class<com.app.office.fc.hssf.record.HyperlinkRecord> r2 = com.app.office.fc.hssf.record.HyperlinkRecord.class
            r1[r0] = r2
            r0 = 55
            java.lang.Class<com.app.office.fc.hssf.record.IndexRecord> r2 = com.app.office.fc.hssf.record.IndexRecord.class
            r1[r0] = r2
            r0 = 56
            java.lang.Class<com.app.office.fc.hssf.record.InterfaceEndRecord> r2 = com.app.office.fc.hssf.record.InterfaceEndRecord.class
            r1[r0] = r2
            r0 = 57
            java.lang.Class<com.app.office.fc.hssf.record.InterfaceHdrRecord> r2 = com.app.office.fc.hssf.record.InterfaceHdrRecord.class
            r1[r0] = r2
            r0 = 58
            java.lang.Class<com.app.office.fc.hssf.record.IterationRecord> r2 = com.app.office.fc.hssf.record.IterationRecord.class
            r1[r0] = r2
            r0 = 59
            java.lang.Class<com.app.office.fc.hssf.record.LabelRecord> r2 = com.app.office.fc.hssf.record.LabelRecord.class
            r1[r0] = r2
            r0 = 60
            java.lang.Class<com.app.office.fc.hssf.record.LabelSSTRecord> r2 = com.app.office.fc.hssf.record.LabelSSTRecord.class
            r1[r0] = r2
            r0 = 61
            java.lang.Class<com.app.office.fc.hssf.record.LeftMarginRecord> r2 = com.app.office.fc.hssf.record.LeftMarginRecord.class
            r1[r0] = r2
            r0 = 62
            java.lang.Class<com.app.office.fc.hssf.record.chart.LegendRecord> r2 = com.app.office.fc.hssf.record.chart.LegendRecord.class
            r1[r0] = r2
            r0 = 63
            java.lang.Class<com.app.office.fc.hssf.record.MergeCellsRecord> r2 = com.app.office.fc.hssf.record.MergeCellsRecord.class
            r1[r0] = r2
            r0 = 64
            java.lang.Class<com.app.office.fc.hssf.record.MMSRecord> r2 = com.app.office.fc.hssf.record.MMSRecord.class
            r1[r0] = r2
            r0 = 65
            java.lang.Class<com.app.office.fc.hssf.record.MulBlankRecord> r2 = com.app.office.fc.hssf.record.MulBlankRecord.class
            r1[r0] = r2
            r0 = 66
            java.lang.Class<com.app.office.fc.hssf.record.MulRKRecord> r2 = com.app.office.fc.hssf.record.MulRKRecord.class
            r1[r0] = r2
            r0 = 67
            java.lang.Class<com.app.office.fc.hssf.record.NameRecord> r2 = com.app.office.fc.hssf.record.NameRecord.class
            r1[r0] = r2
            r0 = 68
            java.lang.Class<com.app.office.fc.hssf.record.NameCommentRecord> r2 = com.app.office.fc.hssf.record.NameCommentRecord.class
            r1[r0] = r2
            r0 = 69
            java.lang.Class<com.app.office.fc.hssf.record.NoteRecord> r2 = com.app.office.fc.hssf.record.NoteRecord.class
            r1[r0] = r2
            r0 = 70
            java.lang.Class<com.app.office.fc.hssf.record.NumberRecord> r2 = com.app.office.fc.hssf.record.NumberRecord.class
            r1[r0] = r2
            r0 = 71
            java.lang.Class<com.app.office.fc.hssf.record.ObjectProtectRecord> r2 = com.app.office.fc.hssf.record.ObjectProtectRecord.class
            r1[r0] = r2
            r0 = 72
            java.lang.Class<com.app.office.fc.hssf.record.ObjRecord> r2 = com.app.office.fc.hssf.record.ObjRecord.class
            r1[r0] = r2
            r0 = 73
            java.lang.Class<com.app.office.fc.hssf.record.PaletteRecord> r2 = com.app.office.fc.hssf.record.PaletteRecord.class
            r1[r0] = r2
            r0 = 74
            java.lang.Class<com.app.office.fc.hssf.record.PaneRecord> r2 = com.app.office.fc.hssf.record.PaneRecord.class
            r1[r0] = r2
            r0 = 75
            java.lang.Class<com.app.office.fc.hssf.record.PasswordRecord> r2 = com.app.office.fc.hssf.record.PasswordRecord.class
            r1[r0] = r2
            r0 = 76
            java.lang.Class<com.app.office.fc.hssf.record.PasswordRev4Record> r2 = com.app.office.fc.hssf.record.PasswordRev4Record.class
            r1[r0] = r2
            r0 = 77
            java.lang.Class<com.app.office.fc.hssf.record.PrecisionRecord> r2 = com.app.office.fc.hssf.record.PrecisionRecord.class
            r1[r0] = r2
            r0 = 78
            java.lang.Class<com.app.office.fc.hssf.record.PrintGridlinesRecord> r2 = com.app.office.fc.hssf.record.PrintGridlinesRecord.class
            r1[r0] = r2
            r0 = 79
            java.lang.Class<com.app.office.fc.hssf.record.PrintHeadersRecord> r2 = com.app.office.fc.hssf.record.PrintHeadersRecord.class
            r1[r0] = r2
            r0 = 80
            java.lang.Class<com.app.office.fc.hssf.record.PrintSetupRecord> r2 = com.app.office.fc.hssf.record.PrintSetupRecord.class
            r1[r0] = r2
            r0 = 81
            java.lang.Class<com.app.office.fc.hssf.record.ProtectionRev4Record> r2 = com.app.office.fc.hssf.record.ProtectionRev4Record.class
            r1[r0] = r2
            r0 = 82
            java.lang.Class<com.app.office.fc.hssf.record.ProtectRecord> r2 = com.app.office.fc.hssf.record.ProtectRecord.class
            r1[r0] = r2
            r0 = 83
            java.lang.Class<com.app.office.fc.hssf.record.RecalcIdRecord> r2 = com.app.office.fc.hssf.record.RecalcIdRecord.class
            r1[r0] = r2
            r0 = 84
            java.lang.Class<com.app.office.fc.hssf.record.RefModeRecord> r2 = com.app.office.fc.hssf.record.RefModeRecord.class
            r1[r0] = r2
            r0 = 85
            java.lang.Class<com.app.office.fc.hssf.record.RefreshAllRecord> r2 = com.app.office.fc.hssf.record.RefreshAllRecord.class
            r1[r0] = r2
            r0 = 86
            java.lang.Class<com.app.office.fc.hssf.record.RightMarginRecord> r2 = com.app.office.fc.hssf.record.RightMarginRecord.class
            r1[r0] = r2
            r0 = 87
            java.lang.Class<com.app.office.fc.hssf.record.RKRecord> r2 = com.app.office.fc.hssf.record.RKRecord.class
            r1[r0] = r2
            r0 = 88
            java.lang.Class<com.app.office.fc.hssf.record.RowRecord> r2 = com.app.office.fc.hssf.record.RowRecord.class
            r1[r0] = r2
            r0 = 89
            java.lang.Class<com.app.office.fc.hssf.record.SaveRecalcRecord> r2 = com.app.office.fc.hssf.record.SaveRecalcRecord.class
            r1[r0] = r2
            r0 = 90
            java.lang.Class<com.app.office.fc.hssf.record.ScenarioProtectRecord> r2 = com.app.office.fc.hssf.record.ScenarioProtectRecord.class
            r1[r0] = r2
            r0 = 91
            java.lang.Class<com.app.office.fc.hssf.record.SelectionRecord> r2 = com.app.office.fc.hssf.record.SelectionRecord.class
            r1[r0] = r2
            r0 = 92
            java.lang.Class<com.app.office.fc.hssf.record.chart.SeriesRecord> r2 = com.app.office.fc.hssf.record.chart.SeriesRecord.class
            r1[r0] = r2
            r0 = 93
            java.lang.Class<com.app.office.fc.hssf.record.chart.SeriesTextRecord> r2 = com.app.office.fc.hssf.record.chart.SeriesTextRecord.class
            r1[r0] = r2
            r0 = 94
            java.lang.Class<com.app.office.fc.hssf.record.SharedFormulaRecord> r2 = com.app.office.fc.hssf.record.SharedFormulaRecord.class
            r1[r0] = r2
            r0 = 95
            java.lang.Class<com.app.office.fc.hssf.record.SSTRecord> r2 = com.app.office.fc.hssf.record.SSTRecord.class
            r1[r0] = r2
            r0 = 96
            java.lang.Class<com.app.office.fc.hssf.record.StringRecord> r2 = com.app.office.fc.hssf.record.StringRecord.class
            r1[r0] = r2
            r0 = 97
            java.lang.Class<com.app.office.fc.hssf.record.StyleRecord> r2 = com.app.office.fc.hssf.record.StyleRecord.class
            r1[r0] = r2
            r0 = 98
            java.lang.Class<com.app.office.fc.hssf.record.SupBookRecord> r2 = com.app.office.fc.hssf.record.SupBookRecord.class
            r1[r0] = r2
            r0 = 99
            java.lang.Class<com.app.office.fc.hssf.record.TabIdRecord> r2 = com.app.office.fc.hssf.record.TabIdRecord.class
            r1[r0] = r2
            r0 = 100
            java.lang.Class<com.app.office.fc.hssf.record.TableRecord> r2 = com.app.office.fc.hssf.record.TableRecord.class
            r1[r0] = r2
            r0 = 101(0x65, float:1.42E-43)
            java.lang.Class<com.app.office.fc.hssf.record.TableStylesRecord> r2 = com.app.office.fc.hssf.record.TableStylesRecord.class
            r1[r0] = r2
            r0 = 102(0x66, float:1.43E-43)
            java.lang.Class<com.app.office.fc.hssf.record.TextObjectRecord> r2 = com.app.office.fc.hssf.record.TextObjectRecord.class
            r1[r0] = r2
            r0 = 103(0x67, float:1.44E-43)
            java.lang.Class<com.app.office.fc.hssf.record.TopMarginRecord> r2 = com.app.office.fc.hssf.record.TopMarginRecord.class
            r1[r0] = r2
            r0 = 104(0x68, float:1.46E-43)
            java.lang.Class<com.app.office.fc.hssf.record.UncalcedRecord> r2 = com.app.office.fc.hssf.record.UncalcedRecord.class
            r1[r0] = r2
            r0 = 105(0x69, float:1.47E-43)
            java.lang.Class<com.app.office.fc.hssf.record.UseSelFSRecord> r2 = com.app.office.fc.hssf.record.UseSelFSRecord.class
            r1[r0] = r2
            r0 = 106(0x6a, float:1.49E-43)
            java.lang.Class<com.app.office.fc.hssf.record.UserSViewBegin> r2 = com.app.office.fc.hssf.record.UserSViewBegin.class
            r1[r0] = r2
            r0 = 107(0x6b, float:1.5E-43)
            java.lang.Class<com.app.office.fc.hssf.record.UserSViewEnd> r2 = com.app.office.fc.hssf.record.UserSViewEnd.class
            r1[r0] = r2
            r0 = 108(0x6c, float:1.51E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ValueRangeRecord> r2 = com.app.office.fc.hssf.record.chart.ValueRangeRecord.class
            r1[r0] = r2
            r0 = 109(0x6d, float:1.53E-43)
            java.lang.Class<com.app.office.fc.hssf.record.VCenterRecord> r2 = com.app.office.fc.hssf.record.VCenterRecord.class
            r1[r0] = r2
            r0 = 110(0x6e, float:1.54E-43)
            java.lang.Class<com.app.office.fc.hssf.record.VerticalPageBreakRecord> r2 = com.app.office.fc.hssf.record.VerticalPageBreakRecord.class
            r1[r0] = r2
            r0 = 111(0x6f, float:1.56E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WindowOneRecord> r2 = com.app.office.fc.hssf.record.WindowOneRecord.class
            r1[r0] = r2
            r0 = 112(0x70, float:1.57E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WindowProtectRecord> r2 = com.app.office.fc.hssf.record.WindowProtectRecord.class
            r1[r0] = r2
            r0 = 113(0x71, float:1.58E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WindowTwoRecord> r2 = com.app.office.fc.hssf.record.WindowTwoRecord.class
            r1[r0] = r2
            r0 = 114(0x72, float:1.6E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WriteAccessRecord> r2 = com.app.office.fc.hssf.record.WriteAccessRecord.class
            r1[r0] = r2
            r0 = 115(0x73, float:1.61E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WriteProtectRecord> r2 = com.app.office.fc.hssf.record.WriteProtectRecord.class
            r1[r0] = r2
            r0 = 116(0x74, float:1.63E-43)
            java.lang.Class<com.app.office.fc.hssf.record.WSBoolRecord> r2 = com.app.office.fc.hssf.record.WSBoolRecord.class
            r1[r0] = r2
            r0 = 117(0x75, float:1.64E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.BeginRecord> r2 = com.app.office.fc.hssf.record.chart.BeginRecord.class
            r1[r0] = r2
            r0 = 118(0x76, float:1.65E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartFRTInfoRecord> r2 = com.app.office.fc.hssf.record.chart.ChartFRTInfoRecord.class
            r1[r0] = r2
            r0 = 119(0x77, float:1.67E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartStartBlockRecord> r2 = com.app.office.fc.hssf.record.chart.ChartStartBlockRecord.class
            r1[r0] = r2
            r0 = 120(0x78, float:1.68E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartEndBlockRecord> r2 = com.app.office.fc.hssf.record.chart.ChartEndBlockRecord.class
            r1[r0] = r2
            r0 = 121(0x79, float:1.7E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartStartObjectRecord> r2 = com.app.office.fc.hssf.record.chart.ChartStartObjectRecord.class
            r1[r0] = r2
            r0 = 122(0x7a, float:1.71E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ChartEndObjectRecord> r2 = com.app.office.fc.hssf.record.chart.ChartEndObjectRecord.class
            r1[r0] = r2
            r0 = 123(0x7b, float:1.72E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.CatLabRecord> r2 = com.app.office.fc.hssf.record.chart.CatLabRecord.class
            r1[r0] = r2
            r0 = 124(0x7c, float:1.74E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.DataFormatRecord> r2 = com.app.office.fc.hssf.record.chart.DataFormatRecord.class
            r1[r0] = r2
            r0 = 125(0x7d, float:1.75E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.EndRecord> r2 = com.app.office.fc.hssf.record.chart.EndRecord.class
            r1[r0] = r2
            r0 = 126(0x7e, float:1.77E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.LinkedDataRecord> r2 = com.app.office.fc.hssf.record.chart.LinkedDataRecord.class
            r1[r0] = r2
            r0 = 127(0x7f, float:1.78E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.SeriesToChartGroupRecord> r2 = com.app.office.fc.hssf.record.chart.SeriesToChartGroupRecord.class
            r1[r0] = r2
            r0 = 128(0x80, float:1.794E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AreaFormatRecord> r2 = com.app.office.fc.hssf.record.chart.AreaFormatRecord.class
            r1[r0] = r2
            r0 = 129(0x81, float:1.81E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AreaRecord> r2 = com.app.office.fc.hssf.record.chart.AreaRecord.class
            r1[r0] = r2
            r0 = 130(0x82, float:1.82E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AxisLineFormatRecord> r2 = com.app.office.fc.hssf.record.chart.AxisLineFormatRecord.class
            r1[r0] = r2
            r0 = 131(0x83, float:1.84E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AxisOptionsRecord> r2 = com.app.office.fc.hssf.record.chart.AxisOptionsRecord.class
            r1[r0] = r2
            r0 = 132(0x84, float:1.85E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AxisParentRecord> r2 = com.app.office.fc.hssf.record.chart.AxisParentRecord.class
            r1[r0] = r2
            r0 = 133(0x85, float:1.86E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AxisRecord> r2 = com.app.office.fc.hssf.record.chart.AxisRecord.class
            r1[r0] = r2
            r0 = 134(0x86, float:1.88E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.AxisUsedRecord> r2 = com.app.office.fc.hssf.record.chart.AxisUsedRecord.class
            r1[r0] = r2
            r0 = 135(0x87, float:1.89E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.BarRecord> r2 = com.app.office.fc.hssf.record.chart.BarRecord.class
            r1[r0] = r2
            r0 = 136(0x88, float:1.9E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.CategorySeriesAxisRecord> r2 = com.app.office.fc.hssf.record.chart.CategorySeriesAxisRecord.class
            r1[r0] = r2
            r0 = 137(0x89, float:1.92E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.DatRecord> r2 = com.app.office.fc.hssf.record.chart.DatRecord.class
            r1[r0] = r2
            r0 = 138(0x8a, float:1.93E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.DefaultDataLabelTextPropertiesRecord> r2 = com.app.office.fc.hssf.record.chart.DefaultDataLabelTextPropertiesRecord.class
            r1[r0] = r2
            r0 = 139(0x8b, float:1.95E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.FontBasisRecord> r2 = com.app.office.fc.hssf.record.chart.FontBasisRecord.class
            r1[r0] = r2
            r0 = 140(0x8c, float:1.96E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.FontIndexRecord> r2 = com.app.office.fc.hssf.record.chart.FontIndexRecord.class
            r1[r0] = r2
            r0 = 141(0x8d, float:1.98E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.FrameRecord> r2 = com.app.office.fc.hssf.record.chart.FrameRecord.class
            r1[r0] = r2
            r0 = 142(0x8e, float:1.99E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.LineFormatRecord> r2 = com.app.office.fc.hssf.record.chart.LineFormatRecord.class
            r1[r0] = r2
            r0 = 143(0x8f, float:2.0E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.NumberFormatIndexRecord> r2 = com.app.office.fc.hssf.record.chart.NumberFormatIndexRecord.class
            r1[r0] = r2
            r0 = 144(0x90, float:2.02E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.PlotAreaRecord> r2 = com.app.office.fc.hssf.record.chart.PlotAreaRecord.class
            r1[r0] = r2
            r0 = 145(0x91, float:2.03E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.PlotGrowthRecord> r2 = com.app.office.fc.hssf.record.chart.PlotGrowthRecord.class
            r1[r0] = r2
            r0 = 146(0x92, float:2.05E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.SeriesLabelsRecord> r2 = com.app.office.fc.hssf.record.chart.SeriesLabelsRecord.class
            r1[r0] = r2
            r0 = 147(0x93, float:2.06E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.SeriesListRecord> r2 = com.app.office.fc.hssf.record.chart.SeriesListRecord.class
            r1[r0] = r2
            r0 = 148(0x94, float:2.07E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.SheetPropertiesRecord> r2 = com.app.office.fc.hssf.record.chart.SheetPropertiesRecord.class
            r1[r0] = r2
            r0 = 149(0x95, float:2.09E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.TickRecord> r2 = com.app.office.fc.hssf.record.chart.TickRecord.class
            r1[r0] = r2
            r0 = 150(0x96, float:2.1E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.UnitsRecord> r2 = com.app.office.fc.hssf.record.chart.UnitsRecord.class
            r1[r0] = r2
            r0 = 151(0x97, float:2.12E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.DataItemRecord> r2 = com.app.office.fc.hssf.record.pivottable.DataItemRecord.class
            r1[r0] = r2
            r0 = 152(0x98, float:2.13E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.ExtendedPivotTableViewFieldsRecord> r2 = com.app.office.fc.hssf.record.pivottable.ExtendedPivotTableViewFieldsRecord.class
            r1[r0] = r2
            r0 = 153(0x99, float:2.14E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.PageItemRecord> r2 = com.app.office.fc.hssf.record.pivottable.PageItemRecord.class
            r1[r0] = r2
            r0 = 154(0x9a, float:2.16E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.StreamIDRecord> r2 = com.app.office.fc.hssf.record.pivottable.StreamIDRecord.class
            r1[r0] = r2
            r0 = 155(0x9b, float:2.17E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.ViewDefinitionRecord> r2 = com.app.office.fc.hssf.record.pivottable.ViewDefinitionRecord.class
            r1[r0] = r2
            r0 = 156(0x9c, float:2.19E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.ViewFieldsRecord> r2 = com.app.office.fc.hssf.record.pivottable.ViewFieldsRecord.class
            r1[r0] = r2
            r0 = 157(0x9d, float:2.2E-43)
            java.lang.Class<com.app.office.fc.hssf.record.pivottable.ViewSourceRecord> r2 = com.app.office.fc.hssf.record.pivottable.ViewSourceRecord.class
            r1[r0] = r2
            r0 = 158(0x9e, float:2.21E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.DataLabelExtensionRecord> r2 = com.app.office.fc.hssf.record.chart.DataLabelExtensionRecord.class
            r1[r0] = r2
            r0 = 159(0x9f, float:2.23E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.TextRecord> r2 = com.app.office.fc.hssf.record.chart.TextRecord.class
            r1[r0] = r2
            r0 = 160(0xa0, float:2.24E-43)
            java.lang.Class<com.app.office.fc.hssf.record.chart.ObjectLinkRecord> r2 = com.app.office.fc.hssf.record.chart.ObjectLinkRecord.class
            r1[r0] = r2
            recordClasses = r1
            java.util.Map r0 = recordsToMap(r1)
            _recordCreatorsById = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.record.RecordFactory.<clinit>():void");
    }

    public static Class<? extends Record> getRecordClass(int i) {
        I_RecordCreator i_RecordCreator = _recordCreatorsById.get(Integer.valueOf(i));
        if (i_RecordCreator == null) {
            return null;
        }
        return i_RecordCreator.getRecordClass();
    }

    public static Record[] createRecord(RecordInputStream recordInputStream) {
        Record createSingleRecord = createSingleRecord(recordInputStream);
        if (createSingleRecord instanceof DBCellRecord) {
            return new Record[]{null};
        } else if (createSingleRecord instanceof RKRecord) {
            return new Record[]{convertToNumberRecord((RKRecord) createSingleRecord)};
        } else if (createSingleRecord instanceof MulRKRecord) {
            return convertRKRecords((MulRKRecord) createSingleRecord);
        } else {
            return new Record[]{createSingleRecord};
        }
    }

    public static Record createSingleRecord(RecordInputStream recordInputStream) {
        I_RecordCreator i_RecordCreator = _recordCreatorsById.get(Integer.valueOf(recordInputStream.getSid()));
        if (i_RecordCreator == null) {
            return new UnknownRecord(recordInputStream);
        }
        return i_RecordCreator.create(recordInputStream);
    }

    public static NumberRecord convertToNumberRecord(RKRecord rKRecord) {
        NumberRecord numberRecord = new NumberRecord();
        numberRecord.setColumn(rKRecord.getColumn());
        numberRecord.setRow(rKRecord.getRow());
        numberRecord.setXFIndex(rKRecord.getXFIndex());
        numberRecord.setValue(rKRecord.getRKNumber());
        return numberRecord;
    }

    public static NumberRecord[] convertRKRecords(MulRKRecord mulRKRecord) {
        NumberRecord[] numberRecordArr = new NumberRecord[mulRKRecord.getNumColumns()];
        for (int i = 0; i < mulRKRecord.getNumColumns(); i++) {
            NumberRecord numberRecord = new NumberRecord();
            numberRecord.setColumn((short) (mulRKRecord.getFirstColumn() + i));
            numberRecord.setRow(mulRKRecord.getRow());
            numberRecord.setXFIndex(mulRKRecord.getXFAt(i));
            numberRecord.setValue(mulRKRecord.getRKNumberAt(i));
            numberRecordArr[i] = numberRecord;
        }
        return numberRecordArr;
    }

    public static BlankRecord[] convertBlankRecords(MulBlankRecord mulBlankRecord) {
        BlankRecord[] blankRecordArr = new BlankRecord[mulBlankRecord.getNumColumns()];
        for (int i = 0; i < mulBlankRecord.getNumColumns(); i++) {
            BlankRecord blankRecord = new BlankRecord();
            blankRecord.setColumn((short) (mulBlankRecord.getFirstColumn() + i));
            blankRecord.setRow(mulBlankRecord.getRow());
            blankRecord.setXFIndex(mulBlankRecord.getXFAt(i));
            blankRecordArr[i] = blankRecord;
        }
        return blankRecordArr;
    }

    public static short[] getAllKnownRecordSIDs() {
        if (_allKnownRecordSIDs == null) {
            Map<Integer, I_RecordCreator> map = _recordCreatorsById;
            short[] sArr = new short[map.size()];
            int i = 0;
            for (Integer shortValue : map.keySet()) {
                sArr[i] = shortValue.shortValue();
                i++;
            }
            Arrays.sort(sArr);
            _allKnownRecordSIDs = sArr;
        }
        return (short[]) _allKnownRecordSIDs.clone();
    }

    private static Map<Integer, I_RecordCreator> recordsToMap(Class<? extends Record>[] clsArr) {
        HashMap hashMap = new HashMap();
        HashSet hashSet = new HashSet((clsArr.length * 3) / 2);
        int i = 0;
        while (i < clsArr.length) {
            Class<? extends Record> cls = clsArr[i];
            if (!Record.class.isAssignableFrom(cls)) {
                throw new RuntimeException("Invalid record sub-class (" + cls.getName() + ")");
            } else if (Modifier.isAbstract(cls.getModifiers())) {
                throw new RuntimeException("Invalid record class (" + cls.getName() + ") - must not be abstract");
            } else if (hashSet.add(cls)) {
                try {
                    short s = cls.getField("sid").getShort((Object) null);
                    Integer valueOf = Integer.valueOf(s);
                    if (!hashMap.containsKey(valueOf)) {
                        hashMap.put(valueOf, getRecordCreator(cls));
                        i++;
                    } else {
                        Class<? extends Record> recordClass = ((I_RecordCreator) hashMap.get(valueOf)).getRecordClass();
                        throw new RuntimeException("duplicate record sid 0x" + Integer.toHexString(s).toUpperCase() + " for classes (" + cls.getName() + ") and (" + recordClass.getName() + ")");
                    }
                } catch (Exception unused) {
                    throw new RecordFormatException("Unable to determine record types");
                }
            } else {
                throw new RuntimeException("duplicate record class (" + cls.getName() + ")");
            }
        }
        return hashMap;
    }

    private static I_RecordCreator getRecordCreator(Class<? extends Record> cls) {
        try {
            return new ReflectionConstructorRecordCreator(cls.getConstructor(CONSTRUCTOR_ARGS));
        } catch (NoSuchMethodException unused) {
            try {
                return new ReflectionMethodRecordCreator(cls.getDeclaredMethod("create", CONSTRUCTOR_ARGS));
            } catch (NoSuchMethodException unused2) {
                throw new RuntimeException("Failed to find constructor or create method for (" + cls.getName() + ").");
            }
        }
    }

    public static List<Record> createRecords(InputStream inputStream) throws RecordFormatException {
        return createRecords(inputStream, (AbstractReader) null);
    }

    public static List<Record> createRecords(InputStream inputStream, AbstractReader abstractReader) throws RecordFormatException {
        ArrayList arrayList = new ArrayList(512);
        RecordFactoryInputStream recordFactoryInputStream = new RecordFactoryInputStream(inputStream, true);
        while (true) {
            Record nextRecord = recordFactoryInputStream.nextRecord();
            if (nextRecord == null) {
                recordFactoryInputStream.dispose();
                return arrayList;
            } else if (abstractReader == null || !abstractReader.isAborted()) {
                arrayList.add(nextRecord);
            } else {
                throw new AbortReaderError("abort Reader");
            }
        }
    }
}
