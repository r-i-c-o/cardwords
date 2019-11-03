package com.ricode.kotlinwords.packs;

import android.content.Context;
import android.util.Log;
import com.ricode.kotlinwords.files.AppSettings;
import com.ricode.kotlinwords.utilities.ConstantsKt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private PackHelper mPackHelper;
    private AppSettings mAppSettings;

    private static final String TAG = "WordManager";

    public WordManager(Context context) {
        Context mContext = context.getApplicationContext();
        mPackHelper = new PackHelper(mContext);
        mAppSettings = new AppSettings(mContext);
    }

    private File getFile(PackNames filename) {
        return mPackHelper.getPackFile(filename);
    }

    private boolean isFileEmpty(File file) {
        return file.length() <= ConstantsKt.UNICODE_EMPTY;
    }

    //TODO split method
    private ArrayList<Word> getNWordsFromCurrentPosition(int n) {
        ArrayList<Word> words = new ArrayList<>();
        int position = getWordsPosition();
        int counter = 1;
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(getFile(PackNames.PACK)))){
            while ((line = reader.readLine()) != null) {
                if ((counter - position) >= n) {
                    setWordsPosition(counter);
                    break;
                }
                if (counter >= position){
                    Word word = makeWord(line);
                    words.add(word);
                }
                counter++;
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to make a N-words list" + ioe);
        }
        return words;
    }

    public ArrayList<Word> getAllWordsFromFile(PackNames filename) {
        ArrayList<Word> words = new ArrayList<>();
        if (!isFileEmpty(getFile(filename))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(getFile(filename)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        Word word = makeWord(line);
                        words.add(word);
                    }
                }
                //Log.i(TAG, "Added " + words.size() + " words from " + getFile(filename).toString());
            } catch (IOException ioe) {
                Log.e(TAG, "Couldn't open " + filename + " file: " + ioe);
            }
        }
        return words;
    }

    //TODO MOVE TO LISTCOMPOSER
    public ArrayList<Word> setupWords() {
        ArrayList<Word> current = getAllWordsFromFile(PackNames.LEARN);
        if (current.size() < 20) {
            List<Word> words = getNWordsFromCurrentPosition(20 - current.size());
            current.addAll(words);
        }
        rewriteWordsInFile(PackNames.LEARN, current);
        return current;
    }

    public void rewriteWordsInFile(PackNames filename, List<Word> words) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(filename)))) {
            if (!words.isEmpty()){
                for (Word w : words) {
                    String title = w.getTitle();
                    String tb = w.getTranscription();
                    String tr = w.getTranslation();
                    String complete = title + "\t" + tb + "\t" + tr + "\n";
                    writer.write(complete);
                }
            } else {
                writer.write("");
            }
        } catch (IOException ioe1) {
            Log.e(TAG, "Failed to write words down to " + filename + " file" + ioe1);
        }
    }

    public void appendWordToFile(PackNames filename, Word w) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(filename), true))){
            String title = w.getTitle();
            String tb = w.getTranscription();
            String tr = w.getTranslation();
            String complete = title + "\t" + tb + "\t" + tr + "\n";
            writer.append(complete);
        } catch (IOException ioe1) {
            Log.e(TAG, "Failed to append word to " + filename + " file:" + ioe1);
        }
    }

    private int getWordsPosition() {
        return mPackHelper.getPackPosition();
    }

    private void setWordsPosition(int position) {
        mPackHelper.setPackPosition(position);
    }

    private Word makeWord(String string) {
        int tries = mAppSettings.getNumberOfTries();
        String[] components = string.split("\t");
        return new Word(components[0], components[1], components[2], tries);
    }
}
