package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory = null;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        return boFactory=boFactory==null?new BOFactory():boFactory;
    }

    public enum BOType{
        ADD_NEW_CUSTOMER_BO,ADD_NEW_ITEM_BO,CUSTOMER_WISE_BO,EDIT_ITEM_DETAIL_BO,INCOME_REPORT_BO,MAIN_BO,MANAGEMENT_BO,MODIFY_ITEM_BO,
        MODIFY_ORDER_BO,MODIFY_ORDER_ITEM_BO,REMOVE_ITEM_BO,REMOVE_ORDER_BO;
    }

    public SuperBO getBOImpl(BOType boType){
        switch (boType){
            case ADD_NEW_CUSTOMER_BO:
                return new AddNewCustomerBOImpl();
            case ADD_NEW_ITEM_BO:
                return new AddNewItemBOImpl();
            case CUSTOMER_WISE_BO:
                return new CustomerWiseBOImpl();
            case EDIT_ITEM_DETAIL_BO:
                return new EditItemDetailBOImpl();
            case INCOME_REPORT_BO:
                return new IncomeReportBOImpl();
            case MAIN_BO:
                return new MainBOImpl();
            case MANAGEMENT_BO:
                return new ManagementBOImpl();
            case MODIFY_ITEM_BO:
                return new ModifyItemBOImpl();
            case MODIFY_ORDER_BO:
                return new ModifyOrderBOImpl();
            case MODIFY_ORDER_ITEM_BO:
                return new ModifyOrderItemBOImpl();
            case REMOVE_ITEM_BO:
                return new RemoveItemBOImpl();
            case REMOVE_ORDER_BO:
                return new RemoveOrderBOImpl();
            default:
                return null;
        }
    }
}
