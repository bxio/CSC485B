/*********************
** Bill Xiong       **
** V00737042        **
** CSC485B - A2     **
** Summer 2014      **
*********************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Node{
  private String id;
  private String label;
  private boolean state;


  public boolean getState(){
    return this.state;
  }

  public boolean setState(boolean newState){
    this.state = newState;
  }

  public String getLabel(){
    return this.label;
  }

  public void setLabel(String label){
    this.label = label;
  }

  public String getID(){
    return this.id;
  }

  public void setID(String id){
    this.id = id;
  }

  public String toString(){
    return (this.id+" "+this.label+" "+this.getGenderInWords());
  }

}
