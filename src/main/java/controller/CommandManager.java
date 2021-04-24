package controller;

import controller.commands.*;

public enum CommandManager {
    HELP(new Help(), "help : вывести справку по доступным командам"),
    INFO(new Info(), "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"),
    SHOW(new Show(), "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
    INSERT(new Insert(), "insert null {element} : добавить новый элемент с заданным ключом"),
    UPDATE(new Update(), "update id {element} : обновить значение элемента коллекции, id которого равен заданному"),
    REMOVE_KEY(new RemoveKey(), "remove_key null : удалить элемент из коллекции по его ключу"),
    CLEAR(new Clear(), "clear : очистить коллекцию"),
    SAVE(new Save(), "save : сохранить коллекцию в файл"),
    EXECUTE_SCRIPT(new ExecuteScript(), "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."),
    EXIT(new Exit(), "exit : завершить программу (без сохранения в файл)"),
    REPLCACE_IF_LOWER(new ReplaceIfLower(), "replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого"),
    REMOVE_GREATER_KEY(new RemoveGreaterKey(), "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный"),
    REMOVE_LOWER_KEY(new RemoveLowerKey(), "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный"),
    MIN_BY_CHAPTER(new MinByChapter(), "min_by_chapter : вывести любой объект из коллекции, значение поля chapter которого является минимальным"),
    FILTER_BY_WEAPON_TYPE(new FilterByWeaponType(), "filter_by_weapon_type weaponType : вывести элементы, значение поля weaponType которых равно заданному"),
    PRINT_FIELD_DESCENDING_MELEE_WEAPON(new PrintFieldDescendingMeleeWeapon(), "print_field_descending_melee_weapon : вывести значения поля meleeWeapon всех элементов в порядке убывания"),
    ;

    private final Command command;
    private final String description;

    CommandManager(Command command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Command getCommand() {
        return command;
    }
}
