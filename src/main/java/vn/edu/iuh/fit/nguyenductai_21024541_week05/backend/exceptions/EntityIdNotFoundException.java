package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions;

public class EntityIdNotFoundException extends  Exception{
    public EntityIdNotFoundException(String message){
        super("The entity id = " + message + " was not found!");
    }
}
