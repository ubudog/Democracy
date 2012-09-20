package net.ubudog.democracy;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Democracy extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	File dir = new File("plugins/Democracy");
	
	public void onEnable() { 
		if (!dir.exists()) { 
			dir.mkdir(); 
		}
	}
	
	public void onDisable() { 
		saveConfig(); 
	}
	
	public static String getFinalArg(final String[] args, final int start) {
		final StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("democracy")) { 
			if (sender == null) { 
				log.info("Democracy version 0.1"); 
			} else { 
				sender.sendMessage(ChatColor.GRAY + "Democracy version 0.1"); 
			}
			return true; 
		}
		
		if (cmd.getName().equalsIgnoreCase("startpoll")) { 
			if (args.length <2) { 
				sender.sendMessage(ChatColor.RED + "Not enough arguments!"); 
				sender.sendMessage(ChatColor.RED + "Example: /startpoll rain Do you like rain?"); 
			} else { 
				// Register the poll for later use
				getConfig().set("poll-" + args[0], getFinalArg(args, 1)); 
				saveConfig(); 
				
				String pollname = args[0]; 
				String reason = getFinalArg(args, 1); 
				
				// Broadcast the poll message
				sender.sendMessage(ChatColor.GRAY + "Poll started."); 
				Bukkit.getServer().broadcastMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Democracy" + ChatColor.WHITE + "] " + ChatColor.WHITE + "Player " + sender.getName() + " has started a poll entitled: " + pollname);
				Bukkit.getServer().broadcastMessage(ChatColor.WHITE + "His/her reason was: " + reason);
				Bukkit.getServer().broadcastMessage(ChatColor.WHITE + "To vote, type /vote " + pollname + " yay/nay, replacing yay or nay with your desired vote.");
			}
			
			return true; 
		}
		
		if (cmd.getName().equalsIgnoreCase("vote")) { 
			if (args.length <3) {
				sender.sendMessage(ChatColor.RED + "Too few arguments!"); 
			} else { 
				
			}
		}
		
		
		return false; 
	}

	
}
