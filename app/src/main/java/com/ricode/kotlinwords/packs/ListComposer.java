package com.ricode.kotlinwords.packs;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListComposer {

    private Context mContext;
    public ListComposer(Context context) {
        mContext = context;
    }
    private File wordsFile;

    public List<Word> getWordsFromCurrent() {
        return new ArrayList<>();
    }

    public List<Word> getWordsFromOk() {
        return new ArrayList<>();
    }

}
