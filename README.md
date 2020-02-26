## Send notification to Telegram channel

#### Example
```java
NotifyProvider notify = new NotifyProvider.Builder(TelegramClient.class, "Api Key")
        .build();

notify.send(NotifyLevel.error, "channel id", "Title", "Message");


NotifyProvider notify = new NotifyProvider.Builder(TelegramClient.class, "Api Key")
        .channel("channel id")
        .title("Title")
        .build();

try {
    notify.send(NotifyLevel.none, "Текст");
} catch (NotifyRequiredException e) {
    e.printStackTrace();
}

notify.shutdown();
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