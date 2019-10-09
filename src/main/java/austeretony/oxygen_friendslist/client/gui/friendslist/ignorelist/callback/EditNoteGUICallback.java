package austeretony.oxygen_friendslist.client.gui.friendslist.ignorelist.callback;

import austeretony.alternateui.screen.callback.AbstractGUICallback;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.gui.elements.OxygenCallbackGUIFiller;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIButton;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIText;
import austeretony.oxygen_core.client.gui.elements.OxygenGUITextField;
import austeretony.oxygen_core.client.gui.settings.GUISettings;
import austeretony.oxygen_friendslist.client.FriendsListManagerClient;
import austeretony.oxygen_friendslist.client.gui.friendslist.FriendsListGUIScreen;
import austeretony.oxygen_friendslist.client.gui.friendslist.IgnoreListGUISection;
import austeretony.oxygen_friendslist.common.ListEntry;

public class EditNoteGUICallback extends AbstractGUICallback {

    private final FriendsListGUIScreen screen;

    private final IgnoreListGUISection section; 

    private OxygenGUITextField noteField;

    private OxygenGUIButton confirmButton, cancelButton;

    public EditNoteGUICallback(FriendsListGUIScreen screen, IgnoreListGUISection section, int width, int height) {
        super(screen, section, width, height);
        this.screen = screen;
        this.section = section;
    }

    @Override
    public void init() {
        this.addElement(new OxygenCallbackGUIFiller(0, 0, this.getWidth(), this.getHeight()));
        this.addElement(new OxygenGUIText(4, 5, ClientReference.localize("oxygen_friendslist.gui.friendslist.callback.editNote"), GUISettings.get().getTextScale(), GUISettings.get().getEnabledTextColor()));   
        this.addElement(new OxygenGUIText(6, 18, ClientReference.localize("oxygen.gui.note"), GUISettings.get().getSubTextScale(), GUISettings.get().getEnabledTextColor()));      

        this.addElement(this.noteField = new OxygenGUITextField(6, 25, this.getWidth() - 12, 9, ListEntry.MAX_NOTE_LENGTH, "", 3, false, - 1L));

        this.addElement(this.confirmButton = new OxygenGUIButton(15, this.getHeight() - 12, 40, 10, ClientReference.localize("oxygen.gui.confirmButton")).disable());
        this.addElement(this.cancelButton = new OxygenGUIButton(this.getWidth() - 55, this.getHeight() - 12, 40, 10, ClientReference.localize("oxygen.gui.cancelButton")));
    }

    @Override
    protected void onOpen() {
        this.noteField.setText(this.section.getCurrentListEntry().getNote());
    }

    @Override
    protected void onClose() {
        this.noteField.reset();
        this.confirmButton.disable();
    }

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        boolean flag = super.keyTyped(typedChar, keyCode);   
        if (this.noteField.isDragged())
            this.confirmButton.setEnabled(!this.noteField.getTypedText().isEmpty());
        return flag;   
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {
        if (mouseButton == 0) { 
            if (element == this.cancelButton)
                this.close();
            else if (element == this.confirmButton) {
                FriendsListManagerClient.instance().getPlayerDataManager().editListEntryNoteSynced(
                        this.section.getCurrentListEntry().getPlayerUUID(), this.noteField.getTypedText());
                this.close();
            }
        }
    }
}
