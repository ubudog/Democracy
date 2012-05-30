package net.ubudog.democracy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Democracy extends JavaPlugin {
	
	Logger log; 
	String pollStatus = "closed"; 
	String pollName; 
	String votingOptions1; 
	String votingOptions2; 
	String votingResultsYay;
	String votingResultsNay; 
	String votingSelection; 
	int yayVotes = 0; 
	int nayVotes = 0; 
	String vote; 
	
	public void onEnable() { 
		log = this.getLogger(); 
		log.info("[Democracy] Democracy has been enabled.");
		
		// Create the plugin dir
		try { 
			File plugindir = new File("Democracy"); 
			if (plugindir.exists() == false) { 
				log.info("[Democracy] Running first-run setup..."); 
				plugindir.mkdir();
				
				File votesdir = new File("votes"); 
			}
		} catch (Exception e) { 
			
		}
	}
	
	public void onDisable() { 
		log.info("[Democracy] Democracy has been disabled."); 
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("vote")) {
				vote = args[1]; 
				if (args.length < 2) { 
					sender.sendMessage(ChatColor.RED + "Please define the name of the poll, and your options."); 
				} else { 
					if (pollStatus.toString().equals("open")) {  
						sender.sendMessage(ChatColor.GREEN + "Vote cast for " + args[1] + ".");
						log.info("Player " + sender.getName() + " has voted for " + args[1] + "."); 
						
						if (vote.toString().equalsIgnoreCase("yay")) { 
							yayVotes++; 
						}
						
						if (vote.toString().equalsIgnoreCase("nay")) { 
							nayVotes++; 
						}				
					} else if (pollStatus.toString().equals("closed")) { 
						sender.sendMessage(ChatColor.RED + "No open polls!");
					}
				}
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("startpoll")) { 
			if (args.length < 2) { 
				sender.sendMessage("Please state the name of the poll as an argument."); 
				sender.sendMessage("Example: /startpoll ExamplePoll OptionOne OptionTwo"); 
			} else {  
				pollName = args[0]; 
				votingOptions1 = args[1]; 
				votingOptions2 = args[2]; 
				pollStatus = "open"; 
				
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Democracy] " + ChatColor.WHITE + "New poll started by " + sender.getName() + ", poll name is " + pollName + ".");
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "[Democracy] " + ChatColor.WHITE + "Options for voting are: " + votingOptions1 + ", " + votingOptions2); 
				Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Democracy] " + ChatColor.WHITE + "To vote, type /vote <pollname> <option>"); 
			}
			return true; 
		}
		
		if (cmd.getName().equalsIgnoreCase("stoppoll")) { 
			if (args.length < 1) { 
				sender.sendMessage("Please specify the name of the poll you'd like to close.");
				sender.sendMessage("Example: /stoppoll examplepoll"); 
			} else { 
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Democracy] " + ChatColor.WHITE + "Poll '" + args[0] + "' stopped!");
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Democracy] " + ChatColor.BLUE + "-- Results of Poll --"); 
				Bukkit.getServer().broadcastMessage("Yay Votes (Option 1): " + yayVotes); 
				Bukkit.getServer().broadcastMessage("Nay Votes (Option 2): " + nayVotes); 
				
				if (yayVotes > nayVotes) { 
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Democracy] " + ChatColor.WHITE + "Yays have it."); 
				}
				
				if (nayVotes > yayVotes) { 
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[Democracy] " + ChatColor.WHITE + "Nays have it."); 
				}
				
				pollStatus = "closed"; 
				yayVotes = 0; 
				nayVotes = 0; 
				
				// Write votes to file
				try {
					FileWriter fstream = new FileWriter("results-vote-" + pollName + ".txt");
					BufferedWriter out = new BufferedWriter(fstream);
					out.write("-- Voting Results --");
					out.write("Yay Votes: " + yayVotes); 
					out.write("\n Nay Votes: " + nayVotes); 
					
					if (yayVotes > nayVotes) { 
						out.write("\n Yays won."); 
					}
					
					if (nayVotes > yayVotes) { 
						out.write("\n Nays won."); 
					}
					
					out.close();
				} catch (Exception e) { 
					log.info("Exception occured."); 
				}
		
		if (cmd.getName().equalsIgnoreCase("pollstatus")) { 
			sender.sendMessage("-- Current Voting Status --"); 
			sender.sendMessage("Poll Status: " + pollStatus); 
			sender.sendMessage("Current poll: " + pollName);
			sender.sendMessage("Yay Votes: " + yayVotes); 
			sender.sendMessage("Nay Votes: " + nayVotes); 
		}
		
		if (cmd.getName().equalsIgnoreCase("democracy")) { 
			sender.sendMessage("Version 0.1"); 
			sender.sendMessage("Created by ubudog (http://ubudog.net)"); 
			sender.sendMessage("-- Available Commands --"); 
			sender.sendMessage("/vote <pollname> <option>");
			sender.sendMessage("/pollstatus <pollname>");
			sender.sendMessage("/startpoll pollname option1 option2");
			sender.sendMessage("/stoppoll pollname"); 
			
			sender.sendMessage("On GitHub at: http://github.com/ubudog"); 
				}
			}
		}
		return false;
	}
}