package com.jz.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jz.jd.bean.Master;

/**
 * 测试Hibernate三种状态
 * @author sunjj 2016-10-11
 *
 */
public class TestStutusDao extends BaseHibernateDAO {

	/**
	 * 测试临时状态到持久状态的转变
	 */
	public void testT2P()
	{
		Master master=new Master();
		//临时态
		master.setName("许老师");
		master.setSex(1);
		Session session=getSession();
		Transaction trans=getSession().beginTransaction();
		session.save(master);
		trans.commit();
	
		//持久态
		master.setSex(0);
		session.close();
		//游离态
		master.setName("vae");
		//临时态
		master.setId(1000);
	}
	/**
	 * 测试临时状态到持久状态后，更改属性提交。
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("格洛米");
		master.setSex(0);
		Session session=getSession();
		Transaction transaction=session.beginTransaction();
		session.save(master);
		master.setName("大白");
		transaction.commit();
		session.close();
	}
	/**
	 * 测试持久状态修改后提交
	 */
	public void testP2Update()
	{
	
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		
		//此处保存无意义
		session.save(master);
		session.save(master);
		
		master.setSex(0);
		master.setName("许嵩");
		trans.commit();
		session.close();
	}
	/**
	 * 将持久态转换为游离态后更新提交
	 * clear（对全部） 或 evict（对一个） 或 session关闭
	 */
	public void testP2D2Update()
	{
		Session session=getSession();
		//持久化状态
		Master master=(Master)session.get(Master.class, 4);
		//将持久对象转换为游离对象
		session.evict(master);
		Transaction trans=session.beginTransaction();
		//游离态
		master.setName("企鹅");
		trans.commit();
		session.close();
	}
	/**
	 * 将持久态转换为临时态
	 */
	public void testP2T()
	{
		Session session=getSession();
		//持久化状态
		Master master=(Master)session.get(Master.class, 3);
		Transaction trans=session.beginTransaction();
		session.delete(master);
		master.setName("aa");
		trans.commit();
		session.close();
	}
	/**
	 * 同步持久化对象
	 */
	public void testRefresh()
	{
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 2);
		System.out.println("before"+master.getName());
		Transaction trans=session.beginTransaction();
		trans.commit();
		session.refresh(master);//刷新
		
		System.out.println("after"+master.getName());
		session.close();
	}
	/**
	 * 将游离态转换成持久态
	 */
	public void testD2P()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=new Master();
		//游离态
		master.setId(4);
		master.setName("李钟硕");
		master.setSex(1);
		
		session.update(master);
		//持久
		String name=master.getName();
		trans.commit();
	
	}
	/**
	 * 持久状态修改id(不能改，报错)
	 */
	public void testP2EditId()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=(Master)session.get(Master.class, 2);
		master.setId(10);
		trans.commit();
		session.close();
	}
	/**
	 * 游离态转换为临时状态
	 */
	public void testD2S()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=(Master)session.get(Master.class, 4);
		//游离态
		master.setId(4);
		session.delete(master);
		trans.commit();
	}
	/**
	 * 测试重复的持久化对象(报错)
	 */
	public void testDuplicateP()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//持久态
		Master master=(Master)session.get(Master.class, 2);
		//临时态
		Master master2=new Master();
		//游离态
		master2.setId(2);
		master2.setName("许嵩");
		session.merge(master2);
		//持久态
//		master.setName("一个许嵩");
		master2.setName("二个许嵩");
		System.out.println(master);
		System.out.println(master2);
		trans.commit();
		session.close();
	}
	/**
	 * 消除重复的持久化对象
	 */
	public void testRemoveDupli()
	{
		
	}

	public static void main(String[] args) {
		
		TestStutusDao tsDao=new TestStutusDao();
		
//		tsDao.testT2P();
	
//		tsDao.testT2P2Update();
//		tsDao.testP2Update();
//		tsDao. testP2D2Update();
//		tsDao.testP2T();
//		tsDao.testRefresh();
//		tsDao.testD2P();
//		tsDao.testP2EditId();
//		tsDao.testD2S();
		tsDao.testDuplicateP();
		
	}
}
