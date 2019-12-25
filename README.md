## Send notification to Telegram channel

#### Example
```java
NotifyProvider notify = new NotifyProvider.Builder(TelegramClient.class)
        .setAuth("telegram bot auth key")
        .build();

notify.send(NotifyProvider.Notify.error, "channel id", "Title", "Message");
notify.send(NotifyProvider.Notify.info, "channel id", "Title", "Message");
notify.send(NotifyProvider.Notify.warning, "channel id", "Title", "Message");
```

#### Add maven repository
```xml
<repositories>
    <repository>
        <id>notify-mvn-repo</id>
        <url>https://raw.github.com/itlogic/notify/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```


#### Add dependency
```xml
<dependencies>
    <dependency>
        <groupId>com.github.itlogic</groupId>
        <artifactId>notify</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```