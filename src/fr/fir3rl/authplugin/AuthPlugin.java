package fr.fir3rl.authplugin;

import java.util.ArrayList;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin {

	public ArrayList<String> connectedPlayers= new ArrayList<String>();
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new AuthEvents(this), this);
		getCommand("login").setExecutor(new AuthCommands(this));
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}
	
}
