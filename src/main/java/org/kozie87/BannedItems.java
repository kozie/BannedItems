/**
 * BannedItems - v1.2
 * 
 * Simple plugin that overrides the default /give server command.
 * Only items not in the ignored-items.txt list are allowed to spawn
 * with the command.
 * 
 * This plugin also spawn items in the inventory instead of spawning
 * it in front of the user.
 * The requested amount of stack size is also respected and given as
 * requested.
 * 
 * No license this time ;) Have fun!
 * 
 * @author Kozie <flamefingers at gmail dot com>
 */

package org.kozie87;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BannedItems extends JavaPlugin {

	// Define the logger
	Logger log = Logger.getLogger("Minecraft");
	
	// Set local variables
	private ArrayList<String> ignoreList;
	private File folder;
	private String fileName = "ignored-items.txt";
	
	/**
	 * Function to run when the plugin gets enabled
	 * This function will initiate the list from the file.
	 */
	public void onEnable() {
		
		// Set default values
		folder = getDataFolder();
		ignoreList = new ArrayList<String>();
		
		// Read out the ignored items list, if possible
		loadIgnoreList();
		System.out.println("BannedItems has been loaded!");
	}
	
	/**
	 * Function to execute when the current plugin gets disabled
	 */
	public void onDisable() {
		
		// Run some interesting things here
	}
	
	/**
	 * Function to catch commands given by the player.
	 * This function will catch the /give function.
	 * @param sender The player that inits the command
	 * @param cmd The command object that the player requests
	 * @param commandLabel Just the label of the command, without the slash
	 * @param args Remaining arguments that were passed after the command
	 * @return boolean
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		// Get the server
		Server server = sender.getServer();
		
		// Catch the 'give' command
		if (cmd.getName().equalsIgnoreCase("give") && args.length > 1) {
			
			// Get the player and the value id the user is requesting
			Player player = server.getPlayer(args[0]);
			Player senderPlayer = (Player) sender;
			int itemId = Integer.parseInt(args[1]);
			
			// Check if the player was found
			if (player == null) {
				sender.sendMessage("The requested player is not connected.");
				return true;
			}
			
			// // Check if the item is allowed
			if (!isIgnored(itemId)) {
				
				// Build up the item stack
				ItemStack itemStack = new ItemStack(itemId);
				
				// Check if the stack needs to be incremented
				if (args.length > 2 && Integer.parseInt(args[2]) > 0) {
					itemStack.setAmount(Integer.parseInt(args[2]));
				} else {
					itemStack.setAmount(1);
				}
				
				// Eventually.. give the player what he wants
				// and notice both players about the success.
				player.getInventory().addItem(itemStack);
				sender.sendMessage("Item "+itemId+" given to "+player.getName());
				player.sendMessage(senderPlayer.getName()+" gave you item "+itemId);
				
				return true;
			} else {
				
				// Send message to the player
				sender.sendMessage("Sorry but that item is restricted");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Function that does the whole loading process of the list file.
	 * The file is stored as an array
	 * @return boolean
	 */
	private boolean loadIgnoreList() {
		
		try {
			// Create the folder if it does not exist
			if (!folder.exists()) {
				folder.mkdir();
			}
			
			// Open the list file
			File list = new File(folder.getAbsolutePath() + File.separator + fileName);
			if (list.exists()) {
				
				ignoreList.clear();
				BufferedReader reader = new BufferedReader(new FileReader(list));
				String line = reader.readLine();
				
				// Keep on reading the file until end is reached
				while (line != null) {
					ignoreList.add(line);
					line = reader.readLine();
				}
				
				// Close the reader
				reader.close();
				
				System.out.println("BannedItems: Item ignore list succesfully loaded");
			} else {
				
				System.out.println("BannedItems: Notice! no item ignore list exists.");
			}
		} catch (Exception e) {
			
			System.out.println("error: "+e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Function to check the existence of an item id
	 * in the ignore list
	 * @param itemId
	 * @return boolean
	 */
	private boolean isIgnored(int itemId) {
		
		for (String listedItemId : ignoreList) {
			if (itemId == Integer.parseInt(listedItemId)) {
				return true;
			}
		}
		
		return false;
	}
}