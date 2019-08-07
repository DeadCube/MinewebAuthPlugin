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

		if (cmd.getName().equalsIgnoreCase("login") && sender instanceof Player) {

			Player player = (Player) sender;

			if (args.length == 0) {
				player.kickPlayer(main.readString("commands.login.nopass"));
			}
			if (main.newPlayers.contains(player.getName())) {
				player.sendMessage(main.readString("commands.login.alreadyconnected"));
			}
			;
			String password = DigestUtils.sha256Hex(args[0]);
			String json;
			try {
				json = JsonUtils.readUrl(main.readString("url") + "/auth/getDataLauncher?username=" + player.getName()
						+ "&password=" + password);
				// TODO Auto-generated catch block
				if (json.startsWith("{")) {
					main.newPlayers.remove(player.getName());
					player.sendMessage(main.readString("commands.login.connected"));
					for (String elem : main.readStrings("joinmessage")) {
						Bukkit.broadcastMessage(
								elem.replaceAll("&", "§").replaceAll("%PLAYER%", player.getName())
										.replaceAll("%SERVER%", main.getServer().getName()
												.replaceAll("%PLAYERS%",
														String.valueOf(this.main.getServer().getOnlinePlayers().length))
												.replaceAll("%MAXPLAYERS%",
														String.valueOf(this.main.getServer().getMaxPlayers()))));
					}
				} else if (json.toString().contains("error_password")) {
					player.kickPlayer(main.readString("commands.login.wrongpass"));
				} else {
					player.kickPlayer("Erreur inconnue");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				player.kickPlayer("Erreur interne");
				e.printStackTrace();
			}

		}

		if (cmd.getName().equalsIgnoreCase("forcelogin")) {

			if (args.length != 0) {

				for (Player pl : main.getServer().getOnlinePlayers()) {
					if (pl.getName().equalsIgnoreCase(args[0])) {
						main.newPlayers.remove(args[0]);
						pl.sendMessage(main.readString("commands.forcelogin.success"));
						return false;
					}
				}
				sender.sendMessage(main.readString("commands.forcelogin.noplayer"));

			} else {
				sender.sendMessage(main.readString("commands.forcelogin.needarg"));
			}

		}
		return false;

	}

}
