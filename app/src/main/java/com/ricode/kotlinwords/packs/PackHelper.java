package com.ricode.kotlinwords.packs;

import android.content.Context;
import com.ricode.kotlinwords.utilities.ConstantsKt;
import org.jetbrains.annotations.NotNull;

import java.io.File;

// determines current pack, returns it and some useful methods
public class PackHelper {

    private Context mContext;

    public PackHelper(@NotNull Context context){
        mContext = context.getApplicationContext();
    }

    public String getCurrentPackName(){
        //read shared prefs
        return mContext.getSharedPreferences(ConstantsKt.SP_NAME_CURRENT_PACK, Context.MODE_PRIVATE)
                .getString(ConstantsKt.SP_NAME_CURRENT_PACK, "en_ru_common");
    }

    public void setCurrentPackName(String name){
        //write to shared prefs
        mContext.getSharedPreferences(ConstantsKt.SP_NAME_CURRENT_PACK, Context.MODE_PRIVATE)
                .edit().putString(ConstantsKt.SP_NAME_CURRENT_PACK, name)
                .apply();
    }

    public int getPackPosition() {
        return mContext.getSharedPreferences(ConstantsKt.SP_PACK_PROPERTIES + "_" + getCurrentPackName(), Context.MODE_PRIVATE)
                .getInt(ConstantsKt.SP_PACK_POSITION, 0);
    }

    public void setPackPosition(int pos) {
        mContext.getSharedPreferences(getCurrentPackName(), Context.MODE_PRIVATE)
                .edit().putInt(ConstantsKt.SP_PACK_POSITION, pos)
                .apply();
    }

    public File getPackFile(String name) {
        File dir = new File(mContext.getFilesDir(), getCurrentPackName());
        if (name.equals("pack")) {
            String file = getCurrentPackName() + ".txt";
            return new File(dir, file);
        } else {
            return new File(dir, name);
        }
    }

    public void createPackDirectory(String name){
        //create directory with {name} and add 4 files
    }

}
