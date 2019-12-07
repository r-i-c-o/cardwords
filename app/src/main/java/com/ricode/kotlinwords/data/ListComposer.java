package com.ricode.kotlinwords.data;

import android.content.Context;
import android.util.Log;

import com.ricode.kotlinwords.files.AppSettings;

import java.util.ArrayList;

public class ListComposer {

    private int _numOfWords;
    private WordManager mManager;

    public ListComposer(Context context) {
        Context mContext = context.getApplicationContext();
        mManager = new WordManager(mContext);
        _numOfWords = new AppSettings(mContext).getNumberOfWords();
        Log.i("ObjCounter", "listcomposer created");
    }

    public ArrayList<Word> getWordsFromLearn() {
        return makeLearnList();
    }

    public ArrayList<Word> getWordsFromTest() {
        return mManager.getAllWordsFromFile(PackNames.TEST);
    }

    private ArrayList<Word> makeLearnList() {
        ArrayList<Word> list = mManager.getAllWordsFromFile(PackNames.LEARN);
        Log.i("ListComposer", "learn contains " + list.size());
        if (list.isEmpty()) {
            ArrayList<Word> repeatList = mManager.getAllWordsFromFile(PackNames.REPEAT);
            Log.i("ListComposer", "repeat contains " + repeatList.size());
            if (repeatList.size() < _numOfWords) {
                ArrayList<Word> learnList = mManager.getNWordsFromCurrentPosition(_numOfWords - repeatList.size());
                Log.i("ListComposer", "added from pack " + learnList.size());
                repeatList.addAll(learnList);
                list.addAll(repeatList);
                Log.i("ListComposer", "list contains " + list.size());
                repeatList.clear();
            } else {
                list.addAll(repeatList.subList(0, _numOfWords));
                Log.i("ListComposer", "added to list " + repeatList.subList(0, _numOfWords).size());
                repeatList.subList(0, _numOfWords).clear();
            }
            mManager.rewriteWordsInFile(PackNames.REPEAT, repeatList);
            mManager.rewriteWordsInFile(PackNames.LEARN, list);
        }
        return list;
    }

}
