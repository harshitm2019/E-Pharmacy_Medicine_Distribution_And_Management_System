package com.harshit.pharmacy.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.List;

@ConfigurationProperties(prefix = "file.prescription")
public record FileProperties(

        DataSize maxSize,
        List<String> allowedTypes


) {

    public FileProperties {

        if (allowedTypes != null) {
            allowedTypes = allowedTypes.stream()
                    .map(String::toLowerCase)
                    .toList();
        }
    }

    public long maxSizeBytes() {
        return maxSize != null ? maxSize.toBytes() : 0;
    }

    public boolean isAllowedType(String contentType) {
        if (contentType == null || contentType.isBlank())
            return false;

        String baseType = contentType.split(";")[0].trim().toLowerCase();
        return allowedTypes.contains(baseType);
    }

}
