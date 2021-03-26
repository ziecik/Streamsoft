package pl.streamsoft.repository;

public interface Repository<T, ID> {

    public void add(T entity);

    public void remove(ID id);

    public T find(ID id);

    public void update(ID id, T entity);
}
