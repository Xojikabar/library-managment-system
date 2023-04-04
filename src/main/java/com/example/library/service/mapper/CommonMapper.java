package com.example.library.service.mapper;

import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface CommonMapper <D,E> {
   public D toDto(E e);
   public E toEntity(D d);

}
