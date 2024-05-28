package affairs;

import java.util.Calendar;

/**
 * Клас, який представляє справу, містить поля name, date та isDone, конструктор та методи getName,
 * getTime, getIsDone та setIsDone.
 */
public class Affair {
    /**
     * Поле, яке представляє назву.
     */
    private String name;

    /**
     * Поле, яке представляє дату.
     */
    private Calendar date;

    /**
     * Поле, яке представляє виконання.
     */
    private boolean isDone;

    /**
     * Конструктор класу.
     *
     * @param name   Назва.
     * @param date   Дата.
     * @param isDone Виконано.
     */
    public Affair(String name, Calendar date, boolean isDone) {
        this.name = name;
        this.date = date;
        this.isDone = isDone;
    }

    /**
     * Метод, що повертає назву.
     *
     * @return Назва справи.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, що повертає дату.
     *
     * @return Дата справи.
     */
    public Calendar getTime() {
        return date;
    }

    /**
     * Метод, що повертає виконання.
     *
     * @return Виконання справи.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Метод, який задає виконання справи.
     *
     * @param done Виконання справи.
     */
    public void setIsDone(boolean done) {
        isDone = done;
    }
}
