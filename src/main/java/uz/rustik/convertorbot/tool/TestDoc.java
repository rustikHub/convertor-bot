package uz.rustik.convertorbot.tool;

import org.apache.poi.hwpf.HWPFDocument;

import java.io.*;

public class TestDoc {
    public static void main(String[] args) {
        File file;
        InputStream istream = null;
        try {
            istream = new FileInputStream("/mnt/Personal/Portfolio/tranlator-bot/src/main/java/uz/rustik/tranlatorbot/tool/Buxgalteriya hisobining maqsadi va vazifalari.doc");

            HWPFDocument hwpfDocument = new HWPFDocument(istream);

            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
