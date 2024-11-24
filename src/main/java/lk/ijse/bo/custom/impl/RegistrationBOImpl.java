package lk.ijse.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Registration);

    @Override
    public void placeRegister(List<RegistrationDTO> registrationDTOList) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        // boolean isUpdated =false;
        List<Registration> registrationList = new ArrayList<>();
        boolean isSaved = false;

        try{
            for (RegistrationDTO registrationDTO : registrationDTOList) {
                Student student = new Student();
                student.setS_id(registrationDTO.getSid());

                Program program = new Program();
                program.setPId(registrationDTO.getPid());

                Registration registration = new Registration(/*registrationDTO.getRid(),*/student,program,registrationDTO.getDate(),registrationDTO.getUpfrontpayment(),registrationDTO.getAmoutPaybale());
                registrationList.add(registration);

                isSaved = registrationDAO.saveRegistration(registrationList,session);

            }

            if (isSaved) {
                transaction.commit();
                new Alert(Alert.AlertType.CONFIRMATION,"transaction completed").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            transaction.rollback();
        }finally {
            session.close();
        }

    }

    @Override
    public int getCurrentRegistrationId() {
        return registrationDAO.getCurrentId();
    }

}