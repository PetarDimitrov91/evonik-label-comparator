package org.evonik.core;

import org.apache.commons.csv.CSVRecord;
import org.evonik.models.EmailMessageTranslation;
import org.evonik.models.StorefrontLabel;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Application implements Runnable {
    private static Map<String, String[]> lines = new LinkedHashMap<>();

    @Override
    public void run() {
        Scanner console = new Scanner(System.in);
        System.out.println("What do you want to compare?\nType 1 for Storefront Labels\nType 2 for EmailMessageTranslation");

        int translationSelection = Integer.parseInt(console.nextLine());

        String p1File = "/home/developer/Desktop/label-translations/p1.csv";
        String s1File = "/home/developer/Desktop/label-translations/s1.csv";
        String csvOutputFile = "/home/developer/Desktop/label-translations";
        String htmlOutputFile = "/home/developer/Desktop/label-translations";

        try {
            if (translationSelection == 1) {
                compareStorefrontLabels(p1File, s1File, csvOutputFile, htmlOutputFile);
            } else if (translationSelection == 2) {
                compareEmailMessageTranslation(p1File, s1File, csvOutputFile, htmlOutputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void compareStorefrontLabels(String p1File, String s1File, String csvOutputFile, String htmlOutputFile) throws IOException {
        final CsvComparator<StorefrontLabel> comparator = new CsvComparator<>();

        List<CSVRecord> p1Records = comparator.readCsv(p1File);
        List<CSVRecord> s1Records = comparator.readCsv(s1File);

        List<StorefrontLabel> storefrontLabelsP1 = comparator.convertFromCsvRecord(p1Records, StorefrontLabel::new)
                .stream()
                .sorted((StorefrontLabel::compareTo))
                .toList();

        List<StorefrontLabel> storefrontLabelsS1 = comparator.convertFromCsvRecord(s1Records, StorefrontLabel::new)
                .stream()
                .sorted((StorefrontLabel::compareTo))
                .toList();

        lines = comparator.findNotExistingPropsInP1(lines, storefrontLabelsP1, storefrontLabelsS1);
        lines = comparator.findDiffsInStoreFrontLabel(lines, storefrontLabelsP1, storefrontLabelsS1);

        StorefrontLabel.writeHtml(htmlOutputFile, lines);
    }


    private void compareEmailMessageTranslation(String p1File, String s1File, String csvOutputFile, String htmlOutputFile) throws IOException {
        final CsvComparator<EmailMessageTranslation> comparator = new CsvComparator<>();

        List<CSVRecord> p1Records = comparator.readCsv(p1File);
        List<CSVRecord> s1Records = comparator.readCsv(s1File);

        List<EmailMessageTranslation> emailMessagesP1 = comparator.convertFromCsvRecord(p1Records, EmailMessageTranslation::new)
                .stream()
                .sorted((EmailMessageTranslation::compareTo))
                .toList();

        List<EmailMessageTranslation> emailMessagesS1 = comparator.convertFromCsvRecord(s1Records, EmailMessageTranslation::new)
                .stream()
                .sorted((EmailMessageTranslation::compareTo))
                .toList();
    }
}
