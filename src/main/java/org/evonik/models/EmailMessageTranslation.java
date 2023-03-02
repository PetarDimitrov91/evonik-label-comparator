package org.evonik.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmailMessageTranslation extends Translation implements Comparable<EmailMessageTranslation> {
    private String emailPages;

    public EmailMessageTranslation(CSVRecord record) {
        super(record, 1);
        this.emailPages = record.get(0);
    }

    public static void writeHtml(String outputFileDir, Map<String, String[]> lines) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(outputFileDir + "/emailMessageDifferences.html", StandardCharsets.UTF_8));
        PrintWriter printer = new PrintWriter(writer);

        printer.println("<html>");
        printer.println("<head>");
        printer.println("<title>My HTML Table</title>");
        printer.println("<style>");
        printer.println("table { border-collapse: collapse; width: 100%; }");
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
        printer.println("<th>EmailPage</th>");
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

            String[] output = new String[5];

            System.arraycopy(keyArr, 0, output, 0, 1);
            System.arraycopy(values, 0, output, 1, 4);

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

    public static void writeCsv(String outputFileDir, Map<String, String[]> lines) throws IOException {
        FileWriter writer = new FileWriter(outputFileDir + "/emailMessageDifferences.csv", StandardCharsets.UTF_8);
        CSVPrinter printer = new CSVPrinter(writer,
                CSVFormat.DEFAULT
                        .withHeader("EmailPage", "Key", "P1 Value", "S1 Value", "Language")
                        .withDelimiter(';'));

        for (var entry : lines.entrySet()) {
            String emailPage = entry.getKey();
            String[] values = entry.getValue();
            String key = values[0];

            printer.printRecord(emailPage, key, values[0], values[1], values[2]);
        }

        printer.flush();
        writer.close();
    }

    @Override
    public int compareTo(EmailMessageTranslation o) {
        return this.getKey().compareTo(o.getKey());
    }

    public String getEmailPages() {
        return emailPages;
    }
}
