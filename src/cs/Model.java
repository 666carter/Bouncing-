// some of the code is referenced by CS349 SAMPLE CODE:
// 1: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/09.graphics/java/transformations
// 2: https://git.uwaterloo.ca/cs349-public/1195/tree/master/code/10.mvc/hellomvc3
package src.cs;
import java.util.ArrayList;


public class Model {

    // the data in the model, just a counter
    private int counter;
    private boolean start = false;
    private boolean pause = false;
    private int state = 0;
    private boolean end = false;
    int V;
    int FPS;
    int speed;
    //private boolean gameover = false;
    Canvas canvass;
    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();

    // set the view observer
    public void addView(IView view) {
        views.add(view);
        // update the view to current state of the model
        //view.updateView(start, pause, state, canvass);
    }


    public int getCounterValue() {
        return counter;
    }

    public boolean getEndValue() {
        return end;
    }

    public void setEndValue(Boolean b){
        end = b;
    }

    public void stateHandler(int which, Canvas canvas) {
        if(which == 1) {
            start = true;
            canvas.run = true;
            canvas.start = true;
            state++;
        }else if(which == 2){
            pause = !pause;
            canvas.run = !canvas.run;
            state++;
        }else if(which == 3){
            counter++;
        }else if(which == 4){
            //reset all attributes
            start = false;
            pause = false;
            state = 0;
        }else if(which == 5){
            if(V == 1){
                speed = 5;
            }else if(V == 2){
                speed = 10;
            }else if(V == 3){
                speed = 15;
            }
        }
        if((which != 4) && (which != 3) && (which != 5)) {
            notifyObservers(start, pause, state, canvas);
        }
    }

    // notify the IView observer
    private void notifyObservers(boolean start, boolean pause, int state, Canvas canvas) {
        for (IView view : this.views) {
            view.updateView(start, pause, state, canvas);
        }
    }
}

