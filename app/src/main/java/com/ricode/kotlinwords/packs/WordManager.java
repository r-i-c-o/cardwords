package com.ricode.kotlinwords.packs;

import android.content.Context;
import android.util.Log;
import com.ricode.kotlinwords.files.Settings;
import com.ricode.kotlinwords.utilities.ConstantsKt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private Context mContext;
    private PackHelper mPackHelper;

    private static final String TAG = "LearningHelper";

    private static final String WORDS_FILENAME = "words.txt";
    private static final String POSITION_WORDS = "position.txt";

    private WordManager(Context context) {
        mContext = context.getApplicationContext();
        mPackHelper = new PackHelper(mContext);
    }

    //DEPRECATED
    private File getInternalFileDirectory() {
        return mContext.getFilesDir();
    }

    //MOVED TO PACKHELPER
    // file returning method
    private File getFile(String filename) {
        return mPackHelper.getPackFile(filename);
    }

    private File getWordsFile() {
        return mPackHelper.getPackFile("pack");
    }

    private boolean isFileEmpty(File file) {
        return file.length() <= ConstantsKt.UNICODE_EMPTY;
    }

    //TODO replace arguments with sp methods
    private List<Word> getNWordsFromFile(int position, int n) {
        Settings settings = new Settings(mContext);
        List<Word> words = new ArrayList<>();
        int counter = 1;
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(getWordsFile()))){
            while ((line = reader.readLine()) != null) {
                if ((counter - position) >= n) {
                    setWordsPosition(counter);
                    //Log.i(TAG, "Position " + counter);
                    break;
                }
                if (counter >= position){
                    Word word = makeWord(line);
                    words.add(word);
                    //Log.i(TAG, "Added word in N-list " + line);
                }

                counter++;
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to make a N-words list" + ioe);
        }
        return words;
    }

    //READ FILE
    // get words from file
    public List<Word> getAllWordsFromFile(String filename) {
        List<Word> words = new ArrayList<>();
        if (!isFileEmpty(getFile(filename))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(getFile(filename)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        Word word = makeWord(line);
                        words.add(word);
                    }
                }
                //Log.i(TAG, "Added " + words.size() + " words");
            } catch (IOException ioe) {
                Log.e(TAG, "Couldn't open " + filename + " file: " + ioe);
            }
        }
        else {
            //Log.i(TAG, "File " + filename + " is empty");
        }
        return words;
    }


    //READ/WRITE FILE
    public List<Word> setupWords() {
        List<Word> words;
        List<Word> current = getAllWordsFromFile("current");
        if (current.size() <= 20) {
            //adding words from words file
            words = getNWordsFromFile(getWordsPosition(), 20 - current.size());
            current.addAll(words);
        }
        rewriteWordsInFile("current", current);
        return current;
    }

    //WRITE FILE
    // rewriting file method
    public void rewriteWordsInFile(String filename, List<Word> words) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(filename)))) {
            if (!words.isEmpty()){
                for (Word w : words) {
                    String title = w.getTitle();
                    String tb = w.getTranscription();
                    String tr = w.getTranslation();
                    String complete = title + "\t" + tb + "\t" + tr + "\n";
                    writer.write(complete);
                    //Log.i(TAG, "Written to " + filename + " file string: " + complete);
                }
            } else {
                //Log.i(TAG, "List is empty");
                writer.write("");
            }

        } catch (IOException ioe1) {
            Log.e(TAG, "Failed to write words down to " + filename + " file" + ioe1);
        }

    }

    //WRITE FILE
    // appending word to file method
    public void appendWordToFile(String filename, Word w) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(filename), true))){
            String title = w.getTitle();
            String tb = w.getTranscription();
            String tr = w.getTranslation();
            String complete = title + "\t" + tb + "\t" + tr + "\n";
            writer.append(complete);
            //Log.i(TAG, "Append to " + filename + " file string: " + complete);
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
        String[] components = string.split("\t");
        return new Word(components[0], components[1], components[2]);
    }
}
