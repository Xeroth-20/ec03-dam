package dev.jx.ec03.util;

import android.util.Log;

import androidx.annotation.RawRes;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import dev.jx.ec03.CodemoniumApplication;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static <T> List<T> parseAsList(@RawRes int id, Class<T[]> type) {
        Writer writer = new StringWriter();
        try (InputStream is = CodemoniumApplication.getAppContext().getResources().openRawResource(id)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            char[] buffer = new char[1024];
            int pointer;

            while ((pointer = br.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }

        String json = writer.toString();
        Gson gson = new Gson();

        return Arrays.asList(gson.fromJson(json, type));
    }
}
