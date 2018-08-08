/**
 * 
 */
package edu.coreUtil.genericDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

/**
 * @author eduardo
 *
 */
public abstract class ServicoAbstractGenericDaoImpl<T> implements GenericDao<T> {

	private Class<T> persistentClass;

	/**
	 * 
	 * M�todo respons�vel pela instancia��o e extra��o da classe persistente.
	 * 
	 */

	@SuppressWarnings("unchecked")
	public ServicoAbstractGenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * 
	 * M�todo respons�vel pela persist�ncia de uma inst�ncia da classe
	 * persistente.
	 * 
	 */

	public boolean grava(T t)
			throws EntityExistsException,IllegalArgumentException,TransactionRequiredException,PersistenceException{

		EntityManager em = PersistenceFactory.createEntityManager();

		try {

			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();

			return true;

		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			throw e;

		} catch (IllegalArgumentException e) {
			em.getTransaction().rollback();
			throw e;

		} catch (TransactionRequiredException e) {
			em.getTransaction().rollback();
			throw e;

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw e;

		} finally {

			em.close();
		}
	}

	/**
	 * 
	 * M�todo respons�vel pela atualiza��o de uma inst�ncia da classe
	 * persistente.
	 * 
	 */

	public boolean atualiza(T t) throws IllegalArgumentException,TransactionRequiredException{

		EntityManager em = PersistenceFactory.createEntityManager();

		try {

			em.getTransaction().begin();

			em.merge(t);

			em.getTransaction().commit();

			return true;

		} catch (IllegalArgumentException e) {

			em.getTransaction().rollback();

			throw e;

		} catch (TransactionRequiredException e) {

			em.getTransaction().rollback();

			throw e;

		} finally {

			em.close();

		}
	}

	/**
	 * 
	 * M�todo respons�vel pela remo��o de uma inst�ncia da classe persistente.
	 * 
	 */

	public boolean apagaRegistro(T t) throws IllegalArgumentException,TransactionRequiredException{

		EntityManager em = PersistenceFactory.createEntityManager();

		try {
			em.getTransaction().begin();

			em.remove(t);

			em.getTransaction().commit();

			return true;

		} catch (IllegalArgumentException e) {

			em.getTransaction().rollback();

			throw e;

		} catch (TransactionRequiredException e) {

			em.getTransaction().rollback();

			throw e;

		} finally {

			em.close();

		}
	}

	/**
	 * 
	 * M�todo respons�vel pela busca de uma inst�ncia da classe persistente pela
	 * chave-prim�ria.
	 * 
	 */

	public T buscaPeloId(int pk) throws IllegalArgumentException{

		EntityManager em = PersistenceFactory.createEntityManager();

		try {
			return em.find(this.persistentClass, pk);
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * M�todo respons�vel pela busca de uma inst�ncia da classe persistente pela
	 * chave-prim�ria.
	 * 
	 */

	public T buscaPeloIdString(String pk) throws IllegalArgumentException{

		EntityManager em = PersistenceFactory.createEntityManager();

		try {
			return em.find(this.persistentClass, pk);
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * M�todo respons�vel pela listagem de inst�ncias da classe persistente.
	 *
	 */

	public List<T> listaTodos(Class<T> classe){

		EntityManager em = PersistenceFactory.createEntityManager();

		return em.createQuery("select o from " + classe.getSimpleName() + " o").getResultList();
	}

	/**
	 * 
	 * Esse metodo � mais rapido que o Find Porque n�o traz a referencia dos
	 * outros objetos. Neste caso ele executa como se fosse select * from tabela
	 * campo = valor
	 * 
	 */

	private T getReference(int pk){

		EntityManager em = PersistenceFactory.createEntityManager();

		T t = em.getReference(this.persistentClass, pk);

		return t;
	}
}
