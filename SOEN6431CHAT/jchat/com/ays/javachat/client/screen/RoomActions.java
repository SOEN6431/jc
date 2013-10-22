package com.ays.javachat.client.screen;


/**
 * Is using by Rooms class to notify screen about some events : closeRoomPressed, ignoreUserPressed, canCloseRoom *
 */
public interface RoomActions {
    /**
     * When someone closes chat-room, this event fires *
     */
    public void closeRoomPressed(String aRoomName);

    /**
     * This event fires when someone tryes to close room. If this func will return false, room will not closed *
     */
    public boolean canCloseRoom(String aRoomName);

    /**
     * Fires when someone pressed Ignore User button *
     */
    public void ignoreUserPressed(String aRoomName);
}
