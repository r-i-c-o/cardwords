package com.ricode.kotlinwords.files;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;

public class AssetHelper {

    private static final String WORDS_FOLDER = "words_file";

    private AssetManager mAssetManager;

    public AssetHelper(Context context) {
        mAssetManager = context.getAssets();
        loadFiles(context);
    }

    private void loadFiles(Context context) {
        String[] wordFiles;
        try {
            wordFiles = mAssetManager.list(WORDS_FOLDER);
        } catch (IOException ioe) {
            //Log.e(TAG, "Could not find asset files" + ioe);
            wordFiles = null;
        }
        if (wordFiles != null) {
            File storageDir = context.getApplicationContext().getFilesDir();
            //Log.i(TAG, "Found " + wordFiles.length + " files");
            for (String filename : wordFiles) {
                File movedFile = new File(storageDir, filename);
                //Log.i(TAG, movedFile.exists() ? "File " + movedFile.getAbsolutePath() + " exists"
                  //      : "File " + movedFile.getAbsolutePath() + " is not exist");
                if (!movedFile.exists())
                    try (InputStream inputStream = mAssetManager.open(WORDS_FOLDER + "/" + filename);
                         OutputStream fileOutputStream = new FileOutputStream(movedFile)) {
                        byte[] buffer = new byte[1024];
                        int c;
                        while ((c = inputStream.read(buffer)) > 0) fileOutputStream.write(buffer, 0, c);
                        //Log.i(TAG, "Has written " + movedFile.getAbsolutePath());
                    } catch (IOException ioe1) {
                        //Log.e(TAG, "Could not copy files " + ioe1);
                    }
            }
        }
    }

}
