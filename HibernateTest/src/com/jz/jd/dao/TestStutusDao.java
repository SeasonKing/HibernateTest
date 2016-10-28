package com.jz.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jz.jd.bean.Master;

/**
 * ����Hibernate����״̬
 * @author sunjj 2016-10-11
 *
 */
public class TestStutusDao extends BaseHibernateDAO {

	/**
	 * ������ʱ״̬���־�״̬��ת��
	 */
	public void testT2P()
	{
		Master master=new Master();
		//��ʱ̬
		master.setName("����ʦ");
		master.setSex(1);
		Session session=getSession();
		Transaction trans=getSession().beginTransaction();
		session.save(master);
		trans.commit();
	
		//�־�̬
		master.setSex(0);
		session.close();
		//����̬
		master.setName("vae");
		//��ʱ̬
		master.setId(1000);
	}
	/**
	 * ������ʱ״̬���־�״̬�󣬸��������ύ��
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("������");
		master.setSex(0);
		Session session=getSession();
		Transaction transaction=session.beginTransaction();
		session.save(master);
		master.setName("���");
		transaction.commit();
		session.close();
	}
	/**
	 * ���Գ־�״̬�޸ĺ��ύ
	 */
	public void testP2Update()
	{
	
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		
		//�˴�����������
		session.save(master);
		session.save(master);
		
		master.setSex(0);
		master.setName("����");
		trans.commit();
		session.close();
	}
	/**
	 * ���־�̬ת��Ϊ����̬������ύ
	 * clear����ȫ���� �� evict����һ���� �� session�ر�
	 */
	public void testP2D2Update()
	{
		Session session=getSession();
		//�־û�״̬
		Master master=(Master)session.get(Master.class, 4);
		//���־ö���ת��Ϊ�������
		session.evict(master);
		Transaction trans=session.beginTransaction();
		//����̬
		master.setName("���");
		trans.commit();
		session.close();
	}
	/**
	 * ���־�̬ת��Ϊ��ʱ̬
	 */
	public void testP2T()
	{
		Session session=getSession();
		//�־û�״̬
		Master master=(Master)session.get(Master.class, 3);
		Transaction trans=session.beginTransaction();
		session.delete(master);
		master.setName("aa");
		trans.commit();
		session.close();
	}
	/**
	 * ͬ���־û�����
	 */
	public void testRefresh()
	{
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 2);
		System.out.println("before"+master.getName());
		Transaction trans=session.beginTransaction();
		trans.commit();
		session.refresh(master);//ˢ��
		
		System.out.println("after"+master.getName());
		session.close();
	}
	/**
	 * ������̬ת���ɳ־�̬
	 */
	public void testD2P()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=new Master();
		//����̬
		master.setId(4);
		master.setName("����˶");
		master.setSex(1);
		
		session.update(master);
		//�־�
		String name=master.getName();
		trans.commit();
	
	}
	/**
	 * �־�״̬�޸�id(���ܸģ�����)
	 */
	public void testP2EditId()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=(Master)session.get(Master.class, 2);
		master.setId(10);
		trans.commit();
		session.close();
	}
	/**
	 * ����̬ת��Ϊ��ʱ״̬
	 */
	public void testD2S()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=(Master)session.get(Master.class, 4);
		//����̬
		master.setId(4);
		session.delete(master);
		trans.commit();
	}
	/**
	 * �����ظ��ĳ־û�����(����)
	 */
	public void testDuplicateP()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//�־�̬
		Master master=(Master)session.get(Master.class, 2);
		//��ʱ̬
		Master master2=new Master();
		//����̬
		master2.setId(2);
		master2.setName("����");
		session.merge(master2);
		//�־�̬
//		master.setName("һ������");
		master2.setName("��������");
		System.out.println(master);
		System.out.println(master2);
		trans.commit();
		session.close();
	}
	/**
	 * �����ظ��ĳ־û�����
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
