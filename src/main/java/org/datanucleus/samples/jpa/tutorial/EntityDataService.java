/**
 * Project Name:datanucleus-samples-jpa-tutorial
 * File Name:EntityDataService.java
 * Package Name:org.datanucleus.samples.jpa.tutorial
 * Date:2013-12-7下午3:17:23
 * Copyright (c) 2013, liuchang890726@gmail.com All Rights Reserved.
 *
 */
/**
 * Project Name:datanucleus-samples-jpa-tutorial
 * File Name:EntityDataService.java
 * Package Name:org.datanucleus.samples.jpa.tutorial
 * Date:2013-12-7下午3:17:23
 * Copyright (c) 2013, liuchang890726@gmail.com All Rights Reserved.
 *
 */

package org.datanucleus.samples.jpa.tutorial;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.datanucleus.util.NucleusLogger;

/**
 * ClassName:EntityDataService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2013-12-7 下午3:17:23 <br/>
 * @author   TomasLiu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * ClassName: EntityDataService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2013-12-7 下午3:17:23 <br/>
 * 
 * @author TomasLiu
 * @version
 * @since JDK 1.6
 */
public class EntityDataService {

	private EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction tx;

	//TODO:Tomas Liu: Add context.manager, read persistence.xml!

	public EntityDataService() {
		// Create an EntityManagerFactory for this "persistence-unit"
		// See the file "META-INF/persistence.xml"
		emf = Persistence.createEntityManagerFactory("Tutorial");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	public Boolean insertByMap(Class c, Map<String, Object> map) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		
		
		try {
			Object o = c.newInstance();
			
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry) iter.next();
				String key = entry.getKey().toLowerCase();
				key = key.substring(0, 1).toUpperCase() + key.substring(1);

				Object val = entry.getValue();
				if (key.contains(".")) {
					String[] ass = key.split("\\.");
					String entity = ass[0];
					Object id = ass[1];
					key = entity;
					
					
					entity = "org.datanucleus.samples.jpa.tutorial. " + entity;
					val = this.getById(Class.forName(entity), val);
					
					/*****Decouple!!!
					inv = (Inventory)val;
					if (inv.getProducts() == null)
						inv.setProducts(new HashSet<Product>());
					inv.getProducts().add((Product) o);
					*/
					//TODO
				}
				
				// System.out.println("set" + key);
				Method[] methods = c.getMethods();
				Method method = null;
				for (Method mm : methods) {
					if (mm.getName().equals("setInventory")) {
						System.out.println("EntityDataService.insertByMap()");
					}
					if (mm.getName().equals("set" + key)) {
						method = mm;
						break;
					}
				}
				// Class[] cArg = new Class[1];
				// cArg[0] = String.class;
				// Method method = (Method) c.getMethod("set" + key, cArg);
				//
				method.invoke(o, val);
				
				em.persist(o);
				/*
				if (inv != null)
					em.persist(inv);
				*/
				//TODO:Decouple
			}
			System.out.println("Object have been persisted");
		} catch (Exception e) {
			NucleusLogger.GENERAL.error(">> Exception persisting data", e);
			System.err.println("Error persisting data : " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
		emf.getCache().evictAll();
		System.out.println("");
		return true;
	}

	public Object updateByMap(Class c, Object id, Map<String, Object> m) {
		// Perform some update operations
		em = emf.createEntityManager();
		tx = em.getTransaction();

		Object o = null;
		try {

			tx.begin();
			System.out.println("Executing Update By Id");

			o = em.find(c, id);

			Iterator iter = m.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry) iter.next();
				String key = entry.getKey().toLowerCase();
				key = key.substring(0, 1).toUpperCase() + key.substring(1);
				Object val = entry.getValue();
				// System.out.println("set" + key);
				Method[] methods = c.getMethods();
				Method method = null;
				for (Method mm : methods) {
					if (mm.getName().equals("set" + key)) {
						method = mm;
						break;
					}
				}
				// Class[] cArg = new Class[1];
				// cArg[0] = String.class;
				// Method method = (Method) c.getMethod("set" + key, cArg);
				//
				method.invoke(o, val);
			}
			// em.close();

			tx.commit();
			return true;
		} catch (Exception e) {
			NucleusLogger.GENERAL.error(">> Exception querying data", e);
			System.err.println("Error Updating data : " + e.getMessage());

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
		System.out.println("");

		return o;
	}

	public Boolean delById(Class c, Object id) {
		// Perform some delete operations
		em = emf.createEntityManager();
		tx = em.getTransaction();

		try {

			tx.begin();
			System.out.println("Executing Delete By Id");
			Object o = em.find(c, id);
			em.remove(o);

			// em.close();

			tx.commit();
			return true;
		} catch (Exception e) {
			NucleusLogger.GENERAL.error(">> Exception deleting data", e);
			System.err.println("Error deleting data : " + e.getMessage());

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			// em.close();
		}
		System.out.println("");
		return false;
	}

	public Object getById(Class c, Object id) {
		// Perform some query operations
		em = emf.createEntityManager();
		tx = em.getTransaction();

		try {

			tx.begin();
			System.out.println("Executing Query By Id");

			Object o = em.find(c, id);
			// em.close();

			tx.commit();
			return o;
		} catch (Exception e) {
			NucleusLogger.GENERAL.error(">> Exception querying data", e);
			System.err.println("Error querying data : " + e.getMessage());

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			// em.close();
		}
		System.out.println("");
		return null;
	}
	
	
	public List getByCondition(Class c, Map<String, Object> map) {
		EntityManager em = emf.createEntityManager(); 
		EntityTransaction tx = em.getTransaction();
		List results = new ArrayList();
		String entityName = c.getName().substring(c.getName().lastIndexOf('.') + 1);
		
		try {
			tx.begin();
			String sql = "SELECT X FROM " + entityName + " X";
			//Tomas Liu: condition: no WHERE
			if(!map.entrySet().isEmpty()){
				sql+=" WHERE ";
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, Object> entry = (Map.Entry) iter.next();
					String key = entry.getKey().toLowerCase();
					Object val = entry.getValue();
					
					if (val instanceof String)
						sql += key + " = '" + val + "' AND ";
					else 
						sql += key + " = " + val + " AND ";
				}
			}
			
			//Tomas Liu: need to check index.
			int index=sql.lastIndexOf("AND");
			if(index!=-1){
				sql = sql.substring(0, index);
			}
			
			System.out.println("[debug] sql: " + sql);
			//Query q = em.createQuery("SELECT p FROM Product p WHERE p.price < 150.00 ORDER BY p.price");
			Query q = em.createQuery(sql);
			tx.commit(); 
			
			results = q.getResultList(); 
			Iterator iter1 = results.iterator();
			System.out.println("[debug] size of query results: " + results.size());
		} catch (Exception e) {
				NucleusLogger.GENERAL.error(">> Exception querying data", e);
				System.err.println("Error querying data : " + e.toString());
		} finally {
			if (tx.isActive()) { 
				tx.rollback(); 
			} 
			em.close();
		} 

		return results;
	}

	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
};
