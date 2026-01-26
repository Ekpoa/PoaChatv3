# PoaChatv3

PoaChatv3 is a modular chat customisation plugin for Paper **1.21+**, focused on rich formatting, prefixes, tags, and interactive chat modules.

---

## Features

- Full range of preset chat colours
- Chat colour gradients
- Prefix colour
- Prefix gradients
- Prefix changing via `/prefix`
- Custom chat tags
- Claimable chat tags (via special items)
- Multiple interactive chat modules (placeholders typed directly in chat)

---

## Commands

### `/chatcolor`
**Permission:** `poa.chatcolor`

Opens the main chat customisation GUI.

Sub-options include:
- Preset colours
- Gradient colours
- Custom hex colour input
- Chat tags
- Name colour
- Prefix colour

Aliases:
- `/chatcolour`
- `/namecolor` (opens name colour directly)

---

### `/namecolor`
**Permission:** `poa.chat.namecolor`

Opens the colour GUI to change **name colour** instead of chat colour.

---

### `/prefix <text>`
**Permission:** `poa.prefix`

Sets the player’s prefix using LuckPerms.  
Prefix length and formatting are limited by config.

---

### `/prefixcolor`
**Permission:** `poa.prefixcolor`

Opens a colour picker used to select **prefix gradients**.  
Click two colours to apply a gradient.

---

### `/tags` (alias: `/titles`)

Opens the player’s owned chat tags menu.
> Note: this command does **not** define a permission in `plugin.yml`.  
> Restrict it manually using your permissions plugin if required.

---

### `/chatadmin <player> <subcommand>`
**Permission:** `poa.chatadmin`

Admin-only command for managing chat data and tags.

Available subcommands:
- `newtagitem <tag...>` – creates a claimable tag item
- `addtag <tag...>` – gives a tag to a player
- `setcurrenttag <tag...>` – force-sets current tag
- `removetag` – enables tag removal mode
- `droptagsfromfile true` – drops claimable tags listed in `droptags.yml`
- `setchatcolor <minimessage>`
- `setnamecolor <minimessage>`
- `displayname <name...>`
- `setprefix <prefix...|clear>`
- `setprefixcolor <minimessage|clear>`

---

## Permissions

### Core
- `poa.chatcolor` – access `/chatcolor`
- `poa.chatadmin` – access `/chatadmin`
- `poa.chat.namecolor` – change name colour
- `poa.prefix` – change prefix text
- `poa.prefixcolor` – change prefix colour/gradient

---

### Chat Colour Menu
- `poa.chat.color.custom` – allows custom hex colour input
- `poa.chat.color.gradient` – allows gradient colours

---

### Preset Colour Permissions

Preset colours can be unlocked in three ways:

- Per colour
    - `poa.chat.color.<color_name>`
- Per category
    - `poa.chat.color.all.<category>`
- Global
    - `poa.chat.color.all.all`

Categories used:
- `blue`
- `green`
- `red`
- `purple`
- `white`
- `pastel`
- `neon`
- `dark`

---

### Tag & Menu Buttons
- `poa.chat.tags` – access chat tags menu
- `poa.chat.namecolor` – enable name colour button
- `poa.prefixcolor` – enable prefix colour button

---

## Chat Modules

Players can type placeholders directly into chat.  
Each module requires its own permission.

| Placeholder              | Permission |
|--------------------------|-----------|
| `[help]`                 | `poa.chat.modules.help` |
| `[item]`                 | `poa.chat.modules.item` |
| `[inventory]`            | `poa.chat.modules.inventory` |
| `[enderchest]`           | `poa.chat.modules.enderchest` |
| `[pv <number>]`          | `poa.chat.modules.pv` |
| `[kills]`                | `poa.chat.modules.kills` |
| `[deaths]`               | `poa.chat.modules.deaths` |
| `[kdr]`                  | `poa.chat.modules.kdr` |
| `[playtime]`             | `poa.chat.modules.playtime` |
| `[ping]`                 | `poa.chat.modules.ping` |
| `[balance]`              | `poa.chat.modules.balance` |
| `[coords]`               | `poa.chat.modules.coords` |
| `[level]`                | `poa.chat.modules.level` |
| `[titles]`               | `poa.chat.modules.titles` |
| `[time since death]`     | `poa.chat.modules.timesincedeath` |
| `[statistic <stat>]`     | `poa.chat.modules.statistic` |
| `[killed <entity>]`      | `poa.chat.modules.killed` |
| `[killedby <entity>]`    | `poa.chat.modules.killedby` |
| `[placed <material>]`    | `poa.chat.modules.placed` |
| `[mined <material/all>]` | `poa.chat.modules.mined` |

Inventory-based modules open cached views using internal commands.

---

## Tags

### Claimable Tags
Tags can be claimed using special items containing the NBT key:


<img src="assets/gif.gif" width="400">
<img src="assets/color_page1.png" width="400">
<img src="assets/color_page2.png" width="400">

<img src="assets/img_1.png" width="400">
<img src="assets/img_2.png" width="400">


