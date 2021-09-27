package controller;

import model.Chapter;
import model.SpaceMarine;
import model.SpaceMarineManager;
import model.Weapon;
import view.UserInputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class SpaceMarineService {
    private final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private final CommandManager commandManager = CommandManager.getInstance();
    private static SpaceMarineService instance = null;

    private SpaceMarineService() {

    }

    public static SpaceMarineService getInstance() {
        SpaceMarineService current = instance;
        if (current == null) {
            synchronized (SpaceMarineService.class) {
                current = instance;
                if (current == null) {
                    instance = current = new SpaceMarineService();
                }
            }
        }
        return current;
    }

    public String clear() {
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        return "Collection cleared.";
    }

    public String executeScript(String fileName) {
        //TODO: add methods body.
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            UserInputManager.setFromScript(true);
            UserInputManager.setUserInputScanner(scanner);
            UserInputManager.startListening();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Cannot execute script";
        }

        return "Script executed.";
    }

    public String exit() {
        //TODO: must be implemented on client
        return "fuck you.";
    }

    public HashMap<Integer, SpaceMarine> filterByWeaponType(Weapon weapon) {
        HashMap<Integer, SpaceMarine> result = new HashMap<>();
        for (Map.Entry<Integer, SpaceMarine> x : spaceMarineManager.spaceMarineList.entrySet()) {
            if (x.getValue().getWeaponType() == weapon) {
                result.put(x.getKey(), x.getValue());
            }
        }
        return result;
    }

    public String help() {
        return commandManager.getCommandsDescription();
    }

    public String info() {
        return String.format("Collection class: %s\nCreation date: %s\nElements: %d\n",
                spaceMarineManager.spaceMarineList.getClass().getName(),
                SpaceMarineManager.getInitializationDate(),
                spaceMarineManager.spaceMarineList.size());

    }

    public String insert(int key, SpaceMarine value) {
        spaceMarineManager.spaceMarineList.put(key, value);
        return "Successfully inserted.";
    }

    public Map.Entry<Integer, SpaceMarine> minByChapter() {
        Optional<Map.Entry<Integer, SpaceMarine>> minS = spaceMarineManager.spaceMarineList.entrySet().stream()
                .min(Comparator.comparing(a -> a.getValue().getChapter().getName()));
        return minS.orElse(null);
    }

    public Map<Integer, SpaceMarine> printFieldDescendingMeleeWeapon() {
        HashMap<Integer, SpaceMarine> result = new LinkedHashMap<>();

        spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getValue().getMeleeWeapon() != null)
                .sequential()
                .sorted((a, b) -> b.getValue().getMeleeWeapon().getValue() - (a.getValue().getMeleeWeapon().getValue()))
                .forEachOrdered(a -> result.put(a.getKey(), a.getValue()));

        return result;
    }

    public String removeGreaterKey(int key) {
        spaceMarineManager.spaceMarineList = spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getKey() <= key)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        return "Something was removed, or not, idk.";
    }

    public String removeKey(int key) {
        spaceMarineManager.spaceMarineList.remove(key);
        return "Removed object with key: " + key;
    }

    public String removeLowerKey(int key) {
        spaceMarineManager.spaceMarineList = spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getKey() >= key)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        return "Removed objects with key less than: " + key;
    }

    public String replaceIfLower(int key, SpaceMarine spaceMarine) {
        String result = "Nothing was changed.";
        if (spaceMarineManager.spaceMarineList.get(key).getName().compareTo(spaceMarine.getName()) > 0) {
            spaceMarineManager.spaceMarineList.put(key, spaceMarine);
            result = "Object with key: " + key + " was updated.";
        }
        return result;
    }

    public String save() {
        boolean b = spaceMarineManager.writeToScv();
        if (b) return "Collection saved to the file";
        return "Some problem occurred while saving collection";
    }

    public Map<Integer, SpaceMarine> show() {
        return spaceMarineManager.spaceMarineList;
    }

    public String update(long id, SpaceMarine spaceMarine) {
        String result = "Unable to updated object with id: " + id;
        Integer key = null;
        for(Map.Entry<Integer, SpaceMarine> entry : spaceMarineManager.spaceMarineList.entrySet()) {
            if (entry.getValue().getId() == id) {
                key = entry.getKey();
                result = "Object with id: " + id + " was updated";
            }
        }
        spaceMarine.setId(id);
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
        return result;
    }
}
