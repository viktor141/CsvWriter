package org.writer;

import org.writer.implement.CsvWriter;
import org.writer.model.Months;
import org.writer.model.Person;
import org.writer.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvWriter writer = new CsvWriter();

        List<Person> persons = List.of(
                Person.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .dayOfBirth(15)
                        .monthOfBirth(Months.JANUARY)
                        .yearOfBirth(1990)
                        .build(),
                Person.builder()
                        .firstName("Jane")
                        .lastName("Smith")
                        .dayOfBirth(20)
                        .monthOfBirth(Months.FEBRUARY)
                        .yearOfBirth(1985)
                        .build()
        );
        writer.writeToFile(persons, "persons.csv");

        List<Student> students = List.of(
                Student.builder()
                        .name("Alice")
                        .score(List.of("Math: A", "Science: B"))
                        .build(),
                Student.builder()
                        .name("Bob")
                        .score(List.of("Math: B", "Science: A"))
                        .build()
        );
        writer.writeToFile(students, "students.csv");
    }

}