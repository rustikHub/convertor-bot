package uz.rustik.convertorbot.utils;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
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
        return text;
    }

    private String getStringFromPdfDocument(InputStream inputStream) throws IOException {
        PDFParser pdfParser = new PDFParser(new RandomAccessReadBuffer(inputStream));

        COSDocument cosDocument = pdfParser.getDocument();
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        PDDocument pdDocument = new PDDocument(cosDocument);

        return pdfTextStripper.getText(pdDocument);
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
