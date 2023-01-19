package org.example.bot;

import org.example.bot.service.BasketService;
import org.example.bot.service.BotService;
import org.example.bot.service.OrderService;
import org.example.bot.utils.BotConstants;
import org.example.bot.utils.BaseBot;
import org.example.model.Product;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

public class MyBot extends TelegramLongPollingBot implements BaseBot {
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    System.out.println(chatId);
                    ReplyKeyboardMarkup headReplyMarkups = BotService.replyKeyboardMarkup(
                            List.of(BotConstants.CATEGORIES.name(), BotConstants.ORDERS.name(), BotConstants.BASKET.name()), DIVIDER
                    );
                    botExecute(MessageType.SEND_MESSAGE, BotService.buildSendMessage(chatId, "Welcome to " + message.getFrom().getFirstName(), null, headReplyMarkups));
                } else if (message.getText().equals(BotConstants.CATEGORIES.name())) {

                } else if (message.getText().equals(BotConstants.ORDERS.name())) {
                    List<Product> orderList = new BasketService().getOrderList(chatId);
                    System.out.println(orderList.size());
                    for (Product product : orderList) {
                        File file = new File("web/WEB-INF/image/" + product.getName());
                        InputFile inputFile = new InputFile(file, product.getName());
                        SendPhoto sendPhoto = new SendPhoto();
                        System.out.println(file);
                        System.out.println(inputFile);
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setPhoto(inputFile);
                        sendPhoto.setCaption(
                                "Name : " + product.getName() + "\n" +
                                        "Price : " + product.getPrice() + "\n" +
                                        "Info : " + product.getInfo()+"\n"+
                                        "Date : "+product.getCreatedDate()
                        );
//                        sendPhoto.setReplyMarkup(BotService.buildInlineDifferentCallBack(List.of("\uD83D\uDED2 Buy", "❌"), List.of("Yes: " + product.getId(), "No:" + product.getName()), 2));
                        botExecute(MessageType.SEND_PHOTO, sendPhoto);
                    }

                } else if (message.getText().equals(BotConstants.BASKET.name())) {

                    List<Product> basketList=new BasketService().getBasketList(chatId);
                    for (Product product : basketList) {
                        File file = new File("web/WEB-INF/image/" + product.getName());
                        InputFile inputFile = new InputFile(file, product.getName());
                        SendPhoto sendPhoto = new SendPhoto();
                        System.out.println(file);
                        System.out.println(inputFile);
                        sendPhoto.setChatId(chatId);
                        sendPhoto.setPhoto(inputFile);
                        sendPhoto.setCaption(
                                "Name : " + product.getName() + "\n" +
                                        "Price : " + product.getPrice() + "\n" +
                                        "Info : " + product.getInfo()
                        );
                        sendPhoto.setReplyMarkup(BotService.buildInlineDifferentCallBack(List.of("\uD83D\uDED2 Buy", "❌"), List.of("Yes: " + product.getName(), "No:" + product.getName()), 2));
                        botExecute(MessageType.SEND_PHOTO, sendPhoto);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackQueryData = callbackQuery.getData();

            if (callbackQueryData.startsWith("Yes:")) {
                BasketService basketService = new BasketService();
                basketService.put_buy(update, callbackQueryData);
            }else if (callbackQueryData.startsWith("No:"))
            {
                BasketService basketService=new BasketService();
                basketService.deleteProduct(update,callbackQueryData);
            }
        }
    }

    private void botExecute(MessageType messageType, Object object) {
        try {
            switch (messageType) {
                case SEND_PHOTO -> execute((SendPhoto) object);
                case SEND_MESSAGE -> execute((SendMessage) object);
                case EDIT_MESSAGE -> execute((EditMessageText) object);
                case DELETE_MESSAGE -> execute((DeleteMessage) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void botExecute(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
