package uz.rustik.convertorbot.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import uz.rustik.convertorbot.tool.Convertor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DocxConvertor extends Convertor {

    public File convertor(InputStream inputStream, ConvertorType convertorType) {
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(OPCPackage.open(inputStream));

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null) {
                            if (convertorType.equals(ConvertorType.KRILL_UZB)) {
                                text = convertKirillToUzb(text);
                            } else {
                                text = convertUzbToKirill(text);
                            }
                            r.setText(text, 0);
                        }
                    }
                }
            }

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null) {
                                    if (convertorType.equals(ConvertorType.KRILL_UZB)) {
                                        text = convertKirillToUzb(text);
                                    } else {
                                        text = convertUzbToKirill(text);
                                    }
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }

            String filePath = "src/main/resources/temp_files/docxText.docx";

            FileOutputStream outputStream = new FileOutputStream(filePath);
            doc.write(outputStream);
            outputStream.close();
            return new File(filePath);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
