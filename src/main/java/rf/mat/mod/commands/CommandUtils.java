package rf.mat.mod.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class CommandUtils {
	public static List getTabUsernames() {
		final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return Lists.newArrayList();
        }
        return player.sendQueue.getPlayerInfoMap().stream().map(netPlayerInfo -> netPlayerInfo.getGameProfile().getName()).collect(Collectors.toList());
	}
	
	public static List getSimilarStrings(List<String> strings,String toMatch) {
		ArrayList<String> match = new ArrayList<String>();
		for (String str : strings) {
			if (str.startsWith(toMatch)) {
				match.add(str);
			}
		}
		return match;
	}
}
