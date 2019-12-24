## Send notification to Telegram channel

####Add maven repository
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

#### Example
```java
NotifyProvider notify = new NotifyProvider.Builder(TelegramClient.class)
        .setAuth("telegram bot auth key")
        .build();

notify.error("channel id", "Title", "Message");
notify.info("channel id", "Title", "Message");
notify.warning("channel id", "Title", "Message");
```