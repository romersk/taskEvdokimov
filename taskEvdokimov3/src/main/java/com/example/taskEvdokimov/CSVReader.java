package com.example.taskEvdokimov;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSVReader {

    private List<String[]> allData;

    public CSVReader() {
        this.allData = new ArrayList<>();
    }

    public void parseFromFileToDB() throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource("logins.csv");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        this.readFromFile(absolutePath,',');

        res = getClass().getClassLoader().getResource("postings.csv");
        file = Paths.get(res.toURI()).toFile();
        String absolutePathNew = file.getAbsolutePath();
        this.readFromFile(absolutePathNew,';');
    }

    private void readFromFile(String path, char separator) {
        try {
            FileReader filereader = new FileReader(path);

            CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();

            if (path.contains("logins"))
            {
                com.opencsv.CSVReader csvReader = new CSVReaderBuilder(filereader)
                        .withCSVParser(parser)
                        .withSkipLines(1)
                        .build();

                allData = csvReader.readAll();
                this.toSaveIntoDBLogins();
            } else {
                com.opencsv.CSVReader csvReader = new CSVReaderBuilder(filereader)
                        .withCSVParser(parser)
                        .withSkipLines(2)
                        .build();

                allData = csvReader.readAll();
                this.toSaveIntoDBPostings();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toSaveIntoDBLogins() throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        for (String[] row:
                allData) {
            dbConnection.insertUsers(row[0], row[1].substring(1), Boolean.parseBoolean(row[2].substring(1)),
                    row[3].substring(1), row[4].substring(1));
        }
    }

    private void toSaveIntoDBPostings() throws SQLException, ParseException {
        DBConnection dbConnection = DBConnection.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd.MM.yyyy" );
        for (String[] row:
                allData) {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            Number number = format.parse(row[7].substring(1));

            LocalDate localDateOne = LocalDate.parse(row[2].substring(1), formatter);
            LocalDate localDateTwo = LocalDate.parse(row[3].substring(1), formatter);

            int idProduct = dbConnection.insertProduct(row[4].substring(1), number.doubleValue(),row[6].substring(1));
            dbConnection.insertSupply(Long.parseLong(row[0]), localDateOne.toString(), localDateTwo.toString(), row[9].substring(1), idProduct, Integer.parseInt(row[5].substring(1)));
        }
    }
}

