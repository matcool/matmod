package rf.mat.mod.commands;

import net.minecraft.command.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import rf.mat.mod.MatMod;

public class ExampleCMD extends CommandBase {
	
	public String getCommandName() {
		return "example";
	}
	
	public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canSenderUseCommand(final ICommandSender sender) {
        return true;
    }

	public String getCommandUsage(ICommandSender sender) {
		return "/example";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
	}
	
	private void showMessage(final String message, final ICommandSender sender) {
        sender.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
}
