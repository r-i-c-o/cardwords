package com.ricode.cardwords.files;

import android.content.Context;
import com.ricode.cardwords.database.PackClient;
import java.io.File;

public class AssetHelper {
    private static final String ASSET_WORDS_FILE = "en_ru_common.db";
    private Context mContext;
    private PackClient mClient;

    public AssetHelper(Context context) {
        mContext = context;
        mClient = PackClient.Companion.getInstance(context);
    }

    public void loadFiles() {
        File db = mContext.getApplicationContext().getDatabasePath(ASSET_WORDS_FILE).getAbsoluteFile();
        if (!db.exists())
            mClient.importDbFromAssets();
    }
}
