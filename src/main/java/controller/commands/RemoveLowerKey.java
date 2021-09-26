package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveLowerKey extends Command {

    public RemoveLowerKey() {
        description = "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
        name = "remove_lower_key";
        argType = new Class[]{Integer.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
//        Integer key = (int)(args[1]);
//        spaceMarineManager.spaceMarineList = spaceMarineManager.spaceMarineList.entrySet().stream()
//                .filter(a -> a.getKey() >= key)
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//        return null;

        DTO<String> dto = DTOFactory.getInstance().getDTO();
        if (this.args.length != 1) {
            throw new CommandExecutionFailed(String.format("Ti conch? Argumenti normal'nie peredai. nado: %d, ti dal: %d", 1, this.args.length));
        }
        try {
            int key = Integer.parseInt(this.args[0].toString());
            String s = spaceMarineService.removeLowerKey(key);
            dto.setData(s);
            dto.setStatus(DTOStatus.OK);
        } catch (Exception e) {
            throw new CommandExecutionFailed(e.getMessage());
        }
        return dto;
    }
}
