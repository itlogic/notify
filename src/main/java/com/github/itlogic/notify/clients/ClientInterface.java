package com.github.itlogic.notify.clients;

import com.github.itlogic.notify.exceptions.NotifySendException;

/**
 * Client interface.
 */
public interface ClientInterface {
    void setApiKey(String apiKey);
    void send(String chat, String message) throws NotifySendException;
}
