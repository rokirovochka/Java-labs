package main;

import main.commands.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        initCommandsMap();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getFirstWord(String text) {
        int index = text.indexOf(' ');
        final int beginIndex = 0;
        if (text.contains(" ")) {
            return text.substring(beginIndex, index);
        } else {
            return text;
        }
    }

    public void onUpdateReceived(Update update) {
        WeatherModel weatherModel = new WeatherModel();
        Message message = update.getMessage();
        String text;
        if (message != null && message.hasText()) {
            if (!commands.containsKey(getFirstWord(message.getText())))
                text = Constants.HELP_MESSAGE_TEXT;
            else
                text = commands.get(getFirstWord(message.getText())).execute(message, weatherModel);
            sendMsg(message, text);
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(Constants.HELP_BUTTON_TEXT));
        keyboardFirstRow.add(new KeyboardButton(Constants.DATE_BUTTON_TEXT));
        keyboardFirstRow.add(new KeyboardButton(Constants.TIME_BUTTON_TEXT));
        keyboardFirstRow.add(new KeyboardButton(Constants.WEATHER_BUTTON_TEXT));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void initCommandsMap() {
        commands = new HashMap<>();
        commands.put(Constants.DATE_CHECK_TEXT, new DateCommand());
        commands.put(Constants.HELP_CHECK_TEXT, new HelpCommand());
        commands.put(Constants.LOCATION_CHECK_TEXT, new LocationCommand());
        commands.put(Constants.TIME_CHECK_TEXT, new TimeCommand());
        commands.put(Constants.TRANSLATE_CHECK_TEXT, new TranslateCommand());
        commands.put(Constants.WEATHER_CHECK_TEXT, new WeatherCommand());
    }

    private static final String BOT_USERNAME = "rokirovochkaBot";
    private static final String BOT_TOKEN = "1233354824:AAFMUH8DNFKjiOJoPHLU_UDSJYF79Yht0cc";
    private static Map<String, Command> commands;
}
