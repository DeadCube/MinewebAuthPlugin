package fr.fir3rl.authplugin;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AuthEvents implements Listener {

	private AuthPlugin main;

	public AuthEvents(AuthPlugin main) {
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		main.newPlayers.add(player.getName());
		player.sendMessage(main.readString("hintmessage"));
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				if (e.getMessage().toString().startsWith("/")) {

				} else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(main.readString("events.chatinteract"));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				if (e.getMessage().toString().contains("login")) {

				} else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(main.readString("events.othercommand"));
				}
			}
		}
	}

	public void onBlockBreak(BlockBreakEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(main.readString("events.breakblock"));
			}
		}
	}

	public void onBlockPlace(BlockPlaceEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(main.readString("events.placeblock"));
			}
		}
	}

}
