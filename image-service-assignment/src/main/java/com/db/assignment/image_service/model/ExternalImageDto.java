package com.db.assignment.image_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalImageDto {

    private String s3ObjectUrl;
    private ImageRequestDto imageRequestDto;
    private String bucket;
    private ImageMetaData imageMetaData;
}
