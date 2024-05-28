package affairs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Клас, який представляє список справ, містить поля title, affairList, sortedByName, sortedByNameRev,
 * sortedByDate, sortedByDateRev, sortedByIsDone та sortedByIsDoneRev. Містить методи getTitle,
 * getAffairList, setTitle, setAffairList, sortByName, sortByDate, sortByIsDone та setSortedFalse.
 */
public class AffairList {
    /**
     * Поле, яке представляє заголовок.
     */
    private String title;
    /**
     * Поле, яке представляє список самих справ.
     */
    private List<Affair> affairList = new ArrayList<>();
    //private List affairList[][] = {new List[1]{affairs}, new List[]{affairs}} ;
    /**
     * Поле, яке представляє чи відсортовано за назвою.
     */
    private boolean sortedByName;
    /**
     * Поле, яке представляє чи відсортовано за назвою реверсивно.
     */
    private boolean sortedByNameRev;
    /**
     * Поле, яке представляє чи відсортовано за датою.
     */
    private boolean sortedByDate;
    /**
     * Поле, яке представляє чи відсортовано за датою реверсивно.
     */
    private boolean sortedByDateRev;
    /**
     * Поле, яке представляє чи відсортовано за виконанням.
     */
    private boolean sortedByIsDone;
    /**
     * Поле, яке представляє чи відсортовано за виконанням реверсивно.
     */
    private boolean sortedByIsDoneRev;

    /**
     * Метод, що повертає заголовок.
     *
     * @return Заголовок списку справ.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод, що повертає список самих справ.
     *
     * @return Список справ.
     */
    public List<Affair> getAffairList() {
        return affairList;
    }

    /**
     * Метод, який задає заголовок.
     *
     * @param title Заголовок списку справ.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод, який задає список справ.
     *
     * @param affairList Список справ.
     */
    public void setAffairList(List<Affair> affairList) {
        this.affairList = affairList;
    }

    /**
     * Метод, який сортує список справ за назвою.
     *
     * @return Відсортований список справ за назвою.
     */
    public List<Affair> sortByName() {
        if (sortedByName) {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
            setSortedFalse();
            sortedByNameRev = true;
        } else {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            setSortedFalse();
            sortedByName = true;
        }
        return affairList;
    }

    /**
     * Метод, який сортує список справ за датою.
     *
     * @return Відсортований список справ за датою.
     */
    public List<Affair> sortByDate() {
        if (sortedByDate) {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
            setSortedFalse();
            sortedByDateRev = true;
        } else {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
            setSortedFalse();
            sortedByDate = true;
        }
        return affairList;
    }

    /**
     * Метод, який сортує список справ за виконанням.
     *
     * @return Відсортований список справ за виконанням.
     */
    public List<Affair> sortByIsDone() { ////Check if working
        if (sortedByIsDone) {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return Boolean.compare(o2.getIsDone(), o1.getIsDone());
                }
            });
            setSortedFalse();
            sortedByIsDoneRev = true;
        } else {
            affairList.sort(new Comparator<Affair>() {
                @Override
                public int compare(Affair o1, Affair o2) {
                    return Boolean.compare(o1.getIsDone(), o2.getIsDone());
                }
            });
            setSortedFalse();
            sortedByIsDone = true;
        }
        return affairList;
    }

    /**
     * Метод, який задає логічні поля сортування як false.
     */
    private void setSortedFalse() {
        sortedByName = false;
        sortedByNameRev = false;
        sortedByDate = false;
        sortedByDateRev = false;
        sortedByIsDone = false;
        sortedByIsDoneRev = false;
    }
}
