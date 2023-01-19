package org.example.bot.service;

import org.example.model.Category;
import org.example.model.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public abstract class BotService {
    String URL = "jdbc:postgresql://localhost:5432/mvc";
    String USER = "postgres";
    String DRIVER = "org.postgresql.Driver";
    String PASSWORD = "admin";

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


    public static ReplyKeyboardMarkup buildReplyMarkup(final List<String> menuList, final int column) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        final List<KeyboardRow> keyboardRowList = new ArrayList<>();
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        KeyboardRow keyboardRow = new KeyboardRow();
        for (int i = 0; i < menuList.size(); i++) {
            keyboardRow.add(new KeyboardButton(menuList.get(i)));

            if (i % column == 0) {
                keyboardRowList.add(keyboardRow);
                keyboardRow = new KeyboardRow();
            }
        }
        if (!keyboardRow.isEmpty()) {
            keyboardRowList.add(keyboardRow);
        }
        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getMarkup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rowList);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        return markup;
    }

    public static InlineKeyboardMarkup buildInlineMarkup(final List<Object> objectList, final int column) {
        return new InlineKeyboardMarkup(
                BotService.getInlineKeyboardRowList(objectList, column)
        );
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

//                rows.addAll(List.of(
//                        categoryROW(
//                                button("\uD83D\uDED2  " + front.get(j), back.get(j)
//                                ))));
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
    private static List<List<InlineKeyboardButton>> getInlineKeyboardRowList(List<Object> objectList, int column) {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        int index = 0;
        for (Object object : objectList) {
//            if (object instanceof Region region){
//                InlineKeyboardButton button = new InlineKeyboardButton();
//                button.setText(region.getName());
//                button.setCallbackData(REGION + region.getId());
//                inlineKeyboardButtons.add(button);
//            }
//            if (object instanceof AutoNumber autoNumber) {
//                InlineKeyboardButton button = new InlineKeyboardButton();
//                button.setText(DETAILS + autoNumber.getAutoNumber());
//                button.setCallbackData(DETAILS + autoNumber.getAutoNumber());
//                inlineKeyboardButtons.add(button);
//                if (index + 1 == objectList.size()) {
//                    list.add(inlineKeyboardButtons);
//                    inlineKeyboardButtons = new ArrayList<>();
//
//                    InlineKeyboardButton prev = new InlineKeyboardButton();
//                    prev.setText(PREV);
//                    prev.setCallbackData(PREV);
//                    inlineKeyboardButtons.add(prev);
//
//                    InlineKeyboardButton next = new InlineKeyboardButton();
//                    next.setText(NEXT);
//                    next.setCallbackData(NEXT);
//                    inlineKeyboardButtons.add(next);
//                    list.add(inlineKeyboardButtons);
//                }
//            }
            if (object instanceof String str) {
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText(str);
                button1.setCallbackData(str.toUpperCase());
                inlineKeyboardButtons.add(button1);
            }
            if ((index + 1) % column == 0) {
                list.add(inlineKeyboardButtons);
                inlineKeyboardButtons = new ArrayList<>();
            }
            index++;
        }
        return list;
    }

    public static InlineKeyboardMarkup getProductInlineKeyboardMarkup(List<Product> productList, int n) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);

        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            InlineKeyboardButton inlineKeyboardButton = null;
            inlineKeyboardButton = new InlineKeyboardButton(product.getName());
            inlineKeyboardButton.setCallbackData("P" + product.getId());
            row.add(inlineKeyboardButton);
            if (i % n == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        if (row.size() > 0) {
            rows.add(row);
        }
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getCategoryInlineKeyboardMarkup(List<Category> categoryList, int n) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);

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
        }
        if (row.size() > 0) {
            rows.add(row);
        }
        return inlineKeyboardMarkup;
    }

}
