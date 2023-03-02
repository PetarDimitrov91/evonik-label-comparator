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
    private static Map<String, String[]> storefrontLines = new LinkedHashMap<>();
    private static Map<String, String[]> emailMessageTrnsLines = new LinkedHashMap<>();

    @Override
    public void run() {
        Scanner console = new Scanner(System.in);
        System.out.println("What do you want to compare?\nType 1 for Storefront Labels\nType 2 for EmailMessageTranslation\nType 3 for both");

        int translationSelection = Integer.parseInt(console.nextLine());

        String p1LabelTrnsFile = "/home/developer/Desktop/label-translations/p1.csv";
        String s1LabelTrnsFile = "/home/developer/Desktop/label-translations/s1.csv";
        String p1EmailTrnsFile = "/home/developer/Desktop/email-translations/p1-email.csv";
        String s1EmailTrnsFile = "/home/developer/Desktop/email-translations/s1-email.csv";
        String labelsOutputDir = "/home/developer/Desktop/label-translations";
        String emailTranslationsOutputDir = "/home/developer/Desktop/email-translations";

        try {
            if (translationSelection == 1) {
                compareStorefrontLabels(p1LabelTrnsFile, s1LabelTrnsFile, labelsOutputDir);
            } else if (translationSelection == 2) {
                compareEmailMessageTranslation(p1EmailTrnsFile, s1EmailTrnsFile, emailTranslationsOutputDir);
            } else {
                compareStorefrontLabels(p1LabelTrnsFile, s1LabelTrnsFile, labelsOutputDir);
                compareEmailMessageTranslation(p1EmailTrnsFile, s1EmailTrnsFile, emailTranslationsOutputDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void compareStorefrontLabels(String p1File, String s1File, String outputDir) throws IOException {
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

        storefrontLines = comparator.findNotExistingPropsInP1(storefrontLines, storefrontLabelsP1, storefrontLabelsS1);
        storefrontLines = comparator.findDiffsInStoreFrontLabel(storefrontLines, storefrontLabelsP1, storefrontLabelsS1);

        StorefrontLabel.writeHtml(outputDir, storefrontLines);
        StorefrontLabel.writeCSV(outputDir, storefrontLines);
    }


    private void compareEmailMessageTranslation(String p1File, String s1File, String outputDir) throws IOException {
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

        emailMessageTrnsLines = comparator.findNotExistingEmailMessageTranslationsInP1(emailMessageTrnsLines, emailMessagesP1, emailMessagesS1);
        emailMessageTrnsLines = comparator.findDiffsInEmailMessageTranslation(emailMessageTrnsLines, emailMessagesP1, emailMessagesS1);

        EmailMessageTranslation.writeHtml(outputDir, emailMessageTrnsLines);
        EmailMessageTranslation.writeCsv(outputDir, emailMessageTrnsLines);
    }
}
