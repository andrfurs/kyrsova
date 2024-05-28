package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ipzkyrsova.todolist.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Клас, який зв'язує масив даних з користувацьким інтерфейсом, розширює ArrayAdapter<Map<String, String>>.
 * Містить поля onButtonClickListener та onCheckBoxClickListener. Містить конструктор та методи
 * setOnButtonClickListener, setOnCheckBoxClickListener та getView.
 */
public class OutputAdapter extends ArrayAdapter<Map<String, String>> {
    /**
     * Поле, яке представляє слухача натиску на кнопку.
     */
    private View.OnClickListener onButtonClickListener;
    /**
     * Поле, яке представляє слухача натиску на чекбокс.
     */
    private View.OnClickListener onCheckBoxClickListener;

    /**
     * Конструктор класу.
     *
     * @param context  Контекст.
     * @param resource Ресурс.
     * @param items    Елемент.
     */
    public OutputAdapter(Context context, int resource, List<Map<String, String>> items) {
        super(context, resource, items);
    }

    /**
     * Метод, який задає слухача натиску на кнопку.
     *
     * @param listener Слухач.
     */
    public void setOnButtonClickListener(View.OnClickListener listener) {
        this.onButtonClickListener = listener;
    }

    /**
     * Метод, який задає слухача натиску на чекбокс.
     *
     * @param listener Слухач.
     */
    public void setOnCheckBoxClickListener(View.OnClickListener listener) {
        this.onCheckBoxClickListener = listener;
    }

    /**
     * Метод, який повертає подання.
     *
     * @param position    Позиція елемента в наборі даних адаптера, подання якого ми хочемо отримати.
     * @param convertView Старе подання.
     * @param parent      Батьківський елемент, до якого буде приєднано це подання.
     * @return Подання.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.info_layout, null);
        }

        Map<String, String> itemMap = getItem(position);

        ImageButton delAffairBtn = view.findViewById(R.id.delAffairBtn);
        TextView nameText = view.findViewById(R.id.nameText);
        nameText.setText(itemMap.get("name"));
        TextView timeText = view.findViewById(R.id.timeText);
        String date = itemMap.get("time");
        timeText.setText(date);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        boolean isChecked = Boolean.parseBoolean(itemMap.get("isChecked"));
        checkBox.setChecked(isChecked);
        timeText.setTextColor(Color.BLACK);
        if (!isChecked) {
            Calendar calendar = new GregorianCalendar();
            String[] dat = date.split("/");
            calendar.set(Integer.parseInt(dat[2]), (Integer.parseInt(dat[1]) - 1), Integer.parseInt(dat[0]));
            long diffInDays = TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
            if (diffInDays <= 1) {
                timeText.setTextColor(Color.RED);
            }
        }

        delAffairBtn.setTag(position);
        delAffairBtn.setOnClickListener(onButtonClickListener);

        checkBox.setTag(position);
        checkBox.setOnClickListener(onCheckBoxClickListener);

        return view;
    }
}
