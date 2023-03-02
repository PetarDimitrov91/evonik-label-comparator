package org.evonik.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class StorefrontLabel extends Translation implements Comparable<StorefrontLabel> {
    public StorefrontLabel(CSVRecord record) {
        super(record, 0);
    }

    public static void writeHtml(String outputFileDir, Map<String, String[]> lines) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(outputFileDir + "/labelDifferences.html", StandardCharsets.UTF_8));
        PrintWriter printer = new PrintWriter(writer);

        printer.println("<html>");
        printer.println("<head>");
        printer.println("<title>My HTML Table</title>");
        printer.println("<style>");
        printer.println("table { border-collapse: collapse; }");
        printer.println("table, th, td { border: 1px solid black; }");
        printer.println("th, td { padding: 5px; }");
        printer.println(".linenum { background-color: #F0F0F0; font-weight: bold; }");
        printer.println("</style>");
        printer.println("</head>");
        printer.println("<body>");
        printer.println("<table>");

        // Write the table header
        printer.println("<tr>");
        printer.println("<th class=\"linenum\">#</th>");
        printer.println("<th>Key</th>");
        printer.println("<th>P1 Value</th>");
        printer.println("<th>S1 Value</th>");
        printer.println("<th>Language</th>");
        printer.println("</tr>");

        // Write the table data
        int lineNumber = 1;

        for (var entry : lines.entrySet()) {
            String key = entry.getKey();
            String[] keyArr = {key};
            String[] values = entry.getValue();

            String[] output = new String[4];

            System.arraycopy(keyArr, 0, output, 0, 1);
            System.arraycopy(values, 0, output, 1, 3);

            printer.println("<tr>");
            printer.println("<td class=\"linenum\">" + lineNumber++ + "</td>");

            for (String value : output) {
                printer.println("<td>" + value + "</td>");
            }

            printer.println("</tr>");
        }

        printer.println("</table>");
        printer.println("</body>");
        printer.println("</html>");

        printer.flush();
        writer.close();
    }

    public static void writeCSV(String outputFileDir, Map<String, String[]> lines) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get(outputFileDir, "labelDifferences.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT
                        .withHeader("Key", "P1 Value", "S1 Value", "Language")
                        .withDelimiter(';')
        );

        for (var entry : lines.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            csvPrinter.printRecord(key, values[0], values[1], values[2]);
        }

        csvPrinter.flush();
        writer.close();
    }

    @Override
    public int compareTo(StorefrontLabel o) {
        return this.getKey().compareTo(o.getKey());
    }
}
