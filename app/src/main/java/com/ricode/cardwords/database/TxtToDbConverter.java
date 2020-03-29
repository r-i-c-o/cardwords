package com.ricode.cardwords.database;

import android.content.Context;
import android.util.Log;

import com.ricode.cardwords.data.PackNames;
import com.ricode.cardwords.data.WordManager;
import com.ricode.cardwords.utilities.ConstantsKt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtToDbConverter {
    //1 get files learn test done repeat
    //2 read files line by line
    //3 find in db words with this title
    //4 update word states in db

    private WordManager wm;
    private Repository repository;

    public TxtToDbConverter(Context ctx) {
        wm = new WordManager(ctx);
        repository = Repository.Companion.getInstance(ctx);
    }

    public void convertLearn() {
        File learnFile = wm.getFile(PackNames.LEARN);
        String line;
        BufferedReader reader = null;
        if (learnFile.length() > ConstantsKt.UNICODE_EMPTY)
        try {
            reader = new BufferedReader(new FileReader(learnFile));
            while ((line = reader.readLine()) != null) {
                String[] components = line.split("\t");
                Word word = repository.getWordByTitle(components[0]);
                word.setState(1);
                repository.updateState(word);
            }
        } catch (IOException ioe) {
            Log.e("txttodb", "Failed to convert learn list" + ioe);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void convertTest() {
        File testFile = wm.getFile(PackNames.TEST);
        String line;
        BufferedReader reader = null;
        if (testFile.length() > ConstantsKt.UNICODE_EMPTY)
        try {
            reader = new BufferedReader(new FileReader(testFile));
            while ((line = reader.readLine()) != null) {
                String[] components = line.split("\t");
                Word word = repository.getWordByTitle(components[0]);
                word.setState(2);
                repository.updateState(word);
            }
        } catch (IOException ioe) {
            Log.e("txttodb", "Failed to convert test list" + ioe);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void convertDone() {
        File doneFile = wm.getFile(PackNames.DONE);
        String line;
        BufferedReader reader = null;
        if (doneFile.length() > ConstantsKt.UNICODE_EMPTY)
            try {
                reader = new BufferedReader(new FileReader(doneFile));
                while ((line = reader.readLine()) != null) {
                    String[] components = line.split("\t");
                    Word word = repository.getWordByTitle(components[0]);
                    word.setState(4);
                    repository.updateState(word);
                }
            } catch (IOException ioe) {
                Log.e("txttodb", "Failed to convert done list" + ioe);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public void convertRepeat() {
        File repeatFile = wm.getFile(PackNames.REPEAT);
        String line;
        BufferedReader reader = null;
        if (repeatFile.length() > ConstantsKt.UNICODE_EMPTY)
        try {
            reader = new BufferedReader(new FileReader(repeatFile));
            while ((line = reader.readLine()) != null) {
                String[] components = line.split("\t");
                Word word = repository.getWordByTitle(components[0]);
                word.setState(3);
                repository.updateState(word);
            }
        } catch (IOException ioe) {
            Log.e("txttodb", "Failed to convert repeat list" + ioe);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
