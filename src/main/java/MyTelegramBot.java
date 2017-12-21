import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.text;
import static javax.swing.UIManager.get;

public class MyTelegramBot extends TelegramLongPollingBot {
    HashMap<String, String> clientsOnMonday = new HashMap<>();
    HashMap<String, String> clientsOnTuesday = new HashMap<>();
    HashMap<String, String> clientsOnWednesday= new HashMap<>();
    HashMap<String, String> clientsOnThursday = new HashMap<>();
    HashMap<String, String> clientsOnFriday = new HashMap<>();
    HashMap<String, String> clientsOnSaturday = new HashMap<>();
    HashMap<String, String> clientsOnSunday = new HashMap<>();
    ArrayList<String> users = new ArrayList<>();
    boolean addModeMonday = false;      // выключаем режим ввода по умолчанию
    boolean addModeTuesday = false;     // выключаем режим ввода по умолчанию
    boolean addModeWednesday = false;   // выключаем режим ввода по умолчанию
    boolean addModeThursday = false;    // выключаем режим ввода по умолчанию
    boolean addModeFriday = false;      // выключаем режим ввода по умолчанию
    boolean addModeSaturday = false;    // выключаем режим ввода по умолчанию
    boolean addModeSunday = false;      // выключаем режим ввода по умолчанию

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
        if (addModeMonday) {                        //проверка режима приема данных на понедельник
            monday(message, chatId);
            addModeMonday = false;
        } else if (addModeTuesday){                 //проверка режима приема данных на вторник
            tuesday(message, chatId);
            addModeTuesday = false;
        } else if (addModeWednesday) {              //проверка режима приема данных на среду
            wednesday(message, chatId);
            addModeWednesday = false;
        } else if (addModeThursday){                //проверка режима приема данных на четверг
            thursday(message, chatId);
            addModeThursday = false;
        } else if (addModeFriday){                  //проверка режима приема данных на пятницу
            friday(message, chatId);
            addModeFriday = false;
        } else if (addModeSaturday) {               //проверка режима приема данных на субботу
            saturday(message, chatId);
            addModeSaturday = false;
        } else if (addModeSunday){                  //проверка режима приема данных на воскресенье
            sunday(message, chatId);
            addModeSunday = false;
        }
        else {

            switch (message) {
                case "/start":
                    sendMessage("Добро пожаловать! Этот бот был создан для удобной записи в спортивный теннисный клуб Lahta-Tennis! \n Cписок команд: \n /запись - арендовать тенисный корт \n /свободноевремя - просмотреть свободное время", chatId);
                case "/запись":     //запустить процесс записи человека
                    addReserv(message, chatId);
                    break;
                case "/Понедельник": //Запись на понедельник
                    sendMessage("Введите ваше имя:", chatId);
                    addModeMonday = true;
                    break;
                case "/Вторник": //Запись на вторник
                    sendMessage("Введите ваше имя:", chatId);
                    addModeTuesday = true;
                    break;
                case "/Среда": //Запись на среду
                    sendMessage("Введите ваше имя:", chatId);
                    addModeWednesday = true;
                    break;
                case "/Четверг": //Запись на четверг
                    sendMessage("Введите ваше имя:", chatId);
                    addModeThursday = true;
                    break;
                case "/Пятница": //Запись на пятницу
                    sendMessage("Введите ваше имя:", chatId);
                    addModeFriday = true;
                    break;
                case "/Суббота": //Запись на субботу
                    sendMessage("Введите ваше имя:", chatId);
                    addModeSaturday = true;
                    break;
                case "/Воскресенье": //Запись на воскресенье
                    sendMessage("Введите ваше имя:", chatId);
                    addModeSunday = true;
                    break;
                case "/Понедельник.": //Просмотр записи на понедельник
                    checkMonday(chatId);
                    break;
                case "/Вторник.": //Просмотр записи на вторник
                    checkTuesday(chatId);
                    break;
                case "/Среда.": //Просмотр записи на среду
                    checkWednesday(chatId);
                    break;
                case "/Четверг.": //Просмотр записи на четверг
                    checkThursday(chatId);
                    break;
                case "/Пятница.": //Просмотр записи на пятницу
                    checkFriday(chatId);
                    addModeFriday = true;
                    break;
                case "/Суббота.": //Просмотр записи на субботу
                    checkSaturday(chatId);
                    addModeSaturday = true;
                    break;
                case "/Воскресенье.": //Просмотр записи на воскресенье
                    checkSunday(chatId);
                    addModeSunday = true;
                    break;
                case "/отмена":
                    hideKeyboard(message, chatId);
                    break;
                case "/свободноевремя":
                    getFreeTime(message, chatId);
                    break;
                default:
                    sendMessage("скорее всего вы ошиблись в команде", chatId);
            }
        }

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
    private void addReserv(String text, long chatId){
        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/Понедельник");
        row.add("/Вторник");
        row.add("/Среда");
        row.add("/Четверг");
        row.add("/Пятница");
        row.add("/Суббота");
        row.add("/Воскресенье");
        row.add("/отмена");
        keyboard.add(row);
        rkm.setKeyboard(keyboard);


        SendMessage sendMessage = new SendMessage()
                .setText("клавиатура была убрана или показана")
                .setChatId(chatId)
                .setReplyMarkup(rkm);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void addMember(String text, long chatId, int messageId){
        String[] кусочки = text.split(" ");
        //cities.put(кусочки[0], кусочки[1]);
        sendMessage("Вы успешно записались!", chatId);
    }

    private void getFreeTime(String message, long chatId) {
            ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("/Понедельник.");
            row.add("/Вторник.");
            row.add("/Среда.");
            row.add("/Четверг.");
            row.add("/Пятница.");
            row.add("/Суббота.");
            row.add("/Воскресенье.");
            row.add("/отмена");
            keyboard.add(row);
            rkm.setKeyboard(keyboard);


            SendMessage sendMessage = new SendMessage()
                    .setText("клавиатура была убрана или показана")
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

    private void monday(String text, long chatId){
        String[] кусочек = text.split(" ");    //запись имени и фамилии в массив
        clientsOnMonday.put(кусочек[0], кусочек[1]);
        sendMessage("Вы успешно записались на понедельник!", chatId);
    }

    private void tuesday(String text, long chatId){
        String[] name = text.split(" ");   //запись имени и фамилии в массив
        clientsOnTuesday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на вторник!", chatId);
    }

    private void wednesday(String text, long chatId){
        String[] name = text.split(" ");    //запись имени и фамилии в массив
        clientsOnWednesday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на среду!", chatId);
    }

    private void thursday(String text, long chatId){
        String[] name = text.split(" ");    //запись имени и фамилии в массив
        clientsOnThursday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на четверг!", chatId);
    }

    private void friday(String text, long chatId){
        String[] name = text.split(" ");    //запись имени и фамилии в массив
        clientsOnFriday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на пятницу!", chatId);
    }

    private void saturday(String text, long chatId){
        String[] name = text.split(" ");    //запись имени и фамилии в массив
        clientsOnSaturday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на субботу!", chatId);
    }

    private void sunday(String text, long chatId){
        String[] name = text.split(" ");    //запись имени и фамилии в массив
        clientsOnSunday.put(name[0], name[1]);
        sendMessage("Вы успешно записались на воскресенье!", chatId);
    }

    private void checkMonday(long chatId){
        String result = "Запись на понедельник: \n";       //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnMonday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkTuesday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnTuesday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkWednesday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnWednesday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkThursday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnThursday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkFriday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnFriday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkSaturday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnSaturday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }
    private void checkSunday(long chatId){
        String result = "Запись на понедельник: \n";        //аккуратный вывод всех имен и фамилий
        for(Map.Entry<String, String> строчка : clientsOnSunday.entrySet()) {
            result += строчка.getKey() + " " + строчка.getValue() + " - " + "время";
            result += "\n";
        }
        sendMessage(result, chatId);
    }



    private void sendPhoto(long chatId, String photo){
        SendPhoto request = new SendPhoto();
        request.setChatId(chatId);
        request.setPhoto(photo);
        try {
            sendPhoto(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

