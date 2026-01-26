package repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Repository {

    public final static String PATH = "/data.csv";
    String pathToFile;
    List<String[]> records = new ArrayList<>();

    private Repository(String pathToFile) {
        this.pathToFile = pathToFile;
        records = getDataFromCSV(pathToFile);
    }

    public Repository(List<String[]> records) {
        this.records = records;
    }

    public static Repository getRepository() {
        return new Repository(PATH);
    }

    List<String[]> getDataFromCSV(String path) {
        List<String[]> recordsList = new ArrayList<>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(path);
            assert stream != null;
            Reader in = new BufferedReader(new InputStreamReader(stream));
            Iterable<CSVRecord> records = CSVFormat.EXCEL.builder().setDelimiter(';').get().parse(in);
            for (CSVRecord record : records) {
                String[] values = new String[record.size()];
                for (int i = 0; i < record.size(); i++) {
                    values[i] = record.get(i);
                }
                recordsList.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return recordsList;
    }

    public List<String[]> getRecords() {
        return records;
    }
}

