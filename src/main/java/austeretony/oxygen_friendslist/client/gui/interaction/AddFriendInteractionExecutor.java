package austeretony.oxygen_friendslist.client.gui.interaction;

import java.util.UUID;

import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.interaction.InteractionMenuEntry;
import austeretony.oxygen_friendslist.client.FriendsListManagerClient;

public class AddFriendInteractionExecutor implements InteractionMenuEntry {

    @Override
    public String getName() {
        return "oxygen_friendslist.interaction.addFriend";
    }

    @Override
    public boolean isValid(UUID playerUUID) {
        return OxygenHelperClient.isPlayerAvailable(playerUUID) && !FriendsListManagerClient.instance().getPlayerDataContainer().haveEntryForUUID(playerUUID);
    }

    @Override
    public void execute(UUID playerUUID) {
        FriendsListManagerClient.instance().getPlayerDataManager().sendFriendRequestSynced(playerUUID);
    }
}
