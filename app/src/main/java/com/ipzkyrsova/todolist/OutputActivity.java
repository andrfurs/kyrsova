package com.ipzkyrsova.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.OutputAdapter;
import adapters.OutputAllAdapter;
import affairs.Affair;
import affairs.AffairList;
import affairs.ToDoList;

/**
 * Клас, який представляє друге вікно програми для показу справ, розширює клас AppCompatActivity.
 * Містить поля titleName, newTitle, position, affair, aff, newName, newDate, toDoList, list та
 * mDateSetListener. Містить методи onCreate, showInfo, deleteAffair, goToMain, addAffair, saveToDoList,
 * loadToDoList, sortName, sortDate, sortIsDone, onDestroy.
 */
public class OutputActivity extends AppCompatActivity {
    /**
     * Поле, яке представляє заголовок списку справ.
     */
    private TextView titleName;
    /**
     * Поле, яке представляє елементи для створення нової справи.
     */
    private LinearLayout newTitle;
    /**
     * Поле, яке представляє позицію даного списку справ у всьому списку.
     */
    private int position;
    /**
     * Поле, яке представляє список самих справ.
     */
    private List<Affair> affair;
    /**
     * Поле, яке представляє список справ та його заголовок.
     */
    private AffairList aff;
    /**
     * Поле, яке представляє поле для вводу назви справи.
     */
    private EditText newName;
    /**
     * Поле, яке представляє поле для вводу кінцевого терміну справи.
     */
    private EditText newDate;
    /**
     * Поле, яке представляє весь список справ.
     */
    private ToDoList toDoList;
    /**
     * Поле, яке представляє список справ.
     */
    private List<AffairList> list;
    /**
     * Поле, яке представляє об'єкт для вибору дати.
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    /**
     * Метод, який викликається при запуску вікна, показує справи, задає обробник натиску на кнопку видалити.
     *
     * @param savedInstanceState Об'єкт для збереження стану користувацького інтерфейсу.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Intent intent = getIntent();
        position = intent.getIntExtra("index", -1);

        loadToDoList();

        list = toDoList.getToDoList();
        String title;
        titleName = findViewById(R.id.titleName);

        newName = findViewById(R.id.newNameEntry);// new
        newDate = findViewById(R.id.newDateEntry);//

        newDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Метод, який показує інтерфейс для вибору дати.
             * @param view Подання.
             */
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(OutputActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            /**
             * Метод, який перетворює дату в рядок.
             * @param datePicker Обирач, пов'язаний з діалогом.
             * @param year Вибраний рік.
             * @param month Вибраний місяць.
             * @param day Вибраний день місяця.
             */
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                newDate.setText(date);
            }
        };

        if (position == -1) {
            newTitle = findViewById(R.id.newTitle);
            newTitle.removeAllViews();
            title = "Усі справи";
            titleName.setText(title);
            List<Affair> af;
            affair = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                af = list.get(i).getAffairList();
                affair.addAll(af);
            }
            aff = new AffairList();
            aff.setTitle(title);
            aff.setAffairList(affair);

            showInfo();
        } else {
            aff = list.get(position);
            title = aff.getTitle();
            titleName.setText(title);
            affair = aff.getAffairList();
            showInfo();
        }
    }

    /**
     * Метод, який виводить на екран справи.
     */
    private void showInfo() {
        ListView listView = findViewById(R.id.affairList);

        List<Map<String, String>> itemList = new ArrayList<>();

        for (Affair aff : affair) {
            Map<String, String> item = new HashMap<>();
            item.put("name", aff.getName());
            item.put("time", aff.getTime().get(5) + "/" + (aff.getTime().get(2) + 1) + "/" + aff.getTime().get(1));
            item.put("isChecked", String.valueOf(aff.getIsDone()));
            itemList.add(item);
        }
        if (position == -1) {
            OutputAllAdapter adapter = new OutputAllAdapter(this, R.layout.title_layout, itemList);
            listView.setAdapter(adapter);
        } else {
            OutputAdapter adapter = new OutputAdapter(this, R.layout.title_layout, itemList);

            adapter.setOnButtonClickListener(new View.OnClickListener() {
                /**
                 * Метод, який видаляє справу.
                 * @param view Подання, на яке було натиснуто.
                 */
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();

                    Affair aff = affair.get(pos);

                    AlertDialog.Builder builder = new AlertDialog.Builder(OutputActivity.this);
                    builder.setMessage("Видалити \"" + aff.getName() + "\"?")
                            .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                                /**
                                 * Метод, який викликається при натисканні кнопки "Так".
                                 * @param dialog Діалогове вікно
                                 * @param id Ідентифікатор кнопки, на яку було натиснуто.
                                 */
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteAffair(pos);
                                }
                            })
                            .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                                /**
                                 * Метод, який викликається при натисканні кнопки "Ні".
                                 * @param dialog Діалогове вікно
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

            adapter.setOnCheckBoxClickListener(new View.OnClickListener() {
                /**
                 * Метод, який обробляє натискання на чекбокс.
                 * @param view Подання, на яке було натиснуто.
                 */
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();

                    affair.get(pos).setIsDone(!affair.get(pos).getIsDone());
                    saveToDoList();
                    showInfo();
                }
            });

            listView.setAdapter(adapter);
        }
    }

    /**
     * Метод, який відкриє початкове вікно для показу заголовків списків справ.
     *
     * @param view Подання.
     */
    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Метод, який видаляє список справ.
     *
     * @param position Позиція заголовку у списку.
     */
    private void deleteAffair(int position) {
        affair.remove(position);
        saveToDoList();
        showInfo();
    }

    /**
     * Метод, який додає справу.
     *
     * @param view Подання.
     */
    public void addAffair(View view) {

        String name = String.valueOf(newName.getText());
        String dateStr = String.valueOf(newDate.getText());
        if (!name.equals("") && !dateStr.equals("")) { ////date???
            String[] dateInfo = dateStr.split("/");
            int day = Integer.parseInt(dateInfo[0]);
            int month = Integer.parseInt(dateInfo[1]);
            int year = Integer.parseInt(dateInfo[2]);
            Calendar date = new GregorianCalendar();
            date.set(year, month - 1, day);
            newName.setText("");
            newDate.setText("");
            affair.add(new Affair(name, date, false));
            saveToDoList();
            showInfo();
        } else {
            Toast.makeText(OutputActivity.this, "Не коректна довжина вводу", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Метод для збереження списку справ.
     */
    private void saveToDoList() {
        toDoList.saveToDoList(OutputActivity.this);
    }

    /**
     * Метод для завантаження списку справ.
     */
    private void loadToDoList() {
        toDoList = ToDoList.loadToDoList(OutputActivity.this);
    }

    /**
     * Метод, який сортує справи за назвою.
     *
     * @param view Подання.
     */
    public void sortName(View view) {
        affair = aff.sortByName();
        showInfo();
    }

    /**
     * Метод, який сортує справи за датою.
     *
     * @param view Подання.
     */
    public void sortDate(View view) {
        affair = aff.sortByDate();
        showInfo();
    }

    /**
     * Метод, який сортує справи за виконанням.
     *
     * @param view Подання.
     */
    public void sortIsDone(View view) {
        affair = aff.sortByIsDone();
        showInfo();
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