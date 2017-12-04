package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.scheduler.TaskType;
import org.alexdev.icarus.game.room.tasks.RollerTask;

import java.util.concurrent.TimeUnit;

public class RollerSpeedCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

    @Override
    public void addArguments() {
        this.arguments.add("seconds");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (player.getRoom() == null) {
                return;
            }

            Room room = player.getRoom();

            if (!room.hasRights(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
                return;
            }

            int speed = Integer.valueOf(args[0]);

            if (speed <= 0) {
                player.sendMessage("You cannot set a roller speed to 0 or lower!");
                return;
            }

            room.getScheduler().removeTask(RollerTask.class);
            room.getScheduler().scheduleEvent(speed, TimeUnit.SECONDS, TaskType.REPEAT, new RollerTask(room));
        }
    }
    

    @Override
    public String getDescription() {
        return "Sets the speed of errors by given seconds.";
    }
}
