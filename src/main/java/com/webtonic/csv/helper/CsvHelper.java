package com.webtonic.csv.helper;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHelper {

    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Student Number", "Firstname", "Lastname", "Course Code", "Course Description", "Grade"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }

    public static List<Student> csvToStudents(InputStream is) {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Student> studentList = new ArrayList<>();


            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Student student = new Student(Integer.parseInt(csvRecord.get(0)),csvRecord.get(1),csvRecord.get(2));
                Course course = new Course(csvRecord.get(3),csvRecord.get(4),csvRecord.get(5),student);

                Student studentSave = new Student(Integer.parseInt(csvRecord.get(0)),csvRecord.get(1),csvRecord.get(2),course);

                studentList.add(studentSave);

            }

            return studentList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream studentsToCSV(List<Student> studentList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Student student : studentList) {
                List<String> data = Arrays.asList(
                        String.valueOf(student.getStudentNumber()),
                        student.getFirstName(),
                        student.getLastName()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }


}
