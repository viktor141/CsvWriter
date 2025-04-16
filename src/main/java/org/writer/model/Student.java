package org.writer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.writer.annotation.CsvField;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Student {

    @CsvField
    private String name;

    @CsvField
    private List<String> score;
}