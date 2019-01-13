package rf.mat.mod;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.client.registry.*;
import rf.mat.mod.commands.*;

@Mod(modid = "matsimplemod", version = "1.1", acceptedMinecraftVersions = "[1.8.9]")
public class MatMod {
	
	private static MatMod instance;
	public static final String nokeymsg = "Please set your api key!";
	public static String apikey = nokeymsg;
	private final Minecraft mc;
	public boolean onHypixel;
	
	//minigame
	private KeyBinding pbw3v3;
	private KeyBinding pbw2s;
	private KeyBinding pswin;
	
	//random
	private KeyBinding sendInc;
	private KeyBinding excuse;
	
	public MatMod() {
        this.mc = Minecraft.getMinecraft();
        this.apikey = nokeymsg;
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		MatMod.instance = this;
		MinecraftForge.EVENT_BUS.register((Object)this);
		
		ClientCommandHandler.instance.registerCommand((ICommand)new BWStatsCMD());
		ClientCommandHandler.instance.registerCommand((ICommand)new MatConfigCMD());
		
		ClientRegistry.registerKeyBinding(this.pbw3v3 = new KeyBinding("Bedwars 3v3", 0, "MatMod"));
		ClientRegistry.registerKeyBinding(this.pbw2s = new KeyBinding("Bedwars Doubles", 0, "MatMod"));
		ClientRegistry.registerKeyBinding(this.pswin = new KeyBinding("Skywars Solo Insane", 0, "MatMod"));
		ClientRegistry.registerKeyBinding(this.sendInc = new KeyBinding("Send \"inc\" In Chat", 0, "MatMod"));
		ClientRegistry.registerKeyBinding(this.excuse = new KeyBinding("Sends random excuse", 0, "MatMod"));
		
 		MatMod.apikey = ConfigUtil.getApiKey();
    }
	
	@SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
        if (pbw3v3.isPressed() && onHypixel) {
        	sendChatMsg("/play bedwars_four_three");
        }
        if (pbw2s.isPressed() && onHypixel) {
        	sendChatMsg("/play bedwars_eight_two");
        }
        if (pswin.isPressed() && onHypixel) {
        	sendChatMsg("/play solo_insane");
        }
        if (sendInc.isPressed()) {
        	sendChatMsg("inc");
        }
        if (excuse.isPressed()) {
        	Random generator = new Random();
        	String[] excuses = { "i lagged", "WTF no kb", "wtf my fps went to 2 during that fight",
        			"if my fucking spacebar worked", "wtf it didnt sprint", "ffs i hate my internet" };
        	int i = generator.nextInt(excuses.length);
        	sendChatMsg(excuses[i]);
        }
    }
	
	public static MatMod getInstance() {
		return MatMod.instance;
	}
	
	public Minecraft getMinecraft() {
        return this.mc;
    }
	
	public void sendChatMsg(String msg) {
		this.getMinecraft().thePlayer.sendChatMessage(msg);
	}
	
	public static void setApiKey(String key) {
		apikey = key;
		ConfigUtil.setConfigFile();
	}

	
	@SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.onHypixel = (!this.mc.isSingleplayer() && event.manager.getRemoteAddress().toString().toLowerCase().contains("hypixel.net"));
    }
}
