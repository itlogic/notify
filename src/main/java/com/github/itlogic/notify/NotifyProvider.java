package com.github.itlogic.notify;

import com.github.itlogic.notify.clients.ClientInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Notification provider.
 */
public class NotifyProvider {

    /**
     * Level notification.
     */
    public enum Notify {
        /**
         * Info.
         */
        info,

        /**
         * Warning.
         */
        warning,

        /**
         * Error.
         */
        error
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private ClientInterface client;

    public NotifyProvider(final ClientInterface client) {
        this.client = client;
    }

    public final void send(final Notify level, final String chat, final String title, final String message) {
        switch (level) {
            case info:
                executor.submit(new NotifyTask(client, chat, makeInfoMessage(title, message)));
                break;
            case warning:
                executor.submit(new NotifyTask(client, chat, makeWarningMessage(title, message)));
                break;
            case error:
                executor.submit(new NotifyTask(client, chat, makeErrorMessage(title, message)));
                break;
            default:
                break;
        }
    }

    private String makeErrorMessage(final String title, final String message) {
        return "‼️ *" + title + "* ‼\n" + message;
    }

    private String makeWarningMessage(final String title, final String message) {
        return "⚠️ *" + title + "* ⚠️\n" + message;
    }

    private String makeInfoMessage(final String title, final String message) {
        return "❕ *" + title + "* ❕️\n" + message;
    }

    /**
     * Notification provider builder.
     */
    public static class Builder {

        private ClientInterface client;

        public Builder(final Class<? extends ClientInterface> client) {
            try {
                this.client = client.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public final Builder setAuth(final String auth) {
            this.client.setAuth(auth);
            return this;
        }

        public final NotifyProvider build() {
            return new NotifyProvider(client);
        }
    }
}
