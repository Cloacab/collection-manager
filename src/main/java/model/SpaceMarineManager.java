package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class SpaceMarineManager {
    private static SpaceMarineManager instance = null;
    private static LocalDate initializationDate = null;
    private static final String header = "key,id,name,coordinates_x,coordinates_y,creationDate,health,category,weaponType,meleeWeapon,chapter_name,chapter_world\n";

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

    public void readFromCsv(String fileName) {
        File file = new File(fileName);

        try(Scanner scanner = new Scanner(file)) {
            scanner.nextLine();

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

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] arguments = line.split(",", -1);

                key = Integer.valueOf(arguments[0]);
                id = Long.parseLong(arguments[1]);
                maxId = Math.max(id, maxId);
                name = arguments[2];
                coordinates_x = Long.parseLong(arguments[3]);
                coordinates_y = Float.parseFloat(arguments[4]);
                creationDate = LocalDate.parse(arguments[5]);
                health = Long.parseLong(arguments[6]);
                category = arguments[7].isEmpty() ? null : AstartesCategory.valueOf(arguments[7]);
                weaponType = Weapon.valueOf(arguments[8]);
                meleeWeapon = arguments[9].isEmpty() ? null : MeleeWeapon.valueOf(arguments[9]);
                chapter_name = arguments[10];
                chapter_world = arguments[11].isEmpty() ? null : arguments[11];

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
        }
    }

    public void writeToScv(String fileName) {
      try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
          BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);

          outputStream.write(header.getBytes());

          for (Map.Entry<Integer, SpaceMarine> entry : spaceMarineList.entrySet()) {
              String result  = "";
              byte[] resultBytes;

              Integer key = entry.getKey();
              SpaceMarine spaceMarine = entry.getValue();

              result += String.valueOf(key) + ","
                      + String.valueOf(spaceMarine.getId()) + ","
                      + spaceMarine.getName() + ","
                      + String.valueOf(spaceMarine.getCoordinates().getX()) + ","
                      + String.valueOf(spaceMarine.getCoordinates().getY()) + ","
                      + spaceMarine.getCreationDate().toString() + ","
                      + String.valueOf(spaceMarine.getHealth()) + ","
                      + (spaceMarine.getCategory() == null ? "," : spaceMarine.getCategory() + ",")
                      + spaceMarine.getWeaponType() + ","
                      + (spaceMarine.getMeleeWeapon() == null ? "," : spaceMarine.getMeleeWeapon() + ",")
                      + spaceMarine.getChapter().getName() + ","
                      + (spaceMarine.getChapter().getWorld() == null ? "" : spaceMarine.getChapter().getWorld())
                      + "\n";

              resultBytes = result.getBytes();
              outputStream.write(resultBytes);
          }

          outputStream.flush();
          outputStream.close();

      } catch (Exception e) {
          e.printStackTrace();
          System.out.println("Unable to read file.");
      }
    }
}
