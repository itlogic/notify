### Send notification to Telegram channel

Example
```java
NotifyProvider notify = new NotifyProvider.Builder(TelegramClient.class)
        .setAuth("telegram bot auth key")
        .build();

notify.error("channel id", "Title", "Message");
notify.info("channel id", "Title", "Message");
notify.warning("channel id", "Title", "Message");
```