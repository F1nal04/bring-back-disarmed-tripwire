# Bring Back Disarmed Tripwire

Restores the pre-1.21.1 behavior of tripwire: when you use shears on tripwire string, it is disarmed instead of broken.

## What it does

- First shear hit on tripwire string: the string remains and becomes disarmed; shears lose 1 durability.
- Subsequent break (or shear) on an already disarmed string: the string breaks normally.
- Works in singleplayer and on dedicated servers.
- Clients do not need the mod to join a modded server running this.

## Installation (Fabric only)

### Dedicated server

1. Install Fabric Loader for your server version.
2. Place the built JAR into the `mods/` folder along with Fabric API.
3. Start the server. Vanilla clients can connect.

### Singleplayer

1. Install Fabric Loader and Fabric API on your client.
2. Place the JAR into your client `mods/` folder.
3. Launch the game. The behavior applies because singleplayer runs an integrated server.

## Build from source

```bash
./gradlew build
```

The remapped JAR will be in `build/libs/`.

## License

- CC0-1.0
