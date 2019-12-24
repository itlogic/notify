package com.github.itlogic.notify;

import com.github.itlogic.notify.clients.ClientInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotifyProvider {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private ClientInterface client;

    public NotifyProvider(ClientInterface client) {
        this.client = client;
    }

    public void error(String chat, String title, String message) {
        executor.submit(new NotifyTask(client, chat, makeErrorMessage(title, message)));
    }

    public void warning(String chat, String title, String message) {
        executor.submit(new NotifyTask(client, chat, makeWarningMessage(title, message)));
    }

    public void info(String chat, String title, String message) {
        executor.submit(new NotifyTask(client, chat, makeInfoMessage(title, message)));
    }

    private String makeErrorMessage(String title, String message) {
        return "‼️ *" + title + "* ‼\n" + message;
    }

    private String makeWarningMessage(String title, String message) {
        return "⚠️ *" + title + "* ⚠️\n" + message;
    }

    private String makeInfoMessage(String title, String message) {
        return "❕ *" + title + "* ❕️\n" + message;
    }

    public static class Builder {

        private ClientInterface client;

        public Builder(Class<? extends ClientInterface> client) {
            try {
                this.client = client.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public Builder setAuth(String auth) {
            this.client.setAuth(auth);
            return this;
        }

        public NotifyProvider build() {
            return new NotifyProvider(client);
        }
    }
}
