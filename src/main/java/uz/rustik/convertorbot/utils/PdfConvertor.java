package uz.rustik.convertorbot.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import uz.rustik.convertorbot.tool.Convertor;

import java.io.IOException;
import java.io.InputStream;

public class PdfConvertor extends Convertor {

    public String convert(InputStream inputStream, ConvertorType convertorType) throws IOException {
        String text = getStringFromPdfDocument(inputStream);

        if (convertorType.equals(ConvertorType.KRILL_UZB)) {
            text = convertKirillToUzb(text);
        } else {
            text = convertUzbToKirill(text);
        }
//        System.out.println(text);
        return text;
    }

    private String getStringFromPdfDocument(InputStream inputStream) throws IOException {

        PDDocument pdDocument = PDDocument.load(inputStream);


        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        String text = "";

        try {
            text = pdfTextStripper.getText(pdDocument);
        } catch (Exception e) {
            System.out.println("Can not get text from pdf!");
        }
//        System.out.println(text);
        return text;
    }

/* Just for Testing
public static void main(String[] args) {
        try {
            convert(new FileInputStream("/mnt/Personal/Portfolio/tranlator-bot/src/main/java/uz/rustik/tranlatorbot/tool/DV-2022-Instructions-Uzbek.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
