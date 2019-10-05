package com.ricode.kotlinwords.packs;

import android.content.Context;
import com.ricode.kotlinwords.utilities.ConstantsKt;

// determines current pack, returns it and some useful methods
public class PackHelper {

    private Context mContext;

    private static final int UNICODE_EMPTY_FILE_SIZE = 3;

    public PackHelper(Context context){
        mContext = context;
    }

    public String getCurrentPackDirectory(){
        //read shared prefs
        return mContext.getSharedPreferences(ConstantsKt.SP_NAME_CURRENT_PACK, Context.MODE_PRIVATE)
                .getString(ConstantsKt.SP_NAME_CURRENT_PACK, "lang_lang_name");
    }

    public void setCurrentPackDirectory(String name){
        //write to shared prefs
        mContext.getSharedPreferences(ConstantsKt.SP_NAME_CURRENT_PACK, Context.MODE_PRIVATE)
                .edit().putString(ConstantsKt.SP_NAME_CURRENT_PACK, name)
                .apply();
    }

    public void createPackDirectory(){

    }

}
