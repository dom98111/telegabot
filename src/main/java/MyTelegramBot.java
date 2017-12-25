import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.*;

import static javax.swing.UIManager.get;

public class MyTelegramBot extends TelegramLongPollingBot {

   boolean test = true;


    @Override
    public String getBotUsername() {
        return "LahtaTennisBot";
    }

    @Override

    public String getBotToken() {
        return "505716268:AAGRLQXn8ob0LbjcjiwrNZXpWQSl6hfPoCg";
    }



    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();
        String message = update.getMessage().getText();
        if (){
            switch (message) {
                case "/запись":     //запустить процесс записи человека
                    sendMessage("Введите ваше имя:", chatId);
                    addModeName =  true;
                    break;
                case "/отмена":
                    hideKeyboard(message, chatId);
                    break;
                default:
                    sendMessage("скорее всего вы ошиблись в команде", chatId);
            }
        }

    }


    private void sendBitcoinPrice (

    ){

    }


    private void sendMessage(String text, long chatId){
        SendMessage sendMessage = new SendMessage()
                .setText(text)
                .setChatId(chatId);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void showKeyboard(String text, long chatId){
        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/курс " + convertDate(1514160001));
        row.add("/отмена");
        keyboard.add(row);
        rkm.setKeyboard(keyboard);


        SendMessage sendMessage = new SendMessage()
                .setText("www.test.ru")
                .setChatId(chatId)
                .setReplyMarkup(rkm);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void hideKeyboard(String text, long chatId){        //скрытие клавиатуры команд
        ReplyKeyboardRemove rkm = new ReplyKeyboardRemove();

        SendMessage sendMessage = new SendMessage()
                .setText("Клавиатура была скрыта!")
                .setChatId(chatId)
                .setReplyMarkup(rkm);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    /**
     * метод возвращает запись по дню и времени
     */
    private void checkAdd(String day, String time, String name) {
        Record record = get(day, time);
        if (record == null) {
            add(day, time, name);
        } else {
            if (record.members.size() < 5){
                record.addMember(name);
            }
        }
    }

    private void remove(String day, String time, String name) {
        Record record = get(day, time);
        if (record != null) {
            record.removeMember(name);
        }
    }

    private Record get(String day, String time) {
        int size = records.size();
        for (int i = 0; i < size; i++) {
            Record record = records.get(i);
            if (Objects.equals(record.day, day) && Objects.equals(record.time, time)){
                return record;
            }
        }

        return null;
    }

    private void add(String day, String time, String name) {
        Record record = new Record();

        record.time = time;
        record.day = day;
        record.addMember(name);
    }

}

