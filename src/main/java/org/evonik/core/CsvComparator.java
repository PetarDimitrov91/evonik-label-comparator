package org.evonik.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.commons.lang3.StringUtils;
import org.evonik.enums.Lang;
import org.evonik.models.StorefrontLabel;
import org.evonik.models.Translation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvComparator<T extends Translation> {
    public CsvComparator() {
    }

    public List<CSVRecord> readCsv(String fileLocation) throws IOException {
        Reader reader = new FileReader(fileLocation);

        CSVParser records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withDelimiter(';')
                .parse(reader);

        return new ArrayList<>(records.getRecords());
    }

    public List<T> convertFromCsvRecord(List<CSVRecord> records, Function<CSVRecord, T> mapper) {
        return records.stream().map(mapper).collect(Collectors.toList());
    }

    public Map<String, String[]> findNotExistingPropsInP1(Map<String, String[]> lines, List<? extends Translation> p1, List<? extends Translation> s1) {
        for (Translation translationS1 : s1) {
            boolean matchFound = false;
            String keyS1 = translationS1.getKey();

            for (Translation translationP1 : p1) {
                String keyP1 = translationP1.getKey();

                if (keyS1.equals(keyP1)) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                lines.put(keyS1, new String[]{" ------ Match not found", "", ""});
            }
        }

        return lines;
    }

    public Map<String, String[]> findDiffsInStoreFrontLabel(Map<String, String[]> lines, List<StorefrontLabel> p1, List<StorefrontLabel> s1) {
        int smallerFile = Math.min(p1.size(), s1.size());

        int counter = 0;
        for (int i = 0; i < smallerFile; i++) {
            StorefrontLabel recordP1 = p1.get(i);
            String keyP1 = recordP1.getKey();

            for (int j = 0; j < smallerFile; j++) {
                StorefrontLabel recordS1 = s1.get(j);
                String keyS1 = recordS1.getKey();

                if (keyP1.equals(keyS1)) {
                    counter++;

                    compareProps(lines, recordP1, recordS1, keyP1, keyS1);
                }
            }
        }

        lines.put("Matches found", new String[]{String.format("%d", counter), "", ""});

        return lines;
    }

    private void compareProps(Map<String, String[]> lines, Translation recordP1, Translation recordS1, String keyP1, String keyS1) {
        String diffLangEN = StringUtils.difference(recordP1.getContentEN().trim(), recordS1.getContentEN().trim());

        if (diffLangEN.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.EN, new String[]{recordP1.getContentEN(), recordS1.getContentEN(), Lang.EN.toString()});
        }

        String diffLangDE = StringUtils.difference(recordP1.getContentDE().trim(), recordS1.getContentDE().trim());
        if (diffLangDE.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.DE, new String[]{recordP1.getContentDE(), recordS1.getContentDE(), Lang.DE.toString()});
        }

        String diffLangIT = StringUtils.difference(recordP1.getContentIT().trim(), recordS1.getContentIT().trim());
        if (diffLangIT.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.IT, new String[]{recordP1.getContentIT(), recordS1.getContentIT(), Lang.IT.toString()});
        }

        String diffLangES = StringUtils.difference(recordP1.getContentES().trim(), recordS1.getContentES().trim());
        if (diffLangES.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.ES, new String[]{recordP1.getContentES(), recordS1.getContentES(), Lang.ES.toString()});
        }

        String diffLangRU = StringUtils.difference(recordP1.getContentRU().trim(), recordS1.getContentRU().trim());
        if (diffLangRU.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.RU, new String[]{recordP1.getContentRU(), recordS1.getContentRU(), Lang.RU.toString()});
        }

        String diffLangTR = StringUtils.difference(recordP1.getContentTR().trim(), recordS1.getContentTR().trim());
        if (diffLangTR.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.TR, new String[]{recordP1.getContentTR(), recordS1.getContentTR(), Lang.TR.toString()});
        }

        String diffLangPL = StringUtils.difference(recordP1.getContentPL().trim(), recordS1.getContentPL().trim());
        if (diffLangPL.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.PL, new String[]{recordP1.getContentPL(), recordS1.getContentPL(), Lang.PL.toString()});
        }

        String diffLangHU = StringUtils.difference(recordP1.getContentHU().trim(), recordS1.getContentHU().trim());
        if (diffLangHU.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.HU, new String[]{recordP1.getContentHU(), recordS1.getContentHU(), Lang.HU.toString()});
        }

        String diffLangFR = StringUtils.difference(recordP1.getContentFR().trim(), recordS1.getContentFR().trim());
        if (diffLangFR.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.FR, new String[]{recordP1.getContentFR(), recordS1.getContentFR(), Lang.FR.toString()});
        }

        String diffLangJA = StringUtils.difference(recordP1.getContentJA().trim(), recordS1.getContentJA().trim());
        if (diffLangJA.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.JA, new String[]{recordP1.getContentJA(), recordS1.getContentJA(), Lang.JA.toString()});
        }

        String diffLangZH = StringUtils.difference(recordP1.getContentZH().trim(), recordS1.getContentZH().trim());
        if (diffLangZH.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.ZH, new String[]{recordP1.getContentZH(), recordS1.getContentZH(), Lang.ZH.toString()});
        }

        String diffLangKO = StringUtils.difference(recordP1.getContentKO().trim(), recordS1.getContentKO().trim());
        if (diffLangKO.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.KO, new String[]{recordP1.getContentKO(), recordS1.getContentKO(), Lang.KO.toString()});
        }

        String diffLangRO = StringUtils.difference(recordP1.getContentRO().trim(), recordS1.getContentRO().trim());
        if (diffLangRO.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.RO, new String[]{recordP1.getContentRO(), recordS1.getContentRO(), Lang.RO.toString()});
        }

        String diffLangCS = StringUtils.difference(recordP1.getContentCS().trim(), recordS1.getContentCS().trim());
        if (diffLangCS.length() > 0) {
            lines.put(keyP1 + " -- " + Lang.CS, new String[]{recordP1.getContentCS(), recordS1.getContentCS(), Lang.CS.toString()});
        }
    }
}
