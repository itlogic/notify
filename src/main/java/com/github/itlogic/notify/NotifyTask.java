package com.github.itlogic.notify;

import com.github.itlogic.notify.clients.ClientInterface;
import com.github.itlogic.notify.exceptions.NotifySendException;

/**
 * Task. Send notification.
 */
public class NotifyTask implements Runnable {

    private ClientInterface client;
    private String chat;
    private String message;

    public NotifyTask(final ClientInterface client, final String chat, final String message) {
        this.client = client;
        this.chat = chat;
        this.message = message;
    }

    @Override
    public final void run() {
        try {
            client.send(chat, message);
        } catch (NotifySendException e) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, e);
        }
    }
}
