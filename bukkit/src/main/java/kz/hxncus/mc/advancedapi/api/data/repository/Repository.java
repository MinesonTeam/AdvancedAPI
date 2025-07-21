package kz.hxncus.mc.advancedapi.api.data.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Базовый интерфейс для репозиториев
 * @param <ID> Тип идентификатора
 * @param <T>  Тип сущности
 */
public interface Repository<ID, T> {
    /**
     * Сохраняет сущность
     * @param entity Сущность для сохранения
     * @return Сохраненная сущность
     */
    T save(T entity);

    /**
     * Сохраняет коллекцию сущностей
     * @param entities Коллекция сущностей
     * @return Коллекция сохраненных сущностей
     */
    Collection<T> saveAll(Collection<T> entities);

    /**
     * Находит сущность по ID
     * @param id Идентификатор
     * @return Optional с найденной сущностью
     */
    Optional<T> findById(ID id);

    /**
     * Проверяет существование сущности по ID
     * @param id Идентификатор
     * @return true если сущность существует
     */
    boolean existsById(ID id);

    /**
     * Возвращает все сущности
     * @return Коллекция всех сущностей
     */
    Collection<T> findAll();

    /**
     * Возвращает количество сущностей
     * @return Количество сущностей
     */
    long count();

    /**
     * Удаляет сущность по ID
     * @param id Идентификатор
     */
    void deleteById(ID id);

    /**
     * Удаляет сущность
     * @param entity Сущность для удаления
     */
    void delete(T entity);

    /**
     * Удаляет все сущности
     */
    void deleteAll();
}
