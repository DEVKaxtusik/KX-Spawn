# ✨ KX-Spawn Plugin

Welcome to the **KX-Spawn** plugin! 🎉 This is a simple, lightweight spawn plugin that supports **Folia** and **all Paper forks**. It provides essential spawn management features for your Minecraft server 🧭.

## 🚀 Features

- ✅ **Folia Support**: Fully compatible with Folia, ensuring smooth performance and stability.
- 🌀 **Teleportation**: Allows players to teleport to the spawn location with a configurable delay.
- 🧍‍♂️ **Movement Check**: Cancels teleportation if the player moves, ensuring they stay in place.
- 💬 **Custom Messages**: Configurable messages for teleportation events, including cancellation and completion.
- ♻️ **Configuration Reload**: Easily reload configuration and messages without restarting the server.
- 🧵 **Concurrent Handling**: Uses thread-safe data structures for optimal performance.

## 📦 Installation

1. 📥 Download the latest version of the KX-Spawn plugin from **here** or from the [releases page](https://github.com/DEVKaxtusik/KX-Spawn/releases).
2. 📂 Place the downloaded `.jar` file into your server's `plugins` directory.
3. 🔁 Start or restart your Minecraft server to load the plugin.
4. 🛠️ Configure the plugin by editing the `config.yml` and `messages.yml` files in the `plugins/KX-Spawn` directory.

## 🧭 Commands

- `/spawn` - Teleports the player to the spawn location.
- `/spawn set` - Sets the current location as the spawn point (requires permission).
- `/spawn reload` - Reloads the plugin configuration and messages (requires permission).

## 🔐 Permissions

- `kx.spawn.use` - Allows the use of the `/spawn` command.
- `kx.spawn.set` - Allows setting the spawn location.
- `kx.spawn.reload` - Allows reloading the plugin configuration and messages.

## ⚙️ Configuration

The plugin provides a `config.yml` file for setting the teleportation delay and movement check options. The `messages.yml` file allows you to customize the messages sent to players during teleportation events.

## 🛠️ Support

If you encounter any issues or have any questions, please report them on the [GitHub issues page](https://github.com/DEVKaxtusik/KX-Spawn/issues).

🙌 Thank you for using **KX-Spawn**! We hope it enhances your Minecraft server experience. 🧡
