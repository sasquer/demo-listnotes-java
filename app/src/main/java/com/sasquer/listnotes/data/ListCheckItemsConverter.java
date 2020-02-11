package com.sasquer.listnotes.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListCheckItemsConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<NoteCheckItem> stringToCheckList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<NoteCheckItem>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String checkListToString(List<NoteCheckItem> objects) {
        return gson.toJson(objects);
    }
}
