package affairs;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас, який представляє список списків справ, містить поле toDoList та методи addAffairList, removeAffairList,
 * getLength, getToDoList, serializeList, deserializeList, saveToDoList та loadToDoList.
 */
public class ToDoList {
    /**
     * Поле, яке представляє список списків справ.
     */
    private List<AffairList> toDoList = new ArrayList<>();

    /**
     * Метод, що додає список справ.
     *
     * @param affairList Список справ.
     */
    public void addAffairList(AffairList affairList) {
        toDoList.add(affairList);
    }

    /**
     * Метод, що видаляє список справ.
     *
     * @param affairList Список справ.
     */
    public void removeAffairList(AffairList affairList) {
        toDoList.remove(affairList);
    }

    /**
     * Метод, що повертає довжину списку справ.
     *
     * @return Довжина списку справ.
     */
    public int getLength() {
        return toDoList.size();
    }

    /**
     * Метод, що повертає список справ.
     *
     * @return Список справ.
     */
    public List<AffairList> getToDoList() {
        return toDoList;
    }

    /**
     * Метод, який повертає список справ в JSON.
     *
     * @param toDoList Список справ.
     * @return Список справ в JSON.
     */
    private static String serializeList(ToDoList toDoList) {
        Gson gson = new Gson();
        return gson.toJson(toDoList);
    }

    /**
     * Метод, який повертає список справ.
     *
     * @param jsonString Список справ в JSON.
     * @return Список справ.
     */
    private static ToDoList deserializeList(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<ToDoList>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    /**
     * Метод, який зберігає список справ.
     *
     * @param context Контекст.
     */
    public void saveToDoList(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        String json = ToDoList.serializeList(this);
        System.out.println(json);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("myList", json);
        editor.apply();
    }

    /**
     * Метод, який завантажує список справ.
     *
     * @param context Контекст.
     * @return Список справ.
     */
    public static ToDoList loadToDoList(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        if (sPref != null) {
            String json = sPref.getString("myList", ""); //myList
            return ToDoList.deserializeList(json);
        } else {
            return new ToDoList();
        }
    }
}
