package model;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpaceMarineManager {
    private static SpaceMarineManager instance = null;
    private static String fileName;
    private static LocalDate initializationDate = null;
    private static final String header = "key,id,name,coordinates_x,coordinates_y,creationDate,health,category,weaponType,meleeWeapon,chapter_name,chapter_world";

    public LinkedHashMap<Integer, SpaceMarine> spaceMarineList = new LinkedHashMap<>();

    private SpaceMarineManager () {

    }

    public static SpaceMarineManager getInstance() {
        if (initializationDate == null) {
            initializationDate = LocalDate.now();
        }
        if (instance == null) {
            instance = new SpaceMarineManager();
        }
        return instance;
    }

    public static LocalDate getInitializationDate() {
        return initializationDate;
    }

    public static void setFileName(String fileName){
        SpaceMarineManager.fileName = fileName;
    }

    public void readFromCsv() {
        File file = new File(fileName);

        try(CSVReader reader = new CSVReader(new FileReader(fileName), ',', '"', 1)) {

            String [] nextLine;
//            SpaceMarine class's fields:
            Integer key;
            long id;
            long maxId = 0;
            String name;
            long coordinates_x;
            float coordinates_y;
            LocalDate creationDate;
            long health;
            AstartesCategory category;
            Weapon weaponType;
            MeleeWeapon meleeWeapon;
            String chapter_name;
            String chapter_world;

            SpaceMarine spaceMarine;
            Coordinates coordinates;
            Chapter chapter;

            while ((nextLine = reader.readNext()) != null) {

                key = Integer.valueOf(nextLine[0]);
                id = Long.parseLong(nextLine[1]);
                maxId = Math.max(id, maxId);
                name = nextLine[2];
                coordinates_x = Long.parseLong(nextLine[3]);
                coordinates_y = Float.parseFloat(nextLine[4]);
                creationDate = LocalDate.parse(nextLine[5]);
                health = Long.parseLong(nextLine[6]);
                category = nextLine[7].isEmpty() ? null : AstartesCategory.valueOf(nextLine[7]);
                weaponType = Weapon.valueOf(nextLine[8]);
                meleeWeapon = nextLine[9].isEmpty() ? null : MeleeWeapon.valueOf(nextLine[9]);
                chapter_name = nextLine[10];
                chapter_world = nextLine[11].isEmpty() ? null : nextLine[11];

                SpaceMarine.setCounter((int) maxId);

                coordinates = new Coordinates(coordinates_x, coordinates_y);
                chapter = new Chapter(chapter_name, chapter_world);
                spaceMarine = new SpaceMarine(id,
                        name,
                        coordinates,
                        creationDate,
                        health,
                        category,
                        weaponType,
                        meleeWeapon,
                        chapter);
                spaceMarineList.put(key, spaceMarine);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Unable to read file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToScv() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',', '"');
            writer.writeNext(header.split(","));

            for (Map.Entry<Integer, SpaceMarine> entry : spaceMarineList.entrySet()) {
                String [] result;

                Integer key = entry.getKey();
                SpaceMarine spaceMarine = entry.getValue();

                result = new String[]{String.valueOf(key),
                        String.valueOf(spaceMarine.getId()),
                        spaceMarine.getName(),
                        String.valueOf(spaceMarine.getCoordinates().getX()),
                        String.valueOf(spaceMarine.getCoordinates().getY()),
                        spaceMarine.getCreationDate().toString(),
                        String.valueOf(spaceMarine.getHealth()),
                        (spaceMarine.getCategory() == null ? "" : String.valueOf(spaceMarine.getCategory())),
                        String.valueOf(spaceMarine.getWeaponType()),
                        (spaceMarine.getMeleeWeapon() == null ? "" : String.valueOf(spaceMarine.getMeleeWeapon())),
                        spaceMarine.getChapter().getName(),
                        (spaceMarine.getChapter().getWorld() == null ? "" : spaceMarine.getChapter().getWorld())};

                writer.writeNext(result);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read file.");
        }
    }
}
