package mx.com.endtoend.domain;

public interface AbstractFactory<T> {
	T createFactory(String factoryName);
}
