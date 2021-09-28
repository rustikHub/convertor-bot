package uz.rustik.convertorbot.tool;

import java.util.HashMap;

public class Convertor {

    public enum ConvertorType {
        UZB_KRILL,
        KRILL_UZB
    }

    public HashMap<String, String> uzb_kril;
    private HashMap<String, String> kril_uzb;

    public Convertor() {
        init();
    }

    private void init() {
        uzb_kril = new HashMap<>();
        kril_uzb = new HashMap<>();

        uzb_kril.put("q", "қ");
        uzb_kril.put("e", "е");
        uzb_kril.put("r", "р");
        uzb_kril.put("t", "т");
        uzb_kril.put("y", "й");
        uzb_kril.put("u", "у");
        uzb_kril.put("i", "и");
        uzb_kril.put("o", "о");
        uzb_kril.put("p", "п");
        uzb_kril.put("a", "а");
        uzb_kril.put("s", "с");
        uzb_kril.put("d", "д");
        uzb_kril.put("f", "ф");
        uzb_kril.put("g", "г");
        uzb_kril.put("h", "ҳ");
        uzb_kril.put("j", "ж");
        uzb_kril.put("k", "к");
        uzb_kril.put("l", "л");
        uzb_kril.put("z", "з");
        uzb_kril.put("x", "х");
        uzb_kril.put("v", "в");
        uzb_kril.put("b", "б");
        uzb_kril.put("n", "н");
        uzb_kril.put("m", "м");
        uzb_kril.put("'", "ь");
        uzb_kril.put("sh", "ш");
        uzb_kril.put("ch", "ч");
        uzb_kril.put("ng", "нг");
        uzb_kril.put("g'", "ғ");
        uzb_kril.put("o'", "ў");
        uzb_kril.put("o‘", "ў");
        uzb_kril.put("g‘", "ғ");
        uzb_kril.put(" ", " ");
        uzb_kril.put("yo", "ё");
        uzb_kril.put("yu", "ю");
        uzb_kril.put("ya", "я");
        uzb_kril.put("ye","е");
        uzb_kril.put(" e", " э");
        uzb_kril.put("ts", "ц");
        uzb_kril.put("Ts", "Ц");
        uzb_kril.put("TS", "Ц");

        uzb_kril.put("Q", "Қ");
        uzb_kril.put("E", "Е");
        uzb_kril.put("R", "Р");
        uzb_kril.put("T", "Т");
        uzb_kril.put("Y", "Й");
        uzb_kril.put("U", "У");
        uzb_kril.put("I", "И");
        uzb_kril.put("O", "О");
        uzb_kril.put("P", "П");
        uzb_kril.put("A", "А");
        uzb_kril.put("S", "С");
        uzb_kril.put("D", "Д");
        uzb_kril.put("F", "Ф");
        uzb_kril.put("G", "Г");
        uzb_kril.put("H", "Ҳ");
        uzb_kril.put("J", "Ж");
        uzb_kril.put("K", "К");
        uzb_kril.put("Z", "З");
        uzb_kril.put("X", "Х");
        uzb_kril.put("V", "В");
        uzb_kril.put("B", "Б");
        uzb_kril.put("N", "Н");
        uzb_kril.put("M", "М");
        uzb_kril.put(" E", "Э");

        uzb_kril.put("Sh", "Ш");
        uzb_kril.put("Ch", "Ч");
        uzb_kril.put("Ng", "НГ");
        uzb_kril.put("SH", "Ш");
        uzb_kril.put("CH", "Ч");
        uzb_kril.put("NG", "НГ");
        uzb_kril.put("G'", "Ғ");

        uzb_kril.put("G‘", "Ғ");
        uzb_kril.put("O‘", "Ў");
        uzb_kril.put("O'", "Ў");
        uzb_kril.put("Yo", "Ё");
        uzb_kril.put("YO", "Ё");
        uzb_kril.put("YU", "Ю");
        uzb_kril.put("Yu", "Ю");
        uzb_kril.put("Ya", "Я");
        uzb_kril.put("YA", "Я");
        uzb_kril.put("Ye","Е");
        uzb_kril.put("YE","Е");

        kril_uzb.put("Қ", "Q");
        kril_uzb.put("Е", "E");
        kril_uzb.put("Э","E");
        kril_uzb.put("Р", "R");
        kril_uzb.put("Т", "T");
        kril_uzb.put("Й", "Y");
        kril_uzb.put("У", "U");
        kril_uzb.put("И", "I");
        kril_uzb.put("О", "O");
        kril_uzb.put("П", "P");
        kril_uzb.put("А", "A");
        kril_uzb.put("С", "S");
        kril_uzb.put("Д", "D");
        kril_uzb.put("Ф", "F");
        kril_uzb.put("Г", "G");
        kril_uzb.put("Ҳ", "H");
        kril_uzb.put("Ж", "J");
        kril_uzb.put("К", "K");
        kril_uzb.put("Л", "L");
        kril_uzb.put("З", "Z");
        kril_uzb.put("Х", "X");
        kril_uzb.put("В", "V");
        kril_uzb.put("Б", "B");
        kril_uzb.put("Н", "N");
        kril_uzb.put("М", "M");
        kril_uzb.put("Ь", "'");
        kril_uzb.put("Ш", "Sh");
        kril_uzb.put("Ч", "Ch");
        kril_uzb.put("Нг", "Ng");
        kril_uzb.put("Ғ", "G‘");
        kril_uzb.put("Ў", "O‘");
        kril_uzb.put("Ё", "Yo");
        kril_uzb.put("Ю", "Yu");
        kril_uzb.put("Я", "Ya");

        kril_uzb.put("қ", "q");
        kril_uzb.put("е", "e");
        kril_uzb.put("р", "r");
        kril_uzb.put("т", "t");
        kril_uzb.put("й", "y");
        kril_uzb.put("у", "u");
        kril_uzb.put("и", "i");
        kril_uzb.put("о", "o");
        kril_uzb.put("п", "p");
        kril_uzb.put("а", "a");
        kril_uzb.put("с", "s");
        kril_uzb.put("д", "d");
        kril_uzb.put("ф", "f");
        kril_uzb.put("г", "g");
        kril_uzb.put("ҳ", "h");
        kril_uzb.put("ж", "j");
        kril_uzb.put("к", "k");
        kril_uzb.put("л", "l");
        kril_uzb.put("з", "z");
        kril_uzb.put("х", "x");
        kril_uzb.put("в", "v");
        kril_uzb.put("б", "b");
        kril_uzb.put("н", "n");
        kril_uzb.put("м", "m");
        kril_uzb.put("ь", "'");
        kril_uzb.put("ш", "sh");
        kril_uzb.put("ч", "ch");
        kril_uzb.put("нг", "ng");
        kril_uzb.put("ғ", "g‘");
        kril_uzb.put("ў", "o‘");
        kril_uzb.put(" ", " ");
        kril_uzb.put("ъ","'");
        kril_uzb.put("э", "e");
        kril_uzb.put("ё", "yo");
        kril_uzb.put("ю", "yu");
        kril_uzb.put("я", "ya");
        kril_uzb.put("ц", "ts");
        kril_uzb.put("Ц", "Ts");

    }

