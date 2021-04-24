package model;

import javax.xml.validation.SchemaFactoryConfigurationError;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class SpaceMarine {
    private final long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Rules @UserInput
    private final String name; //Поле не может быть null, Строка не может быть пустой
    @Rules @Complex @UserInput
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Rules(leftBorder = 0) @UserInput
    private long health; //Значение поля должно быть больше 0
    @Rules(nullable = true) @UserInput
    private AstartesCategory category; //Поле может быть null
    @Rules @UserInput
    private Weapon weaponType; //Поле не может быть null
    @Rules(nullable = true) @UserInput
    private MeleeWeapon meleeWeapon; //Поле может быть null
    @Rules @Complex @UserInput
    private Chapter chapter; //Поле не может быть null

    static private final AtomicInteger counter = new AtomicInteger(-1);

    public SpaceMarine() {
        id = counter.incrementAndGet();
        name = "mock" + getId();
        setCoordinates(new Coordinates());
        setCreationDate(LocalDate.now());
        setHealth(100 + getId());
        setCategory(AstartesCategory.SCOUT);
        setWeaponType(Weapon.BOLT_RIFLE);
        setMeleeWeapon(MeleeWeapon.CHAIN_SWORD);
        setChapter(new Chapter("xd", null));
    }

    public SpaceMarine(long id,
                       String name,
                       Coordinates coordinates,
                       LocalDate creationDate,
                       long health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) {

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(String name,
                       Coordinates coordinates,
                       long health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) {

        this.id = counter.incrementAndGet();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public static void setCounter(int value) {
        counter.set(value);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", category=" + category +
                ", weaponType=" + weaponType +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                '}';
    }
}
