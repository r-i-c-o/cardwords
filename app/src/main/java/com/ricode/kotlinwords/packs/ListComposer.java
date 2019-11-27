package com.ricode.kotlinwords.packs;

import android.content.Context;

import java.util.ArrayList;

public class ListComposer {

    private WordManager mManager;

    public ListComposer(Context context) {
        Context mContext = context.getApplicationContext();
        mManager = new WordManager(mContext);
    }

    public ArrayList<Word> getWordsFromLearn() {
        return mManager.setupWords();
    }

    public ArrayList<Word> getWordsFromTest() {
        return mManager.getAllWordsFromFile(PackNames.TEST);
    }

    private ArrayList<Word> makeLearnList() {
        /*if learn file contains any word, return learn file
        otherwise
        if repeat file contains @number_of_words or more, then
        return list with @number_of_words
        * if repeat file contains less than @number_of_words, then add from pack file*/

        return new ArrayList();
    }

}
