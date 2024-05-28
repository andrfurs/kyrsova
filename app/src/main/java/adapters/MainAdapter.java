package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ipzkyrsova.todolist.R;

import java.util.List;

/**
 * Клас, який зв'язує масив даних з користувацьким інтерфейсом, розширює ArrayAdapter<String>.
 * Містить поле onButtonClickListener. Містить конструктор та методи setOnButtonClickListener, getView.
 */
public class MainAdapter extends ArrayAdapter<String> {
    /**
     * Поле, яке представляє слухача натиску на кнопку.
     */
    private View.OnClickListener onButtonClickListener;

    /**
     * Конструктор класу.
     *
     * @param context  Контекст.
     * @param resource Ресурс.
     * @param items    Елемент.
     */
    public MainAdapter(Context context, int resource, List<String> items) {
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
            view = inflater.inflate(R.layout.title_layout, null);
        }

        ImageButton delTitleBtn = view.findViewById(R.id.delAffairBtn);
        TextView title = view.findViewById(R.id.titleText);
        title.setText(getItem(position));

        delTitleBtn.setTag(position);
        delTitleBtn.setOnClickListener(onButtonClickListener);

        return view;
    }
}
