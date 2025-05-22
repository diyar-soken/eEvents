# 🥋 Sumo Event Plugin - by Diyar Soken

**Sumo Event** is a Minecraft PvP plugin that introduces an intense 1v1 sumo-style tournament. 
Players battle on a platform, trying to knock each other off — the last player standing wins!

---

## 🎮 Features

- ✅ Fully automated 1vs1 Sumo event system
- ✅ Supports multiple players in a knockout-style tournament
- ✅ Easy-to-use commands with configurable permissions
- ✅ Auto-handling of countdowns, matches, and winner detection
- ✅ Compatible with Minecraft **1.8 to 1.20+**
- ✅ PlaceholderAPI support for real-time stats in scoreboards, chat, and tablists

---

## 🛠️ Commands

| Command               | Description                     | Permission        |
|-----------------------|---------------------------------|-------------------|
| `/sumo host`          | Starts the Sumo event           | `ezar.sumo.host`  |
| `/sumo stop`          | Stops the current Sumo event    | `sumo.stop`       |
| `/sumo join`          | Join the active Sumo event      | `ezar.sumo.join`  |
| `/sumo spec`          | Join the active Sumo event      | `ezar.sumo.spec`  |
| `/sumo setlobby`      | Set the spawn point for players | `ezar.sumo.admin` |
| `/sumo setspawn spec` | Set the spawn point for players | `ezar.sumo.admin` |
| `/sumo setspawn 1/2`  | Set the spawn point for players | `ezar.sumo.admin` |

---

## 📂 Installation

1. Download the latest version of the plugin `.jar` file.
2. Place it in your server’s `/plugins/` folder.
3. Restart or reload your server.
4. Configure settings in `config.yml` if needed.
5. Modify the scoreboard design in `design.yml`.

---

## 📊 Placeholders (Requires PlaceholderAPI)

These placeholders can be used in chat, scoreboards, tablists, holograms, and other supported interfaces:

| Placeholder                   | Description                                         |
|-------------------------------|-----------------------------------------------------|
| `%sumo_isStarted%`            | Returns `true` if the Sumo event is started         |
| `%sumo_isInTournament%`       | Returns `true` if the player is in the tournament   |
| `%sumo_players%`              | Current number of players in the tournament         |
| `%sumo_maxPlayers%`           | Maximum number of players allowed                   |
| `%sumo_state%`                | Current tournament state (`Lobby`, `In game`, etc.) |
| `%sumo_player1%`              | Name of Player 1 currently fighting                 |
| `%sumo_player2%`              | Name of Player 2 currently fighting                 |
| `%sumo_player1_ping%`         | Ping of Player 1                                    |
| `%sumo_player2_ping%`         | Ping of Player 2                                    |
| `%sumo_fighting%`             | Returns `true` if a match is currently active       |

---

## ⚙️ Requirements

- Minecraft server: **Spigot**, **Paper**, or compatible fork
- Java 8 or higher
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (for placeholder support)

---

## 📧 Support & Feedback

Created by **Diyar Soken**  
For suggestions, support, or bug reports, feel free to open an [Issue](https://github.com/diyar-soken/eEvents/issues) or contact the developer directly.

---
