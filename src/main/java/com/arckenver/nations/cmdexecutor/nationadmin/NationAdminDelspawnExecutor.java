package com.arckenver.nations.cmdexecutor.nationadmin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.arckenver.nations.DataHandler;
import com.arckenver.nations.LanguageHandler;
import com.arckenver.nations.Utils;
import com.arckenver.nations.object.Nation;

public class NationAdminDelspawnExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			if (!ctx.<String>getOne("nation").isPresent())
			{
				src.sendMessage(Text.of(TextColors.YELLOW, "/na delspawn <nation> <name>"));
				return CommandResult.success();
			}
			Nation nation = DataHandler.getNation(ctx.<String>getOne("nation").get());
			if (nation == null)
			{
				src.sendMessage(Text.of(TextColors.RED, LanguageHandler.CB));
				return CommandResult.success();
			}
			if (!ctx.<String>getOne("name").isPresent())
			{
				src.sendMessage(Text.builder()
						.append(Text.of(TextColors.AQUA, LanguageHandler.ER.split("\\{SPAWNLIST\\}")[0]))
						.append(Utils.formatNationSpawns(nation, TextColors.YELLOW, "delhome"))
						.append(Text.of(TextColors.AQUA, LanguageHandler.ER.split("\\{SPAWNLIST\\}")[1])).build());
				return CommandResult.success();
			}
			String spawnName = ctx.<String>getOne("name").get();
			if (nation.getSpawn(spawnName) == null)
			{
				src.sendMessage(Text.of(TextColors.RED, LanguageHandler.ES));
				return CommandResult.success();
			}
			nation.removeSpawn(spawnName);
			DataHandler.saveNation(nation.getUUID());
			src.sendMessage(Text.of(TextColors.AQUA, LanguageHandler.ET));
		}
		else
		{
			src.sendMessage(Text.of(TextColors.RED, LanguageHandler.CA));
		}
		return CommandResult.success();
	}
}
