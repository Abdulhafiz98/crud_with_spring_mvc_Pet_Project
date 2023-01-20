package org.example.bot.service;

import org.example.bot.MessageType;
import org.example.bot.MyBot;
import org.example.dao.UserDao;
import org.example.model.Category;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public abstract class BotService {

    public static String SEPARATOR = "/";
    public static int PARENT_ID = 1;
    public static String CHECK = "Check registration";
    public static int PRODUCT_ID = 2;

    public static String PRODUCT = "product";
    public static String PREV = "Prev";
    public static String NEXT = "Next";
    public static String BACK = "BACK";
    public static String BUY = "BUY";
    public static String FAVOURITES = "ADD FAVOURITES";

    String URL = "jdbc:postgresql://localhost:5432/postgres";
    String USER = "postgres";
    String DRIVER = "org.postgresql.Driver";
    String PASSWORD = "abdulatif032200";

    public static String CATEGORY = "category";

    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USER);
        driverManagerDataSource.setPassword(PASSWORD);
        driverManagerDataSource.setDriverClassName(DRIVER);
        return driverManagerDataSource;
    }

    public static ReplyKeyboardMarkup replyKeyboardMarkup(List<String> menuItems, int n) {
        ReplyKeyboardMarkup r = new ReplyKeyboardMarkup();
        r.setResizeKeyboard(true);
        List<KeyboardRow> buttonRow = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();
        for (int i = 0; i < menuItems.size(); i++) {
            keyboardButtons.add(new KeyboardButton(menuItems.get(i)));
            if (i % n == 0) {
                buttonRow.add(keyboardButtons);
                keyboardButtons = new KeyboardRow();
            }
        }
        if (keyboardButtons.size() > 0) {
            buttonRow.add(keyboardButtons);
        }
        r.setKeyboard(buttonRow);
        return r;
    }
    public static InlineKeyboardButton button(String text, String callBackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBackData);
        return inlineKeyboardButton;
    }
    public static InlineKeyboardMarkup buildInlineDifferentCallBack(List<String> front, List<String> back, int column ){
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttonList=new ArrayList<>();
        InlineKeyboardMarkup i = new InlineKeyboardMarkup();
        for (int j = 0; j <front.size(); j++) {
            if (j % column == 0) {
                buttonList=new ArrayList<>();
            }
            buttonList.add(button(front.get(j),back.get(j)));
        }
        rows.add(buttonList);
        i.setKeyboard(rows);

        return i;
    }
    public static SendMessage buildSendMessage(Long chatId, String text, InlineKeyboardMarkup i, ReplyKeyboardMarkup r) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        if (i != null) {
            sendMessage.setReplyMarkup(i);
        }
        if (r != null) {
            sendMessage.setReplyMarkup(r);
        }
        return sendMessage;
    }

    //================

//    public static ReplyKeyboardMarkup replyKeyboardMarkup(List<String> menuItems, int n) {
//        ReplyKeyboardMarkup r = new ReplyKeyboardMarkup();
//        r.setResizeKeyboard(true);
//        List<KeyboardRow> buttonRow = new ArrayList<>();
//        KeyboardRow keyboardButtons = new KeyboardRow();
//        for (int i = 0; i < menuItems.size(); i++) {
//            keyboardButtons.add(new KeyboardButton(menuItems.get(i)));
//            if (i % n == 0) {
//                buttonRow.add(keyboardButtons);
//                keyboardButtons = new KeyboardRow();
//            }
//        }
//        if (keyboardButtons.size() > 0) {
//            buttonRow.add(keyboardButtons);
//        }
//        r.setKeyboard(buttonRow);
//        return r;
//    }
    public static InlineKeyboardMarkup buildInlineMarkup(final List<Object> objectList, final int column,int pageNumber) {
        return new InlineKeyboardMarkup(
                BotService.getInlineKeyboardRowList(objectList, column, pageNumber)
        );
    }

