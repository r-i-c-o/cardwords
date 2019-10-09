package com.ricode.kotlinwords.files;

import android.content.Context;
import android.util.Log;
import com.ricode.kotlinwords.packs.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// File helping class
// TODO split class into smaller classes

public class LearningHelper {

    private static LearningHelper uniqueInstance;
    private Context mContext;

    private static final String TAG = "LearningHelper";

    private static final String WORDS_FILENAME = "words.txt";
    private static final String POSITION_WORDS = "position.txt";
    private static final int UNICODE_EMPTY_FILE_SIZE = 3;

    private LearningHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    public static LearningHelper getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new LearningHelper(context);
        }
        return uniqueInstance;
    }


    private File getInternalFileDirectory() {
        return mContext.getFilesDir();
    }

    // file returning method
    private File getFile(String filename) {
        String file = filename + ".txt";
        return new File(getInternalFileDirectory(), file);
    }

    private File getWordsFile() {
        return new File(getInternalFileDirectory(), WORDS_FILENAME);
    }

    private File getPositionFile() {
        return new File(getInternalFileDirectory(), POSITION_WORDS);
    }

    private boolean isFileEmpty(File file) {
        return file.length() <= UNICODE_EMPTY_FILE_SIZE;
    }

    //READ FILE
    private List<Word> getNWordsFromFile(int position, int n) {
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
    public List<Word> getWordsFromFile(String filename) {
        List<Word> words = new ArrayList<>();
        if (isFileEmpty(getFile(filename))) {
            //Log.i(TAG, "File " + filename + " is empty");
        }
        else {
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
        return words;
    }


    //READ/WRITE FILE
    public List<Word> setupWords() {
        int position = getWordsPosition();
        List<Word> words;
        List<Word> current = getWordsFromFile("current");
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

    //TODO read position from shared prefs
    private int getWordsPosition() {
        int position;
        try(BufferedReader br = new BufferedReader(new FileReader(getPositionFile()))) {
            String s = br.readLine();
            position = Integer.parseInt(s);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed getting position");
            position = 0;
        }
        return position;
    }

    private void setWordsPosition(int position) {
        try(BufferedWriter wr = new BufferedWriter(new FileWriter(getPositionFile()))) {
            String pos = Integer.toString(position);
            wr.write(pos);
            //Log.i(TAG, "Position set at " + position);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed setting position" + ioe);
        }
    }

    private Word makeWord(String string) {
        String[] components = string.split("\t");
        return new Word(components[0], components[1], components[2]);
    }
}
