package org.example.bot;

import org.example.bot.service.*;
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
            if (message.hasContact()) {
                System.out.println(message.getContact());
                BotService.handleContact(chatId, message, message.getContact());
            } else if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    if (new UserServiceBot().checkUser(chatId, null)) {
                        ReplyKeyboardMarkup headReplyMarkups = BotService.replyKeyboardMarkup(
                                List.of(BotConstants.CATEGORIES.name(), BotConstants.ORDERS.name(), BotConstants.BASKET.name()), DIVIDER
                        );
                        botExecute(MessageType.SEND_MESSAGE, BotService.buildSendMessage(chatId, "Welcome " + message.getFrom().getFirstName(), null, headReplyMarkups));
                    } else {
                        SendMessage sendMessage = BotService.buildSendMessage(chatId, "Please register through the link below \n http://localhost:8080/auth/register", null, BotService.getContactMenu());
                        botExecute(MessageType.SEND_MESSAGE, sendMessage);
                    }
                } else if (message.getText().equals(BotConstants.CATEGORIES.name())) {
                    InlineKeyboardMarkup categoryInlineKeyboardMarkup = BotService.getCategoryInlineKeyboardMarkup(new CategoryServiceBot().getCategoryList(0), 2);
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Choose category", categoryInlineKeyboardMarkup, null);
                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                } else if (message.getText().equals(BotConstants.ORDERS.name())) {
                    List<Product> orderList = new BasketService().getOrderList(chatId);
                    if (orderList.isEmpty()) {
                        SendMessage sendMessage = BotService.buildSendMessage(chatId, "The Order list is empty", null, null);
                        botExecute(MessageType.SEND_MESSAGE, sendMessage);
                    } else {
                        printFoto(orderList, chatId, 0);
                    }
                } else if (message.getText().equals(BotConstants.BASKET.name())) {
                    List<Product> basketList = new BasketService().getBasketList(chatId);
                    if (basketList.isEmpty()) {
                        SendMessage sendMessage = BotService.buildSendMessage(chatId, "The Basket is empty", null, null);
                        botExecute(MessageType.SEND_MESSAGE, sendMessage);
                    } else {
                        printFoto(basketList, chatId, 1);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            Message message = callbackQuery.getMessage();
            Long chatId = message.getChatId();

            if (data.startsWith("Yes:")) {

                DeleteMessage deleteMessage = BotService.buildDeleteMessage(chatId, message.getMessageId());
                botExecute(MessageType.DELETE_MESSAGE, deleteMessage);
                BasketService basketService = new BasketService();
                basketService.put_buy(update, data);
                SendMessage sendMessage = BotService.buildSendMessage(chatId, "Successfully bought", null, null);
                botExecute(MessageType.SEND_MESSAGE, sendMessage);

            } else if (data.startsWith("No:")) {

                BasketService basketService = new BasketService();
                basketService.deleteProduct(update, data);
                DeleteMessage deleteMessage = BotService.buildDeleteMessage(chatId, message.getMessageId());
                botExecute(MessageType.DELETE_MESSAGE, deleteMessage);
                SendMessage sendMessage = BotService.buildSendMessage(chatId, "Successfully deleted", null, null);
                botExecute(MessageType.SEND_MESSAGE, sendMessage);

            } else if (data.startsWith(BotService.CATEGORY)) {
                int categoryParentId = Integer.parseInt(data.substring(BotService.CATEGORY.length()));
                InlineKeyboardMarkup categoryInlineKeyboardMarkup = BotService.getCategoryInlineKeyboardMarkup(new CategoryServiceBot().getCategoryList(categoryParentId), 2);
                List<Product> productList = new ProductService().getProductList(categoryParentId, 1);
                if (productList.isEmpty() && !new CategoryServiceBot().getCategoryList(categoryParentId).isEmpty()) {
                    EditMessageText editMessageText = BotService.buildEditMessage(chatId, "Choose sub category", message.getMessageId(), categoryInlineKeyboardMarkup);
                    botExecute(MessageType.EDIT_MESSAGE, editMessageText);
                } else if (new CategoryServiceBot().getCategoryList(categoryParentId).isEmpty() && productList.isEmpty()) {
                    EditMessageText editMessageText = BotService.buildEditMessage(chatId, "Choose category", message.getMessageId(), new InlineKeyboardMarkup(BotService.getBack(categoryParentId, 0)));
                    botExecute(MessageType.EDIT_MESSAGE, editMessageText);
                } else {
                    InlineKeyboardMarkup inlineKeyboardMarkup = BotService.buildInlineMarkup((List) (productList), 1, 1);
                    EditMessageText editMessageText = BotService.buildEditMessage(chatId, "Choose product", message.getMessageId(), inlineKeyboardMarkup);
                    botExecute(MessageType.EDIT_MESSAGE, editMessageText);
                }
            } else if (data.startsWith(BotService.PRODUCT)) {
                DeleteMessage deleteMessage = BotService.buildDeleteMessage(chatId, message.getMessageId());
                botExecute(MessageType.DELETE_MESSAGE, deleteMessage);
                int productId = Integer.parseInt(data.substring(BotService.PRODUCT.length()));
                SendPhoto productInfo = new ProductService().getProductInfo(chatId, productId);
                botExecute(MessageType.SEND_PHOTO, productInfo);
            } else if (data.startsWith(BotService.BACK)) {
                String[] split = data.split(String.valueOf(BotService.SEPARATOR));
                int parentId = Integer.parseInt(split[BotService.PARENT_ID]);
                int productId = Integer.parseInt(split[BotService.PRODUCT_ID]);
                if (productId != 0) {
                    botExecute(MessageType.DELETE_MESSAGE, BotService.buildDeleteMessage(chatId, message.getMessageId()));
                    List<Product> productList = new ProductService().getProductList(parentId, 1);
                    InlineKeyboardMarkup inlineKeyboardMarkup = BotService.buildInlineMarkup((List) (productList), 1, 1);
                    SendMessage editMessageText = BotService.buildSendMessage(chatId, "Choose product", inlineKeyboardMarkup, null);
                    botExecute(MessageType.SEND_MESSAGE, editMessageText);
                } else if (parentId != 0 && productId == 0) {
                    InlineKeyboardMarkup categoryInlineKeyboardMarkup = BotService.getCategoryInlineKeyboardMarkup(new CategoryServiceBot().getBackList(parentId), 2);
                    EditMessageText sendMessage = BotService.buildEditMessage(chatId, "Choose sub category", message.getMessageId(), categoryInlineKeyboardMarkup);
                    botExecute(MessageType.EDIT_MESSAGE, sendMessage);
                } else if (parentId == 0 && productId == 0) {
                    DeleteMessage deleteMessage = BotService.buildDeleteMessage(chatId, message.getMessageId());
                    botExecute(MessageType.DELETE_MESSAGE, deleteMessage);
                    ReplyKeyboardMarkup headReplyMarkups = BotService.replyKeyboardMarkup(
                            List.of(BotConstants.CATEGORIES.name(), BotConstants.ORDERS.name(), BotConstants.BASKET.name()), DIVIDER
                    );
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Main Menu", null, headReplyMarkups);

                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                }
            } else if (data.startsWith(BotService.FAVOURITES)) {
                int productId = Integer.parseInt(data.substring(BotService.FAVOURITES.length()));
                if (new ProductService().addFavourites(chatId, productId)) {
                     botExecute(MessageType.DELETE_MESSAGE, BotService.buildDeleteMessage(chatId, message.getMessageId()));
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(BotService.getBack(0, 0));
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Successfully added", inlineKeyboardMarkup, null);
                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                } else {
                    botExecute(MessageType.DELETE_MESSAGE, BotService.buildDeleteMessage(chatId, message.getMessageId()));
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(BotService.getBack(0, 0));
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Already in basket", inlineKeyboardMarkup, null);
                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                }
            } else if (data.startsWith(BotService.PREV)) {
                checkPagination(chatId, data, message.getMessageId(), true);
            } else if (data.startsWith(BotService.NEXT)) {
                checkPagination(chatId, data, message.getMessageId(), false);
            } else if (data.startsWith(BotService.BUY)) {
                int productId = Integer.parseInt(data.substring(BotService.BUY.length()));
                if (new ProductService().buyProduct(chatId, productId)) {
                    botExecute(MessageType.DELETE_MESSAGE, BotService.buildDeleteMessage(chatId, message.getMessageId()));
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Successfully bought", new InlineKeyboardMarkup(BotService.getBack(0, 0)), null);
                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                } else {
                    botExecute(MessageType.DELETE_MESSAGE, BotService.buildDeleteMessage(chatId, message.getMessageId()));
                    SendMessage sendMessage = BotService.buildSendMessage(chatId, "Something went wrong!", new InlineKeyboardMarkup(BotService.getBack(0, 0)), null);
                    botExecute(MessageType.SEND_MESSAGE, sendMessage);
                }
            }
        }
    }

    public void botExecute(MessageType messageType, Object object) {
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

    private void printFoto(List<Product> list, Long chatId, int a) {
        for (Product product : list) {
            File file = new File("web/WEB-INF/image/" + product.getProductUrl());
            InputFile inputFile = new InputFile(file, product.getProductUrl());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(inputFile);
            sendPhoto.setCaption(
                    "Name : " + product.getName().substring(0, product.getName().length() - 4) + "\n" +
                            "Price : " + product.getPrice() + "\n" +
                            "Info : " + product.getInfo() + "\n" +
                            "Date : " + product.getCreatedDate()
            );
            if (a != 0) {
                sendPhoto.setReplyMarkup(BotService.buildInlineDifferentCallBack(List.of("\uD83D\uDED2 Buy", "❌"), List.of("Yes: " + product.getName(), "No:" + product.getName()), 2));
            }
            botExecute(MessageType.SEND_PHOTO, sendPhoto);
        }
    }

    private void checkPagination(Long chatId, String data, int messageId, boolean isPrev) {
        String[] split = data.split(BotService.SEPARATOR);
        int categoryId = Integer.parseInt(split[BotService.PARENT_ID]);
        String pageNumber = split[BotService.PRODUCT_ID];

        int page = Integer.parseInt(pageNumber);

        if (isPrev && page > 1) {
            page--;
        }
        if (!isPrev) {
            page++;
        }
        List<Product> productList = new ProductService().getProductList(categoryId, page);
        if (!productList.isEmpty()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = BotService.buildInlineMarkup((List) (productList), 1, page);
            EditMessageText editMessageText = BotService.buildEditMessage(chatId, "Choose product", messageId, inlineKeyboardMarkup);
            botExecute(MessageType.EDIT_MESSAGE, editMessageText);
        }
    }

    public void mainMenu(long chatId) {
        ReplyKeyboardMarkup headReplyMarkups = BotService.replyKeyboardMarkup(
                List.of(BotConstants.CATEGORIES.name(), BotConstants.ORDERS.name(), BotConstants.BASKET.name()), DIVIDER
        );

        SendMessage sendMessage = BotService.buildSendMessage(chatId, "Main Menu", null, headReplyMarkups);

        botExecute(MessageType.SEND_MESSAGE, sendMessage);
    }
}
