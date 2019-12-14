package fr.fir3rl.authplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
		main.newPlayersLocations.add(player.getLocation());
		player.sendMessage(main.readString("hintmessage"));
		player.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("csz.world")), main.getConfig().getInt("csz.x"), main.getConfig().getInt("csz.y"), main.getConfig().getInt("csz.z")));
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
				boolean isOnAir = true;
				while(isOnAir) {
				Location playerloc = e.getPlayer().getLocation();
				Location aboveplayer = (Location) playerloc;
				aboveplayer.setY(aboveplayer.getY()-1);
				if(aboveplayer.getBlock().getType() == Material.AIR) {
					e.getPlayer().teleport(aboveplayer);
				} else {
					isOnAir = false;
				}
				}
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
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		for (String str : main.newPlayers) {
			if (str.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(main.readString("events.dropitem"));
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		int pos = main.newPlayers.lastIndexOf(e.getPlayer().getName());
		if(pos == -1) return;
		main.newPlayers.remove(pos);
		main.newPlayersLocations.remove(pos);
	}

}
