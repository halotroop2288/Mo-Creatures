package drzhark.mocreatures.command;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMoCSpawn extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List<String> aliases = new ArrayList<String>();

    static {
        commands.add("/mocspawn <horse|wyvern> <int>");
        aliases.add("mocspawn");
        //tabCompletionStrings.add("moctp");
    }

    @Override
    public String getCommandName() {
        return "mocspawn";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.mocspawn.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] stringArray) {
        if (stringArray.length == 2) {
            String entityType = stringArray[0];
            int type = 0;
            try {
                type = Integer.parseInt(stringArray[1]);
            } catch (NumberFormatException e) {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE
                        + "The spawn type " + type + " for " + entityType + " is not a valid type."));
                return;
            }

            String playername = par1ICommandSender.getName();
            EntityPlayerMP player =
                    FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerByUsername(playername);
            MoCEntityTameableAnimal specialEntity = null;
            if (entityType.equalsIgnoreCase("horse")) {
                specialEntity = new MoCEntityHorse(player.worldObj);
                specialEntity.setAdult(true);
            } else if (entityType.equalsIgnoreCase("wyvern")) {
                specialEntity = new MoCEntityWyvern(player.worldObj);
                specialEntity.setAdult(false);
            } else if (entityType.equalsIgnoreCase("wyvernghost")) {
                specialEntity = new MoCEntityWyvern(player.worldObj);
                specialEntity.setAdult(false);
                ((MoCEntityWyvern)specialEntity).setIsGhost(true);
            } else {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE
                        + "The entity spawn type " + entityType + " is not a valid type."));
                return;
            }
            double dist = 3D;
            double newPosY = player.posY;
            double newPosX = player.posX - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = player.posZ - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            specialEntity.setPosition(newPosX, newPosY, newPosZ);
            specialEntity.setTamed(true);
            specialEntity.setOwner("NoOwner");
            specialEntity.setPetName("Rename_Me");
            specialEntity.setType(type);

            if ((entityType.equalsIgnoreCase("horse") && (type < 1 || type > 67))
                    || (entityType.equalsIgnoreCase("wyvern") && (type < 1 || type > 12))) {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE
                        + "The spawn type " + type + " is not a valid type."));
                return;
            }
            player.worldObj.spawnEntityInWorld(specialEntity);
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(specialEntity.getEntityId()),
                    new TargetPoint(player.worldObj.provider.getDimensionId(), player.posX, player.posY, player.posZ, 64));
            MoCTools.playCustomSound(specialEntity, "appearmagic", player.worldObj);
        } else {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE
                    + "Invalid spawn parameters entered."));
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given
     * ICommandSender.
     */
    protected List<String> getSortedPossibleCommands(ICommandSender par1ICommandSender) {
        Collections.sort(CommandMoCSpawn.commands);
        return CommandMoCSpawn.commands;
    }

    public void sendCommandHelp(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentTranslation("\u00a72Listing MoCreatures commands"));
        for (int i = 0; i < commands.size(); i++) {
            sender.addChatMessage(new ChatComponentTranslation(commands.get(i)));
        }
    }
}
