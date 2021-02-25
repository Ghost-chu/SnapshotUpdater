## Snapshot Updater
A server bootstrap utility allow you always running newest Minecraft snapshot server!  
The most important thing it's full automatically.  
This project based on https://github.com/PaperMC/Paperclip, thanks for a great job!

### How 2 use
```bash
#!/usr/bin/sh
while true
do
java -Xmx10G -jar SnapShotUpdater.jar <optional minecraft args like --forceUpgrade>
done
```
Java 9+ is required.

### How does it work?
SnapshotUpdater will check and update server to the latest Minecraft snapshot server when booting up.
And it also will load server jar to their classloader and start it up like regular.
In same time, one thread will start up to check updates in background, and stop the server when it found a new update.
The updater and Minecraft server will close, then script will restart it, so it will be up-to-date again, and you're on newest version of Minecraft snapshot server.