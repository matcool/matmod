package rf.mat.mod.commands;

import mat.requests.*;
import rf.mat.mod.commands.CommandUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.*;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import rf.mat.mod.MatMod;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BWStatsCMD extends CommandBase {
	
	public String getCommandName() {
		return "bwstats";
	}
	
	public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canSenderUseCommand(final ICommandSender sender) {
        return true;
    }

	public String getCommandUsage(ICommandSender sender) {
		return "/bwstats";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) {
            throw new WrongUsageException("/bwstats (Hypixel Player Name)", new Object[0]);
        } else {
        	new Thread(() -> {
        		String raw = BasicRequest.getWebsite("https://api.hypixel.net/player?key="+MatMod.apikey+"&name="+args[0]);
        		JsonObject json = (new JsonParser()).parse(raw).getAsJsonObject();
        		System.out.println(json.get("success").getAsBoolean());
        		if (json.get("success").getAsBoolean() == false) {
        			switch (json.get("cause").getAsString()) {  
        				case "Invalid API key!":
        				case "No \"key\" provided!": {
        					showInvalidKeyError(sender);
        					return;
        				}
        			}
        			showMessage(json.get("cause").getAsString(),sender);
        			showError("Error occured",sender);
        			return;
        		}
        		if (json.get("player").isJsonNull()) {
        			showError("Invalid player",sender);
        			return;
        		}
        		JsonObject ach = json.getAsJsonObject("player").getAsJsonObject("achievements");
        		JsonObject bw = json.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars");
        		String name = json.getAsJsonObject("player").get("displayname").getAsString();
        		
        		try {
	        		String stars = ach.get("bedwars_level").toString();
	        		String wins = ach.get("bedwars_wins").toString();
	        		int kills = bw.get("kills_bedwars").getAsInt();
	        		int deaths = bw.get("deaths_bedwars").getAsInt();
	        		showFormattedInfo(name,stars,wins,kills,deaths,EnumChatFormatting.AQUA,sender);
        		} catch (NullPointerException e) {
        			showError("oh noes error hmmm that player probably never played bedwwars",sender);
        		} catch (IllegalStateException e) {
        			showInvalidKeyError(sender);
        		}
        	}).start();
        }
		
	}

	private void showFormattedInfo(String name, String stars, String wins, int kills, int deaths, EnumChatFormatting color, ICommandSender sender) {
		String f = color+""+EnumChatFormatting.BOLD+name+EnumChatFormatting.GOLD+" Bedwars stats:\n";
		f += EnumChatFormatting.GOLD+stars+"\u2b50 stars  "+wins+" wins\n";
		if (deaths == 0) deaths = 1;
		f += EnumChatFormatting.GOLD+Integer.toString(kills)+" kills  "+String.format(java.util.Locale.US,"%.3f", kills/(float)deaths)+" kill/death ratio";
		showMessage(f,sender);
	}
	private void showMessage(final String message, final ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
	
	private void showError(final String message, final ICommandSender sender) {
		showMessage(EnumChatFormatting.RED + message,sender);
	}
	
	private void showInvalidKeyError(final ICommandSender sender) {
		if (MatMod.apikey.contains(MatMod.nokeymsg)) {
			showError("Please set your api key by doing the "+EnumChatFormatting.GOLD+"/api"+EnumChatFormatting.RED+" command on the hypixel server;",sender);
			showError("And the "+EnumChatFormatting.GOLD+"/matconfig setkey <apikey>"+EnumChatFormatting.RED+" command.",sender);
			return;
		}
		showError("Invalid API Key!",sender);
	}
	
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return (args.length == 1) ? CommandUtils.getSimilarStrings(CommandUtils.getTabUsernames(), args[args.length-1]) : null;
    }
}
