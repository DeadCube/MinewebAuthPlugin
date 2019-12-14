package fr.fir3rl.authplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin {
	public ArrayList<String> newPlayers = new ArrayList<String>();
	public ArrayList<Location> newPlayersLocations = new ArrayList<>();

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new AuthEvents(this), this);
		getCommand("login").setExecutor(new AuthCommands(this));
		getCommand("forcelogin").setExecutor(new AuthCommands(this));
		getCommand("unlock").setExecutor(new AuthCommands(this));
		getCommand("authversion").setExecutor(new AuthCommands(this));
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

	public String readString(String path) {
		return getConfig().getString(path).replaceAll("&", "§");
	}

	public List<String> readStrings(String path) {
		return (List<String>) getConfig().getStringList(path);
	}

}
