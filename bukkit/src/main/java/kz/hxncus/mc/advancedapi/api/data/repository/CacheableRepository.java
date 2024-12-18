package kz.hxncus.mc.advancedapi.api.data.repository;

/**
 * Интерфейс для репозиториев с поддержкой кэширования
 * @param <ID> Тип идентификатора
 * @param <T> Тип сущности
 */
public interface CacheableRepository<ID, T> extends Repository<ID, T> {
    /**
     * Очищает кэш
     */
    void clearCache();

    /**
     * Обновляет кэш из источника данных
     */
    void refreshCache();

    /**
     * Проверяет наличие сущности в кэше
     * @param id Идентификатор
     * @return true если сущность в кэше
     */
    boolean isCached(ID id);

    /**
     * Получает размер кэша
     * @return Размер кэша
     */
    int getCacheSize();
}
