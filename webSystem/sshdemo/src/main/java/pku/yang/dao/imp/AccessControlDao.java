package pku.yang.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import pku.yang.dao.IAccessControlDao;
import pku.yang.dao.IAccessControlRepository;
import pku.yang.dao.IStrategyRepository;
import pku.yang.model.AccessControl;
import pku.yang.model.Strategy;
import pku.yang.model.User;


/**
 * 访问控制Dao
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */
@Repository
public class AccessControlDao implements IAccessControlDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public IStrategyRepository strategyRepository;
	
	@Autowired
	public IAccessControlRepository accessControlRepository;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveStratege(Strategy strategy) {
		hibernateTemplate.saveOrUpdate(strategy);
	}
	
	@Override
	public void saveAccessControl(AccessControl accessControl) {
		hibernateTemplate.saveOrUpdate(accessControl);
	}
	
	public void saveAccessControlAndStrategy(AccessControl accessControl,Strategy strategy){
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(accessControl);
		session.saveOrUpdate(accessControl);
	}
	
	@SuppressWarnings("unchecked")
	public Strategy getByID(Integer id) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Strategy.class);
		criteria.add(Restrictions.eq("user_pid", id));
		List<Strategy> strategy = (List<Strategy>) hibernateTemplate.findByCriteria(criteria);
		if(strategy != null && strategy.size()>0){
			return strategy.get(0);
		}
		return null;
	}

	@Override
	public int deleteAccessControl(int accessControlIds) {
		Session session = sessionFactory.getCurrentSession();
		AccessControl accessControl = new AccessControl();
		accessControl.setAccessContorlId(accessControlIds);
		session.delete(accessControl);
	return 1;
	}

	@Override
	public int deleteStrategy(int strategyIds) {
		Session session = sessionFactory.getCurrentSession();
		Strategy strategy = new Strategy();
		strategy.setStrategyID(strategyIds);
		session.delete(strategy);
		return 1;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(String hql) {
		List<User> userList = new ArrayList<User>();
		try{
			userList = sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e){
			setErrorMsg("40004", "属性表达式有误，解析失败");
		}
		
		return userList;
	}
	
	private Map<String,String> errorMsg = new HashMap<String, String>();
	
	public Map<String,String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String code,String msg) {
		this.errorMsg.put(code, msg);
	}
	
	public void clear(){
		this.errorMsg.clear();
	}
}
