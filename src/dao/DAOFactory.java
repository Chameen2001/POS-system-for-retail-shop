package dao;

import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory = null;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory = daoFactory == null ? new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        CUSTOMERDAO,ITEMDAO,ORDERDAO,ORDERDETAILDAO,QUERYDAO;
    }

    public SuperDAO getDAOImpl(DAOType daoType){
        switch (daoType){
            case CUSTOMERDAO:
                return new CustomerDAOImpl();
            case ITEMDAO:
                return new ItemDAOImpl();
            case ORDERDAO:
                return new OrderDAOImpl();
            case ORDERDETAILDAO:
                return new OrderDetailDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
