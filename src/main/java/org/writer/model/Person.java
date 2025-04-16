package org.writer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.writer.annotation.CsvField;

@Data
@Builder
@AllArgsConstructor
public class Person {

    @CsvField(name = "First Name")
    private String firstName;

    @CsvField(name = "Last Name")
    private String lastName;

    @CsvField
    private int dayOfBirth;

    @CsvField
    private Months monthOfBirth;

    @CsvField
    private int yearOfBirth;
}
