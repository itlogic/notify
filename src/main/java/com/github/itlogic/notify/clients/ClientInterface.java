package com.github.itlogic.notify.clients;

public interface ClientInterface {
    void setAuth(String authKey);
    void send(String chat, String message) throws NotifySendException;
}
