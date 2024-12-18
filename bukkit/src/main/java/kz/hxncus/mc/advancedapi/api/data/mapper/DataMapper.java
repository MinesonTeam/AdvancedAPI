package kz.hxncus.mc.advancedapi.api.data.mapper;

import com.google.common.base.Optional;

public interface DataMapper<T> {
	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	Optional<T> find(T t);
}
