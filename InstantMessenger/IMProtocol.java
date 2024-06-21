public class IMProtocol {
    private static final int WAITING = 0;
    private static final int IN_CONVERSATION = 1;

    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput; // declare the output

        if (state == WAITING) { // check the current state
            if (theInput != null) { // check if the input is not null
                theOutput = theInput; // set the input as the output to return
                state = IN_CONVERSATION; // change the state
            } else { // if the input is null then give the default prompt
                theOutput = "Type a message (say Bye to end).";
            }
        } else { // if the state is IN_CONVERSATION
            theOutput = theInput;
            state = WAITING; // change the state back to waiting
        }

        return theOutput; // return the output response
    }

}
