package kz.hxncus.mc.advancedapi.api.data.repository;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Абстрактная реализация репозитория
 */
@Getter
public abstract class AbstractRepository<ID, T> implements CacheableRepository<ID, T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    protected final Map<ID, T> storage = new ConcurrentHashMap<>();

    @Override
    public T save(@NonNull T entity) {
        try {
            ID id = getEntityId(entity);
            this.getStorage().put(id, entity);
            LOGGER.debug("Сохранена сущность с ID: {}", id);
            return entity;
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранении сущности", e);
            throw new RuntimeException("Не удалось сохранить сущность", e);
        }
    }

    // ... остальные методы из предыдущего примера ...

    @Override
    public void clearCache() {
        storage.clear();
        LOGGER.debug("Кэш очищен");
    }

    @Override
    public void refreshCache() {
        try {
            loadData();
            LOGGER.debug("Кэш обновлен");
        } catch (Exception e) {
            LOGGER.error("Ошибка при обновлении кэша", e);
            throw new RuntimeException("Не удалось обновить кэш", e);
        }
    }

    @Override
    public boolean isCached(ID id) {
        return storage.containsKey(id);
    }

    @Override
    public int getCacheSize() {
        return storage.size();
    }

    /**
     * Получает идентификатор сущности
     */
    protected abstract ID getEntityId(T entity);

    /**
     * Загружает данные в кэш
     */
    protected abstract void loadData();
}
