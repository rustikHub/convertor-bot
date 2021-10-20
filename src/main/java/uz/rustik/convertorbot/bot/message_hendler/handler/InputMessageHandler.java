package uz.rustik.convertorbot.bot.message_hendler.handler;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.rustik.convertorbot.bot.TranslatorBot;
import uz.rustik.convertorbot.bot.message_hendler.InputMessageHandlerInterface;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.service.ChatService;
import uz.rustik.convertorbot.service.MessagesService;
import uz.rustik.convertorbot.tool.Convertor;
import uz.rustik.convertorbot.utils.DocxConvertor;
import uz.rustik.convertorbot.utils.PdfConvertor;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class InputMessageHandler implements InputMessageHandlerInterface {

    private final ChatService chatService;
    private final Convertor convertor;
    private final MessagesService messagesService;
    private final TranslatorBot translatorBot;
    private final PdfConvertor pdfConvertor;
    private final DocxConvertor docxConvertor;

    @Value("${telegram.bot.token}")
    private String token;

    public InputMessageHandler(ChatService chatService, Convertor convertor, MessagesService messagesService, @Lazy TranslatorBot translatorBot, PdfConvertor pdfConvertor, DocxConvertor docxConvertor) {
        this.chatService = chatService;
        this.convertor = convertor;
        this.messagesService = messagesService;
        this.translatorBot = translatorBot;
        this.pdfConvertor = pdfConvertor;
        this.docxConvertor = docxConvertor;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        System.out.println("worked");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        String messageText = message.getText();
        Chat chat = chatService.findByChatId(message.getChatId());

        File downloadedFile = null;
        String fileName;
        String fileType = "";

        if (message.hasDocument()) {
            downloadedFile = downloadFile(message.getDocument(), token);

            if (downloadedFile == null) {
                sendMessage.setText(messagesService.getMessage("system.error.file.download", chat.getLocale()));
                return sendMessage;
            }

            fileName = downloadedFile.getName();
            fileType = fileName.substring(fileName.indexOf(".") + 1);
        }

        switch (chat.getBotState()) {

            case KRILL_TO_UZB_HANDLER: {

                if (message.hasDocument()) {

                    switch (fileType) {
                        case "pdf": {
                            SendDocument sendDocument = getPdfConvert(downloadedFile, chat, Convertor.ConvertorType.KRILL_UZB);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);

                            break;
                        }
                        case "docx": {

                            SendDocument sendDocument = getDocxConvert(downloadedFile, chat, Convertor.ConvertorType.KRILL_UZB);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);
                            break;
                        }
                        case "txt": {
                            SendDocument sendDocument = getTxtConvert(downloadedFile, chat, Convertor.ConvertorType.KRILL_UZB);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);
                            break;
                        }
                        default: {
                            sendMessage.setText(messagesService.getMessage("system.error.file.type", chat.getLocale()));
                            translatorBot.sendMessage(sendMessage);

                            sendMessage.setText(messagesService.getMessage("message.kirill.to.uzbek", chat.getLocale()));
                            chat.setBotState(BotState.KRILL_TO_UZB_HANDLER);
                            chatService.saveOrUpdate(chat);


                            return sendMessage;
                        }
                    }

                } else {
                    String convertedMessageText = convertor.convertKirillToUzb(messageText);
                    sendMessage.setText(convertedMessageText);

                    translatorBot.sendMessage(sendMessage);

                }
                sendMessage.setText(messagesService.getMessage("message.kirill.to.uzbek", chat.getLocale()));
                chat.setBotState(BotState.KRILL_TO_UZB_HANDLER);
                chatService.saveOrUpdate(chat);
                break;
            }
            case UZB_TO_KRILL_HANDLER: {
                if (message.hasDocument()) {

                    switch (fileType) {
                        case "pdf": {
                            SendDocument sendDocument = getPdfConvert(downloadedFile, chat, Convertor.ConvertorType.UZB_KRILL);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);

                            break;
                        }
                        case "docx": {

                            SendDocument sendDocument = getDocxConvert(downloadedFile, chat, Convertor.ConvertorType.UZB_KRILL);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);
                            break;
                        }
                        case "txt": {
                            SendDocument sendDocument = getTxtConvert(downloadedFile, chat, Convertor.ConvertorType.UZB_KRILL);

                            if (sendDocument == null) {
                                sendMessage.setText(messagesService.getMessage("system.error.converting", chat.getLocale()));
                                return sendMessage;
                            }

                            translatorBot.sendDocument(sendDocument);
                            break;
                        }
                        default: {
                            sendMessage.setText(messagesService.getMessage("system.error.file.type", chat.getLocale()));
                            translatorBot.sendMessage(sendMessage);

                            sendMessage.setText(messagesService.getMessage("message.uzbek.to.kirill", chat.getLocale()));
                            chat.setBotState(BotState.UZB_TO_KRILL_HANDLER);
                            chatService.saveOrUpdate(chat);


                            return sendMessage;
                        }
                    }

                } else {
                    String convertedMessageText = convertor.convertUzbToKirill(messageText);
                    sendMessage.setText(convertedMessageText);

                    translatorBot.sendMessage(sendMessage);
                }
                sendMessage.setText(messagesService.getMessage("message.uzbek.to.kirill", chat.getLocale()));
                chat.setBotState(BotState.UZB_TO_KRILL_HANDLER);
                chatService.saveOrUpdate(chat);
                break;
            }
        }

        return sendMessage;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.MESSAGE_HANDLER;
    }

    @Override
    public BotState backState(Message message) {
        return BotState.MAIN_MENU;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private SendDocument getTxtConvert(File downloadedFile, Chat chat, Convertor.ConvertorType convertorType) {
        try {
            String allText = readFile(downloadedFile.getPath(), StandardCharsets.UTF_8);
            String convertedText;

            if (convertorType.equals(Convertor.ConvertorType.KRILL_UZB)) {
                convertedText = convertor.convertKirillToUzb(allText);
            } else {
                convertedText = convertor.convertUzbToKirill(allText);
            }

            Path path = Files.write(Paths.get(downloadedFile.getPath()), convertedText.getBytes(StandardCharsets.UTF_8));
            File temp_file = path.toFile();

            return new SendDocument(chat.getId().toString(), new InputFile(temp_file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SendDocument getDocxConvert(File downloadedFile, Chat chat, Convertor.ConvertorType convertorType) {
        try {
            File convertedDocxFile = docxConvertor.convertor(new FileInputStream(downloadedFile), convertorType);
            return new SendDocument(chat.getId().toString(), new InputFile(convertedDocxFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SendDocument getPdfConvert(File downloadedFile, Chat chat, Convertor.ConvertorType convertorType) {
        String convertedText;

        try {
            convertedText = pdfConvertor.convert(new FileInputStream(downloadedFile), convertorType);
            if (convertedText.isEmpty()) {
                return null;
            }
            Path path = Files.write(Paths.get("src/main/resources/temp_files/" + hash(chat.getId()) + ".txt"), convertedText.getBytes(StandardCharsets.UTF_8));
            File temp_File = path.toFile();

            return new SendDocument(chat.getId().toString(), new InputFile(temp_File));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String hash(Long chatId) {
        long hash = 2;
        return String.valueOf(chatId * hash);
    }

    public File downloadFile(Document document, String token) {
        try {
            URL url = new URL("https://api.telegram.org/bot" + token + "/getFile?file_id=" + document.getFileId());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println(bufferedReader);

            String getFileResponse;

            getFileResponse = bufferedReader.readLine();
            System.out.println(getFileResponse);

            JSONObject jsonObject = new JSONObject(getFileResponse);
            String filePath = jsonObject
                    .getJSONObject("result")
                    .getString("file_path");

            System.out.println(filePath);

            String fileName = document.getFileName();
            File temp_File = new File("src/main/resources/temp_files/" + hash(new Date().getTime()) + fileName.substring(fileName.lastIndexOf(".")));

            InputStream inputStream = new URL("https://api.telegram.org/file/bot" +
                    token + "/" + filePath).openStream();

            FileUtils.copyInputStreamToFile(inputStream, temp_File);

            bufferedReader.close();
            inputStream.close();
            return temp_File;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
  /* ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboard(false);

                List<KeyboardRow> keyboardRows = new ArrayList<>();

                KeyboardRow row1 = new KeyboardRow();
                row1.add(messagesService.getMessage("system.menu.kirill.to.uzbek", chat.getLocale()));
                row1.add(messagesService.getMessage("system.menu.uzbek.to.kirill", chat.getLocale()));

                keyboardRows.add(row1);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);*/