package com.app.office.fc.hslf.record;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.PointerIconCompat;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.fc.hpsf.Constants;
import java.lang.reflect.Field;
import java.util.HashMap;

public final class RecordTypes {
    public static final Type AnimationInfo = new Type(4116, AnimationInfo.class);
    public static final Type AnimationInfoAtom = new Type(4081, AnimationInfoAtom.class);
    public static final Type BaseTextPropAtom = new Type(4002, (Class<? extends Record>) null);
    public static final Type BinaryTagDataBlob = new Type(5003, BinaryTagDataBlob.class);
    public static final Type BookmarkCollection = new Type(2019, (Class<? extends Record>) null);
    public static final Type BookmarkEntityAtom = new Type(4048, (Class<? extends Record>) null);
    public static final Type BookmarkSeedAtom = new Type(2025, (Class<? extends Record>) null);
    public static final Type CString = new Type(4026, CString.class);
    public static final Type CharFormatAtom = new Type(4066, (Class<? extends Record>) null);
    public static final Type ClientVisualElementContainer = new Type(61756, ClientVisualElementContainer.class);
    public static final Type ColorSchemeAtom = new Type(2032, ColorSchemeAtom.class);
    public static final Type Comment2000 = new Type(12000, Comment2000.class);
    public static final Type Comment2000Atom = new Type(12001, Comment2000Atom.class);
    public static final Type Comment2000Summary = new Type(12004, (Class<? extends Record>) null);
    public static final Type Comment2000SummaryAtom = new Type(12005, (Class<? extends Record>) null);
    public static final Type CompositeMasterId = new Type(1052, (Class<? extends Record>) null);
    public static final Type CurrentUserAtom = new Type(4086, (Class<? extends Record>) null);
    public static final Type DateTimeMCAtom = new Type(4087, (Class<? extends Record>) null);
    public static final Type DefaultRulerAtom = new Type(4011, (Class<? extends Record>) null);
    public static final Type DocRoutingSlip = new Type(1030, (Class<? extends Record>) null);
    public static final Type Document = new Type(1000, Document.class);
    public static final Type DocumentAtom = new Type(PointerIconCompat.TYPE_CONTEXT_MENU, DocumentAtom.class);
    public static final Type DocumentEncryptionAtom = new Type(12052, DocumentEncryptionAtom.class);
    public static final Type EndDocument = new Type(PointerIconCompat.TYPE_HAND, (Class<? extends Record>) null);
    public static final Type Environment = new Type(PointerIconCompat.TYPE_ALIAS, Environment.class);
    public static final int EscherAlignRule = 61459;
    public static final int EscherAnchor = 61454;
    public static final int EscherArcRule = 61460;
    public static final int EscherBSE = 61447;
    public static final int EscherBStoreContainer = 61441;
    public static final int EscherBlip_END = 61719;
    public static final int EscherBlip_START = 61464;
    public static final int EscherCLSID = 61462;
    public static final int EscherCalloutRule = 61463;
    public static final int EscherChildAnchor = 61455;
    public static final int EscherClientAnchor = 61456;
    public static final int EscherClientData = 61457;
    public static final int EscherClientRule = 61461;
    public static final int EscherClientTextbox = 61453;
    public static final int EscherColorMRU = 61722;
    public static final int EscherColorScheme = 61728;
    public static final int EscherConnectorRule = 61458;
    public static final int EscherDeletedPspl = 61725;
    public static final int EscherDg = 61448;
    public static final int EscherDgContainer = 61442;
    public static final int EscherDgg = 61446;
    public static final int EscherDggContainer = 61440;
    public static final int EscherOPT = 61451;
    public static final int EscherOleObject = 61727;
    public static final int EscherRegroupItems = 61720;
    public static final int EscherSelection = 61721;
    public static final int EscherSolverContainer = 61445;
    public static final int EscherSp = 61450;
    public static final int EscherSpContainer = 61444;
    public static final int EscherSpgr = 61449;
    public static final int EscherSpgrContainer = 61443;
    public static final int EscherSplitMenuColors = 61726;
    public static final int EscherTextbox = 61452;
    public static final int EscherUserDefined = 61730;
    public static final Type ExAviMovie = new Type(4102, ExAviMovie.class);
    public static final Type ExCDAudio = new Type(4110, (Class<? extends Record>) null);
    public static final Type ExCDAudioAtom = new Type(4114, (Class<? extends Record>) null);
    public static final Type ExControl = new Type(4078, ExControl.class);
    public static final Type ExControlAtom = new Type(4091, ExControlAtom.class);
    public static final Type ExEmbed = new Type(4044, ExEmbed.class);
    public static final Type ExEmbedAtom = new Type(4045, ExEmbedAtom.class);
    public static final Type ExHyperlink = new Type(4055, ExHyperlink.class);
    public static final Type ExHyperlinkAtom = new Type(4051, ExHyperlinkAtom.class);
    public static final Type ExLink = new Type(4046, (Class<? extends Record>) null);
    public static final Type ExLinkAtom = new Type(4049, (Class<? extends Record>) null);
    public static final Type ExMCIMovie = new Type(4103, ExMCIMovie.class);
    public static final Type ExMIDIAudio = new Type(4109, (Class<? extends Record>) null);
    public static final Type ExMediaAtom = new Type(4100, ExMediaAtom.class);
    public static final Type ExObjList = new Type(1033, ExObjList.class);
    public static final Type ExObjListAtom = new Type(1034, ExObjListAtom.class);
    public static final Type ExObjRefAtom = new Type(3009, (Class<? extends Record>) null);
    public static final Type ExOleObjAtom = new Type(4035, ExOleObjAtom.class);
    public static final Type ExOleObjStg = new Type(4113, ExOleObjStg.class);
    public static final Type ExQuickTimeMovie = new Type(4074, (Class<? extends Record>) null);
    public static final Type ExQuickTimeMovieData = new Type(4075, (Class<? extends Record>) null);
    public static final Type ExVideoContainer = new Type(4101, ExVideoContainer.class);
    public static final Type ExWAVAudioEmbedded = new Type(4111, (Class<? extends Record>) null);
    public static final Type ExWAVAudioEmbeddedAtom = new Type(4115, (Class<? extends Record>) null);
    public static final Type ExWAVAudioLink = new Type(4112, (Class<? extends Record>) null);
    public static final Type ExtendedParagraphAtom = new Type(4012, ExtendedParagraphAtom.class);
    public static final Type ExtendedParagraphHeaderAtom = new Type(4015, ExtendedParagraphHeaderAtom.class);
    public static final Type ExtendedPreRuleContainer = new Type(4014, ExtendedPresRuleContainer.class);
    public static final Type FontCollection = new Type(2005, FontCollection.class);
    public static final Type FontEmbeddedData = new Type(4024, (Class<? extends Record>) null);
    public static final Type FontEntityAtom = new Type(4023, FontEntityAtom.class);
    public static final Type FooterMCAtom = new Type(4090, (Class<? extends Record>) null);
    public static final Type GPopublicintAtom = new Type(3024, (Class<? extends Record>) null);
    public static final Type GRColorAtom = new Type(Constants.CP_MAC_CHINESE_TRADITIONAL, (Class<? extends Record>) null);
    public static final Type GRatioAtom = new Type(3031, (Class<? extends Record>) null);
    public static final Type GScalingAtom = new Type(Constants.CP_MAC_JAPAN, (Class<? extends Record>) null);
    public static final Type GenericDateMCAtom = new Type(4088, (Class<? extends Record>) null);
    public static final Type GuideAtom = new Type(PointerIconCompat.TYPE_ZOOM_OUT, (Class<? extends Record>) null);
    public static final Type HandOut = new Type(4041, DummyPositionSensitiveRecordWithChildren.class);
    public static final Type HeadersFooters = new Type(4057, HeadersFootersContainer.class);
    public static final Type HeadersFootersAtom = new Type(4058, HeadersFootersAtom.class);
    public static final Type InteractiveInfo = new Type(4082, InteractiveInfo.class);
    public static final Type InteractiveInfoAtom = new Type(4083, InteractiveInfoAtom.class);
    public static final Type List = new Type(2000, List.class);
    public static final Type MainMaster = new Type(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, MainMaster.class);
    public static final Type MetaFile = new Type(4033, (Class<? extends Record>) null);
    public static final Type NamedShow = new Type(1041, (Class<? extends Record>) null);
    public static final Type NamedShowSlides = new Type(MetaDo.META_SCALEVIEWPORTEXT, (Class<? extends Record>) null);
    public static final Type NamedShows = new Type(MetaDo.META_SCALEWINDOWEXT, (Class<? extends Record>) null);
    public static final Type Notes = new Type(PointerIconCompat.TYPE_TEXT, Notes.class);
    public static final Type NotesAtom = new Type(PointerIconCompat.TYPE_VERTICAL_TEXT, NotesAtom.class);
    public static final Type OEPlaceholderAtom = new Type(3011, OEPlaceholderAtom.class);
    public static final Type OEShapeAtom = new Type(3009, OEShapeAtom.class);
    public static final Type OriginalMainMasterId = new Type(1052, (Class<? extends Record>) null);
    public static final Type OutlineTextRefAtom = new Type(3998, OutlineTextRefAtom.class);
    public static final Type OutlineViewInfo = new Type(1031, (Class<? extends Record>) null);
    public static final Type PPDrawing = new Type(1036, PPDrawing.class);
    public static final Type PPDrawingGroup = new Type(1035, PPDrawingGroup.class);
    public static final Type ParaFormatAtom = new Type(4067, (Class<? extends Record>) null);
    public static final Type PersistPtrFullBlock = new Type(6001, PersistPtrHolder.class);
    public static final Type PersistPtrIncrementalBlock = new Type(6002, PersistPtrHolder.class);
    public static final Type ProgStringTag = new Type(5001, (Class<? extends Record>) null);
    public static final Type PrpublicintOptions = new Type(6000, (Class<? extends Record>) null);
    public static final Type RTFDateTimeMCAtom = new Type(4117, (Class<? extends Record>) null);
    public static final Type RecolorInfoAtom = new Type(4071, (Class<? extends Record>) null);
    public static final Type RoundTripContentMasterId = new Type(1058, (Class<? extends Record>) null);
    public static final Type RoundTripContentMasterInfo12 = new Type(1054, (Class<? extends Record>) null);
    public static final Type RoundTripCustomTableStyles12 = new Type(1064, (Class<? extends Record>) null);
    public static final Type RoundTripHFPlaceholder12 = new Type(1056, RoundTripHFPlaceholder12.class);
    public static final Type RoundTripNotesMasterTextStyles12 = new Type(1063, (Class<? extends Record>) null);
    public static final Type RoundTripOArtTextStyles12 = new Type(1059, (Class<? extends Record>) null);
    public static final Type RoundTripShapeCheckSumForCustomLayouts12 = new Type(1062, (Class<? extends Record>) null);
    public static final Type RoundTripShapeId12 = new Type(MetaDo.META_SETPIXEL, (Class<? extends Record>) null);
    public static final Type SSDocInfoAtom = new Type(InputDeviceCompat.SOURCE_GAMEPAD, (Class<? extends Record>) null);
    public static final Type SSlideLayoutAtom = new Type(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, (Class<? extends Record>) null);
    public static final Type SheetProperties = new Type(1044, (Class<? extends Record>) null);
    public static final Type SlaveContainer = new Type(61765, SlaveContainer.class);
    public static final Type Slide = new Type(PointerIconCompat.TYPE_CELL, Slide.class);
    public static final Type SlideAtom = new Type(PointerIconCompat.TYPE_CROSSHAIR, SlideAtom.class);
    public static final Type SlideListWithText = new Type(4080, SlideListWithText.class);
    public static final Type SlideNumberMCAtom = new Type(4056, (Class<? extends Record>) null);
    public static final Type SlidePersistAtom = new Type(PointerIconCompat.TYPE_COPY, SlidePersistAtom.class);
    public static final Type SlideProgBinaryTagContainer = new Type(5002, SlideProgBinaryTagContainer.class);
    public static final Type SlideProgTagsContainer = new Type(5000, SlideProgTagsContainer.class);
    public static final Type SlideShowSlideInfoAtom = new Type(PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, SlideShowSlideInfoAtom.class);
    public static final Type SlideTimeAtom = new Type(12011, SlideTimeAtom.class);
    public static final Type SlideViewInfo = new Type(PointerIconCompat.TYPE_ZOOM_IN, (Class<? extends Record>) null);
    public static final Type SlideViewInfoAtom = new Type(1022, (Class<? extends Record>) null);
    public static final Type SorterViewInfo = new Type(1032, (Class<? extends Record>) null);
    public static final Type Sound = new Type(2022, Sound.class);
    public static final Type SoundCollAtom = new Type(2021, (Class<? extends Record>) null);
    public static final Type SoundCollection = new Type(2020, SoundCollection.class);
    public static final Type SoundData = new Type(2023, SoundData.class);
    public static final Type SrKinsoku = new Type(4040, (Class<? extends Record>) null);
    public static final Type SrKinsokuAtom = new Type(4050, (Class<? extends Record>) null);
    public static final Type StyleTextPropAtom = new Type(4001, StyleTextPropAtom.class);
    public static final Type Summary = new Type(1026, (Class<? extends Record>) null);
    public static final Type TextBookmarkAtom = new Type(4007, (Class<? extends Record>) null);
    public static final Type TextBytesAtom = new Type(4008, TextBytesAtom.class);
    public static final Type TextCharsAtom = new Type(4000, TextCharsAtom.class);
    public static final Type TextHeaderAtom = new Type(3999, TextHeaderAtom.class);
    public static final Type TextRulerAtom = new Type(4006, TextRulerAtom.class);
    public static final Type TextSpecInfoAtom = new Type(4010, TextSpecInfoAtom.class);
    public static final Type TimeAnimateBehaviorContainer = new Type(61739, TimeAnimateBehaviorContainer.class);
    public static final Type TimeBehaviorContainer = new Type(61738, TimeBehaviorContainer.class);
    public static final Type TimeColorBehaviorAtom = new Type(61749, TimeColorBehaviorAtom.class);
    public static final Type TimeColorBehaviorContainer = new Type(61740, TimeColorBehaviorContainer.class);
    public static final Type TimeCommandBehaviorContainer = new Type(61746, TimeCommandBehaviorContainer.class);
    public static final Type TimeConditionAtom = new Type(61736, TimeConditionAtom.class);
    public static final Type TimeConditionContainer = new Type(61733, TimeConditionContainer.class);
    public static final Type TimeEffectBehaviorContainer = new Type(61741, TimeEffectBehaviorContainer.class);
    public static final Type TimeIterateDataAtom = new Type(61760, TimeIterateDataAtom.class);
    public static final Type TimeMotionBehaviorContainer = new Type(61742, TimeMotionBehaviorContainer.class);
    public static final Type TimeNodeAtom = new Type(61735, TimeNodeAtom.class);
    public static final Type TimeNodeAttributeContainer = new Type(61757, TimeNodeAttributeContainer.class);
    public static final Type TimeNodeContainer = new Type(61764, TimeNodeContainer.class);
    public static final Type TimeRotationBehaviorContainer = new Type(61743, TimeRotationBehaviorContainer.class);
    public static final Type TimeScaleBehaviorContainer = new Type(61744, TimeScaleBehaviorContainer.class);
    public static final Type TimeSequenceDataAtom = new Type(61761, TimeSequenceDataAtom.class);
    public static final Type TimeSetBehaviorContainer = new Type(61745, TimeSetBehaviorContainer.class);
    public static final Type TimeVariant = new Type(61762, TimeVariant.class);
    public static final Type TxCFStyleAtom = new Type(4004, (Class<? extends Record>) null);
    public static final Type TxInteractiveInfoAtom = new Type(4063, TxInteractiveInfoAtom.class);
    public static final Type TxMasterStyleAtom = new Type(4003, TxMasterStyleAtom.class);
    public static final Type TxPFStyleAtom = new Type(4005, (Class<? extends Record>) null);
    public static final Type TxSIStyleAtom = new Type(4009, (Class<? extends Record>) null);
    public static final Type Unknown = new Type(0, (Class<? extends Record>) null);
    public static final Type UserEditAtom = new Type(4085, UserEditAtom.class);
    public static final Type VBAInfo = new Type(IEEEDouble.EXPONENT_BIAS, (Class<? extends Record>) null);
    public static final Type VBAInfoAtom = new Type(1024, (Class<? extends Record>) null);
    public static final Type ViewInfo = new Type(PointerIconCompat.TYPE_GRAB, (Class<? extends Record>) null);
    public static final Type ViewInfoAtom = new Type(PointerIconCompat.TYPE_GRABBING, (Class<? extends Record>) null);
    public static final Type VisualShapeAtom = new Type(11003, VisualShapeAtom.class);
    public static HashMap<Integer, Class<? extends Record>> typeToClass = new HashMap<>();
    public static HashMap<Integer, String> typeToName = new HashMap<>();

    static {
        try {
            Field[] fields = RecordTypes.class.getFields();
            for (int i = 0; i < fields.length; i++) {
                Object obj = fields[i].get((Object) null);
                if (obj instanceof Integer) {
                    typeToName.put((Integer) obj, fields[i].getName());
                }
                if (obj instanceof Type) {
                    Type type = (Type) obj;
                    Object obj2 = type.handlingClass;
                    Integer valueOf = Integer.valueOf(type.typeID);
                    if (obj2 == null) {
                        obj2 = UnknownRecordPlaceholder.class;
                    }
                    typeToName.put(valueOf, fields[i].getName());
                    typeToClass.put(valueOf, obj2);
                }
            }
        } catch (IllegalAccessException unused) {
            throw new RuntimeException("Failed to initialize records types");
        }
    }

    public static String recordName(int i) {
        String str = typeToName.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return "Unknown" + i;
    }

    public static Class<? extends Record> recordHandlingClass(int i) {
        return typeToClass.get(Integer.valueOf(i));
    }

    public static class Type {
        public Class<? extends Record> handlingClass;
        public int typeID;

        public Type(int i, Class<? extends Record> cls) {
            this.typeID = i;
            this.handlingClass = cls;
        }
    }
}
