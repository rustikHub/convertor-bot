package uz.rustik.convertorbot.utils;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {
    FLAG_UZ(EmojiParser.parseToUnicode(":uz:")),
    FLAG_US(EmojiParser.parseToUnicode(":us:")),
    FLAG_RU(EmojiParser.parseToUnicode(":ru:"));


    private final String emojiName;

    Emojis(String emojiName) {
        this.emojiName = emojiName;
    }

    @Override
    public String toString() {
        return emojiName;
    }
}
