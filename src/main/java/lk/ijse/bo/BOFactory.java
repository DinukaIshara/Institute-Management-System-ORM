package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory == null)?  boFactory =  new BOFactory():boFactory;

    }

    public enum BOTypes{
        Student, Programs,Registration, Users,Payment
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case Student:
                return new StudentBOImpl();

            case Programs:
                return  new ProgramBOImpl();

            case Registration:
                return new RegistrationBOImpl();
            case Users:
                return new UserBOImpl();
            case Payment:
                return new PaymentBOImpl();


            default:
                return null;
        }
    }
}
