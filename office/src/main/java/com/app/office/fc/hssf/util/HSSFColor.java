package com.app.office.fc.hssf.util;

import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ss.usermodel.Color;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class HSSFColor implements Color {
    private static Map<Integer, HSSFColor> indexHash;

    public String getHexString() {
        return BLACK.hexString;
    }

    public short getIndex() {
        return 8;
    }

    public static final Map<Integer, HSSFColor> getIndexHash() {
        if (indexHash == null) {
            indexHash = Collections.unmodifiableMap(createColorsByIndexMap());
        }
        return indexHash;
    }

    public static final Hashtable<Integer, HSSFColor> getMutableIndexHash() {
        return createColorsByIndexMap();
    }

    private static Hashtable<Integer, HSSFColor> createColorsByIndexMap() {
        HSSFColor[] allColors = getAllColors();
        Hashtable<Integer, HSSFColor> hashtable = new Hashtable<>((allColors.length * 3) / 2);
        int i = 0;
        while (i < allColors.length) {
            HSSFColor hSSFColor = allColors[i];
            Integer valueOf = Integer.valueOf(hSSFColor.getIndex());
            if (!hashtable.containsKey(valueOf)) {
                hashtable.put(valueOf, hSSFColor);
                i++;
            } else {
                throw new RuntimeException("Dup color index (" + valueOf + ") for colors (" + hashtable.get(valueOf).getClass().getName() + "),(" + hSSFColor.getClass().getName() + ")");
            }
        }
        for (HSSFColor hSSFColor2 : allColors) {
            Integer index2 = getIndex2(hSSFColor2);
            if (index2 != null) {
                hashtable.containsKey(index2);
                hashtable.put(index2, hSSFColor2);
            }
        }
        return hashtable;
    }

    private static Integer getIndex2(HSSFColor hSSFColor) {
        try {
            try {
                return Integer.valueOf(((Short) hSSFColor.getClass().getDeclaredField("index2").get(hSSFColor)).intValue());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        } catch (NoSuchFieldException unused) {
            return null;
        }
    }

    private static HSSFColor[] getAllColors() {
        return new HSSFColor[]{new BLACK(), new BROWN(), new OLIVE_GREEN(), new DARK_GREEN(), new DARK_TEAL(), new DARK_BLUE(), new INDIGO(), new GREY_80_PERCENT(), new ORANGE(), new DARK_YELLOW(), new GREEN(), new TEAL(), new BLUE(), new BLUE_GREY(), new GREY_50_PERCENT(), new RED(), new LIGHT_ORANGE(), new LIME(), new SEA_GREEN(), new AQUA(), new LIGHT_BLUE(), new VIOLET(), new GREY_40_PERCENT(), new PINK(), new GOLD(), new YELLOW(), new BRIGHT_GREEN(), new TURQUOISE(), new DARK_RED(), new SKY_BLUE(), new PLUM(), new GREY_25_PERCENT(), new ROSE(), new LIGHT_YELLOW(), new LIGHT_GREEN(), new LIGHT_TURQUOISE(), new PALE_BLUE(), new LAVENDER(), new WHITE(), new CORNFLOWER_BLUE(), new LEMON_CHIFFON(), new MAROON(), new ORCHID(), new CORAL(), new ROYAL_BLUE(), new LIGHT_CORNFLOWER_BLUE(), new TAN()};
    }

    public static final Hashtable<String, HSSFColor> getTripletHash() {
        return createColorsByHexStringMap();
    }

    private static Hashtable<String, HSSFColor> createColorsByHexStringMap() {
        HSSFColor[] allColors = getAllColors();
        Hashtable<String, HSSFColor> hashtable = new Hashtable<>((allColors.length * 3) / 2);
        int i = 0;
        while (i < allColors.length) {
            HSSFColor hSSFColor = allColors[i];
            String hexString = hSSFColor.getHexString();
            if (!hashtable.containsKey(hexString)) {
                hashtable.put(hexString, hSSFColor);
                i++;
            } else {
                throw new RuntimeException("Dup color hexString (" + hexString + ") for color (" + hSSFColor.getClass().getName() + ") -  already taken by (" + hashtable.get(hexString).getClass().getName() + ")");
            }
        }
        return hashtable;
    }

    public short[] getTriplet() {
        return BLACK.triplet;
    }

    public static final class BLACK extends HSSFColor {
        public static final String hexString = "0:0:0";
        public static final short index = 8;
        public static final short[] triplet = {0, 0, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 8;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class BROWN extends HSSFColor {
        public static final String hexString = "9999:3333:0";
        public static final short index = 60;
        public static final short[] triplet = {153, 51, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 60;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static class OLIVE_GREEN extends HSSFColor {
        public static final String hexString = "3333:3333:0";
        public static final short index = 59;
        public static final short[] triplet = {51, 51, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 59;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class DARK_GREEN extends HSSFColor {
        public static final String hexString = "0:3333:0";
        public static final short index = 58;
        public static final short[] triplet = {0, 51, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 58;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class DARK_TEAL extends HSSFColor {
        public static final String hexString = "0:3333:6666";
        public static final short index = 56;
        public static final short[] triplet = {0, 51, 102};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 56;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class DARK_BLUE extends HSSFColor {
        public static final String hexString = "0:0:8080";
        public static final short index = 18;
        public static final short index2 = 32;
        public static final short[] triplet = {0, 0, 128};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 18;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class INDIGO extends HSSFColor {
        public static final String hexString = "3333:3333:9999";
        public static final short index = 62;
        public static final short[] triplet = {51, 51, 153};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 62;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GREY_80_PERCENT extends HSSFColor {
        public static final String hexString = "3333:3333:3333";
        public static final short index = 63;
        public static final short[] triplet = {51, 51, 51};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 63;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class DARK_RED extends HSSFColor {
        public static final String hexString = "8080:0:0";
        public static final short index = 16;
        public static final short index2 = 37;
        public static final short[] triplet = {128, 0, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 16;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class ORANGE extends HSSFColor {
        public static final String hexString = "FFFF:6666:0";
        public static final short index = 53;
        public static final short[] triplet = {255, 102, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 53;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class DARK_YELLOW extends HSSFColor {
        public static final String hexString = "8080:8080:0";
        public static final short index = 19;
        public static final short[] triplet = {128, 128, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 19;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GREEN extends HSSFColor {
        public static final String hexString = "0:8080:0";
        public static final short index = 17;
        public static final short[] triplet = {0, 128, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 17;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class TEAL extends HSSFColor {
        public static final String hexString = "0:8080:8080";
        public static final short index = 21;
        public static final short index2 = 38;
        public static final short[] triplet = {0, 128, 128};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 21;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class BLUE extends HSSFColor {
        public static final String hexString = "0:0:FFFF";
        public static final short index = 12;
        public static final short index2 = 39;
        public static final short[] triplet = {0, 0, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 12;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class BLUE_GREY extends HSSFColor {
        public static final String hexString = "6666:6666:9999";
        public static final short index = 54;
        public static final short[] triplet = {102, 102, 153};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 54;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GREY_50_PERCENT extends HSSFColor {
        public static final String hexString = "8080:8080:8080";
        public static final short index = 23;
        public static final short[] triplet = {128, 128, 128};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 23;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class RED extends HSSFColor {
        public static final String hexString = "FFFF:0:0";
        public static final short index = 10;
        public static final short[] triplet = {255, 0, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 10;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_ORANGE extends HSSFColor {
        public static final String hexString = "FFFF:9999:0";
        public static final short index = 52;
        public static final short[] triplet = {255, 153, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 52;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIME extends HSSFColor {
        public static final String hexString = "9999:CCCC:0";
        public static final short index = 50;
        public static final short[] triplet = {153, 204, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 50;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class SEA_GREEN extends HSSFColor {
        public static final String hexString = "3333:9999:6666";
        public static final short index = 57;
        public static final short[] triplet = {51, 153, 102};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 57;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class AQUA extends HSSFColor {
        public static final String hexString = "3333:CCCC:CCCC";
        public static final short index = 49;
        public static final short[] triplet = {51, 204, 204};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 49;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_BLUE extends HSSFColor {
        public static final String hexString = "3333:6666:FFFF";
        public static final short index = 48;
        public static final short[] triplet = {51, 102, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 48;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class VIOLET extends HSSFColor {
        public static final String hexString = "8080:0:8080";
        public static final short index = 20;
        public static final short index2 = 36;
        public static final short[] triplet = {128, 0, 128};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 20;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GREY_40_PERCENT extends HSSFColor {
        public static final String hexString = "9696:9696:9696";
        public static final short index = 55;
        public static final short[] triplet = {150, 150, 150};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 55;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class PINK extends HSSFColor {
        public static final String hexString = "FFFF:0:FFFF";
        public static final short index = 14;
        public static final short index2 = 33;
        public static final short[] triplet = {255, 0, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 14;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GOLD extends HSSFColor {
        public static final String hexString = "FFFF:CCCC:0";
        public static final short index = 51;
        public static final short[] triplet = {255, 204, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 51;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class YELLOW extends HSSFColor {
        public static final String hexString = "FFFF:FFFF:0";
        public static final short index = 13;
        public static final short index2 = 34;
        public static final short[] triplet = {255, 255, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 13;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class BRIGHT_GREEN extends HSSFColor {
        public static final String hexString = "0:FFFF:0";
        public static final short index = 11;
        public static final short index2 = 35;
        public static final short[] triplet = {0, 255, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 11;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class TURQUOISE extends HSSFColor {
        public static final String hexString = "0:FFFF:FFFF";
        public static final short index = 15;
        public static final short index2 = 35;
        public static final short[] triplet = {0, 255, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 15;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class SKY_BLUE extends HSSFColor {
        public static final String hexString = "0:CCCC:FFFF";
        public static final short index = 40;
        public static final short[] triplet = {0, 204, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 40;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class PLUM extends HSSFColor {
        public static final String hexString = "9999:3333:6666";
        public static final short index = 61;
        public static final short index2 = 25;
        public static final short[] triplet = {153, 51, 102};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 61;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class GREY_25_PERCENT extends HSSFColor {
        public static final String hexString = "C0C0:C0C0:C0C0";
        public static final short index = 22;
        public static final short[] triplet = {EscherProperties.GEOTEXT__UNICODE, EscherProperties.GEOTEXT__UNICODE, EscherProperties.GEOTEXT__UNICODE};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 22;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class ROSE extends HSSFColor {
        public static final String hexString = "FFFF:9999:CCCC";
        public static final short index = 45;
        public static final short[] triplet = {255, 153, 204};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 45;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class TAN extends HSSFColor {
        public static final String hexString = "FFFF:CCCC:9999";
        public static final short index = 47;
        public static final short[] triplet = {255, 204, 153};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 47;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_YELLOW extends HSSFColor {
        public static final String hexString = "FFFF:FFFF:9999";
        public static final short index = 43;
        public static final short[] triplet = {255, 255, 153};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 43;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_GREEN extends HSSFColor {
        public static final String hexString = "CCCC:FFFF:CCCC";
        public static final short index = 42;
        public static final short[] triplet = {204, 255, 204};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 42;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_TURQUOISE extends HSSFColor {
        public static final String hexString = "CCCC:FFFF:FFFF";
        public static final short index = 41;
        public static final short index2 = 27;
        public static final short[] triplet = {204, 255, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 41;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class PALE_BLUE extends HSSFColor {
        public static final String hexString = "9999:CCCC:FFFF";
        public static final short index = 44;
        public static final short[] triplet = {153, 204, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 44;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LAVENDER extends HSSFColor {
        public static final String hexString = "CCCC:9999:FFFF";
        public static final short index = 46;
        public static final short[] triplet = {204, 153, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 46;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class WHITE extends HSSFColor {
        public static final String hexString = "FFFF:FFFF:FFFF";
        public static final short index = 9;
        public static final short[] triplet = {255, 255, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 9;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class CORNFLOWER_BLUE extends HSSFColor {
        public static final String hexString = "9999:9999:FFFF";
        public static final short index = 24;
        public static final short[] triplet = {153, 153, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 24;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LEMON_CHIFFON extends HSSFColor {
        public static final String hexString = "FFFF:FFFF:CCCC";
        public static final short index = 26;
        public static final short[] triplet = {255, 255, 204};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 26;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class MAROON extends HSSFColor {
        public static final String hexString = "8000:0:0";
        public static final short index = 25;
        public static final short[] triplet = {EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0, 0};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 25;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class ORCHID extends HSSFColor {
        public static final String hexString = "6666:0:6666";
        public static final short index = 28;
        public static final short[] triplet = {102, 0, 102};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 28;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class CORAL extends HSSFColor {
        public static final String hexString = "FFFF:8080:8080";
        public static final short index = 29;
        public static final short[] triplet = {255, 128, 128};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 29;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class ROYAL_BLUE extends HSSFColor {
        public static final String hexString = "0:6666:CCCC";
        public static final short index = 30;
        public static final short[] triplet = {0, 102, 204};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 30;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class LIGHT_CORNFLOWER_BLUE extends HSSFColor {
        public static final String hexString = "CCCC:CCCC:FFFF";
        public static final short index = 31;
        public static final short[] triplet = {204, 204, 255};

        public String getHexString() {
            return hexString;
        }

        public short getIndex() {
            return 31;
        }

        public short[] getTriplet() {
            return triplet;
        }
    }

    public static final class AUTOMATIC extends HSSFColor {
        public static final short index = 64;
        private static HSSFColor instance = new AUTOMATIC();

        public String getHexString() {
            return BLACK.hexString;
        }

        public short getIndex() {
            return 64;
        }

        public short[] getTriplet() {
            return BLACK.triplet;
        }

        public static HSSFColor getInstance() {
            return instance;
        }
    }
}
