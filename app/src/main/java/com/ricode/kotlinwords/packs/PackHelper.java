package com.ricode.kotlinwords.packs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.ricode.kotlinwords.utilities.ConstantsKt;
import org.jetbrains.annotations.NotNull;

import java.io.File;

// determines current pack, returns it and some useful methods
public class PackHelper {

    private Context mContext;

    public PackHelper(@NotNull Context context){
        mContext = context.getApplicationContext();
    }

    private SharedPreferences getAppSettings() {
        return mContext.getSharedPreferences(ConstantsKt.SP_APP_SETTINGS, Context.MODE_PRIVATE);
    }

    private SharedPreferences getPackSettings() {
        return mContext.getSharedPreferences(ConstantsKt.SP_PACK_PROPERTIES + "_" + getCurrentPackName(), Context.MODE_PRIVATE);
    }

    public String getCurrentPackName(){
        return getAppSettings().getString(ConstantsKt.SP_NAME_CURRENT_PACK, "en_ru_common");
    }

    public void setCurrentPackName(String name){
        getAppSettings()
                .edit().putString(ConstantsKt.SP_NAME_CURRENT_PACK, name)
                .apply();
    }

    public int getPackPosition() {
        return getPackSettings()
                .getInt(ConstantsKt.SP_PACK_POSITION, 1);
    }

    public void setPackPosition(int pos) {
        getPackSettings()
                .edit().putInt(ConstantsKt.SP_PACK_POSITION, pos)
                .apply();
    }

    public File getPackFile(PackNames name) {
        File dir = new File(mContext.getFilesDir(), getCurrentPackName());
        if (name == PackNames.PACK) {
            String file = getCurrentPackName() + ".txt";
            return new File(dir, file);
        } else {
            String filename = name.toString().toLowerCase() + ".txt";
            Log.i("PackHelp", filename);
            return new File(dir, filename);
        }
    }

    public boolean isNeededToAddNewWords() {
        return false;
    }

    public void createPackDirectory(String name){
        //create directory with {name} and add 4 files
        File appDir = mContext.getFilesDir();
        File dir = new File(appDir, name);
        if(dir.mkdir()) {
            Log.i("createDir","created");
        } else Log.e("createDir","not created");
    }

}
