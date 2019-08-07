package fr.fir3rl.authplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.minecraft.util.org.apache.commons.codec.digest.DigestUtils;

public class AuthCommands implements CommandExecutor {

	private AuthPlugin main;

	public AuthCommands(AuthPlugin main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		// TODO Auto-generated method stub
		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("login")) {

				if (args.length == 0) {
					player.kickPlayer(main.readString("commands.nopass"));
				}
				if (main.newPlayers.contains(player.getName()))
					;
				String password = DigestUtils.sha256Hex(args[0]);
				String json;
				try {
					json = JsonUtils.readUrl(main.readString("url") + "/auth/getDataLauncher?username="+ player.getName() + "&password=" + password);
					// TODO Auto-generated catch block
					if (json.startsWith("{")) {
						main.newPlayers.remove(player.getName());
						player.sendMessage(main.readString("commands.connected"));
						for (String elem : main.readStrings("joinmessage")) {
							Bukkit.broadcastMessage(elem.replaceAll("&", "§").replaceAll("%PLAYER%", player.getName()).replaceAll("%SERVER%", main.getServer().getName().replaceAll("%PLAYERS%", "" + this.main.getServer().getOnlinePlayers().length).replaceAll("%MAXPLAYERS%", String.valueOf(this.main.getServer().getMaxPlayers()))));
						}
					} else if (json.toString().contains("error_password")) {
						player.kickPlayer(main.readString("commands.wrongpass"));
					} else {
						player.kickPlayer("Erreur inconnue");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					player.kickPlayer("Erreur interne");
					e.printStackTrace();
				}

			}

		} else {

		}
		return false;
	}

}
