package com.n26.cc.server.mappers;


public interface Mapper<K,S,T> {

	T toDTO(S from);
	
	S toEntity(final K id, T from);
	
}
