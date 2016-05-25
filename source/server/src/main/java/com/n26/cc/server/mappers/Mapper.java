package com.n26.cc.server.mappers;

public interface Mapper<S,T> {

	T toDTO(S from);
	
	S toEntity(T from);
	
}
