package warzone.model;


/**
 * This class represents one advance order of the gameplay
 */
public class AdvanceOrder implements Order{

    /**
     * Override of excute
     */
    @Override
    public void execute() {

    }

    /**
     * Override of valid check
     * @return true if valid
     */
    @Override
    public boolean valid(){
        return false;
    }

    /**
     * override of print the order
     */
    @Override
    public void printOrder(){

    }
}
