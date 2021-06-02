package dto.processors;

import dto.Packet;

import java.lang.reflect.Method;

public interface Processor {
    public Method process(Packet packet);
}
