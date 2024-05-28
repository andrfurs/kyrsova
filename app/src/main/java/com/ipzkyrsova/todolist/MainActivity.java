package com.ipzkyrsova.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.MainAdapter;
import affairs.AffairList;
import affairs.ToDoList;

/**
 * Клас, який педставляє головне вікно програми для показу заголовків списків справ, розширює клас
 * AppCompatActivity. Містить поля newtitle, toDoList та listViewTitles. Містить методи onCreate,
 * showTitles, deleteTitle, showAll, addTitle, saveToDoLists, loadToDoList, onDestroy.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Поле, яке представляє поле для вводу нового заголовку списку справ.
     */
    private EditText newTitle;
    /**
     * Поле, яке представляє весь список справ.
     */
    private ToDoList toDoList;
    /**
     * Поле, яке представляє відображення списку заголовків справ.
     */
    private ListView listViewTitles;

    /**
     * Метод, який викликається при запуску програми, показує заголовки списків справ, задає обробник
     * натиску на заголовок.
     *
     * @param savedInstanceState Об'єкт для збереження стану користувацького інтерфейсу.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewTitles = findViewById(R.id.titlesList);
        newTitle = findViewById(R.id.newTitleEntry);

        showTitles();

        listViewTitles.setClickable(true);
        listViewTitles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Метод, відкриє друге вікно для показу справ за певним заголовком.
             * @param parent AdapterView де відбувається натискання.
             * @param view Подання у AdapterView, на яке було натиснуто.
             * @param position Позиція подання виду в адаптері.
             * @param id Ідентифікатор рядка, на який було натиснуто.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
    }

    /**
     * Метод, який виводить на екран заголовки списків справ.
     */
    private void showTitles() {
        loadToDoList();
        List<String> titlesList = new ArrayList<>();
        List<AffairList> list = toDoList.getToDoList();
        for (int i = 0; i < toDoList.getLength(); i++) {
            titlesList.add(list.get(i).getTitle());
        }

        MainAdapter adapter = new MainAdapter(this, R.layout.title_layout, titlesList);// itemList);
        listViewTitles.setAdapter(adapter);

        adapter.setOnButtonClickListener(new View.OnClickListener() {
            /**
             * Метод, який видаляє список справ за заголовком.
             * @param view Подання, на яке було натиснуто.
             */
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

                String title = titlesList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Видалити \"" + title + "\"?")
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            /**
                             * Метод, який викликається при натисканні кнопки "Так".
                             * @param dialog Діалогове вікно.
                             * @param id Ідентифікатор кнопки, на яку було натиснуто.
                             */
                            public void onClick(DialogInterface dialog, int id) {
                                deleteTitle(position);
                            }
                        })
                        .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                            /**
                             * Метод, який викликається при натисканні кнопки "Ні".
                             * @param dialog Діалогове вікно.
                             * @param id Ідентифікатор кнопки, на яку було натиснуто.
                             */
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Метод, який видаляє список справ.
     *
     * @param position Позиція заголовку у списку.
     */
    private void deleteTitle(int position) {
        toDoList.removeAffairList(toDoList.getToDoList().get(position));
        saveToDoList();
        showTitles();
    }

    /**
     * Метод, який відкриє друге вікно для показу всіх справ.
     *
     * @param view Подання, на яке було натиснуто.
     */
    public void showAll(View view) {
        Intent intent = new Intent(this, OutputActivity.class);
        startActivity(intent);
    }

    /**
     * Метод, який додає заголовок списку справ.
     *
     * @param view Подання.
     */
    public void addTitle(View view) {
        AffairList newList = new AffairList();
        String title = String.valueOf(newTitle.getText());
        if (!title.equals("")) {
            newTitle.setText("");
            newList.setTitle(title);
            toDoList.addAffairList(newList);
            saveToDoList();
            showTitles();
        } else {
            Toast.makeText(MainActivity.this, "Не  коректна довжина заголовку", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Метод для збереження списку справ.
     */
    private void saveToDoList() {
        toDoList.saveToDoList(MainActivity.this);
    }

    /**
     * Метод для завантаження списку справ.
     */
    private void loadToDoList() {
        toDoList = ToDoList.loadToDoList(MainActivity.this);
    }

    /**
     * Метод, який викликається при закритті програми.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveToDoList();
    }
}