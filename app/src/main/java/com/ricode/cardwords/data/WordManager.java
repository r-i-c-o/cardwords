package com.ricode.cardwords.data;

import android.content.Context;

import com.ricode.cardwords.files.AppSettings;
import com.ricode.cardwords.utilities.ConstantsKt;

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

    public File getFile(PackNames filename) {
        return mPackHelper.getPackFile(filename);
    }

    public boolean isFileEmpty(PackNames file) {
        return getFile(file).length() <= ConstantsKt.UNICODE_EMPTY;
    }

    ArrayList<Word> getNWordsFromCurrentPosition(int numberOfWords) {
        ArrayList<Word> words = new ArrayList<>();
        int position = mPackHelper.getPackPosition();
        int counter = 1;
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(getFile(PackNames.PACK)));
            while ((line = reader.readLine()) != null) {
                if ((counter - position) >= numberOfWords) {
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
            //Log.e(TAG, "Failed to make a N-words list" + ioe);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }

    ArrayList<Word> getAllWordsFromFile(PackNames filename) {
        ArrayList<Word> words = new ArrayList<>();
        if (!isFileEmpty(filename)) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(getFile(filename)));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        Word word = makeWord(line);
                        words.add(word);
                    }
                }
            } catch (IOException ioe) {
                //Log.e(TAG, "Couldn't open " + filename + " file: " + ioe);
            }
            finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return words;
    }

    public void rewriteWordsInFile(PackNames filename, List<Word> words) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(getFile(filename)));
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
            //Log.e(TAG, "Failed to write words down to " + filename + " file" + ioe1);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendWordToFile(PackNames filename, Word w) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(getFile(filename), true));
            String title = w.getTitle();
            String tb = w.getTranscription();
            String tr = w.getTranslation();
            String complete = title + "\t" + tb + "\t" + tr + "\n";
            writer.append(complete);
        } catch (IOException ioe1) {
            //Log.e(TAG, "Failed to append word to " + filename + " file:" + ioe1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setWordsPosition(int position) {
        mPackHelper.setPackPosition(position);
    }

    private Word makeWord(String string) {
        int tries = mAppSettings.getNumberOfTries() - 1;
        String[] components = string.split("\t");
        return new Word(components[0], components[1], components[2], tries);
    }
}