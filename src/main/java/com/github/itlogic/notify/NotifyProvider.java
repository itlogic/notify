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

    private String channelId;
    private String title;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private ClientInterface client;

    public NotifyProvider(final Builder builder) {
        try {
            this.client = builder.client.newInstance();
            this.client.setAuth(builder.authKey);
            this.channelId = builder.channelId;
            this.title = builder.title;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public final void send(final Notify level, final String channel, final String title, final String message) {
        pushMessageTask(level, channel, title, message);
    }

    public final void send(final Notify level, final String title, final String message) {
        pushMessageTask(level, this.channelId, title, message);
    }

    public final void send(final Notify level, final String message) {
        pushMessageTask(level, this.channelId, this.title, message);
    }

    private void pushMessageTask(final Notify level, final String channel, final String title, final String message) {
        switch (level) {
            case info:
                executor.submit(new NotifyTask(client, channel, makeInfoMessage(title, message)));
                break;
            case warning:
                executor.submit(new NotifyTask(client, channel, makeWarningMessage(title, message)));
                break;
            case error:
                executor.submit(new NotifyTask(client, channel, makeErrorMessage(title, message)));
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

        private Class<? extends ClientInterface> client;
        private String authKey = "";
        private String channelId = "";
        private String title = "";

        public Builder(final Class<? extends ClientInterface> client) {
            this.client = client;
        }

        public final Builder auth(final String auth) {
            this.authKey = auth;
            return this;
        }

        public final Builder channel(final String channelId) {
            this.channelId = channelId;
            return this;
        }

        public final Builder title(final String title) {
            this.title = title;
            return this;
        }

        public final NotifyProvider build() {
            return new NotifyProvider(this);
        }
    }
}
