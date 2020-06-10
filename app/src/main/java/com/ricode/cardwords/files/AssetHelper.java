package com.ricode.cardwords.files;

import android.content.Context;
import android.content.res.AssetManager;

import com.ricode.cardwords.database.PackClient;

//v2 assetHelper
public class AssetHelper {

    //private static final String WORDS_FOLDER = "words_file";

    private AssetManager mAssetManager;
    private PackClient mClient;

    public AssetHelper(Context context) {
        mAssetManager = context.getAssets();
        mClient = PackClient.Companion.getInstance(context);
        loadFiles(context);
    }

    private void loadFiles(Context context) {
        mClient.importDbFromAssets();
        /*File storageDir = context.getApplicationContext().getFilesDir();
        File packDir = new File(storageDir, "en_ru_common");
        if (!packDir.exists()) {
            PackHelper helper = new PackHelper(context);
            helper.createPackDirectory("en_ru_common");
            String[] wordFiles;
            try {
                wordFiles = mAssetManager.list(WORDS_FOLDER);
            } catch (IOException ioe) {
                wordFiles = null;
            }
            if (wordFiles != null) {
                for (String filename : wordFiles) {
                    File movedFile = new File(packDir, filename);
                    if (!movedFile.exists())
                        copyFile(filename, movedFile);
                }
            }
        }*/
    }
    /*private void copyFile(String filename, File movedFile) {
        InputStream inputStream = null;
        OutputStream fileOutputStream = null;
        try
        {
            inputStream = mAssetManager.open(WORDS_FOLDER + "/" + filename);
            fileOutputStream = new FileOutputStream(movedFile);
            byte[] buffer = new byte[1024];
            int c;
            while ((c = inputStream.read(buffer)) > 0) fileOutputStream.write(buffer, 0, c);
        } catch (IOException ioe1) {
            //Log.e("AssetH", "Can't copy files " + ioe1);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}
