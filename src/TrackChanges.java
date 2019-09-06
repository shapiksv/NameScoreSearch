import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TrackChanges {
    public static List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName1 = bufferedReader.readLine();
        String fileName2 = bufferedReader.readLine();
        bufferedReader.close();

        FileReader fileReader1 = new FileReader(fileName1);
        FileReader fileReader2 = new FileReader(fileName2);
        BufferedReader reader1 = new BufferedReader(fileReader1);
        BufferedReader reader2 = new BufferedReader(fileReader2);

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        while (reader1.ready()) {
            list1.add(reader1.readLine());
        }
        while (reader2.ready()) {
            list2.add(reader2.readLine());
        }

        fileReader1.close();
        fileReader2.close();
        reader1.close();
        reader2.close();

        for (int i = 0; i < list1.size(); i++) {
            if (list1.size() == 0 || list2.size() == 0)break;

            if (list1.get(i).equals(list2.get(i))) {
                lines.add(new LineItem(Type.SAME, list1.get(i)));
                list1.remove(i);
                list2.remove(i);
                i = i - 1;
            }
            else if (!list1.get(i).equals(list2.get(i)) && list1.get(i + 1).equals(list2.get(i))) {
                lines.add(new LineItem(Type.REMOVED, list1.get(i)));
                lines.add(new LineItem(Type.SAME, list1.get(i + 1)));
                list1.remove(i + 1);
                list1.remove(i);
                list2.remove(i);
                i = i - 1;
            }
            else if (!list1.get(i).equals(list2.get(i)) && !list1.get(i + 1).equals(list2.get(i))) {
                lines.add(new LineItem(Type.ADDED, list2.get(i)));
                list2.remove(i);
                i = i - 1;
            }
        }

        if (list1.size() < list2.size()) {
            for (int i = 0; i < list2.size(); i++) {
                lines.add(new LineItem(Type.ADDED, list2.get(i)));
            }
        }
        else if (list1.size() > list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                lines.add(new LineItem(Type.REMOVED, list1.get(i)));
            }
        }

        list1.clear();
        list2.clear();
    }


    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
    }
}
