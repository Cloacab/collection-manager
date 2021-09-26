package dto.processors;

import dto.DTO;

public interface Processor {
    public DTO<?> process(DTO<?> request);
}
