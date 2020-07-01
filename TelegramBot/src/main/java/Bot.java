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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
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
            switch (getFirstWord(message.getText())) {
                case Constants.dateCheckText:
                    text = DateService.getDate();
                    break;
                case Constants.timeCheckText:
                    text = DateService.getTime();
                    break;
                case Constants.locationCheckText:
                    try {
                        WeatherService.getWeather(message.getText().replace(Constants.locationCheckText, ""), weatherModel);
                        text = Constants.locationHasChanged;
                        WeatherModel.setCityByDefault(message.getText().replace(Constants.locationCheckText, "").replace(" ", ""));
                    } catch (IOException e) {
                        text = Constants.locationHasntChanged;
                    }
                    break;
                case Constants.translateCheckText:
                    text = TranslateService.getTranslatedText(message.getText().replace(Constants.translateCheckText, ""));
                    break;
                case Constants.weatherCheckText:
                    try {
                        text = WeatherService.getWeather(message.getText(), weatherModel);
                    } catch (IOException e) {
                        text = Constants.cityNotFound;
                    }
                    break;
                default:
                    text = Constants.helpMessageText;
                    break;
            }
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
        keyboardFirstRow.add(new KeyboardButton(Constants.helpButtonText));
        keyboardFirstRow.add(new KeyboardButton(Constants.dateButtonText));
        keyboardFirstRow.add(new KeyboardButton(Constants.timeButtonText));
        keyboardFirstRow.add(new KeyboardButton(Constants.weatherButtonText));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    private static final String botUsername = "rokirovochkaBot";
    private static final String botToken = "1233354824:AAFMUH8DNFKjiOJoPHLU_UDSJYF79Yht0cc";

}
