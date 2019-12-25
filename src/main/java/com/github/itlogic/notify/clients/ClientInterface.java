package com.github.itlogic.notify.clients;

/**
 * Client interface.
 */
public interface ClientInterface {
    void setAuth(String authKey);
    void send(String chat, String message) throws NotifySendException;
}
