package uz.rustik.convertorbot.tool;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TestDocx {
    public static void main(String[] args) {
        Convertor converter = new Convertor();

        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(OPCPackage.open("/mnt/Personal/Portfolio/tranlator-bot/src/main/java/uz/rustik/tranlatorbot/tool/Buxgalteriya hisobining maqsadi va vazifalari.docx"));

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        System.out.println(text);
                        if (text != null) {
                            text = converter.convertUzbToKirill(text);
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
                                    text = converter.convertUzbToKirill(text);
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }

            doc.write(new FileOutputStream("/mnt/Personal/Portfolio/tranlator-bot/src/main/java/uz/rustik/tranlatorbot/tool/Buxgalteriya hisobining maqsadi va vazifalari1.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


    }
}
