package com.example.studentattend.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import static org.litepal.LitePalApplication.getContext;

public class ClipboardUtil {

    public static void copyToClipboard(String result) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("result",result);
        clipboardManager.setPrimaryClip(data);
    }
}
