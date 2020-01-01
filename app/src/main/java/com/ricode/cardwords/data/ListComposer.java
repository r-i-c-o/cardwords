package com.ricode.cardwords.data;

import android.content.Context;

import com.ricode.cardwords.files.AppSettings;

import java.util.ArrayList;

public class ListComposer {

    private int _numOfWords;
    private WordManager mManager;

    public ListComposer(Context context) {
        Context mContext = context.getApplicationContext();
        mManager = new WordManager(mContext);
        _numOfWords = new AppSettings(mContext).getNumberOfWords();
    }

    public ArrayList<Word> getWordsFromLearn() {
        return makeLearnList();
    }

    public ArrayList<Word> getWordsFromTest() {
        return mManager.getAllWordsFromFile(PackNames.TEST);
    }

    private ArrayList<Word> makeLearnList() {
        ArrayList<Word> list = mManager.getAllWordsFromFile(PackNames.LEARN);
        if (list.isEmpty()) {
            ArrayList<Word> repeatList = mManager.getAllWordsFromFile(PackNames.REPEAT);
            if (repeatList.size() < _numOfWords) {
                ArrayList<Word> learnList = mManager.getNWordsFromCurrentPosition(_numOfWords - repeatList.size());
                repeatList.addAll(learnList);
                list.addAll(repeatList);
                repeatList.clear();
            } else {
                list.addAll(repeatList.subList(0, _numOfWords));
                repeatList.subList(0, _numOfWords).clear();
            }
            mManager.rewriteWordsInFile(PackNames.REPEAT, repeatList);
            mManager.rewriteWordsInFile(PackNames.LEARN, list);
        }
        return list;
    }

}
