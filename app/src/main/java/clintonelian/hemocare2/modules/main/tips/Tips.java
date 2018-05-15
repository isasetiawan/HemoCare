package clintonelian.hemocare2.modules.main.tips;

/**
 * Created by Admin on 3/30/2018.
 * create the model class for employee using which we will be adding an arraylist of employees.
 */
public class Tips {
    private String tipsName;
    private String tipsSuggestion;


    public Tips(String tipsName, String tipsSuggestion) {
        this.tipsName = tipsName;
        this.tipsSuggestion = tipsSuggestion;

    }

    /*Getters and setters to access the private members*/
    public String getTipsName() {
        return tipsName;
    }

    public void setTipsName(String tipsName) {
        this.tipsName = tipsName;
    }

    public String getTipsSuggestion() {
        return tipsSuggestion;
    }

    public void setTipsSuggestion(String tipsSuggestion) {
        this.tipsSuggestion = tipsSuggestion;
    }


}