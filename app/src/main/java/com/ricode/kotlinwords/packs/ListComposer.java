package com.ricode.kotlinwords.packs;

import android.content.Context;

import java.util.Collections;
import java.util.List;

public class ListComposer {

    private WordManager mManager;

    public ListComposer(Context context) {
        Context mContext = context.getApplicationContext();
        mManager = new WordManager(mContext);
    }

    public List<Word> getWordsFromLearn() {
        return mManager.setupWords();
    }

    public List<Word> getWordsFromTest() {
        return mManager.getAllWordsFromFile(PackNames.TEST);
    }

    private List<Word> makeLearnList() {
        /*if repeat file contains @number_of_words or more, then
        return list with @number_of_words
        * if repeat file contains less than @number_of_words, then add from pack file*/
        return Collections.emptyList();
    }

}
