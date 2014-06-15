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
  private int gender;
  private String id;
  private String label;

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

  public String getGenderInWords(){
    if(this.gender==0){
      return "Male";
    }else if(this.gender==1){
      return "Female";
    }else{
      return "Unspecified.";
    }
  }

  public int getGender(){
    return this.gender;
  }

  public void setGender(int gender){
    this.gender = gender;
  }

  public String toString(){
    return (this.id+" "+this.label+" "+this.getGenderInWords());
  }

}
