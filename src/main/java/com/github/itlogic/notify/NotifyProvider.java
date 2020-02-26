package com.github.itlogic.notify;

import com.github.itlogic.notify.clients.ClientInterface;
import com.github.itlogic.notify.exceptions.NotifyRequiredException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Notification provider.
 */
public class NotifyProvider {

    private String channelId;
    private String title;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private ClientInterface client;

    public NotifyProvider(final Builder builder) {
        try {
            this.client = builder.client.newInstance();
            this.client.setApiKey(builder.apiKey);
            this.channelId = builder.channelId;
            this.title = builder.title;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public final void send(final NotifyLevel level, final String channel, final String title, final String message) {
        pushMessageTask(level, channel, title, message);
    }

    public final void send(final NotifyLevel level, final String title, final String message) throws NotifyRequiredException {
        if (this.channelId == null || this.channelId.isEmpty()) {
            throw new NotifyRequiredException("Channel id required");
        }
        pushMessageTask(level, this.channelId, title, message);
    }

    public final void send(final NotifyLevel level, final String message) throws NotifyRequiredException {
        if (this.channelId == null || this.channelId.isEmpty()) {
            throw new NotifyRequiredException("Channel id required in builder");
        }
        if (this.title == null || this.title.isEmpty()) {
            throw new NotifyRequiredException("Title required in builder");
        }
        pushMessageTask(level, this.channelId, this.title, message);
    }

    private void pushMessageTask(final NotifyLevel level, final String channel, final String title, final String message) {
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
                executor.submit(new NotifyTask(client, channel, makeCleanMessage(title, message)));
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

    private String makeCleanMessage(final String title, final String message) {
        return "*" + title + "* ️\n" + message;
    }

    /**
     * Notification provider builder.
     */
    public static class Builder {

        private Class<? extends ClientInterface> client;
        private String apiKey = "";
        private String channelId = "";
        private String title = "";

        public Builder(final Class<? extends ClientInterface> client, String apiKey) {
            this.client = client;
            this.apiKey = apiKey;
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

    public final void shutdown() {
        executor.shutdown();
    }
}
