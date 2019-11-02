package com.ricode.kotlinwords.files;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.ricode.kotlinwords.packs.PackHelper;

import java.io.*;

public class AssetHelper {

    private static final String WORDS_FOLDER = "words_file";

    private AssetManager mAssetManager;

    public AssetHelper(Context context) {
        mAssetManager = context.getAssets();
        loadFiles(context);
    }

    private void loadFiles(Context context) {
        PackHelper helper = new PackHelper(context);
        helper.createPackDirectory("en_ru_common");
        String[] wordFiles;
        try {
            wordFiles = mAssetManager.list(WORDS_FOLDER);
        } catch (IOException ioe) {
            wordFiles = null;
        }
        if (wordFiles != null) {
            File storageDir = context.getApplicationContext().getFilesDir();
            File packDir = new File(storageDir, "en_ru_common");
            for (String filename : wordFiles) {
                File movedFile = new File(packDir, filename);
                if (!movedFile.exists())
                    copyFile(filename, movedFile);
            }
        }
    }
    private void copyFile(String filename, File movedFile) {
        try (InputStream inputStream = mAssetManager.open(WORDS_FOLDER + "/" + filename);
             OutputStream fileOutputStream = new FileOutputStream(movedFile)) {
            byte[] buffer = new byte[1024];
            int c;
            while ((c = inputStream.read(buffer)) > 0) fileOutputStream.write(buffer, 0, c);
        } catch (IOException ioe1) {
            Log.e("AssetH", "Can't copy files " + ioe1);
        }
    }

}
