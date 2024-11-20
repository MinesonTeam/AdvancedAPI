package kz.hxncus.mc.advancedapi.api.mapper;

import java.util.Optional;

public interface DataMapper<T> {
	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	Optional<T> find(T t);
}
