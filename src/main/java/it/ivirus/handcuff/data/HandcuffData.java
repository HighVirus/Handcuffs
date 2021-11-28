package it.ivirus.handcuff.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HandcuffData {

    private final Set<UUID> playerCuff = new HashSet<>();
    private final Map<UUID, Long> cooldown = new HashMap<>();
    private final Map<Player, Player> targetIsDragged = new HashMap<>();  //<target, sender>

    @Getter(lazy = true)
    private static final HandcuffData instance = new HandcuffData();

    public boolean isHandCuffed(UUID uuid) {
        return playerCuff.contains(uuid);
    }

    public Player isDragging(Player player) {
        for (Map.Entry<Player, Player> entry : targetIsDragged.entrySet()) {
            if (entry.getValue().equals(player)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
