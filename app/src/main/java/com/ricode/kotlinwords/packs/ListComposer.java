package com.ricode.kotlinwords.packs;

import android.content.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListComposer {

    private Context mContext;
    private PackHelper mPackHelper;

    public ListComposer(@NotNull Context context) {
        mContext = context.getApplicationContext();
        mPackHelper = new PackHelper(mContext);
    }

    public List<Word> getWordsFromLearn() {
        return Collections.emptyList();
    }

    public List<Word> getWordsFromTest() {
        List<Word> words = new ArrayList<>();
        return words;
    }

}
