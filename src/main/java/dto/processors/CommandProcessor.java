package dto.processors;

import controller.commands.Command;
import dto.DTO;

public interface CommandProcessor {
    Command process(DTO<?> dto);
}
