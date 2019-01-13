package rf.mat.mod.commands;

import net.minecraft.command.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import rf.mat.mod.MatMod;

public class MatConfigCMD extends CommandBase {
	
	public String getCommandName() {
		return "matconfig";
	}
	
	public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canSenderUseCommand(final ICommandSender sender) {
        return true;
    }

	public String getCommandUsage(ICommandSender sender) {
		return "/matconfig (setkey)";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) throw new WrongUsageException("/matconfig (setkey)", new Object[0]);
		switch (args[0]) {
			case "setkey": {
				if (args.length == 1) {
					if (MatMod.apikey != null) {
						showMessage(EnumChatFormatting.GRAY + "Current Hypixel Api key: " + EnumChatFormatting.GOLD + MatMod.apikey,sender);
					}
					showMessage(EnumChatFormatting.GREEN + "To get a new api key go to hypixel and do /api.",sender);
					break;
				}
				MatMod.setApiKey(args[1]);
				showMessage(EnumChatFormatting.GRAY + "Api key has been changed to: " + EnumChatFormatting.GOLD + MatMod.apikey,sender);
				break;
			}
		}
	}
	
	private void showMessage(final String message, final ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
}
