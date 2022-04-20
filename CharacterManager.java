import java.util.HashMap;
/**
 * Class Character Manager.
 *
 * This class is part of the James Bond application.
 *
 * This class manages the characters. That is to say, set their position in the map
 * and allow them to move aroud the map.
 *
 * Each character has a name, a description, its own inventory and a current room location.
 *
 * @author Aymen Berbache
 * @version 2021.11.28
 */
public class CharacterManager
{

    private final HashMap<Room,Character> charactersInRoom;
    private final HashMap<String,Character> charactersMap;

    public CharacterManager(){
        charactersInRoom = new HashMap<>();
        charactersMap = new HashMap<>();
    }

    /**
     * Set the character room.
     * @param room  Room containing the character.
     * @param character Character concerned.
     */
    public void setCharacterInRoom(Room room, Character character)
    {
        charactersMap.put(character.getName(),character);
        charactersInRoom.put(room,character);
        character.setCurrentRoom(room);

    }

    /**
     * @param characterName The nam of the character we want.
     * @return The character object.
     */
    public Character getCharacter(String characterName)
    {
        return charactersMap.get(characterName);
    }

    /**
     * Remove character from room.
     * @param room
     * @param character
     */
    public void removeCharacterFromRoom(Room room, Character character)
    {
        charactersInRoom.remove(room, character);
    }

    /**
     * Command that make the character moves randomly exit from exit.
     * @param characterToMove
     */
    public void moveRandomly(Character characterToMove)
    {
        if (!characterToMove.isKilled()) {
            // Create local variable that holds the random exit generated
            Room randomRoom = characterToMove.getCurrentRoom().generateRandomExit();
            removeCharacterFromRoom(characterToMove.getCurrentRoom(),characterToMove);
            setCharacterInRoom(randomRoom,characterToMove);
            System.out.println(characterToMove.getDescription()+" is " + characterToMove.getCurrentRoom().getShortDescription() + ".");
        }
    }
}