    public String convertUzbToKirill(String text) {
        StringBuilder kirillText = new StringBuilder();
        for (int i = 1; i <= text.length(); i++) {
            if (text.length() != i && uzb_kril.containsKey(text.charAt(i - 1) + String.valueOf(text.charAt(i)))) {
                kirillText.append(uzb_kril.get(text.charAt(i - 1) + String.valueOf(text.charAt(i))));
                i++;
            } else if (uzb_kril.containsKey(" " + text.charAt(i - 1)) && i - 1 == 0) {
                kirillText.append(uzb_kril.get(" " + text.charAt(0)).replace(" ", ""));
            } else if (uzb_kril.containsKey(String.valueOf(text.charAt(i - 1)))) {
                kirillText.append(uzb_kril.get(String.valueOf(text.charAt(i - 1))));
            } else {
                kirillText.append(text.charAt(i - 1));
            }
            if (String.valueOf(text.charAt(i - 1)).equals(".") || String.valueOf(text.charAt(i - 1)).equals(";")) {
                kirillText.append("\n");
            }
        }
        return kirillText.toString();
    }

    public String convertKirillToUzb(String text) {

        StringBuilder uzbText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char key = text.charAt(i);

            if (kril_uzb.containsKey(Character.toString(key))) {
                String value = kril_uzb.get(Character.toString(key));
                if (Character.isUpperCase(key)) {
                    uzbText.append(value.replaceFirst(String.valueOf(value.charAt(0)), String.valueOf(value.charAt(0)).toUpperCase()));
                } else {
                    uzbText.append(value);
                }
            } else
                uzbText.append(text.charAt(i));
        }
        return uzbText.toString();
    }


}
