import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.writer.implement.CsvWriter;
import org.writer.model.Months;
import org.writer.model.Person;
import org.writer.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvWriterTest {

    private final CsvWriter writer = new CsvWriter();

    @TempDir
    Path tempDir;

    @Test
    public void testWritePersons() throws IOException {
        List<Person> persons = List.of(
                Person.builder().firstName("John").lastName("Doe").dayOfBirth(15).monthOfBirth(Months.JANUARY).yearOfBirth(1990).build()
        );
        String filePath = tempDir.resolve("persons.csv").toString();
        writer.writeToFile(persons, filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            assertEquals("First Name,Last Name,dayOfBirth,monthOfBirth,yearOfBirth", reader.readLine());
            assertEquals("John,Doe,15,JANUARY,1990", reader.readLine());
        }
    }

    @Test
    public void testWriteStudentsWithList() throws IOException {
        List<Student> students = List.of(
                Student.builder().name("Alice").score(List.of("Math: A", "Science: B")).build()
        );
        String filePath = tempDir.resolve("students.csv").toString();
        writer.writeToFile(students, filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            assertEquals("name,score", reader.readLine());
            assertEquals("Alice,Math: A;Science: B", reader.readLine());
        }
    }

    @Test
    public void testSpecialCharacters() throws IOException {
        List<Person> persons = List.of(
                Person.builder().firstName("John, Jr.").lastName("Doe").dayOfBirth(1).monthOfBirth(Months.JANUARY).yearOfBirth(2000).build()
        );
        String filePath = tempDir.resolve("special.csv").toString();
        writer.writeToFile(persons, filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            assertEquals("First Name,Last Name,dayOfBirth,monthOfBirth,yearOfBirth", reader.readLine());
            assertEquals("\"John, Jr.\",Doe,1,JANUARY,2000", reader.readLine());
        }
    }
}