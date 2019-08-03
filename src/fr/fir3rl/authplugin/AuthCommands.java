package fr.fir3rl.authplugin;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.northenflo.auth.mineweb.AuthMineweb;

public class AuthCommands implements CommandExecutor {

	private AuthPlugin main;
	public AuthCommands(AuthPlugin main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(cmd.getName() == "login") {
				int pos = 0;
				if(args[0].isEmpty()) {
					player.kickPlayer("Mot de passe indéfini");
					return true;
				}
				
				AuthMineweb.setTypeConnection(2);
				AuthMineweb.setUsername(player.getName());
				AuthMineweb.setPassword(args[0]);
				AuthMineweb.setUrlRoot(main.getConfig().getString("url"));
				AuthMineweb.setDebug(main.getConfig().getBoolean("debug"));
				try {
					AuthMineweb.start();
				} catch (DataWrongException e) {
					// TODO Auto-generated catch block
					player.kickPlayer("Mauvais mot de passe");
				} catch (DataEmptyException e) {
					// TODO Auto-generated catch block
					player.kickPlayer("Mot de passe indéfini");
				} catch (ServerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(AuthMineweb.isConnected()) {
					while(!main.connectedPlayers.contains(player.getName())) {
						pos++;
					}
					main.connectedPlayers.remove(pos);
				}
				
			}
			
		} else {
			return true;
		}
		return false;
	}

}
