Project Home
============
Although the project is hosted on GitHub, additional information and a changelog can be found on [the Forums of Bukkit](http://forums.bukkit.org/threads/gen-banneditems-v1-2-banning-items-for-give-935.22447/).

Installation
============
Just put the .jar file and the folder with the
ignored-items.txt in your craftbukkit server's
plugin folder and (re)start the server.

Banning items
=============
You can get the correct data values for the items
from [The Minecraft Wiki](http://www.minecraftwiki.net/wiki/Data_values).

Make a newline-seperated list with only the id's
(numbers) of the item you want to ban.
Save the list as *ignored-items.txt* in a folder
called *BannedItems*. The folder should be in the same
folder of the .jar file.

Usage (in-game)
===============
The command can be used just the same way you would use the default `/give` command.
Alternately you can also use `/g`, `/i` or `/item` instead of `/give`

`/give <PlayerName> <ItemId> <Amount (optional)>` 
`/item <PlayerName> <ItemId> <Amount (optional)>` 
`/g <PlayerName> <ItemId> <Amount (optional)>` 
`/i <PlayerName> <ItemId> <Amount (optional)>`