//    public static SendMessage buildSendMessage(Long chatId, String text, InlineKeyboardMarkup i, ReplyKeyboardMarkup r) {
//        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
//        if (i != null) {
//            sendMessage.setReplyMarkup(i);
//        }
//        if (r != null) {
//            sendMessage.setReplyMarkup(r);
//        }
//        return sendMessage;
//    }

    public static EditMessageText buildEditMessage(Long chatId, String text, Integer messageId, InlineKeyboardMarkup i) {
        EditMessageText editMessage = new EditMessageText(text);
        editMessage.setMessageId(messageId);
        editMessage.setChatId(chatId);
        if (i != null) {
            editMessage.setReplyMarkup(i);
        }
        return editMessage;
    }

    public static DeleteMessage buildDeleteMessage(Long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        return deleteMessage;
    }

    private static List<List<InlineKeyboardButton>> getInlineKeyboardRowList(List<Object> objectList, int column, int pageNumber) {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        int index = 0;
        for (Object object : objectList) {
            if (object instanceof Product product) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(product.getName());
                button.setCallbackData(PRODUCT + product.getId());
                inlineKeyboardButtons.add(button);
                list.add(inlineKeyboardButtons);
                inlineKeyboardButtons = new ArrayList<>();
                if (index == objectList.size() - 1) {
                    list.add(inlineKeyboardButtons);
                    inlineKeyboardButtons = new ArrayList<>();

                    InlineKeyboardButton prev = new InlineKeyboardButton();
                    prev.setText(PREV);
                    prev.setCallbackData(PREV + SEPARATOR + product.getCategoryId() + SEPARATOR + pageNumber);
                    inlineKeyboardButtons.add(prev);

                    list.addAll(getBack(product.getCategoryId(), 0));
                    InlineKeyboardButton next = new InlineKeyboardButton();
                    next.setText(NEXT);
                    next.setCallbackData(NEXT + SEPARATOR + product.getCategoryId() + SEPARATOR + pageNumber);
                    inlineKeyboardButtons.add(next);
                    list.add(inlineKeyboardButtons);
                }
            }
            if (object instanceof String str) {
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText(str);
                button1.setCallbackData(str.toUpperCase());
                inlineKeyboardButtons.add(button1);
                list.add(inlineKeyboardButtons);
            }
            index++;
        }
        return list;
    }

    public static List<List<InlineKeyboardButton>> getBack(int parentId, int productId) {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons2 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(FAVOURITES);
        button1.setCallbackData(FAVOURITES + productId);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(BUY);
        button2.setCallbackData(BUY + productId);
        if(productId != 0){
            inlineKeyboardButtons1.add(button1);
            inlineKeyboardButtons2.add(button2);
            list.add(inlineKeyboardButtons1);
            list.add(inlineKeyboardButtons2);
        }

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BACK);
        button.setCallbackData(BACK + SEPARATOR + parentId + SEPARATOR + productId);
        inlineKeyboardButtons.add(button);
        list.add(inlineKeyboardButtons);

        return list;
    }
    public static InlineKeyboardMarkup getCategoryInlineKeyboardMarkup(List<Category> categoryList, int n) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        List<List<InlineKeyboardButton>> back = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(category.getName());
            inlineKeyboardButton.setCallbackData(CATEGORY + category.getId());
            row.add(inlineKeyboardButton);
            if (i % n == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
            if(i == categoryList.size() - 1){
                back = getBack(category.getParentId(), 0);
            }
        }
        if (row.size() > 0) {
            rows.add(row);
        }
        rows.addAll(back);
        return inlineKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup getContactMenu() {
        KeyboardButton button = new KeyboardButton(CHECK);
        button.setRequestContact(true);

        return getMarkup(getRowList(getRow(button)));
    }

    private static ReplyKeyboardMarkup getMarkup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rowList);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        return markup;
    }
    private static KeyboardRow getRow(KeyboardButton... buttons) {
        return new KeyboardRow(List.of(buttons));
    }

    private static List<KeyboardRow> getRowList(KeyboardRow... rows) {
        return List.of(rows);
    }
    public static void handleContact(long chatId, Message message, Contact contact) {
        if (new UserServiceBot().checkUser(chatId,contact)) {
            new MyBot().mainMenu(chatId);
        }
    }
}
