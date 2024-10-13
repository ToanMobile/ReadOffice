package com.app.office.system;

import com.app.office.constant.MainConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class FileKit {
    private static FileKit mt = new FileKit();

    private FileKit() {
    }

    public static FileKit instance() {
        return mt;
    }

    public void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File deleteFile : listFiles) {
                    deleteFile(deleteFile);
                }
            }
            file.delete();
            return;
        }
        file.delete();
    }

    public void pasteFile(File file, File file2) {
        if (file.isDirectory()) {
            copyFolder(file, file2);
        } else {
            copyFile(file, file2);
        }
    }

    public void copyFile(File file, File file2) {
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFolder(File file, File file2) {
        if (!file2.exists()) {
            file2.mkdir();
        }
        String absolutePath = file2.getAbsolutePath();
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file3 : listFiles) {
                if (absolutePath.endsWith(File.separator)) {
                    pasteFile(file3, new File(absolutePath + file3.getName()));
                } else {
                    pasteFile(file3, new File(absolutePath + File.separator + file3.getName()));
                }
            }
        }
    }

    public boolean isSupport(String str) {
        String lowerCase = str.toLowerCase();
        return lowerCase.indexOf(".") > 0 && (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM) || lowerCase.endsWith("pdf"));
    }

    public boolean isFileMarked(String str, List<File> list) {
        if (!(str == null || list == null || list.size() == 0)) {
            for (File absolutePath : list) {
                if (str.equals(absolutePath.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }
}
