package com.github.itlogic.notify.clients;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Telegram client impl.
 */
public class TelegramClient implements ClientInterface {

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot";
    private static final int HTTP_OK_CODE = 200;

    private String apiKey = "";

    @Override
    public final void setAuth(final String authKey) {
        this.apiKey = authKey;
    }

    @Override
    public final void send(final String chat, final String message) throws NotifySendException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(makeUrl("sendMessage"));
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("chat_id", chat));
        urlParameters.add(new BasicNameValuePair("parse_mode", "markdown"));
        urlParameters.add(new BasicNameValuePair("text", message));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HTTP_OK_CODE) {
                throw new NotifySendException();
            }
        } catch (IOException e) {
            throw new NotifySendException();
        }
    }

    private String makeUrl(final String action) {
        return TELEGRAM_API_URL + apiKey + "/" + action;
    }
}
