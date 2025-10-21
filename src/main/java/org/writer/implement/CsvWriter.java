package org.writer.implement;

import org.writer.Writable;
import org.writer.annotation.CsvField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter implements Writable {

    /**
     * Writes a list of objects to a CSV file using fields annotated with @CsvField.
     *
     * @param data     List of objects to write
     * @param fileName Name of the CSV file to create
     * @throws NullPointerException if data or fileName is null
     * @throws IllegalArgumentException if data or fileName is empty
     */
    @Override
    public void writeToFile(List<?> data, String fileName) {
        if (data == null)
            throw new NullPointerException("data is null or empty");
        if (fileName == null)
            throw new NullPointerException("fileName is null");

        if (fileName.isEmpty())
            throw new IllegalArgumentException("fileName is empty");
        if (data.isEmpty())
            throw new IllegalArgumentException("data is empty");

        Class<?> clazz = data.get(0).getClass();
        List<Field> csvFields = getCsvFields(clazz);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header row
            String header = csvFields.stream()
                    .map(this::getColumnName)
                    .collect(Collectors.joining(","));
            writer.write(header);
            writer.newLine();

            // Write each data row
            for (Object obj : data) {
                String row = csvFields.stream()
                        .map(field -> getFieldValue(field, obj))
                        .collect(Collectors.joining(","));
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + fileName, e);
        }
    }

    /**
     * Retrieves all fields annotated with @CsvField from the given class.
     *
     * @param clazz Class to inspect
     * @return List of annotated fields
     */
    private List<Field> getCsvFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(CsvField.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Gets the column name for a field, using the @CsvField name if specified.
     *
     * @param field Field to process
     * @return Column name
     */
    private String getColumnName(Field field) {
        CsvField annotation = field.getAnnotation(CsvField.class);
        String name = annotation.name();
        return name.isEmpty() ? field.getName() : name;
    }

    /**
     * Extracts the value of a field from an object, handling special cases like collections.
     *
     * @param field Field to access
     * @param obj   Object instance
     * @return String representation of the field value
     */
    private String getFieldValue(Field field, Object obj) {
        try {
            field.setAccessible(true); // Access private fields
            Object value = field.get(obj);
            if (value == null) {
                return "";
            } else if (value instanceof List) {
                List<?> list = (List<?>) value;
                String listStr = list.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(";"));
                return listStr; // Treat as a single CSV field
            } else {
                return value.toString();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }

}
