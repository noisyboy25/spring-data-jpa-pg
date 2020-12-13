package com.example.demo.page;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.springframework.data.domain.PageImpl;

@SuppressWarnings("all")
public class PageSerializer extends StdSerializer<PageImpl> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PageSerializer() {
        super(PageImpl.class);
    }

    @Override
    public void serialize(PageImpl value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("content");
        provider.defaultSerializeValue(value.getContent(), gen);
        gen.writeObjectField("pageable", value.getPageable());
        gen.writeBooleanField("last", value.isLast());
        gen.writeNumberField("totalPages", value.getTotalPages());
        gen.writeNumberField("totalElements", value.getTotalElements());
        gen.writeNumberField("size", value.getSize());
        gen.writeObjectField("sort", value.getSort());
        gen.writeBooleanField("first", value.isFirst());
        gen.writeNumberField("numberOfElements", value.getNumberOfElements());
        gen.writeNumberField("number", value.getNumber());
        gen.writeBooleanField("empty", value.isEmpty());
        gen.writeEndObject();

    }

}
